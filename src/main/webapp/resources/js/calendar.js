(function ($) {

    'use strict';
    var selectedEvent;
    $('body').pagescalendar({
        onViewRenderComplete: function () {
            $.ajax({
                type: "GET",
                url: "data",
                success: function (data) {
                    if (data['success']) {
                        var events = data['reunions'];
                        for (var i = 0; i < events.length; i++) {
                            var newEvent = createNewEvent(events[i]);
                            $('body').pagescalendar('addEvent', newEvent);
                        }
                    } else {
                        showNotification(data['message'], "danger");
                    }
                }
            });
        },
        onEventClick: function (event) {
            //Open Pages Custom Quick View
            if (!$('#calendar-event').hasClass('open'))
                $('#calendar-event').addClass('open');
            selectedEvent = event;
            setEventDetailsToForm(selectedEvent);
        },
        onEventDragComplete: function (event) {
            selectedEvent = event;
            setEventDetailsToForm(selectedEvent);
        },
        onEventResizeComplete: function (event) {
            selectedEvent = event;
            setEventDetailsToForm(selectedEvent);
        },
        onTimeSlotDblClick: function (timeSlot) {
            //Adding a new Event on Slot Double Click
            var newEvent = {
                title: 'Reunión',
                class: 'bg-success-lighter',
                start: timeSlot.date,
                end: moment(timeSlot.date).add(1, 'hour').format(),
                allDay: false,
                index: this.events.length,
                other: {}
            };
            selectedEvent = newEvent;
            $('body').pagescalendar('addEvent', newEvent);
            //Open Pages Custom Quick View
            if (!$('#calendar-event').hasClass('open'))
                $('#calendar-event').addClass('open');
            setEventDetailsToForm(selectedEvent);
        },
        ui: {
            year: {
                visible: !0,
                format: "YYYY",
                startYear: "2000",
                endYear: moment().add(10, "year").format("YYYY"),
                eventBubble: !0
            },
            month: {visible: !0, format: "MMM", eventBubble: !0},
            date: {format: "dddd D, MMMM YYYY"},
            week: {day: {format: "D"}, header: {format: "dd"}, eventBubble: !0, startOfTheWeek: "lun"},
            grid: {dateFormat: "dddd D", timeFormat: "h A", eventBubble: !0, slotDuration: "30"}
        },
    });
    //After the settings Render the Calendar
    $('body').pagescalendar('render');

    function setEventDetailsToForm(event) {
        cleanFormEvent();
        //Show Event date
        $('#event-date').html(moment(event.start).format('dddd D, MMMM'));

        $('#lblfromTime').html(moment(event.start).format('h:mm A'));
        $('#lbltoTime').html(moment(event.end).format('H:mm A'));

        //Load Event Data To Text Field
        $('#eventIndex').val(event.index);
        if ($('#title')[0].tagName == 'INPUT') {
            $('#title').val(event.title);
            $('#place').val(event.other.place);
            $('#ubication').val(event.other.ubication);
            $('#fileName').text(event.other.fileName);
        } else {
            $('#title').text(event.title);
            $('#place').text(event.other.place);
            $('#ubication').text(event.other.ubication);
        }
        if (event.other.uuid) {
            $('#download').attr('class', 'btn btn-success m-l-10');
            $('#download').attr('href', 'download?id=' + event.other.uuid);
        } else {
            $('#download').attr('class', 'btn btn-success m-l-10 hidden');
        }
    }

    $('#eventSave').on('click', function () {
        if (validateEvent()) {
            selectedEvent.title = $('#title').val();
            selectedEvent.other.place = $('#place').val();
            selectedEvent.other.ubication = $('#ubication').val();
            var data = new FormData();
            if (selectedEvent.other.id) {
                data.append('id', selectedEvent.other.id);
            }
            data.append('title', selectedEvent.title);
            data.append('place', selectedEvent.other.place);
            data.append('start', selectedEvent.start);
            data.append('end', selectedEvent.end);
            if (selectedEvent.other.ubication) {
                data.append('ubication', selectedEvent.other.ubication);
            }
            jQuery.each(jQuery('#file')[0].files, function (i, file) {
                data.append(file['name'].split(".")[0], file);
                selectedEvent.other.fileName = file['name'];
            });
            $('#progress-bar-calendar').attr("class", "pull-right");
            $('#eventSave').attr("class", "btn btn-warning btn-cons disabled");
            $.ajax({
                type: "POST",
                url: "create",
                data: data,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data['success']) {
                        showMessage("modalMessage", "Operacion exitosa", true);
                        selectedEvent.other.id = data['id'];
                        selectedEvent.other.uuid = data['uuid'];
                        $('body').pagescalendar('updateEvent', $('#eventIndex').val(), selectedEvent);
                        setTimeout(function () {
                            $('#calendar-event').removeClass('open');
                        }, 1000);
                    } else {
                        showMessage("modalMessage", data['message'], false);
                    }
                },
                complete: function () {
                    $('#progress-bar-calendar').attr("class", "pull-right hidden");
                    $('#eventSave').attr("class", "btn btn-warning btn-cons");
                }
            });
        }
    });
    $('#eventDelete').on('click', function () {
        if (selectedEvent.other.id) {
            $('#progress-bar-calendar').attr("class", "pull-right");
            $('#eventDelete').attr("class", "btn btn-white disabled");
            $.ajax({
                type: "POST",
                url: "delete",
                data: {id: selectedEvent.other.id},
                success: function (data) {
                    if (!data) {
                        showNotification("modalMessage", "Operación fallida", false);
                    } else {
                        $('body').pagescalendar('removeEvent', $('#eventIndex').val());
                        $('#calendar-event').removeClass('open');
                    }
                },
                complete: function () {
                    $('#progress-bar-calendar').attr("class", "pull-right hidden");
                    $('#eventDelete').attr("class", "btn btn-white");
                }
            });
        } else {
            $('body').pagescalendar('removeEvent', $('#eventIndex').val());
            $('#calendar-event').removeClass('open');
        }
    });
    function validateEvent() {
        var title = $('#title').val();
        var place = $('#place').val();
        if (title == '') {
            showMessage("modalMessage", "El campo Título no puede estar vacío");
            return false;
        }
        if (place == '') {
            showMessage("modalMessage", "El campo Lugar no puede estar vacío");
            return false;
        }
        return true;
    }

    function createNewEvent(data) {
        var newEvent = {
            title: data['title'],
            class: 'bg-success-lighter',
            start: moment(data['start']).format(),
            end: moment(data['end']).format(),
            allDay: false,
            other: {
                //You can have your custom list of attributes here
                place: data['place'],
                ubication: data['minuteUbication'],
                uuid: data['documentMetadata']?data['documentMetadata']['id']:'',
                fileName: data['documentMetadata']?data['documentMetadata']['fileName'] + '.' + data['documentMetadata']['extension']:'',
                id: data['id']
            }
        };
        return newEvent;
    }

    function cleanFormEvent() {
        $('#modalMessage').empty();
        $('#eventIndex').val('');
        if ($('#title')[0].tagName == 'INPUT') {
            $('#title').val('');
            $('#place').val('');
            $('#ubication').val('');
        } else {
            $('#title').text('');
            $('#place').text('');
            $('#ubication').text('');
        }
        $('#fileName').text('');
    }

})(window.jQuery);
