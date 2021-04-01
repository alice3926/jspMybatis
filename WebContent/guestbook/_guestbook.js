var path = $("#span_path").text();

$(document).ready(function(){
   //alert('111');
	suntaek_proc('list','1','');
});


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
	var url = "${path}/guestbook_servlet/" + value1 + ".do";
	var url = path+"/guestbook_servlet/" + value1 + ".do";*/
	var url = $("#span_path").text() + "/guestbook_servlet/" + value1 + ".do";
	
	if(value1 == "list"){
		param = {
			"pageNumber" : $("#span_pageNumber").text(),
			"search_option" : $("#span_search_option").text(),
			"search_data" : $("#span_search_data").text()
		} 
	}else if(value1 == "chuga"){
		$("#span_no").text("");
		param = {}
	}else if(value1 == "chugaProc"|| value1 == "sujungProc" || value1 == "sakjaeProc"){
		param = {
			"no" : $("#span_no").text(),
			"name" : $("#name").val(),
			"passwd" : $("#passwd").val(),
			"email" : $("#email").val(),
			"content" : $("#content").val()
		}

	}else if(value1 == "sujung" || value1 == "sakjae"){
		param = {
			"no" : $("#span_no").text()
		}
	}	
	console.log(url);
	$.ajax({
		type: "post",
		data: param,
		url: url,
		success: function(data){
			if(value1 == "list" || value1 == "chuga"|| value1 == "sujung"|| value1 == "sakjae"){
				$("#result").html(data);
			}else if(value1 =="chugaProc"){
				$("#result").html(data);
				if ($("#span_passwd").text() == "T"){
					alert('추가되었습니다.');
					suntaek_all();
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
			 		suntaek_proc('list','1','');
			 	}else{
			 		alert('비밀번호가 다릅니다.');
			 		suntaek_proc('sujung','0',$("#span_no").text());
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
	console.log('aaa');
	$("#span_search_option").text("");
	$("#span_search_data").text("");
	suntaek_proc('list','1','');
}

