function selectNavItem(element) {
    // Remove 'active' class from all nav links
    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(function(link) {
        link.classList.remove('active');
    });

    // Add 'active' class to the clicked nav link
    element.classList.add('active');
}
// function selectNavItemWId(element, itemId) {
//     var navLinks = document.querySelectorAll('#sidebar .nav-link');
//
//     navLinks.forEach(function (link) {
//         link.classList.remove('active');
//     });
//
//     element.classList.add('active');
// }

document.addEventListener("DOMContentLoaded", function () {
    const defaultLinkId = "dashboard";
    const defaultLink = document.getElementById(defaultLinkId);
    defaultLink.click();
});

function loadContent(page, element) {
    selectNavItem(element);
    var contentContainer = document.getElementById("contentContainer");
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            contentContainer.innerHTML = xhr.responseText;
        }
    };

    xhr.open("GET", "/" + page, true);
    xhr.send();
}