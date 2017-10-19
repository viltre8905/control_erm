function executeGuide() {
    $("#alert-modal").modal('show');
    $("#alert-modal-title").text("Si ejecuta esta operación se eliminarán todas las medidas, deficiencias  y respuestas del autocontrol pasado. ¿Desea continuar con la operación?");
    var alertButton = document.getElementById("alert-modal-button");
    alertButton.setAttribute("onclick", "executeBody('execute-guide')");
}
function executeRisk() {
    $("#alert-modal").modal('show');
    $("#alert-modal-title").text("Si ejecuta esta operación se eliminarán todos los objetivos de control de riesgos. ¿Desea continuar con la operación?");
    var alertButton = document.getElementById("alert-modal-button");
    alertButton.setAttribute("onclick", "executeBody('execute-risk')");
}
function executeCommittee() {
    $("#alert-modal").modal('show');
    $("#alert-modal-title").text("Si ejecuta esta operación se eliminarán todos los informes, deficiencias y medidas asignadas por el comité. ¿Desea continuar con la operación?");
    var alertButton = document.getElementById("alert-modal-button");
    alertButton.setAttribute("onclick", "executeBody('execute-committee')");
}

function executeBody(url) {
    $('#progress-bar-alert').attr("class", "pull-right");
    $('#alert-modal-button').attr("class", "btn btn-primary  btn-cons disabled");
    $.ajax({
        type: "DELETE",
        url: url,
        data: {},
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    $("#alert-modal").modal('hide');
                }, 1000);
            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-alert').attr("class", "pull-right hidden");
            $('#alert-modal-button').attr("class", "btn btn-primary  btn-cons");
        }
    });
}