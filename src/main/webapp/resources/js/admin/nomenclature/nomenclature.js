function showModalNomenclature(type) {
    var initTableWithSearch = function () {
        var table = $('#tableWithSearch' + type);
        var settings = {
            "sDom": "<'table-responsive't><'row'<p i>>",
            "sPaginationType": "bootstrap",
            "destroy": true,
            "scrollCollapse": true,
            "oLanguage": {
                "sLengthMenu": "_MENU_ ",
                "sInfo": "Mostrando <b>_START_ al _END_</b> de _TOTAL_ total"
            },
            "iDisplayLength": 5
        };
        table.dataTable(settings);
        $('#search-table' + type).keyup(function () {
            table.fnFilter($(this).val());
        });

        $('#showmodalAdd' + type).unbind();
        $('#showmodalAdd' + type).click(function () {
                $('#NomenclatureModalTitle').empty();
                switch (type) {
                    case 1:
                        $('#type').val(1);
                        $('#NomenclatureModalTitle').text('Acciones de Control');
                        break;
                    case 2:
                        $('#type').val(2);
                        $('#NomenclatureModalTitle').text('Procedencia de Documento');
                        break;
                    case 3:
                        $('#type').val(3);
                        $('#NomenclatureModalTitle').text('Tipo de Documento');
                        break;
                    case 4:
                        $('#type').val(4);
                        $('#NomenclatureModalTitle').text('Cargo');
                        break;
                }
                $('#NomenclatureModalTitle').append('<div class="pull-right hidden" id="progress-bar-add">' +
                    '<div class="col-sm-1 text-center center-scale">' +
                    '<div class="progress-circle-indeterminate m-t-0" data-color="primary"></div></div></div>');
                $('#addNewAppModalNomenclature').modal('show');
            }
        );

    };
    initTableWithSearch();
}
function validateNomenclatureFieldsModal(edit) {
    if (edit) {
        var name = $('#editName').val();
        var modalMessage = 'editModalMessage';
    } else {
        var name = $('#name').val();
        var modalMessage = 'modalMessage';
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío", false);
        return false;
    }
    return true;
}
function addNomenclature() {
    var type = $('#type').val();
    var name = $('#name').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-app').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            name: name,
            type: type,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillNomenclatureModal(this,' + type + ')"><i class="fa fa-pencil"></i></button>' +
                    '<button class="btn btn-success" type="button" onclick="deleteNomenclature(this,' + type + ')"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch' + type);
                name = "<p>" + name + "</p>";
                table.dataTable().fnAddData([
                    name,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModalNomenclature").modal('hide');
                    cleanNomenclatureModal();
                }, 1000);
            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-app').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteNomenclature(delete_button, type) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idNomenclature = row.children[1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {
            idNomenclature: idNomenclature,
            type: type
        },
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch' + type).DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el nomenclador este siendo utilizado en otro componente. Verifique que el nomenclador haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function fillNomenclatureModal(edit_button, type) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var name = row.children[0].children[0].textContent;
    cleanNomenclatureModal(true);
    var idNomenclature = row.children[1].children[1].value;
    $('#editName').val(name);
    $('#type').val(type);
    $('#editNomenclatureModalTitle').empty();
    switch (type) {
        case 1:
            $('#editNomenclatureModalTitle').text('Acciones de Control');
            break;
        case 2:
            $('#editNomenclatureModalTitle').text('Procedencia de Documento');
            break;
        case 3:
            $('#editNomenclatureModalTitle').text('Tipo de Documento');
            break;
        case 4:
            $('#editNomenclatureModalTitle').text('Cargo');
            break;

    }
    $('#editNomenclatureModalTitle').append('<div class="pull-right hidden" id="progress-bar-edit">' +
        '<div class="col-sm-1 text-center center-scale">' +
        '<div class="progress-circle-indeterminate m-t-0" data-color="primary"></div></div></div>');

    $('#idNomenclature').text(idNomenclature);
    $("#edit-nomenclature").unbind();
    $('#edit-nomenclature').click(function (e) {
        if (validateNomenclatureFieldsModal(true)) {
            editNomenclature(row);
        }
    });
    $('#editAppModalNomenclature').modal('show');
}
function editNomenclature(row) {
    var type = $('#type').val();
    var name = $('#editName').val();
    var idNomenclature = $('#idNomenclature').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-nomenclature').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idNomenclature,
            name: name,
            type: type,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].removeChild(row.children[0].children[0]);
                var p1 = document.createElement("p");
                p1.textContent = name;
                row.children[0].appendChild(p1);
                setTimeout(function () {
                    $("#editAppModalNomenclature").modal('hide');
                }, 1000);

            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-nomenclature').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function cleanNomenclatureModal(edit) {
    if (edit) {
        $('#editname').val("");
        $('#editModalMessage').empty();
    } else {
        $('#name').val("");
        $('#modalMessage').empty();
    }
}
function fillEfficacyModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var efficacyId = row.children[row.children.length - 1].children[1].value;
    cleanEfficacyModal();
    $.ajax({
            type: "GET",
            url: "efficacy/data",
            data: {
                id: efficacyId
            },
            success: function (data) {
                if (data['success']) {
                    $('#efficacyPercent').val(data['percent']);
                    $('#add-efficacy-button').unbind();
                    $('#add-efficacy-button').click(function () {
                        if (validateEfficacyModal()) {
                            editEfficacy(row, efficacyId);
                        }
                    });
                    $('#efficacyModal').modal('show');
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        }
    );
}
function validateEfficacyModal() {
    var percent = $('#efficacyPercent').val();
    if (percent == '') {
        showMessage("efficacyModalMessage", "El campo Porcentaje no puede estar vacío", false);
        return false;
    }
    if (!validateDecimalNumber(percent)) {
        showMessage("efficacyModalMessage", "El campo Porcentaje debe ser numérico", false);
        return false;
    }
    return true;
}
function editEfficacy(row, efficacyId) {
    var percent = $('#efficacyPercent').val();
    percent = percent.replace(',', '.');
    $('#progress-bar-edit-efficacy').attr("class", "pull-right");
    $('#add-efficacy-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "efficacy/edit",
        data: {
            id: efficacyId,
            percent: percent
        },
        success: function (data) {
            if (data['success']) {
                showMessage("efficacyModalMessage", "Operación exitosa", true);
                row.children[1].textContent = percent + "%";
                setTimeout(function () {
                    $("#efficacyModal").modal('hide');
                }, 1000);

            } else {
                showMessage("efficacyModalMessage", data['message'], false);
            }
        }
        ,
        complete: function () {
            $('#progress-bar-edit-efficacy').attr("class", "pull-right hidden");
            $('#add-efficacy-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    })
    ;
}
function cleanEfficacyModal() {
    $('#efficacyPercent').val('');
    $('#efficacyModalMessage').empty('');
}
