var draggedTask = null;

function allowDrop(event) {
    event.preventDefault();
    var targetList = event.target.tagName === "UL" ? event.target : event.target.parentElement;
    targetList.classList.add("drag-over");
}

function drag(event) {
    draggedTask = event.target;
    event.dataTransfer.setData("text", event.target.innerText);
    event.target.classList.add("dragging");
}

function drop(event, targetListId) {
    event.preventDefault();
    var data = event.dataTransfer.getData("text");

    var targetList = document.getElementById(targetListId);

    // Aynı listeye bırakıldığında işlemi gerçekleştirme
    if (event.target.tagName === "LI" && event.target.parentElement.id === targetListId) {
        resetDraggingStyles();
        return;
    }

    // Aynı isimde başka bir görev varsa işlemi gerçekleştirme
    if (isDuplicateTask(targetList, data)) {
        resetDraggingStyles();
        return;
    }

    var taskElement = document.createElement("li");
    taskElement.className = "task";
    taskElement.draggable = true;
    taskElement.addEventListener("dragstart", drag);
    taskElement.appendChild(document.createTextNode(data));

    targetList.classList.remove("drag-over");
    targetList.appendChild(taskElement);

    // Silme işlemi
    if (targetListId === "done-list" || targetListId === "todo-list") {
        var otherListId = targetListId === "done-list" ? "todo-list" : "done-list";
        var otherList = document.getElementById(otherListId);
        var otherTasks = otherList.getElementsByClassName("task");
        for (var i = 0; i < otherTasks.length; i++) {
            if (otherTasks[i].innerText === data) {
                otherList.removeChild(otherTasks[i]);
                break;
            }
        }
    }

    resetDraggingStyles();
}

document.addEventListener("dragleave", function (event) {
    var targetList = event.target.tagName === "UL" ? event.target : event.target.parentElement;
    targetList.classList.remove("drag-over");
});

function resetDraggingStyles() {
    draggedTask.classList.remove("dragging");
    draggedTask = null;
}

function isDuplicateTask(list, taskName) {
    var tasks = list.getElementsByClassName("task");
    for (var i = 0; i < tasks.length; i++) {
        if (tasks[i].innerText === taskName) {
            return true;
        }
    }
    return false;
}