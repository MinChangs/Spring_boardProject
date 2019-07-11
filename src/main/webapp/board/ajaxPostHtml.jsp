<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach items="${replyList}" var="reply">
	<c:choose>
		<c:when test="${reply.use_yn == 'y'}">
			<label class="control-label">${reply.reply_content}&nbsp;&nbsp;
				[${reply.userid} / <fmt:formatDate value="${reply.reply_dt}"
					pattern="yyyy-MM-dd" />]
			</label>&nbsp;
			<c:choose>
				<c:when test="${reply.userid == USER_INFO.userId}">
					<button  name="deleteReplyBtn"
						class="btn btn-danger"
						onclick="javascript:ajaxdeleteReply(${reply.reply_seq},${post.post_seq});">삭제</button>
				</c:when>
			</c:choose>
			<br>
		</c:when>
		<c:otherwise>
			<label class="control-label">[삭제된 댓글입니다.]</label>
			<br>
		</c:otherwise>
	</c:choose>
</c:forEach>