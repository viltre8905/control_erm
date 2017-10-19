function initTask(initButton) {
    var row = initButton.parentNode.parentNode.parentNode.parentNode;
    var id = row.children[row.children.length - 1].children[1].value;
    var frequency = $("#frequency").val();
    if (!validateNumber(frequency)) {
        showNotification("El período debe ser un número entero", "danger");
    } else {
        $.ajax({
            type: "POST",
            url: "initTask",
            data: {
                id: id,
                frequency: frequency
            },
            success: function (data) {
                if (data) {
                    showNotification("Operación exitosa", "success");
                    $("#buttonInitTask").attr("class", "btn btn-success disabled");
                    $("#buttonStopTask").attr("class", "btn btn-success");
                } else {
                    showNotification(data["message"], "danger");
                }
            }
        });
    }
}
function stopTask(stopButton) {
    var row = stopButton.parentNode.parentNode.parentNode.parentNode;
    var id = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "stopTask",
        data: {
            id: id
        },
        success: function (data) {
            if (data) {
                showNotification("Operación exitosa", "success");
                $("#buttonInitTask").attr("class", "btn btn-success");
                $("#buttonStopTask").attr("class", "btn btn-success disabled");
            } else {
                showNotification(data["messaage"], "danger");
            }
        }
    });
}
