const el = {
    toast: $("#success-toast"),
    addCategory: {
        alert: $("#add-category-alert"),
        name: $("#add-category-name"),
        button: $("#add-category-button")
    }
}

function getCategoryTextEl(categoryId) {
    return $(`#category-name-${categoryId}`);
}

function getCategoryButton(categoryId) {
    return $(`#category-button-${categoryId}`);
}

function getCategoryDangerAlert(categoryId) {
    return $(`#category-alert-${categoryId}`);
}

function getCategorySection(categoryId) {
    return $(`#category-section-${categoryId}`);
}

function startEdit(categoryId) {
    getCategoryTextEl(categoryId).removeAttr("disabled");
    const button = getCategoryButton(categoryId);
    button.text("Appliquer modifications");
    button.attr('onclick', `edit(${categoryId})`);
}

function edit(categoryId) {
    const button = getCategoryButton(categoryId);
    const alert = getCategoryDangerAlert(categoryId);
    const textEl = getCategoryTextEl(categoryId);
    if (!textEl.val())
        return showAlertEl(alert, "Veuillez préciser le nouveau nom de la catégorie");
    beforeRequest(button, alert);
    sendRequest({
        url: `/admin/categories/${categoryId}`,
        type: 'PATCH',
        data: textEl.val(),
        contentType: 'application/json',
        complete: () => button.removeAttr("disabled"),
        error: error => showAlertEl(alert, getErrorMessageFromStatus(error.status)),
        success() {
            successToast("La catégorie a été modifiée avec succès");
            button.text("Modifier");
            textEl.attr("disabled", true);
        }
    });
}

function getErrorMessageFromStatus(status) {
    if (status === 409)
        return "Le nom est déjà utilisé par une autre catégorie.";
    return "Une erreur s'est produite, veuillez réessayer plus tard.";
}

function remove(categoryId) {
    const button = getCategoryButton(categoryId);
    const alert = getCategoryDangerAlert(categoryId);
    beforeRequest(button, alert);
    sendRequest({
        url: `/admin/categories/${categoryId}`,
        type: 'DELETE',
        complete: () => button.removeAttr("disabled"),
        error: error => showAlertEl(alert, getErrorMessageFromStatus(error.status)),
        success: () => {
            successToast(el.toast, "La catégorie a été supprimée avec succès");
            getCategorySection(categoryId).remove();
        }
    });
}

function addCategory() {
    if (!el.addCategory.name.val())
        return showAlertEl(el.addCategory.alert, "Veuillez préciser le nom de la catégorie");
    console.log(el.addCategory.name.val())
    beforeRequest(el.addCategory.button, el.addCategory.alert);
    sendRequest({
        url: '/admin/categories',
        type: 'POST',
        contentType: 'application/json',
        data: el.addCategory.name.val(),
        complete: () => el.addCategory.button.removeAttr("disabled"),
        error: error => showAlertEl(el.addCategory.alert, getErrorMessageFromStatus(error.status)),
        success: () => location.reload()
    });
}
