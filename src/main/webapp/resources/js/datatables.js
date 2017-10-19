/* ============================================================
 * DataTables
 * Generate advanced tables with sorting, export options using
 * jQuery DataTables plugin
 * For DEMO purposes only. Extract what you need.
 * ============================================================ */
(function ($) {

    'use strict';
    var responsiveHelper = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone: 480
    };
    // Initialize a dataTable with collapsible rows to show more details


    // Initialize datatable showing a search box at the top right corner
    initTableWithSearch();
    initBasicTable();
    initDetailedViewTable('detailedTable');
})(window.jQuery);

function initTableWithSearch() {
    var table = $('#tableWithSearch');
    var settings = {
        "sDom": "<'table-responsive't><'row'<p i>>",
        "sPaginationType": "bootstrap",
        "destroy": true,
        "scrollCollapse": true,
        "oLanguage": {
            "sLengthMenu": "_MENU_ ",
            "sInfo": "Mostrando <b>_START_ al _END_</b> de _TOTAL_ total"
        },
        "iDisplayLength": 10
    };

    table.dataTable(settings);

    // search box for table

    $('#search-table').keyup(function () {
        table.fnFilter($(this).val());
    });
}
function initBasicTable() {


    var table = $('#basicTable');
    var settings = {
        "sDom": "<'table-responsive't><'row'<p i>>",
        "sPaginationType": "bootstrap",
        "destroy": true,
        "paging": false,
        "scrollCollapse": true,
        "aoColumnDefs": [{
            'bSortable': false,
            'aTargets': [0]
        }]

    };
    table.dataTable(settings);
}
// Initialize a dataTable with collapsible rows to show more details
function initDetailedViewTable(id, _format) {
    if (!_format) {
        _format = function (val) {
            if (val == null || val == "") {
                val = "No hay descripción para esta pregunta";
            }
            return '<table class="table table-inline">' +
                '<tr>' +
                '<td>'+val+'</td>' +
                '</tr>' +
                '</table>';
        };
    }


    var table = $('#' + id);

    table.DataTable({
        "sDom": "<'table-responsive't>",
        "scrollCollapse": true,
        "paging": false,
        "bSort": false
    });

    // Add event listener for opening and closing details
    $('#' + id + ' tbody').on('click', '.a1', function () {
        var row1 = $(this).parent();
        if ($(this).parent().hasClass('shown') && $(this).parent().next().hasClass('row-details')) {
            row1.removeClass('shown');
            row1.next().remove();
            return;
        }
        var tr = $(this).closest('tr');
        var row = table.DataTable().row(tr);
        var td = $(this).next();
        var value = td[0].children[9].value;

        $(this).parents('tbody').find('.shown').removeClass('shown');
        $(this).parents('tbody').find('.row-details').remove();

        row.child(_format(value)).show();
        tr.addClass('shown');
        tr.next().addClass('row-details');
    });

}

function generalInitTable(id){

    var table = $('#' + id);
    var settings = {
        "sDom": "<'table-responsive't><'row'<p i>>",
        "sPaginationType": "bootstrap",
        "destroy": true,
        "scrollCollapse": true,
        "oLanguage": {
            "sLengthMenu": "_MENU_ ",
            "sInfo": "Mostrando <b>_START_ al _END_</b> de _TOTAL_ total"
        },
        "iDisplayLength": 10
    };

    table.dataTable(settings);
}