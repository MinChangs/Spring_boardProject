<%@page import="kr.or.ddit.paging.model.PageVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<title>${boardVo.name}</title>
<form id="searchFrm">
	<input type="hidden" value="${board_id}" id="baord_id" name="board_id">
	<select id="searchSelect" name="searchSelect">
		<option id="title" value="title">게시글 제목</option>
		<option id="content" value="content">내용</option>
	</select>
	<input type="text" id="searchTxt" name="searchTxt">
	<button id="searchBtn" type="button">검색</button>
</form>
<style>
.userTr:hover {
	cursor: pointer;
}
</style>
<!-- css, js -->
<%@include file="/common/basicLib.jsp"%>
<script>
	$(document).ready(function() {
		
		$("#boardListTbody").on("click", ".userTr", function() {
			console.log("userTr click")
			//userId를 획득하는 방법
			
			//사용자 아이디를 %$serId 값으로 설정해주고
			
			var userId = $(this).attr('data-userId');
			
			$('#post_seq').val(userId);
			//#frm을 이용하여 submit();
			$("#frm").submit();
		});	

		boardPagingListAjaxHtml('${board_id}', 1, 10);
		

		$('#searchBtn').on('click',function(){
			ajaxpostSearch();
		});

	});

	function boardPagingListAjaxHtml(board_id, page, pageSize) {
		$.ajax({
			url : "${pageContext.request.contextPath}/board/boardPagingAjaxHtml",
			data : "board_id=" + board_id + "&page=" + page + "&pageSize=" + pageSize,
			success : function(data) {
				var html = data.split("SEPERATORSEPERATOR");

				$('#boardListTbody').html(html[0]);
				$('.pagination').html(html[1]);

			},
			error : function(status) {
			}
		});

	}
	
	
	function ajaxpostSearch() {
		datas= $('#searchFrm').serialize();
		$.ajax({
			url : "${pageContext.request.contextPath}/board/ajaxpostSearch",
			data : datas,
			success : function(data) {
				console.log(data);
				var html = data.split("SEPERATORSEPERATOR");
				$('#boardListTbody').html(html[0]);
				$('.pagination').html(html[1]);
// 				alert("성공");
			},
			error : function(status) {
				 alert('에러'+status);
			}
		});

	}
	
</script>

<div class="row">
	<div class="col-sm-8 blog-main">
		<h2 class="sub-header">${boardVo.name}</h2>
		
		
		<form id="frm" action="${pageContext.request.contextPath}/board/post"method="get">
			<input type="hidden" id="post_seq" name="post_seq">
		</form>

		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>게시글 번호</th>
						<th>제목</th>
						<th>작성자 아이디</th>
						<th>작성일시</th>
					</tr>
				</thead>
				<tbody id="boardListTbody">

				</tbody>

			</table>
		</div>

		<a
			href="${pageContext.request.contextPath}/board/insertPost?board_id=${boardVo.board_id}"
			class="btn btn-default pull-right">새글 등록</a>

		<div class="text-center">
			<ul class="pagination">


			</ul>
		</div>
	</div>
</div>

