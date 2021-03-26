<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>    
    
    

    
    
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>

<title>main.jsp</title>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>

<!--
index.jsp를 실행하면
./index.do라는 서블릿? index controller에서 proc돌아가면
url주소는 : http://localhost:8090/jspStudy/index.do
로 뜨지만.
내용은 main.jsp로 뜨게됨.(즉 url주소를 속이는 것. 보안에 좋음)
-->

<table border="1" align="center">
	<tr>
		<td style="padding:20px 20px;">
			<jsp:include page="../include/inc_menu.jsp" ></jsp:include>
		</td>
	</tr>
	<tr>
		<td align="center" style="padding:50px 50px;" height="300px">
			<c:choose>
				<c:when test="${menu_gubun=='index' }">
					<jsp:include page="../main/main_sub.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='member_index' }">
					<jsp:include page="../member/index.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='memo_index' }">
					<jsp:include page="../memo/index.jsp"/>
				</c:when>
			<%-- 	<c:when test="${menu_gubun=='member_login' }">
					<jsp:include page="../member/login.jsp"/>
				</c:when> --%>
				<c:when test="${menu_gubun=='member_chuga' }">
					<jsp:include page="../member/chuga.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='member_sujung' }">
					<jsp:include page="../member/sujung.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='member_sakjae' }">
					<jsp:include page="../member/sakjae.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='id_check' }">
					<jsp:include page="../member/id_check.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='guestbook_index' }">
					<jsp:include page="../guestbook/index.jsp"/>
					
				</c:when>
				<c:when test="${menu_gubun=='survey_index' }">
					<jsp:include page="../survey/index.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='questionBank_index' }">
					<jsp:include page="../survey/index.jsp"/>
				</c:when>		 
				
				<c:when test="${menu_gubun=='board_index' }">
					<jsp:include page="../board/index.jsp"/>
				</c:when>
				
				<c:when test="${menu_gubun=='product_index' }">
					<jsp:include page="../shop/product/index.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='mall_index' }">
					<jsp:include page="../shop/mall/index.jsp"/>
				</c:when>
			
				<c:when test="${menu_gubun=='chart_index' }">
					<jsp:include page="../chart/index.jsp"/>
				</c:when>
				<c:when test="${menu_gubun=='email_index' }">
					<jsp:include page="../email/index.jsp"/>
				</c:when>
				<c:otherwise>
				
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td style="padding:20px 20px;" align="center">
			<jsp:include page="../include/inc_bottom.jsp" ></jsp:include>
		</td>
	</tr>
</table>


</body>
</html>



