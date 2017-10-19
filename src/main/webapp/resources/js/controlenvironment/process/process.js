function completeProcessData() {
    var vision = $('#vision').val();
    var mission = $('#mission').val();
    if (vision == '') {
        showNotification("El campo visión no puede estar vacío", "danger");
    }
    else if (mission == '') {
        showNotification("El campo misión no puede estar vacío", "danger");
    } else {
        $.ajax({
            type: "POST",
            url: "data/save",
            data: {
                vision: vision,
                mission: mission
            },
            success: function (data) {
                if (data['success']) {
                    showNotification("Operación exitosa", "success");
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        });
    }

}
function validateObjectiveModal(edit) {
    var name = $('#name').val();
    var modalMessage = "modalMessage";
    if (edit) {
        name = $('#editName').val();
        modalMessage = "editModalMessage";
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío");
        return false;
    }
    return true;
}
function addObjective() {
    var name = $('#name').val();
    var description = $('#description').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-objective').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "objective/save",
        data: {
            name: name,
            description: description,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillObjectiveModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteObjective(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    name,
                    description,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanObjectiveModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-objective').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteObjective(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idObjective = row.children[2].children[1].value;
    $.ajax({
        type: "POST",
        url: "objective/delete",
        data: {id: idObjective},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el objetivo este siendo utilizado en otro componente. Verifique que el objetivo haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function cleanObjectiveModal(edit) {
    if (edit) {
        $('#editName').val("");
        $('#editDescription').val("");
        $('#editModalMessage').empty();
    } else {
        $('#name').val("");
        $('#description').val("");
        $('#modalMessage').empty();
    }
}
function fillObjectiveModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idObjective = row.children[2].children[1].value;
    cleanObjectiveModal(true);
    $.ajax({
            type: "GET",
            url: "objective/data",
            data: {
                id: idObjective
            },
            success: function (data) {
                if (data['success']) {
                    $('#editName').val(data['name']);
                    var component = data['component'];
                    $('#editDescription').val(data['description']);
                    $('#idObjective').text(idObjective);
                    $("#edit-objective").unbind();
                    $('#edit-objective').click(function (e) {
                        if (validateObjectiveModal(true)) {
                            editObjective(row);
                        }
                    });
                    $('#editAppModalObjective').modal('show');
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
function editObjective(row) {
    var name = $('#editName').val();
    var description = $('#editDescription').val();
    var idObjective = $('#idObjective').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-objective').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "objective/save",
        data: {
            id: idObjective,
            name: name,
            description: description,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = name;
                row.children[1].textContent = description;
                setTimeout(function () {
                    $("#editAppModalObjective").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-objective').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
