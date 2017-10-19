function showAddRisk(objective_id) {
    $('#idObjective').text(objective_id);
    $('#modalAddRisk').modal('show');
}
function validateModalRisk(edit) {
    var description = $('#description').val();
    var generator = $('#generator').val();
    var cause = $('#cause').val();
    var consequence = $('#consequence').val();
    var modalMessage = "riskModalMessage";
    if (edit) {
        description = $('#editDescription').val();
        generator = $('#editGenerator').val();
        cause = $('#editCause').val();
        consequence = $('#editConsequence').val();
        modalMessage = "editRiskModalMessage";
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripción no puede estar vacío", false);
        return false;
    }
    if (generator == '') {
        showMessage(modalMessage, "El campo Posible Causa Negativa no puede estar vacío", false);
        return false;
    }
    if (cause == '') {
        showMessage(modalMessage, "El campo Causas no puede estar vacío", false);
        return false;
    }
    if (consequence == '') {
        showMessage(modalMessage, "El campo Consecuencias no puede estar vacío", false);
        return false;
    }
    return true;
}
function addRisk() {
    var idObjective = $('#idObjective').text();
    var description = $('#description').val();
    var generator = $('#generator').val();
    var assets = $('#assets').val();
    var cause = $('#cause').val();
    var consequence = $('#consequence').val();
    var probability = $('#probability').val();
    var impact = $('#impact').val();
    var procedence = $('#procedence').val();
    $('#progress-bar-add-risk').attr("class", "pull-right");
    $('#add-risk-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            idObjective: idObjective,
            description: description,
            generator: generator,
            assets: assets,
            cause: cause,
            consequence: consequence,
            probability: probability,
            impact: impact,
            procedence: procedence,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("riskModalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillRiskModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Actividades de control"' +
                    ' onclick="redirectRisk(' + data['id'] + ')"><i class="fa fa-history"></i> </a>' +
                    '<button class="btn btn-success" type="button" onclick="deleteRisk(this)"><i' +
                    ' class="fa fa-trash-o"></i></button><a href="#" class="exportReport btn btn-success"' +
                    ' data-toggle="tooltip" data-original-title="Ficha técnica" onclick="exportReportRisk(this)">' +
                    '<i class="fa fa-file-pdf-o"></i> </a></div></div>' +
                    '<input type="text" hidden="true" value="' + data['id'] + '"/>' +
                    '<input type="text" hidden="true" value="' + idObjective + '"/>';
                var table = $('#tableWithSearch' + idObjective);
                var selectProbability = document.getElementById("probability");
                var probabilityText = selectProbability.options[selectProbability.selectedIndex].text;
                var selectImpact = document.getElementById("impact");
                var impactText = selectImpact.options[selectImpact.selectedIndex].text;
                var selectProcedence = document.getElementById("procedence");
                var procedenceText = selectProcedence.options[selectProcedence.selectedIndex].text;
                table.dataTable().fnAddData([
                    description,
                    probabilityText,
                    impactText,
                    procedenceText,
                    data['level'],
                    text
                ]);
                setTimeout(function () {
                    $("#modalAddRisk").modal('hide');
                    cleanModalRisk();
                }, 1000);

            } else {
                showMessage("riskModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add-risk').attr("class", "pull-right hidden");
            $('#add-risk-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function cleanModalRisk(edit) {
    if (edit) {
        $('#editDescription').val('');
        $('#editGenerator').val('');
        $('#editAssets').val('');
        $('#editCause').val('');
        $('#editConsequence').val('');
        $('#editProbability').val('1');
        $('#editImpact').val('1');
        $('#editProcedence').val('1');
        $('#editRiskModalMessage').empty();
    } else {
        $('#description').val('');
        $('#generator').val('');
        $('#assets').val('');
        $('#cause').val('');
        $('#consequence').val('');
        $('#probability').val('1');
        $('#impact').val('1');
        $('#procedence').val('1');
        $('#riskModalMessage').empty();
    }
}
function fillRiskModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idRisk = row.children[row.children.length - 1].children[1].value;
    cleanModalRisk(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idRisk
            },
            success: function (data) {
                if (data['success']) {
                    $('#editDescription').val(data['description']);
                    $('#editGenerator').val(data['generator']);
                    $('#editAssets').val(data['assets']);
                    $('#editCause').val(data['cause']);
                    $('#editConsequence').val(data['consequence']);
                    var probability = data['probability'];
                    var editProbability = document.getElementById('editProbability');
                    for (var i = 0; i < editProbability.options.length; i++) {
                        if (editProbability.options[i].value == probability) {
                            editProbability.selectedIndex = i;
                        }
                    }
                    var impact = data['impact'];
                    var editImpact = document.getElementById('editImpact');
                    for (i = 0; i < editImpact.options.length; i++) {
                        if (editImpact.options[i].value == impact) {
                            editImpact.selectedIndex = i;
                        }
                    }
                    var procedence = data['procedence'];
                    var editProcedence = document.getElementById('editProcedence');
                    for (i = 0; i < editProcedence.options.length; i++) {
                        if (editProcedence.options[i].value == procedence) {
                            editProcedence.selectedIndex = i;
                        }
                    }
                    $('#idRisk').text(idRisk);
                    $("#edit-risk-button").unbind();
                    $('#edit-risk-button').click(function (e) {
                        if (validateModalRisk(true)) {
                            editRisk(row);
                        }
                    });
                    $('#modalEditRisk').modal('show');
                } else {
                    if (data['message'] != null) {
                        showNotification(data['message'], "danger");
                    } else {
                        document.location.reload(true);
                    }
                }
            }
        }
    );
}
function editRisk(row) {
    var idRisk = $('#idRisk').text();
    var description = $('#editDescription').val();
    var generator = $('#editGenerator').val();
    var assets = $('#editAssets').val();
    var cause = $('#editCause').val();
    var consequence = $('#editConsequence').val();
    var probability = $('#editProbability').val();
    var impact = $('#editImpact').val();
    var procedence = $('#editProcedence').val();
    $('#progress-bar-edit-risk').attr("class", "pull-right");
    $('#edit-risk-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idRisk,
            description: description,
            generator: generator,
            assets: assets,
            cause: cause,
            consequence: consequence,
            probability: probability,
            impact: impact,
            procedence: procedence,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editRiskModalMessage", "Operación exitosa", true)
                var selectProbability = document.getElementById("editProbability");
                var probabilityText = selectProbability.options[selectProbability.selectedIndex].text;
                var selectImpact = document.getElementById("editImpact");
                var impactText = selectImpact.options[selectImpact.selectedIndex].text;
                var selectProcedence = document.getElementById("editProcedence");
                var procedenceText = selectProcedence.options[selectProcedence.selectedIndex].text;
                row.children[0].textContent = description;
                row.children[1].textContent = probabilityText;
                row.children[2].textContent = impactText;
                row.children[3].textContent = procedenceText;
                row.children[4].textContent = data['level'];
                setTimeout(function () {
                    $("#modalEditRisk").modal('hide');
                }, 1000);
            } else {
                showMessage("editRiskModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit-risk').attr("class", "pull-right hidden");
            $('#edit-risk-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteRisk(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idRisk = row.children[row.children.length - 1].children[1].value;
    var idObjective = row.children[row.children.length - 1].children[2].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idRisk},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch' + idObjective).DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el cuestionario este siendo utilizado en otro componente. Verifique que el cuestionario haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function showRiskData(show_button) {
    var row = show_button.parentNode.parentNode.parentNode.parentNode;
    var idRisk = row.children[row.children.length - 1].children[1].value;
    cleanModalShowRisk();
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idRisk
            },
            success: function (data) {
                if (data['success']) {
                    $('#showDescription').text(data['description']);
                    $('#showGenerator').text(data['generator']);
                    $('#showAssets').text(data['assets']);
                    $('#showCause').text(data['cause']);
                    $('#showConsequence').text(data['consequence']);
                    var probability = data['probability'];
                    if (probability == '1') {
                        $('#showProbability').text('Baja');
                    } else if (probability == '2') {
                        $('#showProbability').text('Media');
                    } else {
                        $('#showProbability').text('Alta');
                    }
                    var impact = data['impact'];
                    if (impact == '1') {
                        $('#showImpact').text('Leve');
                    } else if (impact == '2') {
                        $('#showImpact').text('Moderado');
                    } else {
                        $('#showImpact').text('Alto');
                    }

                    var procedence = data['procedence'];
                    if (procedence == '1') {
                        $('#showProcedence').text('Interno');
                    } else {
                        $('#showProcedence').text('Externo');
                    }
                    $('#modalShowRisk').modal('show');
                } else {
                    if (data['message'] != null) {
                        showNotification(data['message'], "danger");
                    } else {
                        document.location.reload(true);
                    }
                }
            }
        }
    );
}
function cleanModalShowRisk() {
    $('#showDescription').text('');
    $('#showGenerator').text('');
    $('#showAssets').text('');
    $('#showCause').val('');
    $('#showConsequence').text('');
    $('#showProbability').text('');
    $('#showImpact').text('');
    $('#showProcedence').text('');
}
function redirectRisk(riskId) {
    location.href = 'activities?id=' + riskId;
}
