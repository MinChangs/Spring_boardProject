<%@page import="kr.or.ddit.paging.model.PageVo"%>
<%@page import="kr.or.ddit.user.model.UserVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:forEach items="${postPagingList}" var="post" varStatus="status">
	<c:choose>
		<c:when test="${post.use_yn == 'y'}">
			<tr class="userTr" data-userId="${post.post_seq}">
				<td class="userId">${post.rn}</td>
				<td>${post.title}</td>
				<td>${post.userid}</td>
				<td><fmt:formatDate value="${post.complete_dt}"
						pattern="yyyy-MM-dd" /></td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td class="userId">${post.rn}</td>
				<td>삭제된 게시물입니다.</td>
				<td>삭제된 게시물입니다.</td>
				<td><fmt:formatDate value="${post.complete_dt}"
						pattern="yyyy-MM-dd" /></td>
			</tr>

		</c:otherwise>
	</c:choose>
</c:forEach>


SEPERATORSEPERATOR



<c:choose>
	<c:when test="${pageVo.page==1}">
		<li class="prev disabled"><span>«</span></li>
	</c:when>
	<c:otherwise>
		<li class="prev"><a
			href="javascript:boardPagingListAjaxHtml(${board_id},1, ${pageVo.pageSize});">«</a></li>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${pageVo.page==1}">
		<li class="prev disabled"><span>‹</span></li>
	</c:when>
	<c:otherwise>
		<li class="prev"><a
			href="javascript:boardPagingListAjaxHtml(${board_id},${pageVo.page-1}, ${pageVo.pageSize});">‹</a></li>
	</c:otherwise>
</c:choose>

<c:forEach begin="1" end="${paginationSize}" var="i">
	<c:choose>
		<c:when test="${pageVo.page == i}">
			<li class="active"><span>${i}</span></li>
		</c:when>
		<c:otherwise>
			<li><a
				href="javascript:boardPagingListAjaxHtml(${board_id},${i}, ${pageVo.pageSize});">${i}</a></li>
		</c:otherwise>
	</c:choose>

</c:forEach>

<c:choose>
	<c:when test="${pageVo.page == paginationSize}">
		<li class="next disabled"><span>›</span></li>
	</c:when>
	<c:otherwise>
		<li class="next"><a
			href="javascript:boardPagingListAjaxHtml(${board_id},${pageVo.page+1}, ${pageVo.pageSize});">›</a></li>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${pageVo.page == paginationSize}">
		<li class="next disabled"><span>»</span></li>
	</c:when>
	<c:otherwise>
		<li class="next"><a
			href="javascript:boardPagingListAjaxHtml(${board_id},${data.paginationSize}, ${pageVo.pageSize});">»</a></li>
	</c:otherwise>
</c:choose>

