/**************************************REPORT*******************************************/
function exportReport() {

    $('#progress-bar-export-report').attr("class", "progress-bar-container");
    $('#exportReport').attr("class", "disabled");
    $.ajax({
        type: "POST",
        url: "create_report",
        success: function (data) {
            if (data['success']) {
                redirectURL("export");
            } else {
                showNotification(data['message'], "danger");
            }
        },
        complete: function () {
            $('#progress-bar-export-report').attr("class", "progress-bar-container hidden");
            $('#exportReport').removeAttr("class");
        }
    });
}
function exportReportRisk(button) {
    $('#progress-bar-export-report').attr("class", "progress-bar-container");
    button.setAttribute("class", "exportReport btn btn-success disabled");
    var row = button.parentNode.parentNode.parentNode.parentNode;
    var idRisk = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "create_report",
        data: {
            report: 2,
            idRisk: idRisk
        },
        success: function (data) {
            if (data['success']) {
                location.href = "export?report=2";
            } else {
                showNotification(data['message'], "danger");
            }
        },
        complete: function () {
            $('#progress-bar-export-report').attr("class", "progress-bar-container hidden");
            button.setAttribute("class", "exportReport btn btn-success");
        }
    });
}
/**************************************NAV BAR FUNCTION*******************************************/
function onProcessChange(select, path) {
    var id = select.value;
    $.ajax({
        type: "GET",
        url: path,
        data: {id: id},
        success: function (data) {
            if (!data) {
                showNotification("Operación fallida. Ha ocurrido un error crítico de base de datos", "danger", 5000);
            } else {
                document.location.reload(true);
            }
        }
    });
}
function validateChangePasswordModal() {
    var oldPassword = $('#oldPassword').val();
    var newPassword = $('#newPassword').val();
    var confirmPassword = $('#confirmNewPassword').val();
    if (oldPassword == '' || newPassword == '' || confirmPassword == '') {
        showMessage('changePasswordMessage', 'Debe completar todos los campos', false);
        return false;
    }
    if (confirmPassword != newPassword) {
        showMessage('changePasswordMessage', 'Las contraseñas no coinciden', false);
        $('#newPassword').val('');
        $('#confirmNewPassword').val('');
        return false;
    }
    if (!validateStrongPassword(newPassword)) {
        showMessage('changePasswordMessage', "La contraseña no es lo suficientemente fuerte. " +
            "Asegúrese de que la contraseña contenga al menos 8 caracteres, una letra mayúscula, caracteres especiales y números", false);
        return false;
    }
    return true;
}
function cleanChangePasswordModal() {
    $('#newPassword').val('');
    $('#confirmNewPassword').val('');
    $('#oldPassword').val('');
    $('#changePasswordMessage').empty();
}
function changePassword(path) {
    var oldPassword = $('#oldPassword').val();
    var newPassword = $('#newPassword').val();
    $('#progress-bar-password').attr("class", "pull-right");
    $('#change-password').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: path,
        data: {
            oldPassword: oldPassword,
            newPassword: newPassword
        },
        success: function (data) {
            if (data['success']) {
                showMessage("changePasswordMessage", "Se ha cambiado la contraseña", true);
                setTimeout(function () {
                    $("#changePasswordModal").modal('hide');
                    cleanChangePasswordModal();
                }, 1000);
            } else {
                showMessage("changePasswordMessage", data['message'], false);
                $('#oldPassword').val('');
            }
        },
        complete: function () {
            $('#progress-bar-password').attr("class", "pull-right hidden");
            $('#change-password').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function fillProfileModal(contextPath) {
    cleanProfileModal();
    $.ajax({
            type: "GET",
            url: contextPath + "/profile/data",
            success: function (data) {
                if (data['success']) {
                    $('#profileName').val(data['name']);
                    $('#profileLastName').val(data['lastName']);
                    $('#profileEmail').val(data['email']);
                    $('#profileUserName').val(data['userName']);
                    $('#profileIdentification').val(data['identification']);
                    var ocupation = data['ocupation'];
                    var editOcupation = document.getElementById('profileOcupation');
                    for (var i = 0; i < editOcupation.options.length; i++) {
                        if (editOcupation.options[i].value == ocupation) {
                            editOcupation.selectedIndex = i;
                        }
                    }
                    var profilePathPhoto = data['pathPhoto'];
                    if (profilePathPhoto != null && [profilePathPhoto != '']) {
                        var img = document.createElement('img');
                        img.setAttribute('src', profilePathPhoto);
                        var profilePhoto = document.getElementById('profilePhoto');
                        profilePhoto.appendChild(img);
                        $('#profilePhotoDiv').attr('class', 'fileinput fileinput-exists');
                    }
                    $('#profileModal').modal('show');
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        }
    );
}
function cleanProfileModal() {
    $('#profileName').val("");
    $('#profileLastName').val("");
    $('#profileUserName').empty();
    $('#profileOcupation').val("-1");
    $('#profileEmail').val("");
    $('#profileIdentification').val("");
    $('#profilePhoto').empty();
    $('#profilePhotoDiv').attr('class', ' fileinput fileinput-new');
    $('#profileMessage').empty();
}
function validateProfileModal() {
    var name = $('#profileName').val();
    var lastName = $('#profileLastName').val();
    var ocupation = $('#profileOcupation').val();
    var identification = $('#profileIdentification').val();
    var email = $('#profileEmail').val();
    if (name == '') {
        showMessage("profileMessage", "El campo Nombre no puede estar vacío", false);
        return false;
    }
    if (lastName == '') {
        showMessage("profileMessage", "El campo Apellidos no puede estar vacío", false);
        return false;
    }
    if (email != '' && !validateEmail(email)) {
        showMessage("profileMessage", "La dirección de correo no tiene un formato correcto", false);
        return false;
    }
    if (ocupation == '-1') {
        showMessage("profileMessage", "Debe seleccionar algún cargo", false);
        return false;
    }
    if (identification != '') {
        if (identification.length != 11) {
            showMessage("profileMessage", "El campo Identificación debe contener al menos 11 caracteres", false);
            return false;
        } else if (!validateNumber(identification)) {
            showMessage("profileMessage", "El campo Identificación debe contener solamente números", false);
            return false;
        }
    }
    return true;
}
function saveProfile(contextPath) {
    var name = $('#profileName').val();
    var lastName = $('#profileLastName').val();
    var userName = $('#profileUserName').val();
    var identification = $('#profileIdentification').val();
    var ocupation = $('#profileOcupation').val();
    var email = $('#profileEmail').val();
    var pathPhoto = null;
    if ($('#profilePhoto').children().length > 0) {
        var pathPhoto = $('#profilePhoto').children()[0].getAttribute('src');
    }
    $('#progress-bar-profile').attr("class", "pull-right");
    $('#buttonSaveProfile').attr("class", "btn btn-primary  btn-cons pull-left disabled");
    $.ajax({
            type: "POST",
            url: contextPath + "/profile/save",
            data: {
                name: name,
                lastName: lastName,
                userName: userName,
                identification: identification,
                ocupation: ocupation,
                pathPhoto: pathPhoto,
                email: email
            },
            success: function (data) {
                if (data['success']) {
                    showMessage("profileMessage", "Operación exitosa", true);
                    setTimeout(function () {
                        $("#profileModal").modal('hide');
                        cleanProfileModal();
                    }, 1000);
                } else {
                    showMessage("profileMessage", data['message'], false);
                }
            },
            complete: function () {
                $('#progress-bar-profile').attr("class", "pull-right hidden");
                $('#buttonSaveProfile').attr("class", "btn btn-primary  btn-cons pull-left");
            }
        }
    );
}
function exportEntityData(contextPath) {
    $('#progress-bar-export-entity-data').attr("class", "pull-right");
    $('#exportEntityData').attr("class", "disabled");
    $.ajax({
        type: "POST",
        url: contextPath + "/entity/create_report",
        success: function (data) {
            if (data['success']) {
                redirectURL(contextPath + "/entity/export");
            } else {
                showMessage("entityMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-export-entity-data').attr("class", "pull-right hidden");
            $('#exportEntityData').removeAttr("class");
        }
    });
}

function deleteNotification(contextPath) {
    $('#progress-bar-notification').attr("class", "progress");
    $('#delete-notification').attr("class", "disabled");
    $.ajax({
        type: "POST",
        url: contextPath + "/notification/delete",
        success: function (data) {
            if (data['success']) {
                $("#notification-body").empty();
                $("#notification-footer").empty();
                $("#notification-footer").append("<p>No hay notificaciones</p>");
                $("#notification-drop-down").empty();
            } else {
                showNotification(data['message'], "danger");
            }
        },
        complete: function () {
            $('#progress-bar-notification').attr("class", "progress hidden");
            $('#delete-notification').removeAttr("class");
        }
    });
}
function changeNotificationState(mark, contextPath) {
    var notification_item = mark.parentNode.parentNode;
    var read = false;
    if (notification_item.getAttribute("class").indexOf("unread") != -1) {
        notification_item.setAttribute("class", "notification-item clearfix");
        mark.parentNode.dataset['originalTitle'] = 'Marcar como no leída'
        read = true;
    } else {
        notification_item.setAttribute("class", "notification-item unread clearfix");
        mark.parentNode.dataset['originalTitle'] = 'Marcar como leída'
    }
    var id = mark.parentNode.children[mark.parentNode.children.length - 1].value;
    $.ajax({
        type: "POST",
        url: contextPath + "/notification/changeState",
        data: {
            id: id,
            read: read
        },
        success: function (data) {
            if (!data['success']) {
                showNotification(data['message'], "danger");
            }
        }
    });
}
/**************************************LICENCE*******************************************/
function validateLicence() {
    var haveFile = false;
    jQuery.each(jQuery('#file')[0].files, function (i, file) {
        haveFile = true;
    });
    if (!haveFile) {
        showNotification("Debe añadir un archivo clave", "danger");
    }
    return true;
}
function addLicence() {
    var data = new FormData();
    jQuery.each(jQuery('#file')[0].files, function (i, file) {
        data.append(file['name'].split(".")[0], file);
    });
    $.ajax({
            type: "POST",
            url: "licence/upload",
            data: data,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data['success']) {
                    redirectURL("home");
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        }
    );
}
/**************************************MESSAGE*******************************************/
function showNotification(message, type, time) {
    if (!time) {
        time = 2000;
    }
    $('body').pgNotification({
        style: 'flip',
        message: message,
        position: "top-right",
        timeout: time,
        type: type
    }).show();
}
function showMessage(component, message, success) {
    $('#' + component).empty();
    if (success) {
        $('#' + component).append("<div class='alert alert-success' role='alert'>"
            + "<button class='close' data-dismiss='alert'></button>" + message + "</div>");
    } else {
        $('#' + component).append("<div class='alert alert-danger' role='alert'>"
            + "<button class='close' data-dismiss='alert'></button>" + message + "</div>");
    }

}
/**************************************SESSION*******************************************/
function pinButton() {
    var pin = sessionStorage.getItem('pin');
    if (pin == "1") {
        sessionStorage.setItem('pin', 2);
    } else {
        sessionStorage.setItem('pin', 1);
    }
}
function activatePinMode() {
    if (!$('#withoutPin')[0]) {
        var pin = sessionStorage.getItem('pin');
        if (pin == "1") {
            $('body').attr('class', 'fixed-header  windows desktop pace-done sidebar-visible menu-pin');
        } else {
            $('body').attr('class', 'fixed-header');
        }
    }
}
/**************************************INIT*******************************************/
var initSelect2Plugin = function () {
    $.fn.select2 && $('[data-init-plugin="select2"]').each(function () {
        $(this).select2({minimumResultsForSearch: "true" == $(this).attr("data-disable-search") ? -1 : 1}).on("select2-opening", function () {
            $.fn.scrollbar && $(".select2-results").scrollbar({ignoreMobile: !1})
        })
    })
};
function changeEntity(id) {
    $.ajax({
        type: "POST",
        url: "supervisoryHome/changeEntity",
        data: {id: id},
        success: function (data) {
            if (!data) {
                showNotification("Operación fallida. Ha ocurrido un error crítico de base de datos", "danger", 5000);
            } else {
                document.location.reload(true);
            }
        }
    });
}
function undoEntity() {
    $.ajax({
        type: "POST",
        url: "supervisoryHome/undoEntity",
        success: function (data) {
            if (!data) {
                showNotification("Operación fallida. Ha ocurrido un error crítico de base de datos", "danger", 5000);
            } else {
                document.location.reload(true);
            }
        }
    });
}

function enterEntity(id) {
    $.ajax({
        type: "POST",
        url: "supervisoryHome/enterEntity",
        data: {id: id},
        success: function (data) {
            if (!data) {
                showNotification("Operación fallida. Ha ocurrido un error crítico de base de datos", "danger", 5000);
            } else {
                redirectURL("supervisoryInit");
            }
        }
    });
}
/**************************************GENERAL*******************************************/
function redirectURL(url) {
    location.href = url;
}
/**************************************VALIDATION*******************************************/
function validateNumber(value) {
    var r_exp = /^[0-9]+$/;
    return r_exp.test(value);
}

function validateDecimalNumber(value) {
    var r_exp = /^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$/;
    return r_exp.test(value);
}
function validateEmail(value) {
    var r_exp = /^([a-z]+(.[a-z]+)*[0-9]*)+@[a-z]+(.[a-z]+)*$/;
    return r_exp.test(value);
}

function validateDate(value) {
    var date = value.split("/");
    return Date.now() < Date.parse(date[1] + "/" + date[0] + "/" + date[2]);
}
function validatePassword(input) {
    var span = $(input.parentNode.children[input.parentNode.children.length - 1]);
    if (input.value.length > 0) {
        if (validateStrongPassword(input.value)) {
            span.attr("class", "input-group-addon text-success");
            span.text("Fuerte");
        } else if (validateMediumPassword(input.value)) {
            span.attr("class", "input-group-addon text-warning");
            span.text("Medio");
        } else {
            span.attr("class", "input-group-addon text-danger");
            span.text("Débil");
        }
    } else {
        span.text("");
    }
}

function validateStrongPassword(value) {
    var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
    return strongRegex.test(value);
}
function validateMediumPassword(value) {
    var mediumRegex = new RegExp("^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})");
    return mediumRegex.test(value);
}
