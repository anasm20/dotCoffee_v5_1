let isRegisterSuccessful = localStorage.getItem("register.successful");
if (isRegisterSuccessful !== undefined && isRegisterSuccessful === 'true') {
    $('#register-success-alert').show()
    localStorage.removeItem("register.successful")
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
            localStorage.setItem("user.id", result.id);
            localStorage.setItem("user.firstname", result.firstname);
            localStorage.setItem("user.lastname", result.lastname);
            localStorage.setItem("user.type", result.userType);
            localStorage.setItem("user.token", result.jwt);
            window.location = "./index.html";
            document.cookie = "JSESSIONID=" + result.sessionId;
        }
        ).catch(error => {
            $('#login-error-alert').show();
        });
    }
})