$('#btn-register').click((e) => {
    e.preventDefault();

    const usernameSelector = $('#username');
    const passwordSelector = $('#password');
    const emailSelector = $('#email');
    const lastnameSelector = $('#lastname');
    const firstnameSelector = $('#firstname');
    const registerSuccessSelector = $('#register-success-alert');


    const username = $('#username').val().trim();
    const password = $('#password').val().trim();
    const email = $('#email').val().trim();
    const lastname = $('#lastname').val().trim();
    const firstname = $('#firstname').val().trim();

    const isUsernameValid = username !== undefined && username !== '';
    const isPasswordValid = password !== undefined && password !== '';
    const isEmailValid = email !== undefined && email !== '';
    const isLastnameValid = lastname !== undefined && lastname !== '';
    const isFirstnameValid = firstname !== undefined && firstname !== '';

    if(!isUsernameValid) {
        usernameSelector.addClass("is-invalid")
    } else {
        usernameSelector.removeClass("is-invalid")
    }
    if(!isPasswordValid) {
    } else {
        passwordSelector.removeClass("is-invalid")
    }
    if(!isEmailValid) {
        emailSelector.addClass("is-invalid")
    } else {
        emailSelector.removeClass("is-invalid")
    }
    if(!isLastnameValid) {
        lastnameSelector.addClass("is-invalid")
    } else {
        lastnameSelector.removeClass("is-invalid")
    }
    if(!isFirstnameValid) {
        firstnameSelector.addClass("is-invalid")
    } else {
        firstnameSelector.removeClass("is-invalid")
    }

    if(isUsernameValid && isPasswordValid &&
    isEmailValid && isLastnameValid && isFirstnameValid) {
        usernameSelector.removeClass("is-invalid")
        passwordSelector.removeClass("is-invalid")
        emailSelector.removeClass("is-invalid")
        lastnameSelector.removeClass("is-invalid")
        firstnameSelector.removeClass("is-invalid")

        $.ajax({
            url: 'http://localhost:8080/register',
            type: 'post',
            data: JSON.stringify(
                {
                    username: username,
                    password: password,
                    lastname: lastname,
                    firstname: firstname,
                    email: email
                }),
            contentType: 'application/json; charset=utf-8',
            success: function (result, status, xhr) {
                if(status === 'success'){
                    localStorage.setItem("register.successful", "true")
                    window.location = "./login.html";
                    $('#register-error-alert').hide()
                } else {
                    $('#register-error-alert').show();
                }
            },
            error: function (xhr, status, error) {
                $('#register-error-alert').show();
            }
        });
    }
})