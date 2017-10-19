function initDashboard() {
    $('#guide-porlet').portlet({
        progress: 'circle',
        refresh: true,
        onRefresh: function () {
            $.ajax({
                type: "POST",
                url: "allData",
                success: function (data) {
                    if (data['success']) {
                        if (data['efficacy']['efficacy'] == 'Eficaz') {
                            $('#guide-porlet').attr('class', 'widget-3 panel no-border bg-success no-margin widget-loader-bar');
                        } else if (data['efficacy']['efficacy'] == 'Ineficaz') {
                            $('#guide-porlet').attr('class', 'widget-3 panel no-border bg-danger no-margin widget-loader-bar');
                        } else {
                            $('#guide-porlet').attr('class', 'widget-3 panel no-border bg-warning no-margin widget-loader-bar');
                        }
                        $('#efficacy-header').text(data['efficacy']['efficacy']);
                        $('#questionCount').text(data['questionCount']);
                        $('#countA').text(data['efficacy']['countA']);
                        $('#countN').text(data['efficacy']['countN']);
                        $('#countNP').text(data['efficacy']['countNP']);
                        $('#nAnswer').text(data['efficacy']['nAnswer']);
                        $('#guide-porlet').portlet({
                            refresh: false
                        });
                    } else {
                        showNotification(data['message'], "danger");
                    }
                }
            });
        }
    });

    $('#procedure-porlet').portlet({
        progress: 'circle',
        refresh: true,
        onRefresh: function () {
            $.ajax({
                type: "POST",
                url: "allData",
                success: function (data) {
                    if (data['success']) {
                        if (data['procedurePercent'] >= 100) {
                            $('#procedureResult').attr('class', 'no-margin p-b-5 text-success semi-bold');
                            $('#procedureResult').text('Bien');
                            $('#procedurePercent').attr('class', 'text-success font-montserrat');
                        } else {
                            $('#procedureResult').attr('class', 'no-margin p-b-5 text-danger semi-bold');
                            $('#procedureResult').text('Mal');
                            $('#procedurePercent').attr('class', 'text-danger font-montserrat');
                        }
                        $('#procedurePercent').text(data['procedurePercent'] + '%');
                        $('#procedure-porlet').portlet({
                            refresh: false
                        });
                    } else {
                        showNotification(data['message'], "danger");
                    }
                }
            });
        }
    });
}
function summarySubProcess(link) {
    var i = link.children[0];
    if (i.getAttribute('class') == 'pg-arrow_minimize text-danger') {
        i.setAttribute("class", "pg-arrow_maximize text-danger");
        $('#summarySubProcess').removeAttr('class');
    } else {
        i.setAttribute("class", "pg-arrow_minimize text-danger");
        $('#summarySubProcess').attr('class', 'hidden');
    }
}
function drawSparklinePie() {
    $.ajax({
        type: "POST",
        url: "activities/states",
        success: function (data) {
            if (data['success']) {
                if (data['closeToday'] || data["nonCompletionCount"] != 0 || data["inTime"] != 0 || data["solvedCount"] != 0) {
                    $("#sparkline-pie").sparkline([data["closeToday"], data["nonCompletionCount"], data["inTime"], data["solvedCount"]], {
                        type: 'pie',
                        width: $("#sparkline-pie").width(),
                        height: '200',
                        sliceColors: [$.Pages.getColor('warning'), $.Pages.getColor('danger'), $.Pages.getColor('success'), $.Pages.getColor('complete')]

                    });
                } else {
                    $("#sparkline-pie").text('No hay actividades asignadas.');
                }

                $('#portlet-widget-3').portlet({
                    refresh: false
                });
            }
            else {
                showNotification(data['message'], "danger");
            }
        }
    });

}
