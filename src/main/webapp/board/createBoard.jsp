<%@page import="kr.or.ddit.paging.model.PageVo"%>
<%@page import="kr.or.ddit.user.model.UserVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<title> 게시판 등록</title>
<script>
$(document).ready(function() {
	var msg = '${msg}';
	if(msg !='')
		alert(msg);
		createBoardAjaxHtml();
		
	});
	
	
}
	
	

</script>

				<div class="row">
					<div class="col-sm-8 blog-main">
						<h2 class="sub-header">게시판 등록</h2>
						<form id ="frm" class="form-horizontal" role="form" action="${pageContext.request.contextPath}/board/createBoard" method="post">
							<div class="form-group">
								<label for="boardName" class="col-sm-2 control-label">게시판 이름</label>
								<div class="col-sm-5">
									<input type="text" class="form-control" name="boardCreate" placeholder="게시판 이름" value="">
								</div>
								<div class="col-sm-2">
									<select class="pull-right" name="usecheck">
									    <option value="y" >사용</option>
									    <option value="n">미사용</option>
									</select>
								</div>
								<div class="col-sm-2">
									<button id="createBtn" type="submit" class="btn btn-default pull-right">생성</button>
								</div>
							</div>
							</form>
							<c:forEach items="${allList}" var="board">
							
								<form id ="frm${board.board_id}" class="form-horizontal" role="form" action="${pageContext.request.contextPath }/board/updateBoard" method="post">
									<div class="form-group">
										<label for="boardName" class="col-sm-2 control-label">게시판 이름</label>
										<div class="col-sm-5">
											<input type="text" class="form-control" name="board" placeholder="게시판 이름" value="${board.name}">
											<input type="hidden" id="boardId" name ="boardId" value="${board.board_id}">
										</div>
										<div class="col-sm-2">
											<select class="pull-right" name="usecheck">
												<c:choose>
													<c:when test="${board.use_yn=='y'}">
													    <option value="y" selected="selected">사용</option>
													    <option value="n">미사용</option>
													</c:when>
													<c:otherwise>
														<option value="y">사용</option>
													    <option value="n" selected="selected">미사용</option>
													</c:otherwise>
												</c:choose>
											</select>
										</div>
										<div class="col-sm-2">
											<button id="updateBtn${board.board_id}" type="submit" class="btn btn-default pull-right">수정</button>
										</div>
									</div>
								
							 	</form>	
						 	
							</c:forEach>
		

					</div>
				</div>

