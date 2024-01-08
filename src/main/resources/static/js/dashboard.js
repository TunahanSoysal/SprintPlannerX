function selectNavItem(element) {

    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(function(link) {
        link.classList.remove('active');
    });


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



function openTaskDetails(button) {
    var taskId = button.dataset.taskId;
    var taskName = button.dataset.taskName;
    var taskStatus = button.dataset.taskStatus;
    var taskDeveloper = button.dataset.taskDeveloper;
    var taskAnalyst = button.dataset.taskAnalyst;
    var taskDueDate = button.dataset.taskDueDate;
    var taskFinalSP = button.dataset.taskFinalSP;
    var taskEvent = button.dataset.taskEvent;
    var taskIsStarred = button.dataset.taskIsStarred;


    var task = {
        id: taskId,
        name: taskName,
        status: taskStatus,
        developer: taskDeveloper,
        analyst: taskAnalyst,
        dueDate: taskDueDate,
        finalSP: taskFinalSP,
        event: taskEvent,
        isStarred: taskIsStarred
    };


    showTaskDetails(task);
}

function showTaskDetails(task) {
    document.getElementById("taskDetailsContainer").innerHTML = "<ul>" +
        "<li><strong>ID:</strong> " + task.id + "</li>" +
        "<li><strong>Name:</strong> " + task.name + "</li>" +
        "<li><strong>Status:</strong> " + task.status + "</li>" +
        "<li><strong>Developer:</strong> " + task.developer + "</li>" +
        "<li><strong>Analyst:</strong> " + task.analyst + "</li>" +
        "<li><strong>Due Date:</strong> " + task.dueDate + "</li>" +
        "<li><strong>Final SP:</strong> " + task.finalSP + "</li>" +
        "<li><strong>Event:</strong> " + task.event + "</li>" +
        "<li><strong>Is Starred:</strong> " + task.isStarred + "</li>" +
        "</ul>";
}