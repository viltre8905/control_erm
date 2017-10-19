function fillUserModal(edit_button) {
    cleanUserModal(true);
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var userName = row.children[0].children[0].textContent;
    var idUser = row.children[row.children.length - 1].children[1].value;
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                username: userName
            },
            success: function (data) {
                if (data['success']) {
                    $('#userNameTitle').text(userName);
                    $('#editName').val(data['name']);
                    $('#editUsername').val(userName);
                    $('#editPassword').val("");
                    $('#editLastName').val(data['lastName']);
                    $('#editEmail').val(data['email']);
                    $('#editIdentification').val(data['identification']);
                    var ocupation = data['ocupation'];
                    var editOcupation = document.getElementById('editOcupation');
                    for (var i = 0; i < editOcupation.options.length; i++) {
                        if (editOcupation.options[i].value == ocupation) {
                            editOcupation.selectedIndex = i;
                        }
                    }
                    var roleList = data['roleList'];
                    for (var i = 0; i < roleList.length; i++) {
                        $('#edit_checkbox_roles' + roleList[i]).attr('checked', 'checked');
                    }
                    var pathPhoto = data['pathPhoto'];
                    if (pathPhoto != null && pathPhoto != '') {
                        var img = document.createElement('img');
                        img.setAttribute('src', pathPhoto);
                        var editPhoto = document.getElementById('editPhoto');
                        editPhoto.appendChild(img);
                        $('#editPhotoDiv').attr('class', 'fileinput fileinput-exists');
                    }
                    var entity = data['entity'];
                    var editEntity = document.getElementById('editEntity');
                    if (editEntity) {
                        for (i = 0; i < editEntity.options.length; i++) {
                            if (editEntity.options[i].value == entity) {
                                editEntity.selectedIndex = i;
                            }
                        }
                    }
                    $('#idUser').text(idUser);
                    $("#edit-user").unbind();
                    $('#edit-user').click(function (e) {
                        if (validateUserFieldsModal('edit_checkbox_roles', true)) {
                            editUser(row);
                        }
                    });
                    $('#editAppModalUser').modal('show');
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        }
    );
}
function validateSelectRole(idCheckbox, edit) {
    var checkboxContainerList = $('#' + idCheckbox).children();
    var result = false;
    var isMember = false;
    var isSecretary = false;
    for (var i = 0; i < checkboxContainerList.length; i++) {
        var checkbox = checkboxContainerList[i].children[0];
        if (checkbox.checked) {
            result = true;
            if (checkboxContainerList[i].children[1].textContent.indexOf("Miembro")!=-1) {
                isMember = true;
            }
            if (checkboxContainerList[i].children[1].textContent.indexOf("Secretario")!=-1) {
                isSecretary = true;
            }
        }
    }
    var modalMessage = "modalMessage";
    if (edit) {
        modalMessage = "editModalMessage";
    }
    if (!result) {
        showMessage(modalMessage, "Debe seleccionar al menos un rol", false);
    } else if (isMember && isSecretary) {
        showMessage(modalMessage, "No puede ser miembro y secretario del comité de control", false);
        return false;
    }
    return result;
}
function addUser() {
    var userName = $('#username').val();
    var password = $('#password').val();
    var name = $('#name').val();
    var lastName = $('#lastName').val();
    var identification = $('#identification').val();
    var ocupation = $('#ocupation').val();
    var email = $('#email').val();
    var entity = $('#entity').val();
    var pathPhoto = null;
    if ($('#photo').children().length > 0) {
        var pathPhoto = $('#photo').children()[0].getAttribute('src');
    }
    var roles = "{";
    var body;
    var checkboxContainerList = $('#checkbox_roles').children();
    for (var i = 0; i < checkboxContainerList.length; i++) {
        var checkbox = checkboxContainerList[i].children[0];
        if (checkbox.checked) {
            if (body == null) {
                body = "'";
            } else {
                body += ",'";
            }
            body += checkbox.getAttribute('name') + "':'" + checkbox.getAttribute('name') + "'";
        }
    }
    roles += body + "}";
    $('#progress-bar-add').attr("class", "pull-right");
    $('#buttonAdd').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
            type: "POST",
            url: "create",
            data: {
                username: userName,
                password: password,
                name: name,
                lastName: lastName,
                identification: identification,
                ocupation: ocupation,
                pathPhoto: pathPhoto,
                roles: roles,
                email: email,
                entity: entity,
                action: "add"
            },
            success: function (data) {
                if (data['success']) {
                    showMessage("modalMessage", "Operación exitosa", true);
                    var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                        '<button class="btn btn-success" type="button"' +
                        ' onclick="fillUserModal(this)"><i class="fa fa-pencil"></i></button>' +
                        '<button class="btn btn-success" type="button" onclick="deleteUser(this)"><i' +
                        ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                    var active = '<input id="switchery' + data['id'] + '" type="checkbox" data-init-plugin="switchery"' +
                        ' checked="checked" onchange="userStatus(this)"/>';
                    var group = "";
                    var rolesArray = data['rolesName'];
                    for (var i = 0; i < rolesArray.length; i++) {
                        group += "<p>" + rolesArray[i] + "</p>";
                    }
                    var table = $('#tableWithSearch');
                    if (entity) {
                        var select = document.getElementById("entity");
                        var entityText = select.options[select.selectedIndex].text;
                        table.dataTable().fnAddData([
                            "<p>" + userName + "</p>",
                            name,
                            email,
                            group,
                            active,
                            entityText,
                            text
                        ]);
                    } else {
                        table.dataTable().fnAddData([
                            "<p>" + userName + "</p>",
                            name,
                            email,
                            group,
                            active,
                            text
                        ]);
                    }
                    var switcheryId = "#switchery" + data['id'];
                    new Switchery($(switcheryId).get(0), {color: $.Pages.getColor("success")});
                    setTimeout(function () {
                        $("#addNewAppModal").modal('hide');
                        cleanUserModal();
                    }, 1000);
                } else {
                    showMessage("modalMessage", data['message'], false);
                }
            },
            complete: function () {
                $('#progress-bar-add').attr("class", "pull-right hidden");
                $('#buttonAdd').attr("class", "btn btn-primary  btn-cons pull-right");
            }
        }
    );
}
function editUser(row) {
    var idUser = $('#idUser').text();
    var userName = $('#editUsername').val();
    var password = $('#editPassword').val();
    var name = $('#editName').val();
    var lastName = $('#editLastName').val();
    var identification = $('#editIdentification').val();
    var ocupation = $('#editOcupation').val();
    var email = $('#editEmail').val();
    var entity = $('#editEntity').val();
    var pathPhoto = null;
    if ($('#editPhoto').children().length > 0) {
        var pathPhoto = $('#editPhoto').children()[0].getAttribute('src');
    }
    var roles = "{";
    var body;
    var checkboxContainerList = $('#edit_checkbox_roles').children();
    for (var i = 0; i < checkboxContainerList.length; i++) {
        var checkbox = checkboxContainerList[i].children[0];
        if (checkbox.checked) {
            if (body == null) {
                body = "'";
            } else {
                body += ",'";
            }
            body += checkbox.getAttribute('name') + "':'" + checkbox.getAttribute('name') + "'";
        }
    }
    roles += body + "}";
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-user').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idUser,
            username: userName,
            password: password,
            name: name,
            lastName: lastName,
            identification: identification,
            ocupation: ocupation,
            pathPhoto: pathPhoto,
            roles: roles,
            email: email,
            entity: entity,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                var rolesArray = data['rolesName'];
                for (var i = 0; i < row.children[3].children.length; i++) {
                    row.children[3].removeChild(row.children[3].children[i--]);
                }
                for (i = 0; i < rolesArray.length; i++) {
                    var p = document.createElement("p");
                    p.textContent = rolesArray[i];
                    row.children[3].appendChild(p);
                }
                row.children[0].removeChild(row.children[0].children[0]);
                var p1 = document.createElement("p");
                p1.textContent = userName;
                row.children[0].appendChild(p1);
                row.children[1].textContent = name;
                row.children[2].textContent = email;
                if (entity) {
                    var select = document.getElementById("editEntity");
                    row.children[5].textContent = select.options[select.selectedIndex].text;
                }
                setTimeout(function () {
                    $("#editAppModalUser").modal('hide');
                }, 1000);

            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit').attr("class", "pull-right hidden");
            $('#edit-user').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteUser(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var user = row.children[0].children[0].textContent;

    $.ajax({
        type: "POST",
        url: "delete",
        data: {username: user},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida. Una posible causa es que el usuario este siendo utilizado en otro componente. Verifique que el usuario haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function userStatus(toogle_button) {

    var row = toogle_button.parentNode.parentNode;
    var user = row.children[0].children[0].textContent;

    $.ajax({
        type: "POST",
        url: "status",
        data: {username: user, status: toogle_button.checked},
        success: function (data) {
            if (data) {
                showNotification("Operación exitosa", "success");

            } else {
                showNotification("Operación fallida", "danger");
            }
        }
    });
}
function validateUserFieldsModal(idCheckbox, edit) {
    if (edit) {
        var userName = $('#editUsername').val();
        var name = $('#editName').val();
        var lastName = $('#editLastName').val();
        var ocupation = $('#editOcupation').val();
        var email = $('#editEmail').val();
        var password = $('#editPassword').val();
        var identification = $('#editIdentification').val();
        if ($('#editPhoto').children().length > 0) {
            var pathPhoto = $('#editPhoto').children()[0].getAttribute('src');
        }
        var entity = $('#editEntity').val();
        var modalMessage = "editModalMessage";
    } else {
        userName = $('#username').val();
        name = $('#name').val();
        lastName = $('#lastName').val();
        password = $('#password').val();
        ocupation = $('#ocupation').val();
        email = $('#email').val();
        identification = $('#identification').val();
        if ($('#photo').children().length > 0) {
            pathPhoto = $('#photo').children()[0].getAttribute('src');
        }
        if (password == '') {
            var flag = true;
        }
        entity = $('#entity').val();
        var modalMessage = "modalMessage";
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío", false);
        return false;
    }
    if (lastName == '') {
        showMessage(modalMessage, "El campo Apellidos no puede estar vacío", false);
        return false;
    }
    if (ocupation == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún cargo", false);
        return false;
    }
    if (userName == '') {
        showMessage(modalMessage, "El campo Usuario no puede estar vacío", false);
        return false;
    }
    if (flag) {
        showMessage(modalMessage, "El campo Contraseña no puede estar vacío", false);
        return false;
    }
    if (password != '') {
        if (!validateStrongPassword(password)) {
            showMessage(modalMessage, "La contraseña no es lo suficientemente fuerte." +
                " Asegúrese de que la contraseña contenga al menos 8 caracteres, una letra mayúscula, caracteres especiales y números", false);
            return false;
        }
    }
    if (email != '' && !validateEmail(email)) {
        showMessage(modalMessage, "La dirección de correo no tiene un formato correcto", false);
        return false;
    }
    if (identification != '') {
        if (identification.length != 11) {
            showMessage(modalMessage, "El campo Identificación debe contener al menos 11 caracteres", false);
            return false;
        } else if (!validateNumber(identification)) {
            showMessage(modalMessage, "El campo Identificación debe contener solamente números", false);
            return false;
        }
    }
    if (entity && entity == '-1') {
        showMessage(modalMessage, "Debe seleccionar alguna Entidad", false);
        return false;
    }
    return validateSelectRole(idCheckbox, edit);
}
function cleanUserModal(edit) {
    if (edit) {
        $('#editUsername').val("");
        $('#editName').val("");
        $('#editLastName').val("");
        $('#editOcupation').val("-1");
        $('#editEmail').val("");
        $('#editIdentification').val("");
        $('#editPassword').val("");
        if ($('#editEntity').val()) {
            $('#editEntity').val("-1")
        }
        var divContain = $('#edit_checkbox_roles');
        for (var i = 0; i < divContain.children().length; i++) {
            var checkbox = divContain.children()[i].children[0];
            checkbox.checked = false;
        }
        $('#editPhoto').empty();
        $('#editPhotoDiv').attr('class', ' fileinput fileinput-new');
        $('#editModalMessage').empty();
    } else {
        $('#username').val("");
        $('#name').val("");
        $('#email').val("");
        $('#lastName').val("");
        $('#identification').val("");
        $('#password').val("");
        $('#ocupation').val("-1");
        if ($('#entity').val()) {
            $('#entity').val("-1")
        }
        var divContain = $('#checkbox_roles');
        for (var i = 0; i < divContain.children().length; i++) {
            var checkbox = divContain.children()[i].children[0];
            checkbox.checked = false;
        }
        $('#photo').empty();
        $('#photoDiv').attr('class', ' fileinput fileinput-new');
        $('#modalMessage').empty();
    }
}
