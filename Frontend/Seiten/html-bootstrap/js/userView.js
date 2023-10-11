function updateUserView() {
    var userType = sessionStorage.getItem('user.type');
    var cssCode = '';

    if (userType === 'admin') {
        cssCode = ".bi-person { display:none; }" + ".usertxt {display: none;}" + ".userpage {display:none;}";
    } else if (userType === 'user') {
        cssCode = ".bi-person { display:none; }" + ".admintxt { display: none;}" + ".admindashboard {display:none;}";
    } else {
        cssCode = ".bi-bag-dash { display:none; }" +
            ".bi-box-arrow-in-right { display: none; }" + ".admintxt { display: none;}" + ".usertxt {display: none;}" + ".admindashboard {display:none;}" + ".userpage {display:none;}";
    }

    var styleElement = document.createElement('style');
    styleElement.type = 'text/css';

    if (styleElement.styleSheet) {
        styleElement.styleSheet.cssText = cssCode;
    } else {
        styleElement.appendChild(document.createTextNode(cssCode));
    }

    document.head.appendChild(styleElement);
}

function logoutStorage() {
    sessionStorage.clear();
    console.log("sessionStorage - flushed");
}

document.addEventListener('DOMContentLoaded', function () {
    setTimeout(function () {
        var navItem = document.querySelector('.nav-item');
        if (navItem !== null) {
            var logoutButton = navItem.querySelector('.bi-box-arrow-in-right');
            if (logoutButton !== null) {
                logoutButton.addEventListener('click', function () {
                    logoutStorage();
                });
            }
        }
    }, 100);

    updateUserView();
});