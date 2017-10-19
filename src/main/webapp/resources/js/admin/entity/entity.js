function validateEntityFieldsModal(edit) {
    var name = $('#name').val();
    if ($('#photo').children().length > 0) {
        var pathPhoto = $('#photo').children()[0].getAttribute('src');
    }
    var modalMessage = "modalMessage";
    if (edit) {
        name = $('#editName').val();
        if ($('#editPhoto').children().length > 0) {
            pathPhoto = $('#editPhoto').children()[0].getAttribute('src');
        }
        modalMessage = "editModalMessage";
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío", false);
        return false;
    }
    if (pathPhoto != null && pathPhoto.length > 100000) {
        showMessage(modalMessage, "El tamaño de la imagen execede el límite permitido", false);
        return false;
    }
    return true;
}
function cleanEntityModal(edit) {
    if (edit) {
        $('#editName').val("");
        $('#editAddress').val("");
        $('#editWebAddress').val("");
        $('#editVision').val("");
        $('#editMission').val("");
        $('#editPhoto').empty();
        $('#editPhotoDiv').attr('class', ' fileinput fileinput-new');
        $('#editModalMessage').empty();
    } else {
        $('#name').val("");
        $('#address').val("");
        $('#webAddress').val("");
        $('#vision').val("");
        $('#mission').val("");
        $('#photo').empty();
        $('#photoDiv').attr('class', ' fileinput fileinput-new');
        $('#modalMessage').empty();
    }
}
function addEntityData() {
    var entityName = $('#name').val();
    var address = $('#address').val();
    var webAddress = $('#webAddress').val();
    var entityLogo = '';
    if ($('#photo').children().length > 0) {
        var entityLogo = $('#photo').children()[0].getAttribute('src');
    }
    var vision = $('#vision').val();
    var mission = $('#mission').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-entity').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            entityName: entityName,
            address: address,
            webAddress: webAddress,
            entityLogo: entityLogo,
            vision: vision,
            mission: mission,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillEntityModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Objetivos Estratégicos" ' +
                    'href="strategicObjective/all?entityId=' + data['id'] + '"><i class="pg-ordered_list"></i></a>' +
                    '<button class="btn btn-success" type="button" onclick="deleteEntity(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    entityName,
                    mission,
                    vision,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanEntityModal();
                }, 1000);
            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-entity').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function fillEntityModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idEntity = row.children[row.children.length - 1].children[1].value;
    cleanEntityModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idEntity
            },
            success: function (data) {
                if (data['success']) {
                    $('#editName').val(data['name']);
                    $('#editVision').val(data['vision']);
                    $('#editMission').val(data['mission']);
                    $('#editAddress').val(data['address']);
                    $('#editWebAddress').val(data['webAddress']);
                    var pathPhoto = data['pathPhoto'];
                    if (pathPhoto != null && pathPhoto != '') {
                        var img = document.createElement('img');
                        img.setAttribute('src', pathPhoto);
                        var editPhoto = document.getElementById('editPhoto');
                        editPhoto.appendChild(img);
                        $('#editPhotoDiv').attr('class', 'fileinput fileinput-exists');
                    }
                    $('#idEntity').text(idEntity);
                    $("#edit-entity").unbind();
                    $('#edit-entity').click(function (e) {
                        if (validateEntityFieldsModal(true)) {
                            editEntity(row);
                        }
                    });
                    $('#editAppModalEntity').modal('show');
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
function editEntity(row) {
    var entityName = $('#editName').val();
    var address = $('#editAddress').val();
    var webAddress = $('#editWebAddress').val();
    var entityLogo = '';
    if ($('#editPhoto').children().length > 0) {
        var entityLogo = $('#editPhoto').children()[0].getAttribute('src');
    }
    var vision = $('#editVision').val();
    var mission = $('#editMission').val();
    var idEntity = $('#idEntity').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-entity').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idEntity,
            entityName: entityName,
            address: address,
            webAddress: webAddress,
            entityLogo: entityLogo,
            vision: vision,
            mission: mission,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = entityName;
                row.children[1].textContent = mission;
                row.children[2].textContent = vision;
                setTimeout(function () {
                    $("#editAppModalEntity").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-guide').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteEntity(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idEntity = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idEntity},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que la entidaad este siendo utilizada en otro componente. Verifique que la entidad haya sido desvinculada de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
