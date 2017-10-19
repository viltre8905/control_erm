function validateThemeFieldsModal(edit) {
    var no = $('#no').val();
    var description = $('#description').val();
    var modalMessage = "modalMessage";
    if (edit) {
        no = $('#editNo').val();
        description = $('#editDescription').val();
        modalMessage = "editModalMessage";
    }
    if (no == '') {
        showMessage(modalMessage, "El campo Número no puede estar vacío", false);
        return false;
    }
    if (!validateNumber(no)) {
        showMessage(modalMessage, "El campo Número debe ser un número entero.", false);
        return false;
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripción no puede estar vacío", false);
        return false;
    }
    return true;
}
function cleanThemeModal(edit) {
    if (edit) {
        $('#editNo').val('');
        $('#editDescription').val('');
        $('#editModalMessage').empty();
    } else {
        $('#no').val('');
        $('#description').val('');
        $('#modalMessage').empty();
    }
}
function addTheme() {
    var no = $('#no').val();
    var description = $('#description').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-theme').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            no: no,
            description: description,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillThemeModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Fechas de discusión"' +
                    ' href="themes/discussionDate?id=' + data['id'] + '"><i class="fa fa-calendar"></i></a>' +
                    '<button class="btn btn-success" type="button" onclick="deleteTheme(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    no,
                    description,
                    text
                ]);
                setTimeout(function () {
                    $("#addThemeModal").modal('hide');
                    cleanThemeModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-theme').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}

function fillThemeModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var themeId = row.children[row.children.length - 1].children[1].value;
    cleanThemeModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: themeId
            },
            success: function (data) {
                if (data['success']) {
                    $('#editNo').val(data['no']);
                    $('#editDescription').val(data['description']);
                    $('#themeId').text(themeId);
                    $("#edit-theme").unbind();
                    $('#edit-theme').click(function (e) {
                        if (validateThemeFieldsModal(true)) {
                            editTheme(row);
                        }
                    });
                    $('#editThemeModal').modal('show');
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

function editTheme(row) {
    var no = $('#editNo').val();
    var description = $('#editDescription').val();
    var themeId = $('#themeId').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-theme').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: themeId,
            no: no,
            description: description,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = no;
                row.children[1].textContent = description;
                setTimeout(function () {
                    $("#editThemeModal").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-theme').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}

function deleteTheme(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var themeId = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: themeId},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Ha ocurrido un error inesperado", "danger");
            }
        }
    });
}
/**************************************DISCUSSION DATE*******************************************/
function validateDiscussionDateFieldsModal(edit) {
    var responsible = $('#responsible').val();
    var date = $('#date').val();
    var modalMessage = "modalMessage";
    if (edit) {
        responsible = $('#editResponsible').val();
        date = $('#editDate').val();
        modalMessage = "editModalMessage";
    }
    if (responsible == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún responsable", false);
        return false;
    }
    if (date == '') {
        showMessage(modalMessage, "El campo Fecha no puede estar vacío", false);
        return false;
    }
    else if (!validateDate(date)) {
        showMessage(modalMessage, "La fecha de discusión debe ser mayor que la fecha actual", false);
        return false;
    }
    return true;
}
function cleanDiscussionDateModal(edit) {
    if (edit) {
        $('#editResponsible').val('-1');
        $('#editDate').val('');
        $('#editModalMessage').empty();
    } else {
        $('#responsible').val('-1');
        $('#date').val('');
        $('#modalMessage').empty();
    }
}
function addDiscussionDate() {
    var responsible = $('#responsible').val();
    var themeId = $('#themeId').val();
    var date = $('#date').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-discussion-date').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            themeId: themeId,
            responsible: responsible,
            date: date,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillDiscussionDateModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteDiscussionDate(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    data['responsible'],
                    data['date'],
                    text
                ]);
                setTimeout(function () {
                    $("#addDiscussionDateModal").modal('hide');
                    cleanDiscussionDateModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-discussion-date').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}

function fillDiscussionDateModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var discussionDateId = row.children[row.children.length - 1].children[1].value;
    cleanDiscussionDateModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: discussionDateId
            },
            success: function (data) {
                if (data['success']) {
                    $('#editDate').val(data['date']);
                    var responsible = data['responsible'];
                    var editResponsible = document.getElementById('editResponsible');
                    for (var i = 0; i < editResponsible.options.length; i++) {
                        if (editResponsible.options[i].value == responsible) {
                            editResponsible.selectedIndex = i;
                        }
                    }
                    $('#discussionDateId').text(discussionDateId);
                    $("#edit-discussion-date").unbind();
                    $('#edit-discussion-date').click(function (e) {
                        if (validateDiscussionDateFieldsModal(true)) {
                            editDiscussionDate(row);
                        }
                    });
                    $('#editDiscussionDateModal').modal('show');
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

function editDiscussionDate(row) {
    var responsible = $('#editResponsible').val();
    var date = $('#editDate').val();
    var discussionDateId = $('#discussionDateId').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-discussion-date').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: discussionDateId,
            responsible: responsible,
            date: date,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = data['responsible'];
                row.children[1].textContent = data['date'];
                setTimeout(function () {
                    $("#editDiscussionDateModal").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-discussion-date').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}

function deleteDiscussionDate(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var discussionDateId = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: discussionDateId},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Ha ocurrido un error inesperado", "danger");
            }
        }
    });
}

