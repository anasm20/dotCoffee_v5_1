$(document).ready(() => {
    /**
     * If the user is not an admin, redirect to index.html
     */
    if (sessionStorage.getItem("user.type") != "admin") {
        window.location.href = "index.html";
    }
})

$(document).ready(() => {
    /**
     * If the user is not an admin, redirect to index.html
     */
    $.ajax({
        url: "http://localhost:8080/admin/user/role",
        type: "GET",
        headers: {
            "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
        },
        success: function (result) {
            $("body").css("display", "block");
        },
        error: function (message) {
            window.location.href = "index.html";
        }
    });
    /*
    if (sessionStorage.getItem("user.type") != "admin") {
        window.location.href = "index.html";
    } else {
        // Zeige die Seite, da der Benutzer ein Administrator ist
        $("body").css("display", "block");
    }*/
});


$(document).ready(function () {
    /**
     * fetch products and add them to the table
     */
    $('#productTable').DataTable({
        "ajax": {
            "url": "http://localhost:8080/product",
            "headers": {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS, HEAD",
                "Access-Control-Allow-Headers": "Content-Type, Authorization, Accept, X-Requested-With, remember-me"
            },
            "dataSrc": ""
        },
        "columns": [
            { "data": "titel" },
            {
                "data": "description",
                "render": function (data, type, row, meta) {
                    return data.length > 20 ?
                        data.substr(0, 20) + '...' :
                        data;
                }
            },
            {
                "data": "imagePath",
                "render": function (data, type, row, meta) {
                    return '<img src="' + data + '" width="48" height="48" class="d-block mx-auto" />';
                }
            },
            { "data": "price" },
            { "data": "category.name" },
            { "data": "quantity" },
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<button class="btn btn-primary m-1" id="editProductBtn" pid="' + data + '">Edit</button>' +
                        '<button class="btn btn-danger" id="deleteProductBtn" pid="' + data + '">Delete</button>';
                }
            }
        ],
    });
});

$(document).ready(function () {
    /**
     * fetch users and add them to the table
     */
    $('#userTable').DataTable({
        "ajax": {
            "url": "http://127.0.0.1:8080/admin/user",
            "headers": {
                
                "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS, HEAD",
                "Access-Control-Allow-Headers": "Content-Type, Authorization, Accept, X-Requested-With, remember-me"
            },
            "dataSrc": function (json) {
                return json;
            },
            "error": function (xhr, error, thrown) {
                $('#userTable').DataTable().draw(false);
            },
            "type": "GET",
            "dataType": "json",
            "contentType": "application/json",
        },
        "columns": [
            { "data": "firstname" },
            { "data": "lastname" },
            { "data": "username" },
            { "data": "email" },
            { "data": "userType" },
            { "data": "enabled" },
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<button class="btn btn-primary m-1" id="editUserBtn" uid="' + data + '">Edit</button>' +
                        '<button class="btn btn-danger" id="deleteUserBtn" uid="' + data + '">Delete</button>';
                }
            }
        ],
        "error": function () {
            alert("error occured");
        }
    });
});

/**
 * on click listener for the delete product button
 */
$(document).on('click', '#deleteProductBtn', function () {
    var data = $(this).attr('pid');
    deleteProduct(data);
});

/**
 * this function deletes a product
 */
function deleteProduct(id) {
    $.ajax({
        url: "http://localhost:8080/admin/product/" + id,
        type: "DELETE",
        headers: {
            "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
        },
        success: function (result) {
            location.reload();
        }
    });
}

/**
 * Add click listener to product modals add button
 */
$(document).ready(function () {
    $('#addNewProductBtn').click(function () {
        addProduct();
    });
});

/**
 * Fetches the product categories and adds them to the select
 * Shows the modal
 * gets values from form and sends them to the backend
 */
function addProduct() {
    // fetch categories and add them to the select
    $('#category').empty();
    $.ajax('http://localhost:8080/category', {
        type: 'GET',
        success: function (data, status, xhr) {
            data.forEach(element => {
                $('#category').append('<option value="' + element.id + '">' + element.name + '</option>');
            });
        },
        error: function (jqXhr, textStatus, errorMessage) {
            alert('Error' + errorMessage);
        }
    });

    $('.productModalContainer').css('display', 'block');

    $('#addProductBtnForm').click(function () {
        const formData = new FormData();
        formData.append('titel', $('#title').val());
        formData.append('description', $('#description').val());
        formData.append('price', $('#price').val());
        formData.append('categoryId', $('#category').val());
        formData.append('quantity', $('#quantity').val());
        formData.append('image', $('#imagePath')[0].files[0]);

        // Define the URL for the fetch request
        var url = 'http://localhost:8080/admin/product';

        // Create the request headers
        var headers = new Headers();
        headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("user.token"));

        // Create the fetch options
        var options = {
            method: 'POST',
            headers: headers,
            body: formData, // Use the FormData object for the request body
        };


        // Perform the fetch request
        fetch(url, options)
            .then(function (response) {
                if (response.ok) {
                    // Handle success here
                    location.reload();
                } else {
                    // Handle error here
                    alert('Error: ' + response.status + ' - ' + response.statusText);
                }
            })
            .catch(function (error) {
                // Handle network or other errors here
                console.error('Fetch Error:', error);
            });
    });

}

/*
* Close the modal
*/
$('#closeProductBtnForm').click(function () {
    $('.productModalContainer').css('display', 'none');
});

/* When edit button is clicked, populate the form with the product data and show the modal */
$(document).on('click', '#editProductBtn', function () {
    var data = $(this).attr('pid');
    populateForm(data);
});

function populateForm(id) {
    // fetch categories and add them to the select
    $('#category').empty();
    $.ajax('http://localhost:8080/category', {
        type: 'GET',
        success: function (data, status, xhr) {
            data.forEach(element => {
                $('#category').append('<option value="' + element.id + '">' + element.name + '</option>');
            });
        },
        error: function (jqXhr, textStatus, errorMessage) {
            alert('Error' + errorMessage);
        }
    });

    $('.productModalContainer').css('display', 'block');

    // change add product title to update product
    $('#addProductBtnForm').text('Update');
    $('#addProductBtnForm').attr('id', 'updateProductBtnForm');

    // fetch product data
    $.ajax('http://localhost:8080/product/' + id, {
        type: 'GET',
        success: function (data, status, xhr) {
            $('#title').val(data.titel);
            $('#description').val(data.description);
            $('#price').val(Number(data.price.replace(',', '.')));
            $('#category').val(data.category.id);
            $('#quantity').val(data.quantity);
            $('#updateProductBtnForm').attr('pid', data.id);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            alert('Error' + errorMessage);
        }
    });
}

// Update product
$(document).on('click', '#updateProductBtnForm', function () {
    const id = $(this).attr('pid');
    const formData = new FormData();
    formData.append('titel', $('#title').val());
    formData.append('description', $('#description').val());
    formData.append('price', $('#price').val());
    formData.append('categoryId', $('#category').val());
    formData.append('quantity', $('#quantity').val());
    formData.append('image', $('#imagePath')[0].files[0]);
    formData.append('id', id);

    var url = 'http://localhost:8080/admin/product/' + id;

    // Create the request headers
    var headers = new Headers();
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("user.token"));

    // Create the fetch options
    var options = {
        method: 'PUT',
        headers: headers,
        body: formData, // Use the FormData object for the request body
    };

    fetch(url, options)
        .then(function (response) {
            if (response.ok) {
                // Handle success here
                location.reload();
            } else {
                // Handle error here
                alert('Error: ' + response.status + ' - ' + response.statusText);
            }
        })
        .catch(function (error) {
            // Handle network or other errors here
            console.error('Fetch Error:', error);
        });

    // closeProductBtnForm add click listener
    $('#closeProductBtnForm').click(function () {
        $('.productModalContainer').css('display', 'none');
    });

    $('#updateProductBtnForm').attr('id', 'addProductBtnForm');
    $('#addProductBtnForm').text('Add Product');
});

/**
 * on click listener for the delete user button
 */
$(document).on('click', '#deleteUserBtn', function () {
    var data = $(this).attr('uid');
    deleteUser(data);
});

/**
 * this function deletes a user
 */
function deleteUser(id) {
    $.ajax(
        {
            url: "http://localhost:8080/admin/user/" + id,
            type: "DELETE",
            headers: {
                "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
            },
            success: function (result) {
                location.reload();
            }
        }
    )
}

/**
 * Add click listener to modals user modals add button
 */
$(document).ready(function () {
    $('#addNewUserBtn').click(function () {
        addUser();
    });
});

/**
 * Shows the modal
 * gets values from form and sends them to the backend
 */
function addUser() {
    $('.userModalContainer').css('display', 'block');

    $('#addUserBtnForm').click(function () {
        // Define the URL for the fetch request
        var url = 'http://localhost:8080/user';

        // Perform the fetch request
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
            },
            body: JSON.stringify({
                firstname: $('#firstname').val(),
                lastname: $('#lastname').val(),
                username: $('#username').val(),
                email: $('#email').val(),
                password: $('#password').val(),
                userType: $('#userType').val(),
                enabled: $('#enabled').val()
            })
        })
            .then(function (response) {
                if (response.ok) {
                    // Handle success here
                    location.reload();
                } else {
                    // Handle error here
                    alert('Error: ' + response.status + ' - ' + response.statusText);
                }
            })
            .catch(function (error) {
                // Handle network or other errors here
                alert('Fetch Error:', error);
            });
    });
}

/*
* Close the modal
*/
$('#closeUserBtnForm').click(function () {
    $('.userModalContainer').css('display', 'none');
});

/* When edit button is clicked, populate the form with the user data and show the modal */
$(document).ready(function () {
    $(document).on('click', '#editUserBtn', function () {
        var data = $(this).attr('uid');
        populateUserForm(data);
    });
});

function populateUserForm(id) {
    $('.userModalContainer').css('display', 'block');

    // change add user title to update user
    $('#addUserBtnForm').text('Update');
    $('#addUserBtnForm').attr('id', 'updateUserBtnForm');
    // fetch user data
    $.ajax('http://localhost:8080/user/' + id, {
        type: 'GET',
        headers: {
            "Authorization" : "Bearer " + sessionStorage.getItem("user.token"),
        },
        success: function (data, status, xhr) {
            // disable password edit
            $('#password').prop('disabled', true);
            $('#firstname').val(data.firstname);
            $('#lastname').val(data.lastname);
            $('#username').val(data.username);
            $('#email').val(data.email);
            $('#userType').val(data.userType);
            $('#enabled').val(data.enabled);
            $('#updateUserBtnForm').attr('uid', data.id);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            alert('Error' + errorMessage);
        }
    });
}

// Update user
$(document).on('click', '#updateUserBtnForm', function () {
    const id = $(this).attr('uid');

    var url = 'http://localhost:8080/user/' + id;

    // Create the request headers
    var headers = new Headers();
    headers.append('Authorization', 'Bearer ' + sessionStorage.getItem("user.token"));
    headers.append('Content-Type', 'application/json');


    // Create the fetch options
    var options = {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify({
            firstname: $('#firstname').val(),
            lastname: $('#lastname').val(),
            username: $('#username').val(),
            email: $('#email').val(),
            userType: $('#userType').val(),
            enabled: 'true',
            password: $('#password').val(),
            id: id
        })
    };

    fetch(url, options)
        .then(function (response) {
            if (response.ok) {
                // Handle success here
                location.reload();
            } else {
                // Handle error here
                alert('Error: ' + response.status + ' - ' + response.statusText);
            }
        })
        .catch(function (error) {
            // Handle network or other errors here
            console.error('Fetch Error:', error);
        });

    // closeUserBtnForm add click listener
    $('#closeUserBtnForm').click(function () {
        $('.userModalContainer').css('display', 'none');
    });

    $('#updateUserBtnForm').attr('id', 'addUserBtnForm');
    $('#addUserBtnForm').text('Add User');
    $('.userModalContainer').css('display', 'none');
});