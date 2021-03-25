<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>    

<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2">
			<h2>이메일 보내기</h2>
		</td>
	</tr>
	<tr>
		<td style="align: center;">발신자 이름</td>
		<td>
			<input type="text" name="fromName" id="fromName">
		</td>
	</tr>
	<tr>
		<td style="align: center;">발신자 이메일</td>
		<td>
			<input type="text" name="fromEmail" id="fromEmail">
		</td>
	</tr>
	<tr>
		<td style="align: center;">수신자 이메일</td>
		<td>
			<input type="text" name="toEmail" id="toEmail">
		</td>
	</tr>
	<tr>
		<td style="align: center;">제목</td>
		<td>
			<input type="text" name="subject" id="subject">
		</td>
	</tr>
	<tr>
		<td style="align: center;">내용</td>
		<td>
			<textarea name="content" id="content" style="width:300px;height:100px;" wrap="hard"></textarea>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="50px;">
			<button type="button" id="btnSave">메일보내기</button>
		</td>
	</tr>
</table>


<script>
	$(document).ready(function(){
		$("#fromName").focus();
				
		$("#btnSave").click(function(){
			if(confirm('발송하시겠습니까?')){
				GoPage('chugaProc');
			}
		});
	});
</script>