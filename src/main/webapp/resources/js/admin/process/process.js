function validateProcess(edit) {
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
function addProcess(isSubProcess) {
    var name = $('#name').val();
    var responsible = $('#responsible').val();
    var description = $('#description').val();
    var requestData = {
        name: name,
        responsible: responsible,
        description: description,
        action: "add"
    };
    if (isSubProcess) {
        var idProcess = $('#idProcess').val();
        requestData['idProcess'] = idProcess;
    }
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-process').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: requestData,
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillProcessModal(this,' + isSubProcess + ')"><i class="fa fa-pencil"></i></button>';

                if (!isSubProcess) {
                    text += '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Ver Sub-Procesos"' +
                        ' href="sub-process/sub-processes?id=' + data['id'] + '"><i class="pg-indent"></i></a>';
                    var extraParam = "";
                } else {
                    extraParam = "&processId=" + idProcess;
                }
                text += '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Ver Actividades"' +
                    ' href="activity/activities?id=' + data['id'] + extraParam + '"><i class="fa fa-table"></i></a>' +
                    '<button class="btn btn-success" type="button" onclick="deleteProcess(this,' + isSubProcess + ')"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                var select = document.getElementById("responsible");
                var responsibleText = select.options[select.selectedIndex].text;
                var member = '<select id="members" onchange="setMembers(this,true);" class=" full-width"' +
                    ' data-init-plugin="select2" multiple>';
                var membersIdArray = data['membersId'];
                var membersNameArray = data['membersName'];
                for (var i = 0; i < membersIdArray.length; i++) {
                    member += '<option value="' + membersIdArray[i] + '">' + membersNameArray[i] + '</option>';
                }
                member += '</select>';
                table.dataTable().fnAddData([
                    name,
                    description,
                    responsibleText,
                    member,
                    text
                ]);
                initSelect2Plugin();
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanProcessModal();
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
function setMembers(select, isSubProcess) {
    var row = select.parentNode.parentNode;
    var idProccess = row.children[4].children[1].value;
    var members = "{";
    var body = "";
    var value = 1;
    for (var i = 0; i < select.options.length; i++) {
        if (select.options[i].selected) {
            if (body == "") {
                body = "'";
            } else {
                body += ",'";
            }
            body += "value " + select.options[i].value + "':'" + select.options[i].value + "'";
            value++;
        }
    }
    members += body + "}";
    var requestData = {
        id: idProccess,
        members: members
    };
    if (isSubProcess) {
        var idProcess = $('#idProcess').val();
        requestData['idProcess'] = idProcess;
    }
    $.ajax({
        type: "POST",
        url: "members",
        data: requestData,
        success: function (data) {
            if (data['success'] && data['message-warning']) {
                showNotification(data['message-warning'], "warning", 8000);
            } else if (!data['success'] && data['message-error']) {
                showNotification(data['message-error'], "danger");
            }
        }
    });

}
function deleteProcess(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idProccess = row.children[4].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idProccess},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el proceso este siendo utilizado en otro componente. Verifique que el proceso haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function fillProcessModal(edit_button, isSubProcess) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idProccess = row.children[4].children[1].value;
    cleanProcessModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idProccess
            },
            success: function (data) {
                if (data['success']) {
                    $('#processTitle').text(data['name']);
                    $('#editName').val(data['name']);
                    var responsible = data['responsible'];
                    var editResponsible = document.getElementById('editResponsible');
                    for (var i = 0; i < editResponsible.options.length; i++) {
                        if (editResponsible.options[i].value == responsible) {
                            editResponsible.selectedIndex = i;
                        }
                    }
                    $('#editDescription').val(data['description']);
                    if (isSubProcess) {
                        $('#idSubProccess').text(idProccess);
                    } else {
                        $('#idProccess').text(idProccess);
                    }
                    $("#edit-process").unbind();
                    $('#edit-process').click(function (e) {
                        if (validateProcess(true)) {
                            editProccess(isSubProcess, row);
                        }
                    });
                    $('#editAppModalProcess').modal('show');
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
function editProccess(isSubProcess, row) {
    var name = $('#editName').val();
    var responsible = $('#editResponsible').val();
    var description = $('#editDescription').val();
    var requestData = {
        name: name,
        responsible: responsible,
        description: description,
        action: "edit"
    };
    if (isSubProcess) {
        var idProccess = $('#idProcess').val();
        var id = $('#idSubProccess').text();
        requestData['idProcess'] = idProccess;
        requestData['id'] = id;
    } else {
        idProccess = $('#idProccess').text();
        requestData['id'] = idProccess;
    }
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-process').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: requestData,
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                var select = document.getElementById("editResponsible");
                var componentText = select.options[select.selectedIndex].text;
                row.children[0].textContent = name;
                row.children[1].textContent = description;
                row.children[2].textContent = componentText;
                if (isSubProcess) {
                    for (var i = 0; i < row.children[3].children.length; i++) {
                        row.children[3].removeChild(row.children[3].children[i--]);
                    }
                    var selectMembers = document.createElement("select");
                    selectMembers.setAttribute("id", "members");
                    selectMembers.setAttribute("onchange", "setMembers(this," + isSubProcess + ");");
                    selectMembers.setAttribute("class", "full-width");
                    selectMembers.setAttribute("data-init-plugin", "select2");
                    selectMembers.setAttribute("multiple", "");
                    var membersIdArray = data['membersId'];
                    var membersNameArray = data['membersName'];
                    var selectedMemberIdArray = data['selectedMemberId'];
                    for (i = 0; i < membersIdArray.length; i++) {
                        var isSelected = false;
                        for (var j = 0; j < selectedMemberIdArray.length; j++) {
                            if (membersIdArray[i] == selectedMemberIdArray[j]) {
                                isSelected = true;
                            }
                        }
                        var option = document.createElement("option");
                        option.setAttribute("value", membersIdArray[i]);
                        if (isSelected) {
                            option.setAttribute("selected", "selected");
                        }
                        option.textContent = membersNameArray[i];
                        selectMembers.appendChild(option);
                    }
                    row.children[3].appendChild(selectMembers);
                    initSelect2Plugin();
                }
                setTimeout(function () {
                    $("#editAppModalProcess").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-process').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });

}
function cleanProcessModal(edit) {
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
