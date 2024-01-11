function selectNavItem(element) {

    var navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    navLinks.forEach(function(link) {
        link.classList.remove('active');
    });


    element.classList.add('active');
}
function selectNavItemWId(element, itemId) {
    var navLinks = document.querySelectorAll('#sidebar .nav-link');

    navLinks.forEach(function (link) {
        link.classList.remove('active');
    });

    element.classList.add('active');
}

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
    var taskIdInput = document.getElementById("taskId");
    if (taskIdInput) {
        taskIdInput.value = task.id;
    }

    var taskNameInput = document.getElementById("taskName");
    if (taskNameInput) {
        taskNameInput.value = task.name;
    }

    var taskStatusInput = document.getElementById("taskStatus");
    if (taskStatusInput) {
        taskStatusInput.value = task.status;
    }

    var taskDeveloperInput = document.getElementById("taskDeveloper");
    if (taskDeveloperInput) {
        taskDeveloperInput.value = task.developer;
    }

    var taskAnalystInput = document.getElementById("taskAnalyst");
    if (taskAnalystInput) {
        taskAnalystInput.value = task.analyst;
    }

    var taskDueDateInput = document.getElementById("taskDueDate");
    if (taskDueDateInput) {
        taskDueDateInput.value = task.dueDate;
    }

    var taskFinalSPInput = document.getElementById("taskFinalSP");
    if (taskFinalSPInput) {
        taskFinalSPInput.value = task.finalSP;
    }

    var taskEventInput = document.getElementById("taskEvent");
    if (taskEventInput) {
        taskEventInput.value = task.event;
    }

    var taskIsStarredInput = document.getElementById("taskIsStarred");
    if (taskIsStarredInput) {
        taskIsStarredInput.value = task.isStarred;
    }
}


function saveTaskDetails() {
    // Burada formdaki verileri alıp bir HTTP isteği yapabilir ve veriyi güncelleyebilirsiniz.
    // Örnek olarak bir fetch kullanalım:
    var taskId = document.getElementById("taskId").value;
    var taskName = document.getElementById("taskName").value;
    var taskStatus = document.getElementById("taskStatus").value;
    var taskDeveloper = document.getElementById("taskDeveloper");
    var taskAnalyst = document.getElementById("taskAnalyst");
    var taskDueDate = document.getElementById("taskDueDate").value;
    var taskFinalSP = document.getElementById("taskFinalSP").value;
    var taskEvent = document.getElementById("taskEvent").value;
    var taskIsStarred = document.getElementById("taskIsStarred").value;


    fetch('/tasks/'+taskId, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: taskName,
            status: taskStatus
            // ... Diğer verileri ekleyin ...
        }),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert('Task details saved successfully!');
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('An error occurred while saving task details.');
        });
}

$(document).ready(function () {
    // Modal açıldığında formu sıfırla
    $('#createTaskModal').on('show.bs.modal', function (e) {
        $('#createTaskForm')[0].reset();
    });

    // Form submit olduğunda
    $('#createTaskForm').submit(function (e) {
        e.preventDefault();

        // Burada form verilerini işleyebilir ve task oluşturabilirsiniz
        var taskName = $('#taskName').val();
        // Diğer form verilerini de al

        // Task oluşturma fonksiyonunu çağırabilirsiniz
        createTask(taskName);

        // Modalı kapat
        $('#createTaskModal').modal('hide');
    });
});

function createTask(taskName) {
    // Burada task oluşturma işlemini yapabilirsiniz
    // Örneğin, bir API'ye POST isteği göndererek
}