<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2">
		<c:choose>
			<c:when test = "${dto.no!=0}">
				<h2>답글 쓰기</h2>
			</c:when>
			<c:otherwise>
				<h2>게시글 쓰기</h2>
			</c:otherwise>
		</c:choose>	
		</td>
	</tr>
	<tr>
		<td style="align:center;"> 작성자	</td>
		<td><input type="text" name="writer" id="writer"></td>
	</tr>
		<tr>
		<td style="align:center;"> 이메일	</td>
		<td><input type="text" name="email" id="email"></td>
	</tr>
		<tr>
		<td style="align:center;"> 비밀번호	</td>
		<td><input type="password" name="passwd" id="passwd"></td>
	</tr>
		<tr>
		<td style="align:center;"> 제목	</td>
		<td><input type="text" name="subject" id="subject" value="${dto.subject }"></td>
	</tr>
	<tr>
		<td style="align:center;"> 내용	</td>
		<td><textarea name="content" id="content" style="width:300px; height:100px;">${dto.content }</textarea></td>
	</tr>
	<c:choose>
			<c:when test = "${dto.no<=0}">
				<tr>
						<td style="align:center;">공지글</td>
						
				</tr>
			</c:when>
			<c:otherwise>
					<tr>
						<td style="align:center;">공지글</td>
						<td>
							<input type="text" name="noticeGubun" id="noticeGubun">
							<input type="checkbox" name="noticeGubunCheckBox" id="noticeGubunCheckBox" value="T" onclick="clickChk('noticeGubun');">
							공지글 체크
						</td>
					</tr>
			</c:otherwise>
		</c:choose>	

		<tr>
		<td style="align:center;">비밀글</td>
		<td>
			<input type="text" name="secretGubun" id="secretGubun">
			<input type="checkbox" name="secretGubunCheckBox" value="T" onclick="clickChk('secretGubun');">
			비밀글 체크
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2" height="50px">
			<button type="button" id="btnChuga">등록하기</button>
			<button type="button" id="btnList">목록으로</button>
		</td>
	</tr>
</table>


<script>
	$(document).ready(function() {
		$("#writer").focus(); //페이지 준비 되면 바로 입력할수 있도록 커서띄움
		
		$("#btnChuga").click(function() {
			if (confirm('등록하시겠습니까?')) {
				
				GoPage('chugaProc','');
			}
		});
		$("#btnList").click(function(){
			GoPage('list','');
		});
	});
	
	function clickChk(value1){
		if(value1=='noticeGubun'){	
			if($("input:checkbox[name=noticeGubunCheckBox]").is(":checked")== true) {
				$("#noticeGubun").val("T");
			}else {
				$("#noticeGubun").val("");
			}
		}else{	
			if($("input:checkbox[name=secretGubunCheckBox]").is(":checked")== true) {
				$("#secretGubun").val("T");
			}else {
				$("#secretGubun").val("");
			}
		}
	}

	
	
</script>




