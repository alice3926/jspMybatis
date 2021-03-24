<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../include/inc_header.jsp" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>id check창</title>
</head>
<body>


<form name="DirForm">
<table border="1" width="80%">
<tr>
	<th colspan="2">아이디 찾기</th>
</tr>
<tr>
	<td width="150">아이디</td>
	<td>
	
		<input type="text" name="id" id="id" value="" style="width:100px;">
		<span id="spanMsg"></span>
		<br>
		<input type="text" name="result" id="result" value="" style="width:100px;">
	</td>
</tr>
<tr>
	<td colspan="2" align="center" style="height:50px;">
		<button type="button" onclick="search();">검색</button>
		<span id="spanView" style="display:none;">
			<button type="button" onclick="save();">적용</button>
		</span>			
	</td>
</tr>
</table>
</form>

<script>
function search(){
	var id=$("#id").val();
	
	if (id == ''){
		alert('아이디를 입력하세요.');
		$("#id").focus();
		return;
	}
	$.ajax({
		type: "post",
		data: $('form').serialize(),
		url: "${path}/member_servlet/id_check_win_Proc.do",
		success: function(data){
			$("#id").val(id);
			$("#result").val(data);
			
			if($("#result").val().length > 0){
				$("#spanMsg").html('사용 가능한 아이디입니다.');
				$("#spanMsg").css('color','blue');
				$("#spanMsg").css('font-size','7px');
				$("#spanView").show();
			}else{
				$("#spanMsg").html('이미 사용중인 아이디입니다.');
				$("#spanMsg").css('color','gray');
				$("#spanMsg").css('font-size','7px');
				$("#spanView").hide();
			}
		}
	});
}

function save(){
	var id = $("#id").val();
	var result =$("#result").val();
	if(id == result && id.length > 0 && result.length){
		var id = document.getElementById("id").value;
		opener.document.getElementById("id").value=id;
		window.close();
			
	}else{
		alert("다시 검색을 해주세요.");
		$("#id").val('');
		$("#result").val('');
		$("#id").focus();	
	}
}
</script>

</body>
</html>