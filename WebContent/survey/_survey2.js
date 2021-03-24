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


   function suntaek_list(value1) {
      $("#span_list_gubun").text(value1);
      $("#span_pageNumber").text(1);
       suntaek_proc('list','1','');
   }






function GoPage(value1){
    var param;
	var url = $("#span_path").text() + "/survey_servlet/" + value1 + ".do";
	
	if(value1 == "list"){
		param = {
		 "list_gubun" : $("#span_list_gubun").text(),
         "pageNumber" : $("#span_pageNumber").text(),
         "search_option" : $("#span_search_option").text(),
         "search_data" : $("#span_search_data").text(),
         "search_date_check" : $("#span_search_date_check").text(),
         "search_date_s" : $("#span_search_date_s").text(),
         "search_date_e" : $("#span_search_date_e").text()
		} 
	}else if(value1 == "chuga"){
		$("#span_no").text("");
		param = {}
	}else if(value1 == "chugaProc" || value1 == "sujungProc" || value1 == "sakjaeProc"){
		param = {
			"no" : $("#span_no").text(),
			"question" : $("#question").val(),
			"ans1" : $("#ans1").val(),
			"ans2" : $("#ans2").val(),
			"ans3" : $("#ans3").val(),
			"ans4" : $("#ans4").val(),
			"status" : $("#gender").val(),
			"syear" : $("#syear").val(),
			"smonth" : $("#smonth").val(),
			"sday" : $("#sday").val(),
			"lyear" : $("#lyear").val(),
			"lmonth" : $("#lmonth").val(),
			"lday" : $("#lday").val(),
		}

	}else if(value1 == "view"){
		param = {
			"pageNumber" : $("#span_pageNumber").text(),
			"search_option" : $("#span_search_option").text(),
			"search_data" : $("#span_search_data").text(),
			"no" : $("#span_no").text()
		}
	}else if(value1 == "sujung"){
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

function suntaek_status(value1){
	if(value1=='1'){
		$("#gender").val(value1);
	}else{
		$("#gender").val(value1);
	}
}

   function GoSaveProc(){
	   if(confirm('저장하시겠습니까?')){
	      var param={
	            "answer_total": $("#aaa").text()
	      }
	         $.ajax({
	            type:"post",
	            data: param,
	            url: "${path}/survey_servlet/saveProc.do",
	            success: function(){
	               suntaek_page('1');
	            }
	         });
	   }   
	   
	}
 
    function GoViewProc(){//view.jsp에서
    if(confirm('저장하시겠습니까?')) {
    		DirForm.method="post";
    		DirForm.action="${path}/survey_servlet/viewProc.do";
    		DirForm.submit();
    }
 }


function GoViewProc2(){
	  var param = {
			  "no":$("#span_no").text(),
			  "answer" : $("#span_answer").text()
	  }
	  
	  
	  $.ajax({
        type: "post",
        data: param,
        url: "${path}/survey_servlet/viewProc2.do",
        success: function(data){
         	suntaek_proc('list','1','');
        }
     });
	  
	  
	  
}



    function check_answer(value1){
    	$("#span_answer").text(value1);
    	if(value1=='1'){
    		$("#mun1").text('❶');
    		$("#mun2").text('②');
    		$("#mun3").text('③');
    		$("#mun4").text('④');
    	}else if(value1=='2'){
    		$("#mun1").text('①');
    		$("#mun2").text('❷');
    		$("#mun3").text('③');
    		$("#mun4").text('④');
    	}else if(value1=='3'){
    		$("#mun1").text('①');
    		$("#mun2").text('②');
    		$("#mun3").text('❸');
    		$("#mun4").text('④');
    	}else if(value1=='4'){
    		$("#mun1").text('①');
    		$("#mun2").text('②');
    		$("#mun3").text('③');
    		$("#mun4").text('❹');
    	}
    }

    function check_answer_2(value1, value2){
        $("#span_answer_" + value1).text(value2);
        if (value2 == '1') {
           $("#mun1_" + value1).text('❶');
           $("#mun2_" + value1).text('②');
           $("#mun3_" + value1).text('③');
           $("#mun4_" + value1).text('④');
        } else if (value2 == '2') {
           $("#mun1_" + value1).text('①');
           $("#mun2_" + value1).text('❷');
           $("#mun3_" + value1).text('③');
           $("#mun4_" + value1).text('④');
        } else if (value2 == '3') {
           $("#mun1_" + value1).text('①');
           $("#mun2_" + value1).text('②');
           $("#mun3_" + value1).text('❸');
           $("#mun4_" + value1).text('④');
        } else if (value2 == '4') {
           $("#mun1_" + value1).text('①');
           $("#mun2_" + value1).text('②');
           $("#mun3_" + value1).text('③');
           $("#mun4_" + value1).text('❹');
        }
        var counter = parseInt($("#span_list_size").text());
        console.log("counter:" + counter);
        var msg = "";
        for (i=counter; i>0; i--) {
           q_no = $("#q_" + i).text();
           answer = $("#span_answer_" + i).text();
           
           console.log("msg:" + msg);
           if (answer.length > 0) {
              if (msg == "") {
                 msg = q_no + ":" + answer;
              } else {
                 msg = msg + "|" + q_no + ":" + answer;
              }
           }
           
        }
        
        $("#aaa").text(msg);
     }

   
   

