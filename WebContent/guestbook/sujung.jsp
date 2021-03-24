<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<h1>방명록 수정</h1>
<table border="1" width="80%">
	<tr>
		<td style="width:20%;">이름</td>
		<td ><input type="text" name="name" id="name" value="${dto.name }"></td>
	</tr>
	<tr>
		<td >이메일</td>
		<td><input type="text" name="email"  id="email" value="${dto.email }"></td>
	</tr>
	<tr>
		<td >비밀번호</td>
		<td><input type="password" name="passwd"  id="passwd" value=""></td>
	</tr>
	<tr>
		<td>내용</td>
		<td>
			<textarea id="content" name="content"  id="content" rows="10" cols="80">
			${dto.content }
			</textarea>
		</td>
	</tr>
	<tr>
		<td >등록일</td>
		<td>${dto.regi_date }</td>
	</tr>
	<tr>
		<td colspan="2" align="center" style="height:20px;">
			<button type="button" onclick="GoPage('sujungProc');">수정</button>
			<button type="button" onclick="suntaek_proc('list','1','');">목록으로</button>
		</td>
	</tr>
</table>
