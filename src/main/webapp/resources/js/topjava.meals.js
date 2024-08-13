const mealAjaxUrl = "js/meals/";

const updateDate = updateTableWithFilter;

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

function filter() {
    $('#filter').click(function (e) {
        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
        });
        e.preventDefault();
    });
}

function cleanTable() {
    $('#filter')[0].reset();
    $.get(ctx.ajaxUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function updateTableWithFilter() {
        $.ajax({
            type: "GET",
            url: ctx.ajaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
        });
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
                    "dsc"
                ]
            ]
        })
    );
});