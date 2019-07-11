<%@page import="kr.or.ddit.paging.model.PageVo"%>
<%@page import="kr.or.ddit.user.model.UserVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<title>게시물 등록</title>

<!-- css, js -->
<%@include file="/common/basicLib.jsp"%>
<script src="${pageContext.request.contextPath}/SE2/js/HuskyEZCreator.js"></script>
<script>

var oEditors = []; // 개발되어 있는 소스에 맞추느라, 전역변수로 사용하였지만, 지역변수로 사용해도 전혀 무관 함.

$(document).ready(function() {
	
	var msg = '${msg}';
	if(msg !='')
		alert(msg);
	// Editor Setting
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors, // 전역변수 명과 동일해야 함.
		elPlaceHolder : "smarteditor", // 에디터가 그려질 textarea ID 값과 동일 해야 함.
		sSkinURI : "${pageContext.request.contextPath}/SE2/SmartEditor2Skin.html", // Editor HTML
		fCreator : "createSEditor2", // SE2BasicCreator.js 메소드명이니 변경 금지 X
		htParams : {
			// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseToolbar : true,
			// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,
			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true, 
		}
	});
	


	// 전송버튼 클릭이벤트
	$("#postRegBtn").click(function(){
		if(confirm("저장하시겠습니까?")) {
			// id가 smarteditor인 textarea에 에디터에서 대입
			oEditors.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);

			// 이부분에 에디터 validation 검증
			if(validation()) {
				$("#frm").submit();
			}
		}
	})
});

// 필수값 Check
function validation(){
	var contents = $.trim(oEditors[0].getContents());
	if(contents === '<p>&nbsp;</p>' || contents === ''){ // 기본적으로 아무것도 입력하지 않아도 <p>&nbsp;</p> 값이 입력되어 있음. 
		alert("내용을 입력하세요.");
		oEditors.getById['smarteditor'].exec('FOCUS');
		return false;
	}

	return true;
}






attachFile = {
        idx:1,
        add:function(){ // 파일필드 추가
            var o = this;
            var idx = o.idx;
            
            if(idx==5){
            	alert("첨부파일개수 5개가 넘었습니다")
            	return;
            }
 
 
            var file = document.all ? document.createElement('<input name="files">') : document.createElement('input');
            file.type = 'file';
            file.name = 'files';
            file.id = 'fileField' + o.idx;

            document.getElementById('attachFileDiv').appendChild(file);
 
            o.idx++;
        }
     
    }


</script>
				<div class="row">
					<div class="col-sm-8 blog-main">
						<h2 class="sub-header">게시물 등록</h2>
						<form id ="frm" class="form-horizontal" role="form" action="${pageContext.request.contextPath }/board/insertPost" method="post" enctype="multipart/form-data">
							<input type="hidden" id="board_id" name ="board_id" value="${board_id}">
							<div class="form-group">
								<label for="userId" class="col-sm-2 control-label">제목</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="title" name="title" placeholder="제목">
								</div>
							</div>
		
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">내용</label>
								<div class="col-sm-10">
									<textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width:766px; height:412px;"></textarea> 
								</div>
							</div>
							
							<div class="form-group">
								<label for="filename" class="col-sm-2 control-label">첨부파일</label>
								<div class="col-sm-10" id ="attachFileDiv">
								
								<input type="button" value="첨부파일 추가" onclick="attachFile.add()" style="margin-left:5px">
								<input type="file" name="files" value="">
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button id="postRegBtn" type="button" class="btn btn-default">게시물 등록</button>
								</div>
							</div>
						</form>	
					</div>
				</div>
