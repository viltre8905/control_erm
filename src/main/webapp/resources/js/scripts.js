(function ($) {

    'use strict';

    $(document).ready(function () {
        // Initializes search overlay plugin.
        // Replace onSearchSubmit() and onKeyEnter() with
        // your logic to perform a search and display results
        $(".list-view-wrapper").scrollbar();

        $('[data-pages="search"]').search({
            searchField: '#overlay-search',
            closeButton: '.overlay-close',
            suggestions: '#overlay-suggestions',
            brand: '.brand',
            onSearchSubmit: function (searchString) {
                console.log("Search for: " + searchString);
            },
            onKeyEnter: function (searchString) {
                console.log("Live search for: " + searchString);
                var searchField = $('#overlay-search');
                var searchResults = $('.search-results');

                clearTimeout($.data(this, 'timer'));
                searchResults.fadeOut("fast");
                var wait = setTimeout(function () {

                    searchResults.find('.result-name').each(function () {
                        if (searchField.val().length != 0) {
                            $(this).html(searchField.val());
                            searchResults.fadeIn("fast");
                        }
                    });
                }, 500);
                $(this).data('timer', wait);

            }
        });
        $('#showmodalAdd').click(function () {
            $('#addNewAppModal').modal('show');
        });
        $('#changePasswordLink').click(function (event) {
            event.preventDefault();
            $('#changePasswordModal').modal('show');
        });
        $('#entityDataLink').click(function (event) {
            event.preventDefault();
            $('#entityDataModal').modal('show');
        });
        $('#profileLink').click(function (event) {
            event.preventDefault();
        });
        $('#delete-notification').click(function (event) {
            event.preventDefault();
        });
        $('.notification-mark').click(function (event) {
            event.preventDefault();
            
        });
        var settings = {
            format: 'dd/mm/yyyy',
            language: 'es'
        };
        $('.date1').datepicker(settings);
        $('#exportReport').click(function (event) {
            event.preventDefault();
            exportReport();
        });
        $('#exportRiskPlanReport').click(function (event) {
            event.preventDefault();
            $('#progress-bar-export-report').attr("class", "progress-bar-container");
            $('#exportRiskPlanReport').attr("class", "disabled");
            $.ajax({
                type: "POST",
                url: "create_report",
                data: {report: 1},
                success: function (data) {
                    if (data['success']) {
                        location.href = "export?report=1";
                    } else {
                        showNotification(data['message'], "danger");
                    }
                },
                complete: function () {
                    $('#progress-bar-export-report').attr("class", "progress-bar-container hidden");
                    $('#exportRiskPlanReport').removeAttr("class");
                }
            });
        });
        $('.progress-bar-container').attr('class', 'progress-bar-container hidden');
        $('.exportReport').click(function (event) {
            event.preventDefault();
        });
        $('#exportEntityData').click(function (event) {
            event.preventDefault();
        });
    });


})(window.jQuery);