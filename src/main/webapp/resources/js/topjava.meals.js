const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            return JSON.parse(stringData,
                function (key, value) {
                    return (key === 'dateTime') ? value.substring(0, 16).replace('T', ' ') : value;
                }
            );
        }
    }
});

$(function () {
    $('#startDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });
});
$(function () {
    $('#endDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });
});

$(function () {
    $('#startTime').datetimepicker({
        datepicker: false,
        format: 'H:i',
    });
});
$(function () {
    $('#endTime').datetimepicker({
        datepicker: false,
        format: 'H:i',
    });
});

$(function () {
    $('#dateTime').datetimepicker({
        format:'Y-m-d H:i',
        lang:'ru'
    });
});

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",

                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }

            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                    $(row).attr("data-meal-excess", data.excess);
            }
        })
    );
});