<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<form name="DirForm">
<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2"><h2>로그인</h2></td>
	</tr>
	<tr>
		<td width="150">아이디</td>
		<td><input type="text" name="id" value=""></td>
	</tr>
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="passwd" value=""></td>
	</tr>
	<tr>
		<td colspan="2" align="center" style="height:50px;">
			<button type="button" onclick="login();">LOGIN</button>
			&nbsp;&nbsp;
			<button type="button" onclick="join();">JOIN</button>			
		</td>
	</tr>
</table>
</form>

<script>
function login(){
	if(confirm('로그인하시겠습니까?')){
		DirForm.method="post";
		DirForm.action="${path}/member_servlet/loginProc.do";
		DirForm.submit();
	}
}
function join(){
	if(confirm('가입페이지로 이동하시겠습니까?')){
		location.href="${path}/member_servlet/index.do?word=chuga";
	}
}
</script>
    