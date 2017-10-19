function showAnswer(check_box, questionId, answer) {
    if (check_box.checked) {
        switch (answer) {
            case 1:
                $('#modalAffirmativeAnswer').modal('show');
                break;
            case 2:
                $('#modalNegativeAnswer').modal('show');
                break;
            case 3:
                $('#modalRejectAnswer').modal('show');
                break;
        }
        $('#idQuestion').val(questionId);
    }
}
function cleanAffirmativeAnswerModal() {
    $('#affirmativeAnswerDescription').val('');
    var documentTitle = $('#documentTitle').val('');
    var documentType = $('#documentType').val('-1');
    var documentProcedence = $('#documentProcedence').val('-1');
    $('#affirmativeAnswerModalMessage').empty();
    $('#fileContainer').empty();
    var input = document.createElement("input");
    var container = document.getElementById("fileContainer");
    input.setAttribute("type", "file");
    input.setAttribute("id", "file");
    input.setAttribute("name", "file");
    container.appendChild(input);
}
function cleanRejectAnswerModal() {
    $('#rejectDescription').val('');
    $('#rejectAnswerModalMessage').empty();
}
function cleanNegativeAnswerModal() {
    $('#negativeAnswerDescription').val('');
    $('#negativeAnswerModalMessage').empty();
}
function cleanModal(answer) {
    var idQuestion = $('#idQuestion').val();
    var switcheryId = '#switcheryYes' + idQuestion;
    switch (answer) {
        case 2:
            switcheryId = '#switcheryNO' + idQuestion;
            break;
        case 3:
            switcheryId = '#switcheryNOP' + idQuestion;
            break;
    }

    var span = $(switcheryId).next();
    span.attr("style", "box-shadow: 0px 0px 0px 0px rgb(223, 223, 223) inset; border-color: rgb(223, 223, 223); background-color: rgb(255, 255, 255); transition: border 0.4s ease 0s, box-shadow 0.4s ease 0s;");
    span[0].children[0].setAttribute("style", "left: 0px; transition: left 0.2s ease 0s;");
    $(switcheryId).prop("checked", false);
    switch (answer) {
        case 1:
            cleanAffirmativeAnswerModal();
            break;
        case 2:
            cleanNegativeAnswerModal();
            break;
        case 3:
            cleanRejectAnswerModal();
            break;
    }

}
function validateAffirmativeAnswer() {
    var description = $('#affirmativeAnswerDescription').val();
    if (description == '') {
        showMessage('affirmativeAnswerModalMessage', "El campo Descripción no puede estar vacío", false);
        return false;
    }
    var data = new FormData();
    var haveFile = false;
    var documentTitle = $('#documentTitle').val();
    var documentType = $('#documentType').val();
    var documentProcedence = $('#documentProcedence').val();
    jQuery.each(jQuery('#file')[0].files, function (i, file) {
        data.append(file['name'].split(".")[0], file);
        haveFile = true;
    });
    if (haveFile && (documentTitle == '' || documentType == '-1' || documentProcedence == '-1') ||
        documentTitle != '' && (!haveFile || documentType == '-1' || documentProcedence == '-1') ||
        documentType != '-1' && (!haveFile || documentTitle == '' || documentProcedence == '-1') ||
        documentProcedence != '-1' && (!haveFile || documentTitle == '' || documentType == '-1')) {
        showMessage('affirmativeAnswerModalMessage', "Para subir un documento debe completar todos los campos", false);
        return false;
    }
    return true;
}
function setAffirmativeAnswer() {
    var description = $('#affirmativeAnswerDescription').val();
    var documentTitle = $('#documentTitle').val();
    var documentType = $('#documentType').val();
    var documentProcedence = $('#documentProcedence').val();
    var idQuestion = $('#idQuestion').val();
    var data = new FormData();
    data.append('id', idQuestion);
    data.append('description', description);
    var haveFile = false;
    jQuery.each(jQuery('#file')[0].files, function (i, file) {
        data.append(file['name'].split(".")[0], file);
        haveFile = true;
    });
    if (haveFile) {
        data.append('title', documentTitle);
        data.append('type', documentType);
        data.append('procedence', documentProcedence);
    }
    $('#progress-bar-affirmative').attr("class", "pull-right");
    $('#affirmative-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "affirmativeAnswer/create",
        data: data,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data['success']) {
                showMessage("affirmativeAnswerModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    $('#modalAffirmativeAnswer').modal('hide');
                    cleanAffirmativeAnswerModal();
                    cleanModal(2);
                    cleanModal(3);
                    $('#buttonEvidence' + idQuestion).attr('class', 'btn btn-success');
                    var processId = $('#process' + idQuestion).val();
                    $('#buttonEvidence' + idQuestion).attr("onclick", "showEvidence(this,1," + processId + ")");
                }, 1000);
            } else {
                showMessage("affirmativeAnswerModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-affirmative').attr("class", "pull-right hidden");
            $('#affirmative-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function validateNegativeAnswer() {
    var description = $('#negativeAnswerDescription').val();
    if (description == '') {
        showMessage('negativeAnswerModalMessage', "El campo Deficiencia no puede estar vacío", false);
        return false;
    }
    return true;
}
function setNegativeAnswer() {
    var description = $('#negativeAnswerDescription').val();
    var idQuestion = $('#idQuestion').val();
    $('#progress-bar-negative').attr("class", "pull-right");
    $('#negative-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "negativeAnswer/create",
        data: {
            id: idQuestion,
            deficiency: description
        },
        success: function (data) {
            if (data['success']) {
                showMessage("negativeAnswerModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    $('#modalNegativeAnswer').modal('hide');
                    cleanNegativeAnswerModal();
                    cleanModal(1);
                    cleanModal(3);
                    $('#buttonEvidence' + idQuestion).attr('class', 'btn btn-success');
                    var processId = $('#process' + idQuestion).val();
                    $('#buttonEvidence' + idQuestion).attr("onclick", "showEvidence(this,2," + processId + ")");
                }, 1000);
            } else {
                showMessage("negativeAnswerModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-negative').attr("class", "pull-right hidden");
            $('#negative-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function validateRejectAnswer() {
    var description = $('#rejectDescription').val();
    if (description == '') {
        showMessage('rejectAnswerModalMessage', "El campo Descripción no puede estar vacío", false);
        return false;
    }
    return true;
}
function setRejectAnswer() {
    var description = $('#rejectDescription').val();
    var idQuestion = $('#idQuestion').val();
    $('#progress-bar-reject').attr("class", "pull-right");
    $('#reject-button').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "rejectAnswer/create",
        data: {
            id: idQuestion,
            description: description
        },
        success: function (data) {
            if (data['success']) {
                showMessage("rejectAnswerModalMessage", "Operación exitosa", true);
                setTimeout(function () {
                    $('#modalRejectAnswer').modal('hide');
                    cleanRejectAnswerModal();
                    cleanModal(1);
                    cleanModal(2);
                    $('#buttonEvidence' + idQuestion).attr('class', 'btn btn-success');
                    var processId = $('#process' + idQuestion).val();
                    $('#buttonEvidence' + idQuestion).attr("onclick", "showEvidence(this,1," + processId + ")");
                }, 1000);
            } else {
                showMessage("rejectAnswerModalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-reject').attr("class", "pull-right hidden");
            $('#reject-button').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function showEvidence(show_button, type, processId) {
    if (type == 1) {
        $('#evidenceTitle').text("Evidencia");
    } else if (type == 2) {
        $('#evidenceTitle').text("Deficiencia");
    } else {
        $('#evidenceTitle').text("Razón");
    }
    var row = show_button.parentNode.parentNode;
    var idQuestion = row.children[row.children.length - 1].children[1].value;
    cleanActivitySolutionModal();
    $.ajax({
            type: "GET",
            url: "evidence",
            data: {
                id: idQuestion,
                processId: processId
            },
            success: function (data) {
                if (data['success']) {
                    $('#description').text(data['description']);
                    if (data['title']) {
                        $('#documentData').attr('class', 'row');
                        $('#title').text(data['title']);
                        $('#type').text(data['type']);
                        $('#procedence').text(data['procedence']);
                        $('#fileName').text(data['fileName']);
                        $('#download').attr('href', 'document?id=' + data['id']);
                    }
                    $('#evidenceModal').modal('show');
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
function exportAllGuideReports() {
    for (var i = 1; i < 7; i++) {
        $('#exportReport' + i).click(function (event) {
            event.preventDefault();
            exportGuideReport(this.getAttribute("id"));
        });
    }
}
function exportGuideReport(id) {
    $('#progress-bar-export-report').attr("class", "progress-bar-container");
    $(id).attr("class", "disabled");
    $.ajax({
        type: "POST",
        url: "create_report",
        data: {
            value: id.substring(12, id.length)
        },
        success: function (data) {
            if (data['success']) {
                redirectURL("export");
            } else {
                showNotification(data['message'], "danger");
            }
        },
        complete: function () {
            $('#progress-bar-export-report').attr("class", "progress-bar-container hidden");
            $(id).removeAttr("class");
        }
    });
}
