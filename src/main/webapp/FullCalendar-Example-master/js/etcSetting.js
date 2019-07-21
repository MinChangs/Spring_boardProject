//SELECT 색 변경
$('#edit-color').change(function () {
    $(this).css('color', $(this).val());
});

//필터
$('.filter').on('change', function () {
    $('#calendar').fullCalendar('rerenderEvents');
});

$("#type_filter").select2({
    placeholder: "선택..",
    allowClear: true
});



//datetimepicker
$("#edit-startDate, #edit-endDate").datetimepicker({
	format: 'YYYY-MM-DD'
});

//datetimepicker
$("#edit-startTime, #edit-endTime").datetimepicker({
	format: 'HH:mm'
});