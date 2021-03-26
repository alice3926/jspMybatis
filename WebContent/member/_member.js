var path = $("#span_path").text();
var value_;
$(document).ready(function(){
   //alert('111');
console.log(value_);
if(value_=="chuga"){
	suntaek_proc('chuga','1','');
}else if (value_=="sujung"){
	suntaek_proc('sujung','1','');
}else{
	suntaek_proc('list','1','');
}
});

function getgujun(value1){
	console.log('getValue in');
	value_ = value1;
	console.log(value_);
	if(value_=='login'){
		suntaek_proc('login','','');
	}else if(value_=='chuga'){
		suntaek_proc('chuga','','');
	}else{
		suntaek_proc('list','','');
	}
	
	
}

function suntaek_proc(value1, value2, value3){
   $("#span_proc").text(value1);
   
   if (value2 != "0"){ 
      $("#span_pageNumber").text(value2);
   }
   
   if (value3 != "0"){
      $("#span_no").text(value3);
   }
   GoPage(value1);   
}

function GoPage(value1){
    var param;/*
	var url = "${path}/member_servlet/" + value1 + ".do";
	var url = path+"/member_servlet/" + value1 + ".do";*/
	var url = $("#span_path").text() + "/member_servlet/" + value1 + ".do";
	
	if(value1 == "list"){
		param = {
			"pageNumber" : $("#span_pageNumber").text(),
			"search_option" : $("#span_search_option").text(),
			"search_data" : $("#span_search_data").text()
		} 
	}else if(value1 == "chuga"){
		$("#span_no").text("");
		param = {}
	}else if(value1 == "chugaProc" || value1 == "sujungProc" || value1 == "sakjaeProc"){
		param = {
			"no" : $("#span_no").text(),
			"id" : $("#id").val(),
			"passwd" : $("#passwd").val(),
			"passwdChk" : $("#passwdChk").val(),
			"name" : $("#name").val(),
			"gender" : $("#gender").val(),
			"bornYear" : $("#bornYear").val(),
			"postcode" : $("#sample6_postcode").val(),
			"address" : $("#sample6_address").val(),
			"detailAddress" : $("#sample6_detailAddress").val(),
			"extraAddress" : $("#sample6_extraAddress").val()
		}

	}else if(value1 == "view"){
		param = {
			"pageNumber" : $("#span_pageNumber").text(),
			"search_option" : $("#span_search_option").text(),
			"search_data" : $("#span_search_data").text(),
			"no" : $("#span_no").text()
		}
	}else if(value1 == "sujung" || value1 == "sakjae"){
		param = {
			"no" : $("#span_no").text()
		}
	}else if(value1 == "login"){
		$("#span_no").text("");
	}else if(value1 == "loginProc"){
		param = {
			"id" : $("#id").val(),
			"passwd" : $("#passwd").val()
		}
	}	
	console.log(url);
	$.ajax({
		type: "post",
		data: param,
		url: url,
		success: function(data){
			if(value1 == "list" || value1 == "veiw" || value1 == "chuga"|| value1 == "sujung"|| value1 == "sakjae"){
				$("#result").html(data);
			}else if(value1 =="chugaProc"){
				$("#result").html(data);
				if ($("#span_passwd").text() == "T"){
					alert('추가되었습니다.');
			 		suntaek_proc('list','1','');
			 	}else{
			 		alert('추가실패.');
			 		suntaek_proc('chuga','0','');
			 	}
			}else if(value1 == 'sakjaeProc'){
				$("#result").html(data);
					if ($("#span_passwd").text() == "T"){
					alert('삭제되었습니다.');
			 		suntaek_proc('list','1','');
			 	}else{
			 		alert('비밀번호가 다릅니다.');
			 		suntaek_proc('sakjae','0',$("#span_no").text());
			 	}
				
			}else if(value1 =="sujungProc"){
				$("#result").html(data);
					if ($("#span_passwd").text() == "T"){
					alert('수정되었습니다.');
			 		suntaek_proc('view','0',$("#span_no").text());
			 	}else{
			 		alert('비밀번호가 다릅니다.');
			 		suntaek_proc('sujung','0',$("#span_no").text());
			 	}
				
			}else if(value1 =="loginProc"){
				$("#result").html(data);
					if ($("#span_passwd").text() == "T"){
					alert('로그인성공');
			 		suntaek_proc('list','1','');
			 	}else{
			 		alert('로그인실패');
			 		suntaek_proc('login','0','');
			 	}
				
			}else{
				$("#result").html(data);
			}
		}
	});
	
	
}

function suntaek_page(value1){
	$("#span_pageNumber").text(value1);
	$("#span_no").text("");
	GoPage('list');
}
function suntaek_all(){
	$("#span_search_option").text("");
	$("#span_search_data").text("");
	suntaek_proc('list','1','');
}

function suntaek_gender(value1){
	if(value1=='M'){
		$("#gender").val(value1);
	}else{
		$("#gender").val(value1);
	}
}

