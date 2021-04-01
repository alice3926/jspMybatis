



function GoPage(value1 , value2) {
	var url = "${path}/board_servlet/"+value1+".do";
	
	
	if(value1=='chuga')	{
		$("#span_no").text("");
		var param={}
	
	}else if(value1=='reply'|| value1=='sujung'||value1=='sakje')	{
		var param={
			"no" : $("#span_no").text()
		}
	
	}else if(value1=='chugaProc'|| value1=='sujungProc'||value1=='sakjeProc'){
		//alert(value1);
		var param={
			"no" : $("#span_no").text(),
			"tbl" : $("#span_tbl").text(),
			"writer" : $("#writer").val(),
			"email" : $("#email").val(),
			"passwd" : $("#passwd").val(),
			"subject" : $("#subject").val(),
			"content" : $("#content").val(),
			"noticeGubun" : $("#noticeGubun").val(),
			"secretGubun" : $("#secretGubun").val()
		}
	}else if(value1=='list'){
		var param ={
				"tbl" : $("#span_tbl").text(),
				"pageNumber" : $("#span_pageNumber").text(),
				"search_option" : $("#span_search_option").text(),
				"search_data" : $("#span_search_data").text()
		}
	}else if(value1 == 'view'){
		$("#span_no").text(value2);
		console.log(value2);
		
		var param ={
				"no" : $("#span_no").text(),
				"commentNo" : $("#span_commentNo").text(),
				"tbl" : $("#span_tbl").text(),
				"pageNumber" : $("#span_pageNumber").text(),
				"search_option" : $("#span_search_option").text(),
				"search_data" : $("#span_search_data").text(),
				"view_passwd" : $("#view_passwd").val() 
		}
	}
	
	console.log(url);
	$.ajax({
		
		type:"post",
		data: param,
		url: url,
		success: function(data){ 
			if(value1=="chugaProc"){
				suntaek_all();
			}else if(value1=="sujungProc"){
				$("#result").html(data);
				
			 	if ($("#span_passwd").text() == "T"){
					alert('수정되었습니다.');
			 		GoPage('view', $("#span_no").text());
			 	}else{
			 		alert('비밀번호가 다릅니다.');
			 		GoPage('sujung',$("#span_no").text());
			 	}
			}else {
				$("#result").html(data);
			}
		}
	});
	
	
}

function suntaek_page(value1){
	$("#span_pageNumber").text(value1);
	$("#span_no").text("");
	GoPage('list','');
}
//comment
function comment_proc(value1,value2){
	var param;
	if (value1 == 'commentSave'){
		 param = {
			       "commentPageNumber" : $("#span_commentPageNumber").text(),
			       "no" : $("#span_no").text(),
			       "writer" : $("#comment_writer").val(),
			       "passwd" : $("#comment_passwd").val(),
			       "content" : $("#comment_content").val()
			    }
	}else if(value1 == 'commentmodifyProc'){
		 
		 param = {
				 "no" : $("#span_no").text(),
			     "commentNo" : $("#span_commentNo").text(),
				 "writer" : $("#comment_writer").val(),
			     "passwd" : $("#comment_passwd").val(),
			     "content" : $("#comment_content").val()

			    }
	}else if(value1 == 'commentSakjaeProc'){
		 $("#span_commentNo").text(value2);
		 
		 param = {
				 "no" : $("#span_no").text(),
			     "commentNo" : $("#span_commentNo").text()
			    }
	}
    var url = "${path}/board_servlet/"+value1+".do";
    console.log(url);
    $.ajax({
       type: "post",
       data: param,
       url: url,
       success: function(data){ //콜백함수 (서버에서 처리가 완료된 후 실행되는 코드)
		if(value1 == 'commentmodifyProc'){
			 $("#span_commentNo").text("");
	         comment_list();
		}else{
			  comment_list();
		}
          
       }
    });
 }
 
 function comment_suntaek_page(value1) {
    $("#span_commentPageNumber").text(value1);
    comment_list();
 }

function suntaek_all(){
	$("#span_search_option").text("");
	$("#span_search_data").text("");
	GoPage('list','');
}
