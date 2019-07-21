<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="blog-header">
	<h1 class="blog-title">Main (Tiles)</h1>
	<p class="lead blog-description">Jsp / Spring.</p>
</div>

<div class="row">

	<div class="col-sm-8 blog-main">

		<div class="blog-post">
			<h2 class="blog-post-title">JSP</h2>
			<p class="blog-post-meta">2017.10.30, room 201</p>
			<p>jsp를 통한 웹 프로그래밍 학습</p>
			<hr>

			<h3>뉴스</h3>
			<p>뉴스크롤링.</p>
<!-- 			<ul> -->
<%-- 				<li>${userVo}</li> --%>
<!-- 				<li>servlet 동작원리</li> -->
<!-- 				<li>jsp와 servlet의 관계</li> -->
<!-- 				<li>jsp 스크립틀릿 요소</li> -->
<!-- 				<li>jsp action tag (standard)</li> -->
<!-- 				<li>jstl</li> -->
<!-- 				<li>db pooling</li> -->
<!-- 				<li>페이지 모듈화</li> -->
				${news}

<!-- 			</ul> -->
			
		</div>
	</div>
	<!-- /.blog-main -->
</div>