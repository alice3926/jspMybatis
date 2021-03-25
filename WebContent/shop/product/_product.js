var path = $("#span_path").text();

$(document).ready(function(){
	suntaek_proc('list','1','');
});


function suntaek_proc(value1, value2, value3){
	if (value1=='chuga'){
		
		$("#span_no").text("");
	}else if(value1=='chugaProc'){
		
	}else if(value1=='view'){
		$("#span_no").text(value3);
	}
	
	if(value1 !=''){
		$("#span_proc").text(value1);
	}
	if(value2 !=''){
		$("#span_pageNumber").text(value2);
	}
	if(value3 !=''){
		$("#span_no").text(value3);
	}

	GoPage(value1);
}

   function suntaek_list(value1) {
      $("#span_list_gubun").text(value1);
      $("#span_pageNumber").text(1);
       suntaek_proc('list','1','');
   }






function GoPage(value1){
	var param={};
	var process_data;
	var content_type;
	var url = $("#span_path").text() + "/product_servlet/" + value1 + ".do";
	
	if(value1=='chuga'){
		param={}
	}else if(value1=='chugaProc'){
		process_data = false;
		content_type = false;
		
		param = new FormData();
	
		param.append("name", $("#name").val());
		param.append("price", $("#price").val());
		param.append("description", $("#description").val());
	
		var file_counter = parseInt($('input[name="file"]').length);
		for(i=0; i<file_counter; i++){
			param.append(i, $('input[name="file"]')[i].files[0]);
		}
	}else if(value1=='view'){
		param.no=$("#span_no").text();
		
	}else if(value1=='list'){
		param.pageNumber=$("#span_pageNumber").text();
		param.search_option=$("#span_search_option").text();
		param.search_data=$("#span_search_data").text();
	
	}else if (value1=='sujung'){
		param.no=$("#span_no").text();
		
	}else if (value1=='sakje'){
		param.no=$("#span_no").text();
	}else if (value1=='sakjeProc'){
		param.no=$("#span_no").text();
	}else if(value1=='sujungProc'){
		process_data = false;
		content_type = false;
		
		param = new FormData();
		param.append("no", $("#span_no").text());
		param.append("name", $("#name").val());
		param.append("price", $("#price").val());
		param.append("description", $("#description").val());
	
		var file_counter = parseInt($('input[name="file"]').length);
		for(i=0; i<file_counter; i++){
			param.append(i, $('input[name="file"]')[i].files[0]);
		}
	}
	
	console.log(url);
	$.ajax({
		
		type:"post",
		data: param,
		processData:process_data,
		contentType:content_type,
		url: url,
		success: function(data){ 
			if(value1=='chugaProc'){
				suntaek_proc('list','1','');
			}else if(value1=='sujungProc'){
				suntaek_proc('list','1','');
			}else if(value1=='sakjeProc'){
				$("#result").html(data);
				if ($("#span_result").text() == "T"){
					alert('삭제되었습니다.');
			 		GoPage('list', '1');
			 	}else{
			 		alert('삭제실패');
			 		GoPage('list', '1');
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
	$("#span_list_gubun").text("all"),
	$("#span_search_option").text("");
	$("#span_search_data").text("");
	suntaek_proc('list','1','');
}


   

