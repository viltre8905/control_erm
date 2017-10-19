/**
 * Created by day on 04/09/2017.
 */
function validateActivityProcess(edit) {
    var name = $('#name').val();
    var responsible = $('#responsible').val();
    var description = $('#description').val();
    var modalMessage = "modalMessage";
    if (edit) {
        name = $('#editName').val();
        responsible = $('#editResponsible').val();
        description = $('#editDescription').val();
        modalMessage = "editModalMessage";
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío", false);
        return false;
    }
    if (responsible == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún responsable", false);
        return false;
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripción no puede estar vacío", false);
        return false;
    }
    return true;

}
function addActivityProcess() {
    var name = $('#name').val();
    var responsible = $('#responsible').val();
    var description = $('#description').val();
    var idProcess = $('#idProcess').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-process').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            idProcess: idProcess,
            name: name,
            responsible: responsible,
            description: description,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillActivityProcessModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteProcess(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                var select = document.getElementById("responsible");
                var responsibleText = select.options[select.selectedIndex].text;
                table.dataTable().fnAddData([
                    name,
                    description,
                    responsibleText,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanActivityProcessModal();
                }, 1000);
            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-process').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteActivityProcess(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idActivity},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que la actividad este siendo utilizada en otro componente. Verifique que la actividad haya sido desvinculada de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function fillActivityProcessModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idActivity = row.children[row.children.length - 1].children[1].value;
    cleanActivityProcessModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idActivity
            },
            success: function (data) {
                if (data['success']) {
                    $('#activityProcessTitle').text(data['name']);
                    $('#editName').val(data['name']);
                    var responsible = data['responsible'];
                    var editResponsible = document.getElementById('editResponsible');
                    for (var i = 0; i < editResponsible.options.length; i++) {
                        if (editResponsible.options[i].value == responsible) {
                            editResponsible.selectedIndex = i;
                        }
                    }
                    $('#editDescription').val(data['description']);
                    $('#idActivityProccess').text(idActivity);
                    $("#edit-activity-process").unbind();
                    $('#edit-activity-process').click(function (e) {
                        if (validateActivityProcess(true)) {
                            editActivityProccess(row);
                        }
                    });
                    $('#editAppModalActivityProcess').modal('show');
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
function editActivityProccess(row) {
    var name = $('#editName').val();
    var responsible = $('#editResponsible').val();
    var description = $('#editDescription').val();
    var idActivityProccess = $('#idActivityProccess').text();
    var idProcess = $('#idProcess').val();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-activity-process').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idActivityProccess,
            idProcess: idProcess,
            name: name,
            responsible: responsible,
            description: description,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                var select = document.getElementById("editResponsible");
                var componentText = select.options[select.selectedIndex].text;
                row.children[0].textContent = name;
                row.children[1].textContent = description;
                row.children[2].textContent = componentText;
                setTimeout(function () {
                    $("#editAppModalActivityProcess").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-activity-process').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });

}
function cleanActivityProcessModal(edit) {
    if (edit) {
        $('#editName').val("");
        $('#editDescription').val("");
        $('#editResponsible').val("-1");
        $('#editModalMessage').empty();
    } else {
        $('#name').val("");
        $('#description').val("");
        $('#responsible').val("-1");
        $('#modalMessage').empty();
    }
}