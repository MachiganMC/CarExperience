function beforeRequest(button, alert) {
    alert.css("display", "none");
    button.attr("disabled", true);
}

function sendRequest(parameters) {
    parameters['beforeSend'] = request => request.setRequestHeader('X-XSRF-TOKEN', Cookies.get('XSRF-TOKEN'));
    $.ajax(parameters);
}

function showAlertEl(el, message) {
    el.text(message);
    el.css("display", "block");
}

function showAlert(selector, message) {
    showAlertEl($(selector), message);
}

function successToast(toast, message) {
    toast.find(".toast-body").text(message);
    toast.toast('show');
}
