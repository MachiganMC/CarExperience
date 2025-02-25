const el = {
    username: $("#username"),
    password: $("#password"),
    submitButton: $("[type=submit]"),
    alert: $(".alert")
};

function login() {
    el.submitButton.attr('disabled', true);
    el.alert.css('display', 'none');
    sendRequest({
        url: 'login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            username: el.username.val(),
            password: el.password.val()
        }),
        complete: () => el.submitButton.removeAttr('disabled'),
        error: error => onError(error.status),
        success: () => window.location = '/admin'
    })
}

function onError(status) {
    let message;
    switch (status) {
        case 400:
            message = "Nom d'utilisateur ou mot de passe incorrect.";
            break;
        case 429:
            message = "Vous avez effectué trop de tentatives, veuillez réessayer plus tard."
            break;
        default:
            message = "Une erreur s'est produite, veuillez réessayer plus tard";
    }
    el.alert.css('display', 'block');
    el.alert.text(message);
}
