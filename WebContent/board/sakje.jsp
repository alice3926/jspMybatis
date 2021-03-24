<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>    

		<table border="1" align="center" width="80%">
			<tr>
				<td colspan="2">
					<h2>게시글 삭제</h2>
				</td>
			</tr>
			<tr>
				<td style="align:center;">작성자</td>
				<td><input type="text" name="writer" id="writer" value="${dto.writer }" readonly></td>	
			</tr>
			<tr>
				<td style="align:center;">이메일</td>
				<td><input type="text" name="email" id="email" value="${dto.email }" readonly></td>	
			</tr>
			<tr>
				<td style="align:center;">비밀번호</td>
				<td><input type="password" name="passwd" id="passwd" value="${dto.passwd }" ></td>	
			</tr>	
			<tr>
				<td style="align:center;">제목</td>
				<td><input type="text" name="subject" id="subject" value="${dto.subject }" readonly></td>	
			</tr>
			
			<tr>
				<td style="align:center;">내용</td>
				<td>
					<textarea name="content" id="content" style="width:300px; height:100px;" wrap="hard" readonly>
					${dto.content }
					</textarea>
				</td>	
			</tr>
			<tr>
				<td style="align:center;">공지글</td>
				<td>
					<input type="text" name="noticeGubun" id="noticeGubun" value="<c:if test = "${dto.noticeNo > 0}">T</c:if><c:if test = "${dto.noticeNo == 0}">F</c:if>">
					
				</td>
			</tr>

		<tr>
			<td style="align:center;">비밀글</td>
			<td>
				<input type="text" name="secretGubun" id="secretGubun" value="${dto.secretGubun}" readonly>
				
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2" height="50px">
				<button type="button" id="btnSakje">삭제하기</button>
				<button type="button" id="btnList">목록으로</button>
			</td>
		</tr>
	</table>
				
<script>
$(document).ready(function(){
	$("#passwd").select();
	$("#passwd").focus();
	
	$("#btnSakje").click(function(){
		if(confirm('삭제하시겠습니까?')){
			GoPage('sakjeProc');
		}
	})
	$("#btnList").click(function(){
		suntaek_page('1');
	});

});	





</script>
