function validateCommitteeFieldsModal() {
    var member = $('#member').val();
    if (member == '-1') {
        showMessage("modalMessage", "Debe seleccionar algún miembro", false);
        return false;
    }
    return true;
}
function addCommitteeMember() {
    var idMember = $('#member').val();
    var secretary = $('#switchery').attr('checked') == 'checked';
    $('#progress-bar-committee').attr("class", "pull-right");
    $('#add-committee-member').attr("class", "btn btn-primary  btn-cons pull-right disabled");
    $.ajax({
        type: "POST",
        url: "create",
        data: {
            id: idMember,
            secretary: secretary
        },
        success: function (data) {
            if (data['success']) {
                showMessage("modalMessage", "Operación exitosa", true);
                var text = '<div class="row"><div class="btn-group b-grey b-l b-r p-l-15 p-r-15">' +
                    '<button class="btn btn-success" type="button" onclick="deleteCommitteeMember(this)"><i' +
                    ' class="fa fa-trash-o"></i></button></div></div><input type="text" hidden="true" value="' + data['id'] + '"/>';
                var table = $('#tableWithSearch');
                table.dataTable().fnAddData([
                    data['fullName'],
                    data['ocupation'],
                    data['email'],
                    secretary ? "Si" : "No",
                    text
                ]);
                setTimeout(function () {
                    $("#addNewAppModal").modal('hide');
                    cleanCommitteeModal();
                }, 1000);

            } else {
                showMessage("modalMessage", data['message'], false);
            }
        },
        complete: function () {
            $('#progress-bar-committee').attr("class", "pull-right hidden");
            $('#add-committee-member').attr("class", "btn btn-primary  btn-cons pull-right");
        }
    });
}
function cleanCommitteeModal() {
    $('#member').val('');
    $('#modalMessage').empty();
    $('#switchery').removeAttr('checked');
    span = $('#switchery').next();
    span.attr("style", "box-shadow: 0px 0px 0px 0px rgb(223, 223, 223) inset; border-color: rgb(223, 223, 223); background-color: rgb(255, 255, 255); transition: border 0.4s ease 0s, box-shadow 0.4s ease 0s;");
    span[0].children[0].setAttribute("style", "left: 0px; transition: left 0.2s ease 0s;");
}
function deleteCommitteeMember(delete_button) {
    var row = delete_button.parentNode.parentNode.parentNode.parentNode;
    var idUser = row.children[row.children.length - 1].children[1].value;
    $.ajax({
        type: "POST",
        url: "delete",
        data: {id: idUser},
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
