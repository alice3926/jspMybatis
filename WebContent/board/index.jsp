<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

menu_gubun : ${menu_gubun }<br>
naljaMap : ${naljaMap }<br>
ip : ${ip }<br>
tbl : <span id = "span_tbl">${tbl }</span><br>
pageNumber : <span id="span_pageNumber">${pageNumber }</span><br>
no :  <span id="span_no">${no }</span><br>

search_option : <span id = "span_search_option">${search_option }</span><br>
search_data : <span id="span_search_data">${search_data }</span><br>
passwd : <span id="span_passwd"></span><br>

commentPageNumber : <span id="span_commentPageNumber">${commentPageNumber }</span><br>
commentNo :  <span id="span_commentNo">${commentDto.comment_no }</span><br>
<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트 위한 것  -->


<div id="result" style="border:1px solid red; height:1000px; position : relative;" ></div>

<script>
$(document).ready(function() {
	<c:if test="${menu_gubun == 'board_index'}">
		GoPage('list');
	
	</c:if>
});
</script>

<script type="text/javascript" src="${path }/board/_board.js"></script>