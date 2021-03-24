var path = $("#span_path").text();

$(document).ready(function(){
   
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
    var param;
	var url = $("#span_path").text() + "/memo_servlet/" + value1 + ".do";
	
	if(value1 == "list"){
		param = {
			"pageNumber" : $("#span_pageNumber").text(),
		} 
	}else if(value1 == "insertProc" || value1 == "sujungProc" || value1 == "sakjaeProc"){
		param = {
			"no" : $("#span_no").text(),
			"oneno" : $("#oneno").val(),
			"writerName" : $("#writerName").val(),
			"content" : $("#content").val()
		}
	}else if(value1 == "sujung") {
		param = {
			"no" : $("#span_no").text(),
			"pageNumber" : $("#span_pageNumber").text()
		}
		
	}
	console.log(url);
	$.ajax({
		type: "post",
		data: param,
		url: url,
		success: function(data){
			if(value1 == "list" ||value1 =="sujung"){
				$("#result").html(data);
			}else if(value1 =="insertProc"){
				$("#result").html(data);
				if ($("#span_passwd").text() == "T"){
					alert('추가되었습니다.');
					$("#span_no").text("");
					
			 		suntaek_proc('list','1','');
			 	}else{
			 		alert('추가실패.');
			 		suntaek_proc('insert','0','');
			 	}
			
			}else if(value1 =="sujungProc" || value1 =="sakjaeProc"){
				$("#result").html(data);
				if ($("#span_passwd").text() == "T"){
					alert('성공.');
					$("#span_no").text("");
					$("#span_writer").text("");
					$("#span_content").text("");
			 		suntaek_proc('list','1','');
			 	}else{
			 		alert('실패.');
			 		suntaek_proc('list','1','');
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


