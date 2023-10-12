let isRegisterSuccessful = sessionStorage.getItem("register.successful");
if (isRegisterSuccessful !== undefined && isRegisterSuccessful === 'true') {
    $('#register-success-alert').show()
    sessionStorage.removeItem("register.successful")
}

$('#btn-login').click((e) => {
    e.preventDefault();
    const usernameSelector = $('#username');
    const passwordSelector = $('#password');
    const registerSuccessSelector = $('#register-success-alert');
    registerSuccessSelector.hide();
    const username = usernameSelector.val().trim();
    const password = passwordSelector.val().trim();

    const isUsernameValid = username !== undefined && username !== '';
    const isPasswordValid = password !== undefined && password !== '';

    if (!isUsernameValid) {
        usernameSelector.addClass("is-invalid")
    }
    if (!isPasswordValid) {
        passwordSelector.addClass("is-invalid")
    }
    if (isPasswordValid && isUsernameValid) {
        usernameSelector.removeClass("is-invalid")
        passwordSelector.removeClass("is-invalid")

        fetch('http://localhost:8080/login', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username: username, password: password })
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                $('#login-error-alert').show();
            }
        }).then(result => {
            console.log(result);
            sessionStorage.setItem("user.id", result.id);
            sessionStorage.setItem("user.firstname", result.firstname);
            sessionStorage.setItem("user.lastname", result.lastname);
            sessionStorage.setItem("user.type", result.userType);
            sessionStorage.setItem("user.token", result.jwt);
            window.location = "./index.html";
            document.cookie = "JSESSIONID=" + result.sessionId;
        }
        ).catch(error => {
            $('#login-error-alert').show();
        });
    }
})