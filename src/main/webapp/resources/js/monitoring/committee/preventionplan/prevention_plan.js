function riskStatus(toogle_button) {
    var td = toogle_button.parentNode.parentNode.parentNode;
    var idRisk = td.children[1].value;

    $.ajax({
        type: "POST",
        url: "status",
        data: {id: idRisk, status: toogle_button.checked},
        success: function (data) {
            if (data) {
                showNotification("Operación exitosa", "success");

            } else {
                showNotification("Operación fallida", "danger");
            }
        }
    });
}
