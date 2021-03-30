<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>
<c:if test="${sessionScope.cookNo==null || sessionScope.cookNo==0 }">
	양양의 홈페이지에 오신것을 환영합니다.<br>
	<img src="${path }/attach/image/butterBread.jpeg" title="앙버터빵" alt="앙버터빵" width="400px" height="500px">
	<!--  chart 넣는것이 면접관들 눈에 보기 좋음 -->
</c:if>
<c:if test="${sessionScope.cookNo!=null && sessionScope.cookNo>0 }">
	<font style="font-size:100px; font-weight:bold;">${sessionScope.cookName }님
	<br>환영합니다.</font>
</c:if>