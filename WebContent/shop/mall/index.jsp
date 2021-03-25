<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

proc : <span id="span_proc"></span><br>
pageNumber : <span id="span_pageNumber">${pageNumber }</span><br>
no :  <span id="span_no">${no }</span><br>
search_option : <span id = "span_search_option">${search_option }</span><br>
search_data : <span id="span_search_data">${search_data }</span><br>
jumun_su : <span id="span_jumun_su"></span><br>
<input type="text" name="a" style="display: ;"><br><!-- ajax 테스트 위한 것  -->


<div id="result" style="border:1px solid red; height:1000px; position : relative;" ></div>

<script>
$(document).ready(function() {
	<c:if test="${menu_gubun == 'mall_index'}">
		suntaek_proc('mall_list','1','');
	</c:if>
});
function suntaek_proc(value1,value2,value3){
	if(value1 =='mall_list'){
		$("#span_proc").text(value1);
		$("#span_no").text("");
	}else if(value1 =='mall_view'){
		$("#span_proc").text(value1);
		$("#span_no").text(value3);
	}else if(value1 =='mall_search'){
		value1 = 'mall_list';
		$("#span_proc").text(value1);
		$("#span_no").text("");
		$("#span_search_option").text($("#span_search_option").val());
		$("#span_serach_data").text($("#span_search_data").val());
	}else if(value1 =='cart_chuga'){
		$("#span_proc").text(value1);
		$("#span_no").text(value3);
		$("#span_jumun_su").text($("#buy_counter").val());
	}else if(value1 =='cart_list'){
		$("#span_proc").text(value1);
		$("#span_no").text("");
		$("#span_jumun_su").text("");
	}else if(value1 =='cart_view'){
		if(confirm('제품상세보기 페이지로 이동하시겠습니까?')){
			value1 = 'mall_view';
			$("#span_proc").text(value1);
			$("#span_no").text(value3);
		}else{
			return;
		}
	}else if(value1 =='cart_sujung'){
		$("#span_proc").text(value1);
		$("#span_no").text(value3);
	}else if(value1 =='cart_clear'){
		if(confirm('정말 비우시겠습니까?')){
			$("#span_proc").text(value1);
			$("#span_no").text("");
			$("#span_jumun_su").text("");
		}else{
			return;
		}
	}
	
	
	if(value2 !=''){
		$("#span_pageNumber").text(value2);
	}
	if(value3 !=''){
		$("#span_no").text(value3);
	}
	console.log(value1);
	GoPage(value1);
}

function GoPage(value1) {
		var param;
		var process_data;
		var content_type;
		var url = "${path}/mall_servlet/"+value1+".do";
		
		if(value1=="mall_list"){
			param={};
			param.pageNumber = $("#span_pageNumber").text();
			param.search_option = $("#span_search_option").text();
			param.search_data = $("#span_search_data").text();
		}else if(value1=="mall_view"){
			param={};
			param.no = $("#span_no").text();
			param.search_option = $("#span_search_option").text();
			param.search_data = $("#span_search_data").text();
		
		}else if(value1=="cart_chuga"){
			param={};
			param.no = $("#span_no").text();
			param.buy_counter = $("#span_jumun_su").text();
		}else if(value1=="cart_list"){
			param={};
			param.buy_counter = $("#span_jumun_su").text();
 			param.pageNumber = $("#span_pageNumber").text();
		}else if(value1=="cart_sujung"){
			var no = $("#span_no").text();
			param={};
			param.no = $("#span_no").text();
			param.buy_counter = $("#cart_amount"+no).val();
		}else if(value1=="cart_clear"){
			var chk_no = '';
			$('input[type="checkbox"]:checked').each(function(index){
				if (index !=0) {
					chk_no += ',';
				}
				chk_no += $(this).val();
			});
			alert(chk_no);
			param={};
			param.chk_no = chk_no;
		}
		
	console.log(url);
	$.ajax({
		type:"post",
		data: param,
		url: url,
		success: function(data){ 
			if (value1=='cart_chuga' || value1 == 'cart_clear' || value1 == 'cart_sujung') {
				
				value1 = 'cart_list';
				suntaek_proc(value1,'1','');
				
			}else if (value1 == 'sujungProc') {
				suntaek_proc('view','',$("#span_no").text());
				
			}else{
				$("#result").html(data);
			}
			
			
			
		}
	});
	
}




</script>