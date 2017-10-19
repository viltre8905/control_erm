function validateStrategicObjectiveFieldsModal(edit) {
    var title = $('#title').val();
    var description = $('#description').val();
    var modalMessage = "modalMessage";
    if (edit) {
        title = $('#editTitle').val();
        description = $('#editDescription').val();
        modalMessage = "editModalMessage";
    }
    if (title == '') {
        showMessage(modalMessage, "El campo Título no puede estar vacío", false);
        return false;
    }
    if (description == '') {
        showMessage(modalMessage, "El campo Descripción no puede estar vacío", false);
        return false;
    }
    return true;
}
function addStrategicObjective() {
    var title = $('#title').val();
    var description = $('#description').val();
    var entityId = $('#entityId').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-strategic-objective').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            entityId: entityId,
            title: title,
            description: description,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillStrategicObjectiveModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteStrategicObjective(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    title,
                    description,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanStrategicObjectiveModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-strategic-objective').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteStrategicObjective(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var objectiveId = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: objectiveId},
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
function fillStrategicObjectiveModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var objectiveId = row.children[row.children.length - 1].children[1].value;
    cleanStrategicObjectiveModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: objectiveId
            },
            success: function (data) {
                if (data['success']) {
                    $('#editTitle').val(data['title']);
                    $('#editDescription').val(data['description']);
                    $('#strategicObjectiveId').text(objectiveId);
                    $("#edit-strategic-objective").unbind();
                    $('#edit-strategic-objective').click(function (e) {
                        if (validateStrategicObjectiveFieldsModal(true)) {
                            editStrategicObjective(row);
                        }
                    });
                    $('#editAppModalStrategicObjective').modal('show');
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
function editStrategicObjective(row) {
    var title = $('#editTitle').val();
    var description = $('#editDescription').val();
    var objectiveId = $('#strategicObjectiveId').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-strategic-objective').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: objectiveId,
            title: title,
            description: description,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true)
                row.children[0].textContent = title;
                row.children[1].textContent = description;
                setTimeout(function () {
                    $("#editAppModalStrategicObjective").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-strategic-objective').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function cleanStrategicObjectiveModal(edit) {
    if (edit) {
        $('#editTitle').val("");
        $('#editDescription').val("");
        $('#editModalMessage').empty();
    } else {
        $('#title').val("");
        $('#description').val("");
        $('#modalMessage').empty();
    }
}
