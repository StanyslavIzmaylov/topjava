const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateDate: updateTable
};

function updateTable() {
    $.get(ctx.ajaxUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function setEnabled(id, enabled) {
    $.ajax({
        url: ctx.ajaxUrl + id + "?enabled=" + enabled,
        type: "POST",
        data: enabled,
    }).done(function () {
        updateTable();
        successNoty("Applly");
    });
}

// $(document).ready(function () {
$(function () {
    $(".enable").click(function () {
        if (confirm('Are you sure?')) {
            setEnabled($(this).closest('tr').attr("id"), $(this).prop('checked'));
        }
    });
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
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
                    "asc"
                ]
            ]
        })
    );
});