function validateGuideFieldsModal(edit) {
    var name = $('#name').val();
    var component = $('#component').val();
    var process = $('#process').val();
    var modalMessage = "modalMessage";
    if (edit) {
        name = $('#editName').val();
        component = $('#editComponent').val();
        process = $('#editProcess').val();
        modalMessage = "editModalMessage";
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío", false);
        return false;
    }
    if (component == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún componente", false);
        return false;
    }
    if (process == '-1') {
        showMessage(modalMessage, "Debe seleccionar algún proceso o sub-proceso", false);
        return false;
    }
    return true;
}
function addGuide() {
    var name = $('#name').val();
    var description = $('#description').val();
    var component = $('#component').val();
    var process = $('#process').val();
    $('#progress-bar-add').attr("class", "pull-right");
    $('#add-guide').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            name: name,
            description: description,
            component: component,
            process: process,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillGuideModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<a class="btn btn-success" data-toggle="tooltip" data-original-title="Preguntas"' +
                    ' href="question/questions?id=' + data['id'] + '"><i class="fa fa-question"></i></a>' +
                    '<button class="btn btn-success" type="button" onclick="deleteGuide(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                var select = document.getElementById("component");
                var componentText = select.options[select.selectedIndex].text;
                select = document.getElementById("process");
                var processText = select.options[select.selectedIndex].text;
                table.dataTable().fnAddData([
                    name,
                    description,
                    componentText,
                    processText,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanGuideModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add').attr("class", "pull-right hidden");
            $('#add-guide').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteGuide(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idGuide = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idGuide},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida- Una posible causa es que el cuestionario este siendo utilizado en otro componente- Verifique que el cuestionario haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function fillGuideModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idGuide = row.children[row.children.length - 1].children[1].value;
    cleanGuideModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idGuide
            },
            success: function (data) {
                if (data['success']) {
                    $('#guideTitle').text(data['name']);
                    $('#editName').val(data['name']);
                    var component = data['component'];
                    var editComponent = document.getElementById('editComponent');
                    for (var i = 0; i < editComponent.options.length; i++) {
                        if (editComponent.options[i].value == component) {
                            editComponent.selectedIndex = i;
                        }
                    }
                    var process = data['process'];
                    var editProcess = document.getElementById('editProcess');
                    for (i = 0; i < editProcess.options.length; i++) {
                        if (editProcess.options[i].value == process) {
                            editProcess.selectedIndex = i;
                        }
                    }
                    $('#editDescription').val(data['description']);
                    $('#idGuide').text(idGuide);
                    $("#edit-guide").unbind();
                    $('#edit-guide').click(function (e) {
                        if (validateGuideFieldsModal(true)) {
                            editGuide(row);
                        }
                    });
                    $('#editAppModalGuide').modal('show');
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
function editGuide(row) {
    var name = $('#editName').val();
    var component = $('#editComponent').val();
    var process = $('#editProcess').val();
    var description = $('#editDescription').val();
    var idGuide = $('#idGuide').text();
    $('#progress-bar-edit').attr("class", "pull-right");
    $('#edit-guide').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idGuide,
            name: name,
            component: component,
            process: process,
            description: description,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true)
                var select = document.getElementById("editComponent");
                var componentText = select.options[select.selectedIndex].text;
                select = document.getElementById("editProcess");
                var processText = select.options[select.selectedIndex].text;
                row.children[0].textContent = name;
                row.children[1].textContent = description;
                row.children[2].textContent = componentText;
                row.children[3].textContent = processText;
                setTimeout(function () {
                    $("#editAppModalGuide").modal('hide');
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
function cleanGuideModal(edit) {
    if (edit) {
        $('#editName').val("");
        $('#editDescription').val("");
        $('#editComponent').val("-1");
        $('#editProcess').val("-1");
        $('#editModalMessage').empty();
    } else {
        $('#name').val("");
        $('#description').val("");
        $('#component').val("-1");
        $('#process').val("-1");
        $('#modalMessage').empty();
    }
}
/**************************************ADMIN ASPECT*******************************************/
function validateAspect(edit) {
    var name = $('#aspectName').val();
    var number = $('#aspectNumber').val();
    var modalMessage = "aspectModalMessage";
    if (edit) {
        name = $('#editAspectName').val();
        number = $('#editAspectNumber').val();
        modalMessage = "editAspectModalMessage";
    }
    if (name == '') {
        showMessage(modalMessage, "El campo Nombre no puede estar vacío", false);
        return false;
    }
    if (number == '') {
        showMessage(modalMessage, "El campo Número no puede estar vacío", false);
        return false;
    } else if (!validateNumber(number)) {
        showMessage(modalMessage, "El campo Número debe ser un número entero", false);
        return false;
    }
    return true;
}
function addAspect() {
    var name = $('#aspectName').val();
    var number = $('#aspectNumber').val();
    var idGuide = $('#idGuide').val();
    $('#progress-bar-add-aspect').attr("class", "pull-right");
    $('#add-aspect').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "aspect/create",
        data: {
            name: name,
            number: number,
            idGuide: idGuide,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("aspectModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    var select = document.getElementById("aspectSelect");
                    var option = document.createElement("option");
                    option.setAttribute("value", data["id"]);
                    option.text = name+"- "+number;
                    select.appendChild(option);
                    $("#addNewAppModalAspect").modal('hide');
                    cleanAspectModal();
                }, 1000);

            } else {
                showMessage("aspectModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add-aspect').attr("class", "pull-right hidden");
            $('#add-aspect').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function onAspectChange() {
    var select = document.getElementById("aspectSelect");
    if (select.value != "-1") {
        $("#aspectEditButton").attr("class", "btn btn-primary");
        $("#aspectDeleteButton").attr("class", "btn btn-primary");
        getAllQuestions(select.value);
        $("#aspectPanel").attr("class", "panel panel-default");
        $("#questionPanel").attr("class", "panel panel-default m-b-5");
    } else {
        $("#aspectEditButton").attr("class", "btn btn-primary disabled");
        $("#aspectDeleteButton").attr("class", "btn btn-primary disabled");
        $("#questionPanel").attr("class", "panel panel-default hidden");
        $("#aspectPanel").attr("class", "panel panel-default m-b-5");
    }
}
function fillAspectModal() {
    cleanAspectModal(true);
    var select = document.getElementById("aspectSelect");
    var name = select.options[select.selectedIndex].text;
    $("#editAspectName").val(name.split("- ")[1]);
    $("#editAspectNumber").val(name.split("- ")[0]);
    $("#aspectTitle").text(name.split("- ")[1]);
    $('#editAppModalAspect').modal('show');
}
function editAspect() {
    var name = $('#editAspectName').val();
    var number = $('#editAspectNumber').val();
    var select = document.getElementById("aspectSelect");
    var id = select.value;
    $('#progress-bar-edit-aspect').attr("class", "pull-right");
    $('#edit-aspect').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "aspect/create",
        data: {
            name: name,
            number: number,
            id: id,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editAspectModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    select.options[select.selectedIndex].text = number + "- " + name;
                    $(".select2-chosen").text(number + "- " + name);
                    $("#editAppModalAspect").modal('hide');
                }, 1000);

            } else {
                showMessage("editAspectModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit-aspect').attr("class", "pull-right hidden");
            $('#edit-aspect').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteAspect() {
    $("#alert-modal").modal('show');
    $("#alert-modal-title").text("Si elimina el aspecto se eliminarán automaticamente todas las preguntas relacionadas al aspecto y sus respuestas- ¿Desea continuar con la operación?");
    var alertButton = document.getElementById("alert-modal-button");
    alertButton.setAttribute("onclick", "deleteAspectBody()");
}
function deleteAspectBody() {
    $("#alert-modal").modal('hide');
    var select = document.getElementById("aspectSelect");
    var id = select.value;
    $.ajax({
        type: "POST",
        url: "aspect/delete",
        data: {
            id: id
        },
        success: function (data) {
            if (data) {
                select.removeChild(select.options[select.selectedIndex]);
                $(".select2-chosen").text("Ninguno");
                showNotification("Operación exitosa", "success");
                onAspectChange();
            } else {
                showNotification("Operación fallida- Una posible causa es que el aspecto este siendo utilizado en otro componente- Verifique que el aspecto haya sido desvinculado de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function cleanAspectModal(edit) {
    if (edit) {
        $('#editAspectName').val("");
        $('#editAspectModalMessage').empty();
    } else {
        $('#aspectName').val("");
        $('#aspectModalMessage').empty();
    }
}
function cleanAspectUploadModal() {
    $('#guides').val('-1');
    $('#uploadAspectModalMessage').empty();
}
function validateUploadAspect() {
    var guideId = $('#guides').val();
    if (guideId == '-1') {
        showMessage('uploadAspectModalMessage', "Debe seleccionar al menos un cuestionario", false);
        return false;
    }
    return true;
}
function uploadAspect() {
    var idGuide = $('#idGuide').val();
    var idGuideToLoad = $('#guides').val();
    $('#progress-bar-upload-aspect').attr("class", "pull-right");
    $('#upload-aspect').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "aspect/upload",
        data: {
            idGuide: idGuide,
            idGuideToLoad: idGuideToLoad
        },
        success: function (data) {
            if (data['success']) {
                showMessage("uploadAspectModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    document.location.reload(true);
                }, 1000);
            } else {
                showMessage("uploadAspectModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-upload-aspect').attr("class", "pull-right hidden");
            $('#upload-aspect').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
/**************************************ADMIN QUESTION*******************************************/
function getAllQuestions(id) {
    $.ajax({
        type: "POST",
        url: "questions/all",
        data: {
            id: id
        },
        success: function (data) {
            if (data['success']) {
                var questions = data['questions'];
                var table = $('#tableWithSearch');
                table.dataTable().fnClearTable();
                for (var i = 0; i < questions.length; i++) {
                    var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                        '<button class="btn btn-success" type="button"' +
                        ' onclick="fillQuestionModal(this)"><i class="fa fa-pencil"></i></button>' +
                        '<button id="delete' + questions[i]['id'] + '" class="btn btn-success" type="button" onclick="deleteQuestion(this)"><i' +
                        ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + questions[i]['id'] + '"/>';
                    var procedure = "No";
                    if (questions[i]['procedure']) {
                        procedure = "Si";
                    }
                    table.dataTable().fnAddData([
                        questions[i]['code'],
                        questions[i]['title'],
                        questions[i]['description'],
                        procedure,
                        text
                    ]);
                }

            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        }
    });
}
function validateQuestionFieldsModal(edit) {
    var code = $('#code').val();
    var title = $('#title').val();
    var modalMessage = "modalMessage";
    if (edit) {
        code = $('#editCode').val();
        title = $('#editTitle').val();
        modalMessage = "editModalMessage";
    }
    if (code == '') {
        showMessage(modalMessage, "El campo Código no puede estar vacío", false);
        return false;
    }
    if (title == '') {
        showMessage(modalMessage, "El campo Título no puede estar vacío", false);
        return false;
    }
    return true;
}
function addQuestion() {
    var code = $('#code').val();
    var title = $('#title').val();
    var description = $('#description').val();
    var procedure = $('#switchery').attr('checked') == 'checked';
    var select = document.getElementById("aspectSelect");
    var idAspect = select.value;
    $('#progress-bar-add-question').attr("class", "pull-right");
    $('#add-question').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            code: code,
            title: title,
            idAspect: idAspect,
            description: description,
            procedure: procedure,
            action: "add"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button"' +
                    ' onclick="fillQuestionModal(this)"><i class="fa fa-pencil"></i></button>' +
                    '<button id="delete' + data['id'] + '" class="btn btn-success" type="button" onclick="deleteQuestion(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                var procedureText = "No";
                if (procedure) {
                    procedureText = "Si";
                }
                table.dataTable().fnAddData([
                    code,
                    title,
                    description,
                    procedureText,
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanQuestionModal();
                }, 1000);
            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-add-question').attr("class", "pull-right hidden");
            $('#add-question').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function deleteQuestion(delete_button) {
    $("#alert-modal").modal('show');
    $("#alert-modal-title").text("Si elimina la pregunta se eliminarán automaticamente todas sus respuestas- ¿Desea continuar con la operación?");
    var alertButton = document.getElementById("alert-modal-button");
    alertButton.setAttribute("onclick", "deleteQuestionBody(" + delete_button.getAttribute("id") + ")");
}
function deleteQuestionBody(delete_button) {
    $("#alert-modal").modal('hide');
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idQuestion = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idQuestion},
        success: function (data) {
            if (data) {
                var table = $('#tableWithSearch').DataTable();
                table.row(row).remove().draw(false);
                showNotification("Operación exitosa", "success");
            } else {
                showNotification("Operación fallida- Una posible causa es que la pregunta este siendo utilizada en otro componente- Verifique que la pregunta haya sido desvinculada de todos los componentes y pruebe otra vez.", "danger", 10000);
            }
        }
    });
}
function fillQuestionModal(edit_button) {
    var row = edit_button.parentNode.parentNode.parentNode.parentNode;
    var idQuestion = row.children[row.children.length - 1].children[1].value;
    cleanQuestionModal(true);
    $.ajax({
            type: "GET",
            url: "data",
            data: {
                id: idQuestion
            },
            success: function (data) {
                if (data['success']) {
                    $('#editCode').val(data['code']);
                    ;
                    $('#editTitle').val(data['title']);
                    $('#editDescription').val(data['description']);
                    if (data['procedure']) {
                        $('#editSwitchery').attr('checked', 'checked');
                        var span = $('#editSwitchery').next();
                        span.attr("style", "box-shadow: 0px 0px 0px 16px rgb(16, 207, 189) inset; border-color: rgb(16, 207, 189); background-color: rgb(16, 207, 189); transition: border 0.4s ease 0s, box-shadow 0.4s ease 0s, background-color 1.2s ease 0s;");
                        span[0].children[0].setAttribute("style", "left: 20px; transition: left 0.2s ease 0s;");
                    } else {
                        $('#editSwitchery').removeAttr('checked');
                        span = $('#editSwitchery').next();
                        span.attr("style", "box-shadow: 0px 0px 0px 0px rgb(223, 223, 223) inset; border-color: rgb(223, 223, 223); background-color: rgb(255, 255, 255); transition: border 0.4s ease 0s, box-shadow 0.4s ease 0s;");
                        span[0].children[0].setAttribute("style", "left: 0px; transition: left 0.2s ease 0s;");
                    }
                    $('#idQuestion').text(idQuestion);
                    $("#edit-question").unbind();
                    $('#edit-question').click(function (e) {
                        if (validateQuestionFieldsModal(true)) {
                            editQuestion(row);
                        }
                    });
                    $('#editAppModalQuestion').modal('show');
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
function editQuestion(row) {
    var code = $('#editCode').val();
    var title = $('#editTitle').val();
    var description = $('#editDescription').val();
    var procedure = $('#editSwitchery').attr('checked') == 'checked';
    var idQuestion = $('#idQuestion').text();
    $('#progress-bar-edit-question').attr("class", "pull-right");
    $('#edit-question').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idQuestion,
            code: code,
            title: title,
            description: description,
            procedure: procedure,
            action: "edit"
        },
        success: function (data) {
            if (data['success']) {
                showMessage("editModalMessage", "Operación exitosa", true);
                row.children[0].textContent = code;
                row.children[1].textContent = title;
                row.children[2].textContent = description;
                if (procedure) {
                    row.children[3].textContent = "Si";
                } else {
                    row.children[3].textContent = "No";
                }
                setTimeout(function () {
                    $("#editAppModalQuestion").modal('hide');
                }, 1000);
            } else {
                showMessage("editModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-edit-question').attr("class", "pull-right hidden");
            $('#edit-question').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function cleanQuestionModal(edit) {
    if (edit) {
        $('#editCode').val("");
        $('#editTitle').val("");
        $('#editDescription').val("");
        $('#editModalMessage').empty();
        $('#editSwitchery').removeAttr('checked');
        span = $('#editSwitchery').next();
        span.attr("style", "box-shadow: 0px 0px 0px 0px rgb(223, 223, 223) inset; border-color: rgb(223, 223, 223); background-color: rgb(255, 255, 255); transition: border 0.4s ease 0s, box-shadow 0.4s ease 0s;");
        span[0].children[0].setAttribute("style", "left: 0px; transition: left 0.2s ease 0s;");
    } else {
        $('#code').val("");
        $('#title').val("");
        $('#description').val("");
        $('#modalMessage').empty();
        $('#switchery').removeAttr('checked');
        span = $('#switchery').next();
        span.attr("style", "box-shadow: 0px 0px 0px 0px rgb(223, 223, 223) inset; border-color: rgb(223, 223, 223); background-color: rgb(255, 255, 255); transition: border 0.4s ease 0s, box-shadow 0.4s ease 0s;");
        span[0].children[0].setAttribute("style", "left: 0px; transition: left 0.2s ease 0s;");
    }
}
