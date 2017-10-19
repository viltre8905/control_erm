function supervisoryHome() {

    var inputs = $(".input-entity-dashboard");
    for (var i = 0; i < inputs.length; i++) {
        var entityId = inputs[i].value;
        $('#dashboard-porlet' + entityId).portlet({
            progress: 'circle',
            refresh: true,
            onRefresh: function () {
                $.ajax({
                    type: "POST",
                    url: "supervisoryHome/dashboard/efficacy",
                    data: {id: entityId},
                    success: function (data) {
                        if (data['success']) {

                            $('#speedometer' + data['id']).highcharts({
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
                            supervisoryHomeActivityState(data['id']);
                        } else {
                            showNotification(data['message'], "danger");
                        }
                    }
                });
            }
        });
    }
    var input = $(".input-entity-dashboard-main");
    var entityMainId = input[0].value;
    $('#dashboard-porlet' + entityMainId).portlet({
        progress: 'circle',
        refresh: true,
        onRefresh: function () {
            $.ajax({
                type: "POST",
                url: "supervisoryHome/dashboard/efficacy",
                data: {id: entityMainId},
                success: function (data) {
                    if (data['success']) {

                        $('#speedometer' + data['id']).highcharts({
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
                        supervisoryHomeActivityState(data['id'], true);
                    } else {
                        showNotification(data['message'], "danger");
                    }
                }
            });
        }
    });


    function supervisoryHomeActivityState(id, val) {
        $.ajax({
            type: "POST",
            url: "supervisoryHome/activities/states",
            data: {id: id},
            success: function (data) {
                if (data['success']) {
                    if (data['closeToday'] || data["nonCompletionCount"] != 0 || data["inTime"] != 0 || data["solvedCount"] != 0) {
                        $("#dashboard-sparkline-pie" + id).sparkline([data["closeToday"], data["nonCompletionCount"], data["inTime"], data["solvedCount"]], {
                            type: 'pie',
                            width: val ? $("#sparkline-pie" + id).width() : '100',
                            height: '200',
                            sliceColors: [$.Pages.getColor('warning'), $.Pages.getColor('danger'), $.Pages.getColor('success'), $.Pages.getColor('complete')]

                        });
                    } else {
                        $("#dashboard-sparkline-pie" + id).text('No hay actividades asignadas.');
                    }
                    $('#dashboard-porlet' + id).portlet({
                        refresh: false
                    });
                    $("#dashboard-sparkline-solved" + id).text(data["solvedCount"]);
                    $("#dashboard-sparkline-closed" + id).text(data["closeToday"]);
                    $("#dashboard-sparkline-nocomp" + id).text(data["nonCompletionCount"]);
                    $("#dashboard-sparkline-intime" + id).text(data["inTime"]);
                } else {
                    showNotification(data['message'], "danger");
                }
            }
        });

    }
}
