<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	
<div class="col-sm-3 col-md-2 sidebar">
	<ul class="nav nav-sidebar">
		<li class="active"><a href="#">Main <span class="sr-only">(current)</span></a></li>
		<li class="active"><a
			href="<%=request.getContextPath()%>/board/createBoard">게시판 생성</a></li>
			
		<c:forEach items="${applicationScope.boardList}" var="board" >
		<li class="active" >
			<a  href="<%=request.getContextPath()%>/board/boardPagingView?board_id=${board.board_id}">${board.name}</a>
		</li>
		</c:forEach>
			
	</ul>
</div>