const mealAjaxUrl = "js/meals/";


// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateDate: updateTableWithFilter,
    updateAll:updateTableDate
};

function filter(e) {
        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(ctx.updateAll);
        e.preventDefault();
}
function updateTableDate(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}
function cleanFilter() {
    $('#filter')[0].reset();
    $.get(ctx.ajaxUrl,ctx.updateAll);
}

function updateTableWithFilter() {
        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(ctx.updateAll);
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Update",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});