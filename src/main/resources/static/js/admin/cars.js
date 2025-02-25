const toast = $("#success-toast");

const maxFileSizeEl = $("#max-file-size");
const maxFileSize = {
    value: maxFileSizeEl.attr("data-value"),
    toString: maxFileSizeEl.attr("data-string")
};

const addCarEl = {
    name: $("#add-car-name"),
    horsePower: $("#add-car-horse-power"),
    enginePower: $("#add-car-engine-power"),
    category: $("#add-car-category"),
    pictureEdit: $("#add-car-picture-edit"),
    picture: $("#add-car-picture"),
    alert: $("#add-car-alert"),
    submit: $("#add-car-submit")
};

function getCarEls(id) {
    const el = {
        name: $(`#car-name-${id}`),
        horsePower: $(`#car-horse-power-${id}`),
        enginePower: $(`#car-engine-power-${id}`),
        category: $(`#car-category-${id}`),
        edit: $(`#car-edit-${id}`),
        pictureEdit: $(`#car-picture-edit-${id}`),
        remove: $(`#car-remove-${id}`),
        alert: $(`#car-alert-${id}`),
        pictureZone: $(`#car-picture-zone-${id}`),
        pictureInput: $(`#car-picture-input-${id}`),
        picture: $(`#car-picture-${id}`)
    };
    el['buttons'] = el.edit.add(el.remove).add(el.pictureEdit);
    return el;
}

function getCarFields(id) {
    return $(`.car-field-${id}`);
}

function changeOnClickFunction(button, newFunction) {
   button.removeAttr("onclick");
   button.off("click");
   button.click(newFunction);
}

function startEdit(id) {
    const el = getCarEls(id);
    el.edit.text("Appliquer");
    changeOnClickFunction(el.edit, () => saveEdit(id, el))
    getCarFields(id).removeAttr("disabled");
}

function saveEdit(id, el) {
    if (isInvalid(el)) return;
    beforeRequest(el.buttons, el.alert);
    sendRequest({
        url: `/admin/cars/${id}`,
        type: 'PUT',
        data: JSON.stringify({
            name: el.name.val(),
            category: el.category.val(),
            enginePower: el.enginePower.val(),
            horsePower: el.horsePower.val()
        }),
        contentType: 'application/json',
        complete: () => el.buttons.removeAttr("disabled"),
        success(){
            successToast(toast, "La voiture a été modifiée avec succès !");
            changeOnClickFunction(el.edit, () => startEdit(id));
            stopEdit(id, el);
        },
        error: error => showAlertEl(el.alert, errorStatusToMessage(error.status))
    });
}

function stopEdit(id, el) {
    getCarFields(id).attr("disabled", true);
    el.edit.text("Modifier");
}

function errorStatusToMessage(status) {
    if (status === 404)
        return "La catégorie n'a pas été trouvée.";
    return "Une erreur s'est produite, veuillez réessayée plus tard.";
}

function isInvalid(el) {
    if (!el.name.val()) {
        showAlertEl(el.alert, "Veuillez définir un nom.");
        return true;
    }
    if (el.horsePower.val() <= 0) {
        showAlertEl(el.alert, "La puissance de chevaux doit être strictement positive.");
        return true;
    }
    if (el.enginePower.val() <= 0) {
        showAlertEl(el.alert, "La poussée doit être strictement positive.");
        return true;
    }
    if (!el.category.val()) {
        showAlertEl(el.alert, "Veuillez définir une catégorie.");
        return true;
    }
    return false;
}

function startEditPicture(id) {
    const el = getCarEls(id);
    el.pictureZone.css("display", "block");
    el.pictureEdit.text("Enregistrer la photo");
    changeOnClickFunction(el.pictureEdit, () => savePicture(id, el));
}

function isImageInvalid(input, alertEl) {
    if (!input.files || !input.files[0]) {
        showAlertEl(alertEl, "Veuillez ajouter une image.");
        return true;
    }
    const file = input.files[0];
    if (file.size > maxFileSize.value) {
        showAlertEl(alertEl, `La taille de l'image ne peut pas dépasser ${maxFileSize.toString} (taille: ${(file.size / 1_000_000).toFixed(2)}MB).`);
        return true;
    }
    return false;
}

function savePicture(id, el) {
    const input = el.pictureInput[0];
    if (isImageInvalid(input, el.alert)) return;
    beforeRequest(el.buttons, el.alert);
    const formData = new FormData();
    formData.append("picture", input.files[0]);
    sendRequest({
        url: `/admin/cars/${id}`,
        type: 'PATCH',
        data: formData,
        processData: false,
        contentType: false,
        complete: () => el.buttons.removeAttr("disabled"),
        success() {
            el.pictureEdit.text("Modifier la photo");
            successToast(toast, "La photo a été modifiée avec succès.");
            el.pictureZone.css("display", "none");
        },
        error: error => showAlertEl(el.alert, errorStatusToMessage(error.status))
    });
}

function onUploadPicture(id, input) {
    previewPicture(getCarEls(id).picture, input);
}

function previewPicture(imgEl, input) {
    if (!input.files || !input.files[0]) return;
    const reader = new FileReader();
    reader.onload = function (e) {
        imgEl.attr("src", e.target.result);
        imgEl.css("display", "block");
    };
    reader.readAsDataURL(input.files[0]);
}

function addCar() {
    if (isInvalid(addCarEl)) return;
    const input = addCarEl.pictureEdit[0];
    if (isImageInvalid(input, addCarEl.alert)) return;
    const formData = new FormData();
    formData.append("picture", input.files[0]);
    formData.append("car", new Blob([JSON.stringify({
        name: addCarEl.name.val(),
        category: addCarEl.category.val(),
        horsePower: addCarEl.horsePower.val(),
        enginePower: addCarEl.enginePower.val()
    })], {type: 'application/json'}));
    beforeRequest(addCarEl.submit, addCarEl.alert);
    sendRequest({
        url: '/admin/cars',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        complete: () => addCarEl.submit.removeAttr("disabled"),
        error: error => showAlertEl(addCarEl.alert, errorStatusToMessage(error.status)),
        success: () => location.reload()
    });
}

function remove(id) {
    const el = getCarEls(id);
    beforeRequest(el.buttons, el.alert);
    sendRequest({
        url: `/admin/cars/${id}`,
        type: 'DELETE',
        complete: () => el.buttons.removeAttr("disabled"),
        error: error => showAlertEl(el.alert, errorStatusToMessage(error.status)),
        success() {
            $(`#car-section-${id}`).remove();
            successToast(toast, "La voiture a été supprimée avec succès.");
        }
    });
}
