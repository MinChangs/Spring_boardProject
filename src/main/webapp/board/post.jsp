<%@page import="kr.or.ddit.paging.model.PageVo"%>
<%@page import="kr.or.ddit.user.model.UserVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<meta charset="utf-8">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>게시글 상세조회</title>

<!-- css, js -->
<%@include file="/common/basicLib.jsp"%>
<!-- import script -->
<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js" type="text/javascript"></script>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f7a976957407edfce0f821ce36e56056&libraries=services,clusterer,drawing"></script>
<script>
$(document).ready(function() {
	var msg = '${msg}';
	if(msg !='')
		alert(msg);
	

	$('#ModifyBtn').on("click", function() {
		$("#frm").attr("action", "${pageContext.request.contextPath}/board/postModify");
		$('#frm').submit();
	});
	
	
	$('#deleteBtn').on("click", function() {
		if(confirm("게시물을 삭제하시겠습니까?")) {
			$("#frm").attr("action", "${pageContext.request.contextPath}/board/postDelete");
			$('#frm').submit();	
		}
	});
	
	$('#rePostBtn').on("click", function() {
		$("#frm").attr("action", "${pageContext.request.contextPath}/board/replyPost");
		$('#frm').submit();
	});
	$('#replyBtn').on("click", function() {
		ajaxpostPost("${post.post_seq}");
	});
	
	$('#deleteReplyBtn').on("click", function() {
		ajaxpostPost("${post.post_seq}");
	});
	
	
	ajaxgettPost("${post.post_seq}");
	
	//결제 CID 초기화
	IMP.init("imp21318637");
	
	
	
	getLocation();

	




});
	
	
	//import결제 함수 
	function requestPay() {
		IMP.request_pay({
			//결제회사
		    pg : 'kakaopay', // version 1.1.0부터 지원.
		    //입급종류 ex)카드, 무통장 입금 등..
		    pay_method : 'card',
		    //주문번호 -> seq로 대체
		    merchant_uid : 'merchant_' + new Date().getTime(),
		    //주문명
		    name : '주문명:결제테스트',
		    //가격 -> 게시글 가격으로 넣을 졔정
		    amount : 100,
		    //구매자 이메일-> MemberVo값으로 넣을 예정
		    buyer_email : 'alsckd123@naver.com',
		    //구매자 이름 -> MemberVo값으로 넣을 예정
		    buyer_name : '구매자이름',
		    //구매자 전화번호 -> MemberVo값으로 넣을 예정
		    buyer_tel : '010-1234-5678',
		    //구매자 집 주소 -> MemberVo값으로 넣을 예정
		    buyer_addr : '서울특별시 강남구 삼성동',
		    //구매자 zipcode -> MemberVo값으로 넣을 예정
		    buyer_postcode : '123-456',
		}, function(rsp) {
		    if ( rsp.success ) {
		    	//[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
		    	jQuery.ajax({
		    		url: "/payments/complete", //cross-domain error가 발생하지 않도록 동일한 도메인으로 전송
		    		type: 'POST',
		    		dataType: 'json',
		    		data: {
			    		imp_uid : rsp.imp_uid
			    		//기타 필요한 데이터가 있으면 추가 전달
		    		}
		    	}).done(function(data) {
		    		//[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
		    		if ( everythings_fine ) {
		    			var msg = '결제가 완료되었습니다.';
		    			msg += '\n고유ID : ' + rsp.imp_uid;
		    			msg += '\n상점 거래ID : ' + rsp.merchant_uid;
		    			msg += '\결제 금액 : ' + rsp.paid_amount;
		    			msg += '카드 승인번호 : ' + rsp.apply_num;

		    			alert(msg);
		    		} else {
		    			//[3] 아직 제대로 결제가 되지 않았습니다.
		    			//[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
		    		}
		    	});
		    } else {
		        var msg = '결제에 실패하였습니다.';
		        msg += '에러내용 : ' + rsp.error_msg;

		        alert(msg);
		    }
		});
	}


//gps정보 얻어오기
function getLocation() {
	  if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(showPosition);
	  } else { 
	    x.innerHTML = "Geolocation is not supported by this browser.";
	  }
	}
	

var nLat;
var nLng;

function showPosition(position) {
	
	nLat = position.coords.latitude;
	nLng = position.coords.longitude;
	
	map_init(nLat, nLng);
	
	
}

var map;

function map_init(nLat, nLng){
	var container = document.getElementById('map');
	var options = {
		center: new kakao.maps.LatLng(nLat, nLng),
		level: 3
	};

	map = new kakao.maps.Map(container, options);
	
	drawMarker(nLat,nLng)
	
}


function drawMarker(nLat, nLng){
// 	var imageSrc = '../img/place.png', // 마커이미지의 주소입니다    
//     imageSize = new kakao.maps.Size(32, 37), // 마커이미지의 크기입니다
//     imageOption = {offset: new kakao.maps.Point(16, 37)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
// var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
//     markerPosition = new kakao.maps.LatLng(nLat, nLng); // 마커가 표시될 위치입니다

// // 마커를 생성합니다
// var marker = new kakao.maps.Marker({
//   position: markerPosition,
//   image: markerImage // 마커이미지 설정 
// });

// // 마커가 지도 위에 표시되도록 설정합니다
// marker.setMap(map);  





var imageSrc = '../img/place.png', // 마커이미지의 주소입니다    
imageSize = new kakao.maps.Size(32, 37), // 마커이미지의 크기입니다
imageOption = {offset: new kakao.maps.Point(16, 37)}; // 마커이미지의 옵


var geocoder = new daum.maps.services.Geocoder();

var listData = [
	
	    {
	        groupAddress: '대전광역시 중구 중앙로76' 
	    },
	    {
	        groupAddress: '대전 중구 중앙로 77' 
	    },
	    {
	        groupAddress: '대전 유성구 유성대로654번길 130' 
	    }

];

    
for (var i=0; i < listData.length ; i++) {
	// 주소로 좌표를 검색합니다
	geocoder.addressSearch(listData[i].groupAddress, function(result, status) {

	    // 정상적으로 검색이 완료됐으면 
	     if (status === daum.maps.services.Status.OK) {
		   var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
	       markerPosition = new daum.maps.LatLng(result[0].y, result[0].x);


	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new daum.maps.Marker({
	            map: map,
	            position: markerPosition,
	            image: markerImage
	        });

	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new daum.maps.InfoWindow({
	            content: result[0].y + "," + result[0].x
	        });
	        infowindow.open(map, marker);
	        
			var coords = new daum.maps.LatLng(nLat, nLng);
			map.setCenter(coords);
// 	        marker.setMap(map);

	    } 
	})


	} 

	
}








function ajaxgettPost(post_seq) {
	$.ajax({
		url : "${pageContext.request.contextPath}/board/ajaxPost",
		method : 'get',
		data : "post_seq="+post_seq,
		success : function(data) {
			var html=data;
			$('#ajaxPost').html(html);

		},
		error : function(status) {
				alert('에러'+status);
		}
	});
	
	
}


function ajaxpostPost(post_seq) {
	var datas = $("#postfrm").serialize() ;
	console.log(datas);
	$.ajax({

		url : "${pageContext.request.contextPath}/board/ajaxPost",
		method : 'post',
		data : datas,
		success : function(data) {
			var html=data;
			$('#ajaxPost').html(html);
			$('#replytext').val('');
			alert('댓글 등록 완료');

		},
		error : function(xhr) {
			alert('에러'+xhr.status);
		}
	});
	
		
}
function ajaxdeleteReply(reply_seq,post_seq) {
	$.ajax({
		url : "${pageContext.request.contextPath}/board/ajaxdeleteReply",
		method : 'get',
		data : "reply_seq="+reply_seq+"&post_seq="+post_seq,
		success : function(data) {
			var html=data;
			$('#ajaxPost').html(html);
			alert("댓글삭제 완료");

		},
		error : function(status) {
			alert('에러'+status);
		}
	});

	
}
	









</script>
</head>

<body>
	
				<div class="row">
					<div class="col-sm-8 blog-main">
						<h2 class="sub-header">게시글 상세</h2>
						<form id="frm" class="form-horizontal" role="form" method="get">
							<input type="hidden" name="post_seq" value="${post.post_seq}">
							<input type="hidden" name="boardId" value="${post.board_id}">
							<div class="form-group">
								<label for="userNm" class="col-sm-2 control-label">제목</label>
								<div class="col-sm-10">
									<label  class="control-label">${post.title}</label>
<!-- 									<input type="text" class="form-control" id="userId" name="userId" placeholder="사용자 아이디"> -->
								</div>
							</div>
		
							<div class="form-group">
								<label for="userNm" class="col-sm-2 control-label">내용</label>
								<div class="col-sm-10">
									${post.content}
								</div>
							</div>
							
							<div class="form-group">
								<label for="userNm" class="col-sm-2 control-label">첨부파일</label>
								<div class="col-sm-10">
					
									<c:forEach items="${attachmentList}" var="attach">
										<a href="${pageContext.request.contextPath}/board/fileDownLoad?fileNo=${attach.attachment_seq}&downName=${attach.filename}&post_seq=${post.post_seq}"> ${attach.filename} </a><br>
									</c:forEach>

								
									<label class="control-label"></label>
									<c:choose>
										<c:when test="${post.userid == USER_INFO.userId}">
											<button id="ModifyBtn" class="btn btn-info">수정</button>
											<button id="deleteBtn" class="btn btn-danger">삭제</button>
										</c:when>
									</c:choose>
									<button id="rePostBtn" class="btn btn-info">답글</button>
								</div>
							</div>
							</form>
							<div class="form-group">
								<label for="userNm" class="col-sm-2 control-label">댓글</label>
								<div class="col-sm-10" >
									<div id="ajaxPost">
								
									</div>
									<form id="postfrm" class="form-horizontal" role="form"  method="post">
											<input type="text" name="reply" class="form-control" id="replytext">
											<input type="hidden" name="userId" value ="${USER_INFO.userId}">
											<input type="hidden" name="post_seq" value ="${post.post_seq}">
											<button type="button" id="replyBtn" name="replyBtn" class="btn btn-primary">댓글달기</button>
									</form>
								</div>
								
							</div>

							
							<button onclick="requestPay()">결제하기</button>
							 <div id='calendar'></div>
							
					</div>
				</div>
