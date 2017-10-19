function showAddReport(idActionControl) {
    $('#idActionControl').text(idActionControl);
    $('#addReportModal').modal('show');
}
function validateReportFieldsModal(edit) {
    var title = $('#title').val();
    var ubication = $('#ubication').val();
    var conclution = $('#conclution').val();
    var modalMessage = "modalMessage";
    if (edit) {
        title = $('#editTitle').val();
        ubication = $('#editUbication').val();
        conclution = $('#editConclution').val();
        modalMessage = "editModalMessage";
    }
    if (title == '') {
        showMessage(modalMessage, "El campo Título no puede estar vacío");
        return false;
    }
    if (ubication == '') {
        showMessage(modalMessage, "El campo Ubicación no puede estar vacío");
        return false;
    }
    if (conclution == '') {
        showMessage(modalMessage, "El campo Conclusiones no puede estar vacío");
        return false;
    }
    return true;
}
function addReport() {
    var title = $('#title').val();
    var ubication = $('#ubication').val();
    var conclution = $('#conclution').val();
    var idControlAction = $('#idActionControl').text();
    var data = new FormData();
    data.append('idControlAction', idControlAction);
    data.append('title', title);
    data.append('ubication', ubication);
    data.append('conclution', conclution);
    data.append('action', "add");
    var haveFile = false;
    jQuery.each(jQuery('#file')[0].files, function (i, file) {
        data.append(file['name'].split(".")[0], file);
        haveFile = true;
    });
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-report').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: data,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var url = "'deficiency/all?id=" + data['id'] + "'";
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillReportModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Deficiencias"' +
                    ' onclick="redirectURL(' + url + ')"><i class="fa fa-thumbs-o-down"></i></a>';
                if (haveFile) {
                    var downloadText = "downloadReport('" + data['uuid'] + "');";
                    text += '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Descargar"' +
                        ' onclick="' + downloadText + '"><i class="pg-download"></i></a>';
                }

                text += '<button class="btn btn-success" type="button" onclick="deleteReport(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>' +
                    '<input type="text" hidden="true" value="' + idControlAction + '"/>';
                var table = $('#tableWithSearch' + idControlAction);
                table.dataTable().fnAddData([
                    title,
                    conclution,
                    ubication,
                    text
                ]);
                setTimeout(function () {
                    $('#addReportModal').modal('hide');
                    cleanReportModal();
                }, 1000);
            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-report').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });

}
function cleanReportModal(edit) {
    if (edit) {
        $('#editTitle').val('');
        $('#editUbication').val('');
        $('#editConclution').val('');
        $('#editModalMessage').empty();
        $('#editFileContainer').empty();
        var input = document.createElement("input");
        var container = document.getElementById("editFileContainer");
        input.setAttribute("type", "file");
        input.setAttribute("id", "editFile");
        input.setAttribute("name", "file");
        container.appendChild(input);
    } else {
        $('#title').val('');
        $('#ubication').val('');
        $('#conclution').val('');
        $('#modalMessage').empty();
        $('#fileContainer').empty();
        input = document.createElement("input");
        container = document.getElementById("fileContainer");
        input.setAttribute("type", "file");
        input.setAttribute("id", "file");
        input.setAttribute("name", "file");
        container.appendChild(input);
    }
}
function fillReportModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idReport = row.children[row.children.length - 1].children[1].value;
    cleanReportModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idReport
            },
            success: function (data) {
                if (data['success']) {
                    $('#editTitle').val(data['title']);
                    $('#editUbication').val(data['ubication']);
                    $('#editConclution').val(data['conclution']);
                    $('#idReport').text(idReport);
                    $("#edit-report").unbind();
                    $('#edit-report').click(function (e) {
                        if (validateReportFieldsModal(true)) {
                            editReport(row);
                        }
                    });
                    $('#editReportModal').modal('show');
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
function editReport(row) {
    var title = $('#editTitle').val();
    var ubication = $('#editUbication').val();
    var conclution = $('#editConclution').val();
    var idReport = $('#idReport').text();
    var data = new FormData();
    data.append('id', idReport);
    data.append('title', title);
    data.append('ubication', ubication);
    data.append('conclution', conclution);
    data.append('action', "edit");
    jQuery.each(jQuery('#editFile')[0].files, function (i, file) {
        data.append(file['name'].split(".")[0], file);
    });
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-report').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: data,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = title;
                row.children[1].textContent = conclution;
                row.children[2].textContent = ubication;
                setTimeout(function () {
                    $("#editReportModal").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-report').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteReport(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idReport = row.children[row.children.length - 1].children[1].value;
    var idControlAction = row.children[row.children.length - 1].children[2].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idReport},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch' + idControlAction).DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el cuestionario este siendo utilizado en otro componente. Verifique que el cuestionario haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function downloadReport(fileId) {
    location.href = 'download?id=' + fileId;
}
function cleanDeficiencyModal(edit) {
    if (edit) {
        $('#editNumber').val('');
        $('#editDescription').val('');
        $('#editModalMessage').empty();
    } else {
        $('#number').val('');
        $('#description').val('');
        $('#modalMessage').empty();
    }
}
function validateDeficiencyFieldsModal(edit) {
    var number = $('#number').val();
    var description = $('#description').val();
    var modalMessage = "modalMessage";
    if (edit) {
        number = $('#editNumber').val();
        description = $('#editDescription').val();
        modalMessage = "editModalMessage";
    }
    if (number == '') {
        showMessage(modalMessage, "El campo Número no puede estar vacío");
        return false;
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripcion no puede estar vacío");
        return false;
    }
    if (!validateNumber(number)) {
        showMessage(modalMessage, "El campo Número debe contener numeros enteros");
        return false;
    }
    return true;
}
function addDeficiency() {
    var number = $('#number').val();
    var description = $('#description').val();
    var idReport = $('#idReport').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-deficiency').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            idReport: idReport,
            number: number,
            description: description,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var activitiesURL = "'activity/activities?id=" + data['id'] + "'";
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillDeficiencyModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" data-toggle="tooltip" data-original-title="Actividades"' +
                    ' onclick="redirectURL(' + activitiesURL + ')"><i class="fa fa-history"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteDeficiency(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    number,
                    description,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanDeficiencyModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-deficiency').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function fillDeficiencyModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idDeficiency = row.children[row.children.length - 1].children[1].value;
    cleanDeficiencyModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idDeficiency
            },
            success: function (data) {
                if (data['success']) {
                    $('#editNumber').val(data['number']);
                    $('#editDescription').val(data['description']);
                    $('#idDeficiency').text(idDeficiency);
                    $("#edit-deficiency").unbind();
                    $('#edit-deficiency').click(function (e) {
                        if (validateDeficiencyFieldsModal(true)) {
                            editDeficiency(row);
                        }
                    });
                    $('#editAppModalDeficiency').modal('show');
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
function editDeficiency(row) {
    var number = $('#editNumber').val();
    var description = $('#editDescription').val();
    var idDeficiency = $('#idDeficiency').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-deficiency').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idDeficiency,
            number: number,
            description: description,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = number;
                row.children[1].textContent = description;
                setTimeout(function () {
                    $("#editAppModalDeficiency").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-deficiency').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteDeficiency(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idDeficiency = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idDeficiency},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el cuestionario este siendo utilizado en otro componente. Verifique que el cuestionario haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function showAddReportActivity(deficiencyId) {
    $('#idDeficiency').val(deficiencyId);
    $('#modalActivity').modal('show');
}
function cleanReportActivityModal(edit) {
    if (edit) {
        $('#editActivityDescription').val('');
        $('#editActivityProcess').val('-1');
        $('#editDate').val('');
        $('#editActivityObservation').val('');
        $('#editActivityModalMessage').empty();
    } else {
        $('#activityDescription').val('');
        $('#activityProcess').val('-1');
        $('#date').val('');
        $('#activityObservation').val('');
        $('#activityModalMessage').empty();
    }
}
function validateReportActivity(edit) {
    if (edit) {
        var description = $('#editActivityDescription').val();
        var process = $('#editActivityProcess').val();
        var date = $('#editDate').val();
        var modalMessage = "editActivityModalMessage";
    } else {
        description = $('#activityDescription').val();
        process = $('#activityProcess').val();
        date = $('#date').val();
        modalMessage = "activityModalMessage";
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripción no puede estar vacío", false);
        return false;
    }
    if (process == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún Proceso", false);
        return false;
    }
    if (date == '') {
        showMessage(modalMessage, "Debe seleccionar alguna fecha", false);
        return false;
    } else if (!validateDate(date)) {
        showMessage(modalMessage, "La fecha de cumplimiento debe ser mayor que la fecha actual", false);
        return false;
    }

    return true;
}
function addReportActivity() {
    var description = $('#activityDescription').val();
    var process = $('#activityProcess').val();
    var date = $('#date').val();
    var observation = $('#activityObservation').val();
    var idDeficiency = $('#idDeficiency').val();
    $('#progress-bar-add-activity').attr("class", "pull-right");
    $('#add-activity-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            idDeficiency: idDeficiency,
            description: description,
            process: process,
            date: date,
            observation: observation,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("activityModalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success disabled" type="button" data-toggle="tooltip" data-original-title="Ver soluci&oacute;n"' +
                    ' onclick="showActivitySolution(this)"><i class="fa pg-contact_book"></i></button>' +
                    '<button class="btn btn-success disabled" type="button" data-toggle="tooltip" data-original-title="Aceptar soluci&oacute;n" onclick="acceptSolution(this)"><i' +
                    ' class="fa fa-thumbs-o-up"></i></button> <button class="btn btn-success disabled" type="button" data-toggle="tooltip" data-original-title="Rechazar soluci&oacute;n" onclick="rejectSolution(this)"><i' +
                    ' class="fa fa-thumbs-o-down"></i></button><button class="btn btn-success" type="button" onclick="fillReportActivityModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteReportActivity(this)"><i class="fa fa-trash-o"></i></button>' +
                    ' </div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';

                table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    description,
                    data['process'],
                    data['executor'],
                    "Asignada",
                    data['date'],
                    text
                ]);

                setTimeout(function () {
                    $('#modalActivity').modal('hide');
                    cleanReportActivityModal();
                    if (process == "-2") {
                        document.location.reload(true);
                    }
                }, 1000);
            } else {
                showMessage("activityModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add-activity').attr("class", "pull-right hidden");
            $('#add-activity-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteReportActivity(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idActivity},
        success: function (data) {
            if (data) {
                table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el cuestionario este siendo utilizado en otro componente. Verifique que el cuestionario haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function fillReportActivityModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    cleanReportActivityModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idActivity
            },
            success: function (data) {
                if (data['success']) {
                    $('#editActivityDescription').val(data['description']);
                    var process = data['process'];
                    var editProcess = document.getElementById('editActivityProcess');
                    for (i = 0; i < editProcess.options.length; i++) {
                        if (editProcess.options[i].value == process) {
                            editProcess.selectedIndex = i;
                        }
                    }
                    $('#editDate').val(data['date']);
                    $('#editActivityObservation').val(data['observation']);
                    $('#idActivity').text(idActivity);
                    $("#edit-activity-button").unbind();
                    $('#edit-activity-button').click(function () {
                        if (validateReportActivity(true)) {
                            editReportActivity(row);
                        }
                    });
                    $('#editModalActivity').modal('show');
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
function editReportActivity(row) {
    var description = $('#editActivityDescription').val();
    var process = $('#editActivityProcess').val();
    var date = $('#editDate').val();
    var observation = $('#editActivityObservation').val();
    var idActivity = $('#idActivity').text();
    $('#progress-bar-edit-activity').attr("class", "pull-right");
    $('#edit-activity-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idActivity,
            description: description,
            process: process,
            date: date,
            observation: observation,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editActivityModalMessage", "Operación exitosa", true);
                row.children[0].textContent = description;
                row.children[1].textContent = data['process'];
                row.children[2].textContent = data['executor'];
                row.children[4].textContent = data['date'];
                setTimeout(function () {
                    $("#editModalActivity").modal('hide');
                }, 1000);
            } else {
                showMessage("editActivityModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit-activity').attr("class", "pull-right hidden");
            $('#edit-activity-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
