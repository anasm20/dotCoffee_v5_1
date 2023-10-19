let host = 'http://localhost:8080'


let products = [];
let categories = [];
let cartProducts = [];

const getAllProducts = async () => {
    try {
        let response = await fetch(host.concat('/product'));
        if (response.ok) {
            products = await response.json();
        }
    } catch (e) {
        console.log(e);
    }
    return products;
}

const createProductCard = (product) => {
    const productCard = $(`
      <div class="card" style="width: 20rem;margin: 10px; padding: 10px;">
        <img class="card-img-top" src="${product.imagePath}" alt="Card image cap">
        <div class="card-body">
          <h5 class="card-title">${product.titel}</h5>
          <p class="card-text">${product.description.substring(0, 20)}...</p>
            <p class="card-text" style="color: #b15602;">Preis: ${product.price}€</p>
          <a class="btn btn-primary" style="margin-right: 10px;" id="add-to-cart" type="button" pid="${product.id}">In den Warenkorb</a>
          <a class="btn btn-primary" style="margin-right: 10px;" id="go-to-details" type="button" pid="${product.id}">Details</a>
        </div>
      </div>
    `);
    return productCard;
};

// MAIN FUNCTION
// const populateProducts = async () => {
//     const products = await getAllProducts();
//     const productCards = products.map((product) => createProductCard(product));
//     $('#products').append(productCards);
// };

const populateProducts = async () => {
    // Produkt-ID aus der URL extrahieren
    const productId = getProductByIdFromUrl();

    // Alle Produkte abrufen
    const products = await getAllProducts();

    // Wenn eine Produkt-ID in der URL vorhanden ist, filtere die Produkte
    let filteredProducts = products;
    if (productId) {
        filteredProducts = filterProductsById(products, productId);
    }

    // Produktkarten für gefilterte Produkte erstellen und an das DOM anhängen
    const productCards = filteredProducts.map(product => createProductCard(product));
    $('#products').append(productCards);
};

const getProductByIdFromUrl = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');
    return productId;
};

const filterProductsById = (products, productId) => {
    return products.filter(product => product.id === productId);
};



const getAllCategories = async () => {
    try {
        let response = await fetch(host.concat('/category'));
        if (response.ok) {
            categories = await response.json();
        }
    } catch (e) {
        console.log(e);
    }
    return categories;
}

const createCategoryCard = (category) => {
    const categoryCard = $(`
      <div class="card" style="width: 10rem;margin: 10px; padding: 10px;cursor: pointer;" id="category-card" pid="${category.name}">
        <img class="card-img-top" src="${category.imagePath}" alt="Category Image" height="100px" width="100px" style="object-fit: cover;">
        <div class="card-body">
          <h5 class="card-title">${category.name}</h5>
          <!-- Add more details or actions for categories if needed -->
        </div>
      </div>
    `);
    return categoryCard;
};

const populateCategories = async () => {
    const categories = await getAllCategories();
    // also add category "all" to show all products
    // add image that says "all" or something
    categories.push({ name: 'All', imagePath: 'https://img.freepik.com/free-vector/coffee-types_23-2148558571.jpg' });
    const categoryCards = categories.map((category) => createCategoryCard(category));
    $('#categories').append(categoryCards);
};

// <!-- Product cards will be inserted here using JavaScript -->
const createCartProductCard = (product) => {
    const productCard = $(`
      <div class="card" style="width: 20rem;margin: 10px; padding: 10px;">
        <img class="card-img-top" src="${product.imagePath}" alt="Card image cap">
        <div class="card-body">
          <h5 class="card-title">${product.titel}</h5>
          <p class="card-text">${product.description.substring(0, 20)}...</p>
        <a class="btn btn-danger" style="margin-right: 10px;" id="remove-from-cart" type="button" pid="${product.id}">Entfernen</a>
        </div>
      </div>
    `);
    return productCard;
};


$(document).on('click', '#go-to-details', function () {
    const pid = $(this).attr('pid'); // Nehmen Sie die Product ID auf dieselbe Weise wie im vorherigen Code
    const product = products.find((product) => product.id == pid);
    window.location.href = 'details.html?id=' + pid;
});


// on ready click listener to add to cart button
$(document).on('click', '#add-to-cart', function () {
    const pid = $(this).attr('pid');
    const product = products.find((product) => product.id == pid);
    addToCartServer(product);
});

// on ready click listener to remove from cart button
$(document).on('click', '#remove-from-cart', function () {
    const pid = $(this).attr('pid');
    const product = products.find((product) => product.id == pid);
    removeFromCartServer(product);
    location.reload();
});

// fetch cart from server
const fetchCart = async () => {
    const userId = sessionStorage.getItem("user.id");
    try {
        const url = `http://127.0.0.1:8080/cart/user/${userId}`;
        let response = await fetch(
            url,
            {
                method: 'get',
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
                }
            }
        );
        if (response.ok) {
            const { id, items } = await response.json();
            sessionStorage.setItem("cart.id", id);
            sessionStorage.setItem("cart.items", JSON.stringify(items));
            populateCartProducts();
        }
    } catch (e) {
        console.log(e);
    }
}

const addToCartServer = async (product) => {
    const cartId = sessionStorage.getItem("cart.id");
    const userId = sessionStorage.getItem("user.id");
    const url = `http://127.0.0.1:8080/cart/${cartId}/product/${product.id}/add`;

    try {
        let response = await fetch(
            url,
            {
                method: 'post',
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization": "Bearer " + sessionStorage.getItem("user.token"),
                },
            }
        );
        if (response.ok) {
            console.log(response);
            const { items } = await response.json();
            sessionStorage.setItem("cart.items", JSON.stringify(items));
            cartProducts = items;
            populateCartProducts();
            showSuccessMessage('Produkt wurde zum Warenkorb hinzugefügt!');
        }else{
            showErrorMessage('Produkt konnte nicht zum Warenkorb hinzugefügt werden!');
        }
    }
    catch (e) {
        console.log(e);
        showErrorMessage('Produkt konnte nicht zum Warenkorb hinzugefügt werden!');
    }
}

const removeFromCartServer = async (product) => {
    const cartId = sessionStorage.getItem("cart.id");
    const userId = sessionStorage.getItem("user.id");

    const url = `http://127.0.0.1:8080/cart/${cartId}/product/${product.id}/remove`;

    try {
        let response = await fetch(
            url,
            {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + sessionStorage.getItem("user.token"),
                }
            }
        );
        if (response.ok) {
            const { items } = await response.json();
            sessionStorage.setItem("cart.items", JSON.stringify(items));
            cartProducts = items;
            populateCartProducts();
            showSuccessMessage('Produkt wurde aus dem Warenkorb entfernt!');
        }
        else{
            showErrorMessage('Produkt konnte nicht aus dem Warenkorb entfernt werden!');
        }
    }
    catch (e) {
        console.log(e);
        showErrorMessage('Produkt konnte nicht aus dem Warenkorb entfernt werden!');
    }
}


const populateCartProducts = async () => {
    const products = JSON.parse(sessionStorage.getItem("cart.items"));
    if (products.length === 0) {
        $('#cartProducts').append('<h4>Keine Produkte im Warenkorb!</h4>');
        return;
    }
    const productCards = products.map((product) => createCartProductCard(product));
    $('#cartProducts').append(productCards);
}

const showSuccessMessage = (message) => {
    const alert = $(`
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Success!</strong> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `);
    $('#alert_placeholder').append(alert);
}

const showErrorMessage = (message) => {
    const alert = $(`
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `);
    $('#alert_placeholder').append(alert);
}


// when clicked on category card, it will filter the products
$(document).on('click', '#category-card', function () {
    // reset background color of all cards
    $('#categories').children().each(function () {
        $(this).css('background-color', 'white');
        $(this).css('color', 'black');
    }
    );
    // set background color of clicked card
    $(this).css('background-color', '#b15602');
    $(this).css('color', '#fff');
    
    const categoryName = $(this).attr('pid');
    if (categoryName === 'All') {
        $('#products').empty();
        populateProducts();
        $('#productTitle').text('Alle Produkte');
        return;
    }
    // filter products by category
    const filteredProducts = products.filter((product) => product.category.name === categoryName);
    $('#products').empty();
    const productCards = filteredProducts.map((product) => createProductCard(product));
    $('#products').append(productCards);
    $('#productTitle').text(categoryName + ' Produkte');
});

// search functionality
$(document).on('keydown', '#searchInput', function () {
    const searchValue = $(this).val();
    if (searchValue === '') {
        $('#products').empty();
        populateProducts();
        return;
    }
    const filteredProducts = products.filter((product) => product.titel.toLowerCase().includes(searchValue.toLowerCase()) || product.description.toLowerCase().includes(searchValue.toLowerCase()));
    $('#products').empty();
    const productCards = filteredProducts.map((product) => createProductCard(product));
    $('#products').append(productCards);
    $('#productTitle').text('Suchergebnisse');
});


const loadData = async () => {
    await fetchCart();
    await populateCategories();
    await populateProducts();
    
}

$(document).ready(async () => {
    await loadData();
});

