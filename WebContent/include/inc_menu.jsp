<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>


	<table border="0" align="center">
		<tr>
			<td colspan="15" align="right" style="padding:5px 20px 10px;">
				<c:if test="${sessionScope.cookNo==null || sessionScope.cookNo==0 }">
					<a href="${path}/member_servlet/index.do?word=login">로그인</a>
					<a href="${path }/member_servlet/index.do?word=chuga">회원가입</a>	
				</c:if>
				<c:if test="${sessionScope.cookNo!=null && sessionScope.cookNo>0 }">
					<font style="font-size:20px; font-weight:bold;">${sessionScope.cookName }님 환영합니다.</font>
					<br>
						<a href="${path}/member_servlet/index.do?word=sujung&no=${sessionScope.cookNo}">[회원정보수정]</a>
						<a href="${path}/member_servlet/index.do?word=sakjae&no=${sessionScope.cookNo}">[회원탈퇴]</a>	
						<a href="${path}/member_servlet/logout.do">[로그아웃]	</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td style="padding:0px 20px;" id="home">
					<a href="${path }">HOME</a>		
			</td>
			<td style="padding:0px 20px;" id="member">
				<a href="${path }/member_servlet/index.do" >회원관리</a>
			</td>
			<td style="padding:0px 20px;" id="memo">
				<a href="${path }/memo_servlet/index.do">메모장</a>
			</td>
			<td style="padding:0px 20px;" id="guestbook">
				<a href="${path }/guestbook_servlet/index.do">방명록</a>			
			</td>
			<td style="padding:0px 20px;" id="survey">
				<a href="${path }/survey_servlet/index.do">설문조사</a>			
			</td>
			<td style="padding:0px 20px;" id="questionBank">
				<a href="${path }/survey_servlet/index.do?moveword=questionBank">문제은행</a>			
			</td>
			<td style="padding:0px 20px;" id="board">
				<a href="${path }/board_servlet/index.do">게시판</a>			
			</td>
			<td style="padding:0px 20px;" id="product">
				<a href="${path }/product_servlet/index.do">MAll(상품관리)</a>			
			</td>
			<td style="padding:0px 20px;" id="mall">
				<a href="${path }/mall_servlet/index.do">MAll(상품몰)</a>			
			</td>
			<td style="padding:0px 20px;" id="chart">
				<a href="${path }/chart_servlet/index.do">Chart</a>			
			</td>
			<td style="padding:0px 20px;" id="smtpEmail">
				<a href="${path }/email_servlet/index.do">Email</a>			
			</td>
			<td style="padding:0px 20px;">
				<a href="#">관리자</a>			
			</td>
		</tr>
	</table>
	<br><c:set var="str01" value="${fn:indexOf(menu_gubun,'_')}"></c:set>
	<br><c:set var="str02" value="${fn:substring(menu_gubun,0,str01)}"></c:set>
	<br>${menu_gubun } / ${str01 } / ${str02}
	
	<c:choose>
	<c:when test="${str02 =='index'}">
		<script>$("#home").css("background-color","silver");</script>	
	</c:when>
	<c:when test="${str02 =='member'}">
		<script>$("#member").css("background-color","skyblue");</script>	
	</c:when>
	<c:when test="${str02 =='memo'}">
		<script>$("#memo").css("background-color","pink");</script>	
	</c:when>
	<c:when test="${str02 =='guestbook'}">
		<script>$("#guestbook").css("background-color","yellow");</script>	
	</c:when>
	<c:when test="${str02 =='survey'}">
		<script>$("#survey").css("background-color","silver");</script>	
	</c:when>
	<c:when test="${str02 =='questionBank'}">
		<script>$("#questionBank").css("background-color","silver");</script>	
	</c:when>
	<c:when test="${str02 =='board'}">
		<script>$("#board").css("background-color","silver");</script>	
	</c:when>
	<c:when test="${str02 =='product'}">
		<script>$("#product").css("background-color","pink");</script>	
	</c:when>
	<c:when test="${str02 =='mall'}">
		<script>$("#mall").css("background-color","skyblue");</script>	
	</c:when>
	<c:when test="${str02 =='chart'}">
		<script>$("#chart").css("background-color","pink");</script>	
	</c:when>
	<c:when test="${str02 =='email'}">
		<script>$("#smtpEmail").css("background-color","silver");</script>	
	</c:when>
	</c:choose>
		
		
		
		