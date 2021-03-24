<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>    
<form name="DirForm">      
<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2"><h2>회원정보 삭제</h2></td>
	</tr>
	<tr>
		<td>회원번호</td>
		<td><input type="hidden" name="no" value="${dto.no}">${dto.no} </td>
	</tr>	
	<tr>	
		<td>아이디</td>
		<td><input type="hidden" name="id" id="id" value="${dto.id}">${dto.id} </td>
	</tr>
	<tr>	
		<td>비밀번호</td>
		<td><input type="password" name="passwd"  id="passwd" value=""> </td>
	</tr>	
	<tr>
		<td>이름</td>
		<td>${dto.name}  </td>
	</tr>	
	<tr>
		<td>성별</td>
		<td>${dto.gender}  </td>
	</tr>	
	<tr>
		<td>태어난년도</td>
		<td>${dto.bornYear}  </td>
		
	</tr>	
	<tr>
		<td>가입일</td>
		<td>${dto.regiDate}  </td>
	</tr>

	<tr>
		<td colspan="2" align="center" style="height:50px;">
			
			<button type="button" onclick="suntaek_proc('sakjaeProc','','${dto.no}');">삭제</button>			
		</td>
	</tr>
</table>
</form>

    