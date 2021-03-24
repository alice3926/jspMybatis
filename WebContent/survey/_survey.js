
   function GoChuga(){
      var param = {}
      $.ajax({
         type: "post",
         data: param,
         url: "${path}/survey_servlet/chuga.do",
         success: function(data){
            $("#result").html(data);
         }
      })
   }
   
   function GoChugaProc(){
      if(confirm('등록하시겠습니까?')) {
         $.ajax({
            type: "post",
            data: $('#DirForm').serialize(),
            url: "${path}/survey_servlet/chugaProc.do",
            success: function(){
               suntaek_page('1');
            }
         })
      }
   }
   
   function GoList(){
      var param = {
         "list_gubun" : $("#span_list_gubun").text(),
         "pageNumber" : $("#span_pageNumber").text(),
         "search_option" : $("#span_search_option").text(),
         "search_data" : $("#span_search_data").text(),
         "search_date_check" : $("#span_search_date_check").text(),
         "search_date_s" : $("#span_search_date_s").text(),
         "search_date_e" : $("#span_search_date_e").text()
      }
      $.ajax({
         type: "post",
         data: param,
         url: "${path}/survey_servlet/list.do",
         success: function(result) {
            $("#result").html(result);
            if ($("#span_search_date_check").text() == "O") {
               $("input[id=search_date_check]:checkbox").prop("checked", true);
            } else {
               $("input[id=search_date_check]:checkbox").prop("checked", false);
            }
         }
      })
   }
   
   
   function GoList_2() {
	      var param ={}
	      $.ajax({
	         type: "post",
	         data: param,
	         url: "${path}/survey_servlet/list_2.do",
	         success: function(data){
	            $("#result").html(data);
	         }
	      });
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

   
   function suntaek_list(value1) {
      $("#span_list_gubun").text(value1);
      $("#span_pageNumber").text(1);
       GoList();
   }
   
   
   function suntaek_page(value1) {
      $("#span_pageNumber").text(value1);
      GoList();
   }
   
   function suntaek_view(value1){
	   $("#span_no").text(value1)
	   
	   //나눠서 GoView를 만들수 있음. 나같은 경우 합침
       var param ="no="+value1;
       
       $.ajax({
          type: "post",
          data: param,
          url: "${path}/survey_servlet/view.do",
          success: function(data){
             $("#result").html(data);
          }
       });
    }
 
 
 
 
 
 
    function GoViewProc(){//view.jsp에서
    if(confirm('저장하시겠습니까?')) {
    		DirForm.method="post";
    		DirForm.action="${path}/survey_servlet/viewProc.do";
    		DirForm.submit();
    }
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

   
   

