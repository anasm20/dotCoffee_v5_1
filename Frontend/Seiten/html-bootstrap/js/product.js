const host = 'http://localhost:8080'
const getProductsDataByFilter = (titel, category) => {

    $('#category-name').text(category)

    $.ajax({
        url: host.concat('/product/search'),
        type: 'post',
        data: JSON.stringify(
            {
                titel: titel,
                category: category
            }
        ),
        contentType: 'application/json; charset=utf-8',
        success: function (result, status, xhr) {
            if (status === 'success') {
                let products = createProducts(result);
                $('#products').append(products)
            } else {
                $('#products').append(result)
            }
        },
        error: function (xhr, status, error) {
            alert('error', error)
        }
    });
}

const createProducts = (productsData) => {
    let products = "";
    if (productsData.length !== 0) {
        $.each(productsData, (index, pro_item) => {
            let src_uri = host.concat(pro_item.imagePath);
            let href = "items/item.html?product-id=".concat(encodeURIComponent(pro_item.id));
            const item = `<div class="col-lg-4 col-sm-6 justify-content-center">
                <div class="card" style="width: auto; background-color: rgb(255, 255, 255);">
                    <div class="card-body">
                        <h5 class="card-title">${pro_item.titel}</h5>
                    </div>
                    <img class="card-img-bottom col-lg-4 col-sm-6 d-flex justify-content-center"
                        src=${src_uri} alt="Product image" style="width: auto;">
                    <a href=${href} class="btn btn-primary">
                        zum Artikel
                    </a>
                    <p class="card-text" style="text-align: center; font-size: medium;"><small
                            class="text-muted">Verfügbar</small></p>
                </div>
            </div>`
            products += item;
        })
    } else {
        products = `<div  style="text-align:center;">
                    <h1 class="display-6">Es wurden keine Produkte gefunden!</h1>
                    <br>
                    </div>`
    }
    return products;
}

const getProductsById = (id) => {

    $.ajax({
        url: host.concat('/product/'.concat(id)),
        type: 'get',
        contentType: 'application/json; charset=utf-8',
        success: function (result, status, xhr) {
            if (status === 'success') {
                let productDetail = createProductsDetails(result);
                $('#product-details').append(productDetail)
            } else {
                $('#product-details').append(result)
            }
        },
        error: function (xhr, status, error) {
            alert('error', error)
        }
    });
}

const createProductsDetails = (productData) => {
    let productDetails = "";
    if (productData !== undefined) {
        let src_uri = host.concat(productData.imagePath);
        let href = "shoppingcart.html?product-id=".concat(encodeURIComponent(productData.id));
        const buttonId = `addToCartButton_${productData.id}`; // Use a unique ID for each button

        // Check if the user is on the shopping cart page
        const isShoppingCartPage = window.location.pathname.includes('/items/shoppingcart.html');

        productDetails = `<div class="col-lg-12 col-sm-12 d-flex justify-content-center">
            <div class="card" style="width:350px; background-color: rgb(255, 255, 255);">
                <div class="card-body">
                    <h5 class="card-title">${productData.titel}</h5>
                </div>
                <img class="col-lg-12 col-sm-12 d-flex justify-content-center"
                    src=${src_uri}
                    alt="Card image cap" style="width: auto;">
                <p class="card-text" style="font-size: 17px; padding: 10px;">${productData.description}</p>
                <div style="padding: 10px; color: rgb(255, 60, 0);">
                    <p>Preis: EUR ${productData.price}</p>
                </div>
                ${isShoppingCartPage
                ? '' // If on shopping cart page, do not include the button
                : `<a id="${buttonId}" href="${href}" class="btn btn-primary" data-product-id="${productData.id}" onclick="addToCart(${productData.id})">
                        zum Warenkorb hinzufügen
                      </a>`
            }
            </div>
        </div>`;
    } else {
        productDetails = `<div  style="text-align:center;">
                <h1 class="display-6">Es wurden keine Produkte gefunden!</h1>
                <br>
            </div>`;
            
    }
    return productDetails;
};


const addToCart = (productId) => {
    let isLogged = localStorage.getItem("user.id").trim();
    if (isLogged !== undefined && isLogged !== "") {

        $.ajax({
            url: host.concat('/cart/'.concat(id)),
            type: 'post',
            contentType: 'application/json; charset=utf-8',
            success: function (result, status, xhr) {
                if (status === 'success') {
                    let productDetail = createProductsDetails(result);
                    $('#product-details').append(productDetail)
                } else {
                    $('#product-details').append(result)
                }
            },
            error: function (xhr, status, error) {
                alert('error', error)
            }
        });

    } else {
        window.location = "login.html"
    }
}

const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has('category')) {
    let categoryName = decodeURIComponent(urlParams.get('category'));
    getProductsDataByFilter(undefined, categoryName);
} else if (urlParams.has('product-id')) {
    let productId = decodeURIComponent(urlParams.get('product-id'));
    getProductsById(productId);
}
