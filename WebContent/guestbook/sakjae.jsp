

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>


<form name="form">
<h1>방명록 삭제</h1>
<table border="1" width="80%">
	<tr>
		<td style="width:20%;">이름</td>
		<td>${dto.name }</td>
	</tr>
	<tr>
		<td>이메일</td>
		<td>${dto.email }</td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="passwd"  id="passwd" value=""></td>
	</tr>
	<tr>
		<td>내용</td>
		<td>${dto.content }</td>
	</tr>
	<tr>
		<td>등록일</td>
		<td>${dto.regi_date }</td>
	</tr>
	<tr>
		<td colspan="2" align="center" style="height:50px;">
			<button type="button" onclick="GoPage('sakjaeProc');">삭제</button>
			<button type="button" onclick="suntaek_proc('list','1','');">목록으로</button>
		</td>
	</tr>
</table>


</form>

</body>
</html>