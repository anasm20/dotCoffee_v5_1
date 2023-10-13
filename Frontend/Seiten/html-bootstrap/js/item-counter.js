function displayCartItemCount() {
    const cart = getCartItemCounter();
    let itemCount = cart.length;


    if (itemCount === 0) {
        itemCount = '';
    }

    // Das CSS für das ::before-Pseudoelement dynamisch aktualisieren
    const styleTag = document.getElementById('dynamic-style');
    if (styleTag) {
        styleTag.innerHTML = `
            .content::before {
                content: "${itemCount}";
                font-size: 16px;
                font-weight: bold;
                color: rgb(4, 197, 250);
            }
        `;
    }

    // console.log(`Im Warenkorb befinden sich ${itemCount} Artikel.`);
}


function getCartItemCounter() {
    let cart = sessionStorage.getItem('cart.items');
    if (cart === null) {
        cart = [];
    } else {
        cart = JSON.parse(cart);
    }
    return cart;
} 

// Das CSS für das ::before-Pseudoelement nur erstellen, wenn es nicht bereits existiert
if (!document.getElementById('dynamic-style')) {
    const css = `
        .content::before {
            content: "";
            font-size: 16px;
            font-weight: bold;
            color: rgb(4, 197, 250);;
        }
    `;

    const styleTag = document.createElement('style');
    styleTag.id = 'dynamic-style';
    styleTag.innerHTML = css;
    document.head.appendChild(styleTag);
}

// Die Funktion displayCartItemCount alle 1 Sekunde aufrufen (1000 Millisekunden)
setInterval(displayCartItemCount, 100);
