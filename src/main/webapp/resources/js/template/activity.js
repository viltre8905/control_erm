function showActivitySolution(show_button) {
    var row = show_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    cleanActivitySolutionModal();
    $.ajax({
            type: "GET",
            url: "solution",
            data: {
                id: idActivity
            },
            success: function (data) {
                if (data['success']) {
                    $('#description').text(data['description']);
                    if (data['title']) {
                        $('#documentData').attr('class', 'row');
                        $('#title').text(data['title']);
                        $('#type').text(data['type']);
                        $('#procedence').text(data['procedence']);
                        $('#fileName').text(data['fileName']);
                        $('#download').attr('href', 'document?id=' + data['id']);
                    }
                    $('#activitySolutionModal').modal('show');
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
function cleanActivitySolutionModal() {
    $('#description').val('');
    $('#documentData').attr('class', 'row hide');
    $('#title').text('');
    $('#type').text('');
    $('#procedence').text('');
    $('#fileName').text('');
    $('#download').attr('href', '#');
}
function acceptSolution(accept_button) {
    var row = accept_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "solution/accept",
        data: {id: idActivity},
        success: function (data) {
            if (data['success']) {
                row.children[row.children.length - 3].children[0].textContent = "Aceptada";
                row.children[row.children.length - 1].children[0].children[0].children[1].setAttribute('class', 'btn btn-success disabled');
                row.children[row.children.length - 1].children[0].children[0].children[2].setAttribute('class', 'btn btn-success disabled');
                row.children[row.children.length - 1].children[0].children[0].children[3].setAttribute('class', 'btn btn-success disabled');
                row.children[row.children.length - 1].children[0].children[0].children[4].setAttribute('class', 'btn btn-success disabled');
                showNotification("Operación exitosa", "success");
            } else {
                showNotification(data['message'], "danger");
            }
        }
    });
}
function rejectSolution(reject_button) {
    var row = reject_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "solution/reject",
        data: {id: idActivity},
        success: function (data) {
            if (data['success']) {
                row.children[row.children.length - 3].children[0].textContent = "Rechazada";
                row.children[row.children.length - 1].children[0].children[0].children[2].setAttribute('class', 'btn btn-success disabled');
                showNotification("Operación exitosa", "success");
            } else {
                showNotification(data['message'], "danger");
            }
        }
    });
}
function doActivity(button) {
    var row = button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    $('#idActivity').text(idActivity);
    $('#modalAffirmativeAnswer').modal('show');
    $("#affirmative-button").unbind();
    $('#affirmative-button').click(function () {
        if (validateAffirmativeAnswer()) {
            updateActivity(row);
        }
    });
}
function updateActivity(row) {
    var description = $('#affirmativeAnswerDescription').val();
    var documentTitle = $('#documentTitle').val();
    var documentType = $('#documentType').val();
    var documentProcedence = $('#documentProcedence').val();
    var idActivity = $('#idActivity').text();
    var data = new FormData();
    data.append('id', idActivity);
    data.append('description', description);
    var haveFile = false;
    jQuery.each(jQuery('#file')[0].files, function (i, file) {
        data.append(file['name'].split(".")[0], file);
        haveFile = true;
    });
    if (haveFile) {
        data.append('title', documentTitle);
        data.append('type', documentType);
        data.append('procedence', documentProcedence);
    }
    $('#progress-bar-affirmative').attr("class", "pull-right");
    $('#affirmative-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "update",
        data: data,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data['success']) {
                showMessage("affirmativeAnswerModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    row.children[row.children.length - 3].children[0].textContent = "Resuelta";
                    row.children[row.children.length - 1].children[0].children[0].children[0].setAttribute('class', 'btn btn-success');
                    $('#modalAffirmativeAnswer').modal('hide');
                    cleanAffirmativeAnswerModal();
                }, 1000);
            } else {
                showMessage("affirmativeAnswerModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-affirmative').attr("class", "pull-right hidden");
            $('#affirmative-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function showAddActivity(negativeAnswerId, val) {
    if (val == 1) {
        $('#negativeAnswerId').val(negativeAnswerId);
    }
    $('#modalActivity').modal('show');

}
function cleanActivityModal(edit) {
    if (edit) {
        $('#editActivityDescription').val('');
        $('#editActivityExecutor').val('-1');
        $('#editActivitySubProcess').val('-1');
        $('#editDate').val('');
        $('#editActivityObservation').val('');
        $('#editActivityModalMessage').empty();
    } else {
        $('#activityDescription').val('');
        $('#activityExecutor').val('-1');
        $('#activitySubProcess').val('-1');
        $('#date').val('');
        $('#activityObservation').val('');
        $('#activityModalMessage').empty();
    }
}
function validateActivity(edit) {
    if (edit) {
        var description = $('#editActivityDescription').val();
        var executor = $('#editActivityExecutor').val();
        var subProcess = $('#editActivitySubProcess').val();
        var date = $('#editDate').val();
        var modalMessage = "editActivityModalMessage";
    } else {
        description = $('#activityDescription').val();
        executor = $('#activityExecutor').val();
        subProcess = $('#activitySubProcess').val();
        date = $('#date').val();
        modalMessage = "activityModalMessage";
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripción no puede estar vacío", false);
        return false;
    }
    if (executor == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún ejecutor", false);
        return false;
    }
    if (subProcess == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún sub-proceso", false);
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
function addActivity(val) {
    var description = $('#activityDescription').val();
    var executor = $('#activityExecutor').val();
    var subProcess = $('#activitySubProcess').val();
    var date = $('#date').val();
    var observation = $('#activityObservation').val();
    var negativeAnswerId = $('#negativeAnswerId').val();
    var idRisk = $('#idRisk').val();
    var data = {};
    var url = "create";
    if (val == 1) {
        if ($('#activityType').val() == 1) {
            data['negativeAnswerId'] = negativeAnswerId;
        }
    } else {
        data['idRisk'] = idRisk;
        url = "activity/create";
    }
    data['date'] = date;
    data['description'] = description;
    data['executor'] = executor;
    data['subProcess'] = subProcess;
    data['observation'] = observation;
    data['action'] = "add";

    $('#progress-bar-add-activity').attr("class", "pull-right");
    $('#add-activity-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function (data) {
            if (data['success']) {
                showMessage("activityModalMessage", "Operación exitosa", true);
                var paremeter = val;
                if ($('#activityType').val() == 2) {
                    paremeter = val + ',' + true;
                }
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success disabled" type="button" data-toggle="tooltip" data-original-title="Ver soluci&oacute;n"' +
                    ' onclick="showActivitySolution(this)"><i class="fa pg-contact_book"></i></button>' +
                    '<button class="btn btn-success disabled" type="button" data-toggle="tooltip" data-original-title="Aceptar soluci&oacute;n" onclick="acceptSolution(this)"><i' +
                    ' class="fa fa-thumbs-o-up"></i></button><button class="btn btn-success disabled" type="button" data-toggle="tooltip" data-original-title="Rechazar soluci&oacute;n" onclick="rejectSolution(this)"><i' +
                    ' class="fa fa-thumbs-o-down"></i></button> <button class="btn btn-success" type="button" onclick="fillActivityModal(this,' + val + ')"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteActivity(this,' + paremeter + ')"><i class="fa fa-trash-o"></i></button>' +
                    ' </div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                if (val == 1) {
                    if ($('#activityType').val() == 1) {
                        text += '<input type="text" hidden="true" value="' + negativeAnswerId + '"/>';
                        var table = $('#tableWithSearch' + negativeAnswerId);
                    } else {
                        table = $('#tableWithSearchOtherActivities');
                    }
                } else {
                    table = $('#tableWithSearch');
                }

                if (data['subProcess']) {
                    table.dataTable().fnAddData([
                        description,
                        data['subProcess'],
                        data['executor'],
                        "Asignada",
                        data['date'],
                        text
                    ]);
                } else {
                    table.dataTable().fnAddData([
                        description,
                        data['executor'],
                        "Asignada",
                        data['date'],
                        text
                    ]);
                }
                setTimeout(function () {
                    $('#modalActivity').modal('hide');
                    cleanActivityModal(false, val);
                    if (executor == "-2" || subProcess == "-2") {
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
function fillActivityModal(edit_button, val) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    cleanActivityModal(true, val);
    var url = "data";
    if (val == 2) {
        url = "activity/data";
    }
    $.ajax({
            type: "GET",
            url: url,
            data: {
                id: idActivity
            },
            success: function (data) {
                if (data['success']) {
                    $('#editActivityDescription').val(data['description']);
                    if (data['executor']) {
                        var executor = data['executor'];
                        var editExecutor = document.getElementById('editActivityExecutor');
                        for (var i = 0; i < editExecutor.options.length; i++) {
                            if (editExecutor.options[i].value == executor) {
                                editExecutor.selectedIndex = i;
                            }
                        }
                    } else {
                        var subProcess = data['subProcess'];
                        var editSubProcess = document.getElementById('editActivitySubProcess');
                        for (i = 0; i < editSubProcess.options.length; i++) {
                            if (editSubProcess.options[i].value == subProcess) {
                                editSubProcess.selectedIndex = i;
                            }
                        }
                    }
                    $('#editDate').val(data['date']);
                    $('#editActivityObservation').val(data['observation']);
                    $('#idActivity').text(idActivity);
                    $("#edit-activity-button").unbind();
                    $('#edit-activity-button').click(function () {
                        if (validateActivity(true)) {
                            editActivity(row, val);
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
function editActivity(row, val) {
    var description = $('#editActivityDescription').val();
    var executor = $('#editActivityExecutor').val();
    var subProcess = $('#editActivitySubProcess').val();
    var date = $('#editDate').val();
    var observation = $('#editActivityObservation').val();
    var idActivity = $('#idActivity').text();
    var data = {};
    var url = "create";
    if (val != 1) {
        url = "activity/create";
    }
    data['date'] = date;
    data['description'] = description;
    data['id'] = idActivity;
    data['executor'] = executor;
    data['subProcess'] = subProcess;
    data['observation'] = observation;
    data['action'] = "edit";
    $('#progress-bar-edit-activity').attr("class", "pull-right");
    $('#edit-activity-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function (data) {
            if (data['success']) {
                showMessage("editActivityModalMessage", "Operación exitosa", true);
                row.children[0].textContent = description;
                if (data['subProcess']) {
                    row.children[1].textContent = data['subProcess'];
                    row.children[2].textContent = data['executor'];
                    row.children[4].textContent = data['date'];
                } else {
                    row.children[1].textContent = data['executor'];
                    row.children[3].textContent = data['date'];
                }
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
function deleteActivity(delete_button, val, type) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    var url = "activity/delete";
    if (val == 1) {
        if (!type) {
            var negativeAnswerId = row.children[row.children.length - 1].children[2].value;
        }
        url = "delete";
    }
    $.ajax({
        type: "POST",
        url: url,
        data: {id: idActivity},
        success: function (data) {
            if (data) {
                if (val == 1) {
                    if (!type) {
                        var table = $('#tableWithSearch' + negativeAnswerId).DataTable();
                    } else {
                        table = $('#tableWithSearchOtherActivities').DataTable();
                    }

                } else {
                    table = $('#tableWithSearch').DataTable();
                }
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que la actividad este siendo utilizada en otro componente. Verifique que la actividad haya sido desvinculada de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
