function selectNavItem(element) {
    // Remove 'active' class from all nav links
    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(function(link) {
        link.classList.remove('active');
    });

    // Add 'active' class to the clicked nav link
    element.classList.add('active');
}
function selectNavItemWId(element, itemId) {
    var navLinks = document.querySelectorAll('#sidebar .nav-link');

    navLinks.forEach(function (link) {
        link.classList.remove('active');
    });

    element.classList.add('active');
}