<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2">
			<h2>상품등록</h2>
		</td>
	</tr>
	<tr>
		<td style="align:center;"> 상품명	</td>
		<td><input type="text" name="name" id="name"></td>
	</tr>
		<tr>
		<td style="align:center;"> 가격	</td>
		<td><input type="text" name="price" id="price"></td>
	</tr>
	<tr>
		<td style="align:center;"> 상품설명	</td>
		<td><textarea name="description" id="description" style="width:300px; height:100px;" wrap="hard"></textarea></td>
	</tr>
	<tr>
		<td style="align:center;"> 상품이미지	</td>
		<td>
			<input type="file" name="file">
			<input type="file" name="file">
			<input type="file" name="file">
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
		$("#name").focus(); //페이지 준비 되면 바로 입력할수 있도록 커서띄움
		
		$("#btnChuga").click(function() {
			if (confirm('등록하시겠습니까?')) {
				suntaek_proc('chugaProc','1','');
			}
		});
		$("#btnList").click(function(){
			suntaek_proc('list','1','');
		});
	});
</script>