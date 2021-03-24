

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>


<form name="form">
<h1>방명록 작성</h1>
<table border="1" width="80">
	<tr>
		<td>이름</td>
		<td><input type="text" name="name" id="name" value=""></td>
	</tr>
	<tr>
		<td>이메일</td>
		<td><input type="text" name="email"  id="email" value=""></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="passwd"  id="passwd" value=""></td>
	</tr>
	<tr>
		<td colspan="2">
			<textarea id="content" name="content"  id="content" rows="10" cols="80">
			</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center" style="height:50px;">
			<button type="button" onclick="GoPage('chugaProc');">등록</button>
			<button type="button" onclick="suntaek_proc('list','1','');">목록으로</button>
		</td>
	</tr>
</table>


</form>

</body>
</html>