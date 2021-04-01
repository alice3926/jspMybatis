<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>
<div style="display:none;">
proc : <span id="span_proc"></span><br>
pageNumber : <span id="span_pageNumber">${pageNumber }</span><br>
no :  <span id="span_no">${no }</span><br>
search_option : <span id = "span_search_option">${search_option }</span><br>
search_data : <span id="span_search_data">${search_data }</span><br>
path : <span id="span_path">${path }</span><br>
result :  <span id = "span_result"></span>
</div>
<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트 위한 것  -->


<div id="result" style="border:1px solid red; height:1000px; position : relative;" ></div>
<script type="text/javascript" src="${path }/shop/product/_product.js"></script>




<script>
$(document).ready(function() {
	<c:if test="${menu_gubun == 'product_index'}">
		suntaek_proc('list','1','');
		
	</c:if>
});
</script>