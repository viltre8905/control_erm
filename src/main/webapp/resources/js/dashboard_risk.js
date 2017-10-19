function riskLevelDashboard(generalDashboard) {
    $('#portlet-widget-risk-level').portlet({
        progress: 'circle',
        refresh: true,
        onRefresh: function () {
            $.ajax({
                type: "POST",
                url: generalDashboard ? "level" : "dashboard/risk/level",
                success: function (data) {
                    if (data['success']) {
                        if (data['riskTotal'] > 0) {
                            $('#risk-level-container').highcharts({
                                chart: {
                                    plotBackgroundColor: null,
                                    plotBorderWidth: null,
                                    plotShadow: false
                                },
                                title: {
                                    text: 'Nivel de los riesgos'
                                },
                                colors: ['#F0E68C', '#10cfbd', '#FF8C00', '#f55753',
                                    '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
                                tooltip: {
                                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                                },
                                plotOptions: {
                                    pie: {
                                        allowPointSelect: true,
                                        cursor: 'pointer',
                                        dataLabels: {
                                            enabled: false
                                        },
                                        showInLegend: true
                                    }
                                },
                                series: [{
                                    type: 'pie',
                                    name: 'Porcentaje de riesgos',
                                    data: [
                                        ['Bajo', data['low']],
                                        ['Moderado', data['moderated']],
                                        {
                                            name: 'Alto',
                                            y: data['high'],
                                            sliced: true,
                                            selected: true
                                        },
                                        ['Extremo', data['extreme']]
                                    ]
                                }]
                            });
                        } else {
                            $('#risk-level-container').empty();
                            $('#risk-level-container').append("<p class='m-t-5'>No hay riesgos.</p>")
                        }
                        $('#portlet-widget-risk-level').portlet({
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