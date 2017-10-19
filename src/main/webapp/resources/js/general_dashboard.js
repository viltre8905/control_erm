function dashboard(contextPath) {
    for (var i = 1; i < 7; i++) {
        $('#dashboard-porlet' + i).portlet({
            progress: 'circle',
            onRefresh: function () {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/dashboard/efficacy",
                    data: {val: i},
                    success: function (data) {
                        if (data['success']) {
                            if (data['val'] != 1) {
                                $('#speedometer' + data['val']).highcharts({
                                    chart: {
                                        type: 'gauge',
                                        plotBackgroundColor: null,
                                        plotBackgroundImage: null,
                                        plotBorderWidth: 0,
                                        plotShadow: false
                                    },

                                    title: {
                                        text: ' '
                                    },

                                    pane: {
                                        startAngle: -150,
                                        endAngle: 150,
                                        background: [{
                                            backgroundColor: {
                                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                                stops: [
                                                    [0, '#FFF'],
                                                    [1, '#333']
                                                ]
                                            },
                                            borderWidth: 0,
                                            outerRadius: '109%'
                                        }, {
                                            backgroundColor: {
                                                linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                                                stops: [
                                                    [0, '#333'],
                                                    [1, '#FFF']
                                                ]
                                            },
                                            borderWidth: 1,
                                            outerRadius: '107%'
                                        }, {
                                            // default background
                                        }, {
                                            backgroundColor: '#DDD',
                                            borderWidth: 0,
                                            outerRadius: '105%',
                                            innerRadius: '103%'
                                        }]
                                    },

                                    // the value axis
                                    yAxis: {
                                        min: 0,
                                        max: 100,

                                        minorTickInterval: 'auto',
                                        minorTickWidth: 1,
                                        minorTickLength: 10,
                                        minorTickPosition: 'inside',
                                        minorTickColor: '#666',

                                        tickPixelInterval: 30,
                                        tickWidth: 2,
                                        tickPosition: 'inside',
                                        tickLength: 10,
                                        tickColor: '#666',
                                        labels: {
                                            step: 2,
                                            rotation: 'auto'
                                        },
                                        title: {
                                            text: '%'
                                        },
                                        plotBands: [{
                                            from: 0,
                                            to: 70,
                                            color: '#DF5353' // red
                                        }, {
                                            from: 70,
                                            to: 90,
                                            color: '#DDDF0D' // yellow
                                        }, {
                                            from: 90,
                                            to: 100,
                                            color: '#55BF3B' // green
                                        }]
                                    },
                                    series: [{
                                        name: data['efficacy'],
                                        data: [data['percent']],
                                        tooltip: {
                                            valueSuffix: ' %'
                                        }
                                    }]

                                });
                            } else {
                                if (data['efficacy'] == 'Eficaz') {
                                    $('#dashboard-porlet1').attr('class', 'widget-3 panel no-border bg-success no-margin widget-loader-bar');
                                } else if (data['efficacy'] == 'Ineficaz') {
                                    $('#dashboard-porlet1').attr('class', 'widget-3 panel no-border bg-danger no-margin widget-loader-bar');
                                } else {
                                    $('#dashboard-porlet1').attr('class', 'widget-3 panel no-border bg-warning no-margin widget-loader-bar');
                                }
                                $('#efficacy-header').text(data['efficacy']);
                                $('#questionCount').text(data['questionCount']);
                                $('#countA').text(data['countA']);
                                $('#countN').text(data['countN']);
                                $('#countNP').text(data['countNP']);
                                $('#nAnswer').text(data['nAnswer']);
                            }
                            $('#dashboard-porlet' + data['val']).portlet({
                                refresh: false
                            });
                        } else {
                            showNotification(data['message'], "danger");
                        }
                    }
                });
            }
        });
        $('#dashboard-porlet' + i).portlet({
            refresh: true
        });
    }
    $('#dashboard-sparkline-pie1').portlet({
        progress: 'circle',
        onRefresh: function () {
            generalDashboardActivityState(1);
        }
    });
    $('#dashboard-sparkline-pie1').portlet({
        refresh: true
    });
    $('#dashboard-sparkline-pie2').portlet({
        progress: 'circle',
        onRefresh: function () {
            generalDashboardActivityState(2);
        }
    });
    $('#dashboard-sparkline-pie2').portlet({
        refresh: true
    });
    $('#dashboard-sparkline-pie3').portlet({
        progress: 'circle',
        onRefresh: function () {
            generalDashboardActivityState(3);
        }
    });
    $('#dashboard-sparkline-pie3').portlet({
        refresh: true
    });
    $('#dashboard-sparkline-pie4').portlet({
        progress: 'circle',
        onRefresh: function () {
            generalDashboardActivityState(4);
        }
    });
    $('#dashboard-sparkline-pie4').portlet({
        refresh: true
    });
    $('#dashboard-sparkline-pie5').portlet({
        progress: 'circle',
        onRefresh: function () {
            generalDashboardActivityState(5);
        }
    });
    $('#dashboard-sparkline-pie5').portlet({
        refresh: true
    });
    $('#dashboard-sparkline-pie6').portlet({
        progress: 'circle',
        onRefresh: function () {
            generalDashboardActivityState(6);
        }
    });
    $('#dashboard-sparkline-pie6').portlet({
        refresh: true
    });


    function generalDashboardActivityState(val) {
        $.ajax({
            type: "POST",
            url: contextPath + "/dashboard/activities/states",
            data: {val: val},
            success: function (data) {
                if (data['success']) {
                    if (data['closeToday'] || data["nonCompletionCount"] != 0 || data["inTime"] != 0 || data["solvedCount"] != 0) {
                        $('#sparkline-pie' + val).highcharts({
                            chart: {
                                type: 'column'
                            },
                            title: {
                                text: 'Plan de Medidas',
                            },
                            colors: ['#48b0f7', '#10cfbd', '#f8d053', '#f55753',
                                '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
                            xAxis: {
                                categories: [
                                    'Estado'
                                ]
                            },
                            yAxis: {
                                min: 0,
                                allowDecimals: false,
                                title: {
                                    text: 'Cantidad'
                                }
                            },
                            tooltip: {
                                headerFormat: '<span style="font-size:10px;">{point.key}</span><table>',
                                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                                '<td style="padding:0"><b>{point.y: f} </b></td></tr>',
                                footerFormat: '</table>',
                                shared: true,
                                useHTML: true
                            },
                            plotOptions: {
                                column: {
                                    pointPadding: 0.2,
                                    borderWidth: 0
                                }
                            },
                            series: [{
                                name: 'Resueltas',
                                data: [data["solvedCount"]]

                            }, {
                                name: 'En tiempo',
                                data: [data["inTime"]]

                            }, {
                                name: 'Cierran Hoy',
                                data: [data["closeToday"]]

                            }, {
                                name: 'Incumplidas',
                                data: [data["nonCompletionCount"]]

                            }]
                        });
                    } else {
                        $("#sparkline-pie" + val).text('No hay actividades asignadas.');
                    }
                    $('#dashboard-sparkline-pie' + val).portlet({
                        refresh: false
                    });
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        });

    }
}





