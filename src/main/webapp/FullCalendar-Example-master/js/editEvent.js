/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {
	$("#dayInfo").hide();
    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
    	editEndDate.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD'));
    	editEndTime.val(event.end.format('HH:mm'));
    } else {
    	editEndDate.val(event.end.format('YYYY-MM-DD'));
        editEndTime.val(event.end.format('HH:mm'));
    }

    modalTitle.html('일정 수정');
    editTitle.val(event.title);
    editStartDate.val(event.start.format('YYYY-MM-DD'));
    editStartTime.val(event.start.format('HH:mm'));
    
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor).css('color', event.backgroundColor);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStartDate.val() > editEndDate.val()&&editStartTime.val() > editEndTime.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var startTime;
        var endDate;
        var endTime;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = true;
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            startTime = editStartTime.val();
            endTime = editEndTime.val();
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = false;
            startDate = editStartDate.val();
            startTime = editStartTime.val();
            endDate = editEndDate.val();
            endTime = editEndTime.val();
            displayDate = editEndDate.val()+editEndTime.val();
        }

        eventModal.modal('hide');

        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate+startTime;
        event.end = displayDate;
        event.type = editType.val();
        event.backgroundColor = editColor.val();
        event.description = editDesc.val();
        
        var eventData = [{
        	_id: event._id,
            title: editTitle.val(),
            description: editDesc.val(),
  			startTime: editStartTime.val(),
  			endTime: editEndTime.val(),
  			startDate: editStartDate.val() ,
  			endDate: editEndDate.val(),
  			type: editType.val(),
  			username: '사나',
  			backgroundColor: editColor.val(),
  			textColor: '#ffffff',
  			allDay: false

          }];

        $("#calendar").fullCalendar('updateEvent', event);
        console.log(event);
        //일정 업데이트
        $.ajax({
        	type:"post",
            contentType: 'application/json',
            url: "/boardProject2/updateCalendar",
            data: JSON.stringify(eventData),
            success: function (response) {
                alert('수정되었습니다.')
            }
        });

    });

    // 삭제버튼
    $('#deleteEvent').on('click', function () {
        $('#deleteEvent').unbind();
        $("#calendar").fullCalendar('removeEvents', [event._id]);
        eventModal.modal('hide');

        //삭제시
        $.ajax({
            type: "get",
            url: "/boardProject2/deleteCalendar",
            data: "c_id="+event._id,
            success: function (response) {
                alert('삭제되었습니다.');
            }
        });
    });
};