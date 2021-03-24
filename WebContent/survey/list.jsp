<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>

<%@ include file="../include/inc_header.jsp" %>

<table border="0" align="center" width="80%">
   <tr>
      <td colspan="7">
         <h2>설문조사 목록</h2>
      </td>
   </tr>
   
   <tr>   
      <td colspan="7">
<!--          <select name="search_option" id="search_option"> -->
<!--             <option value="">--선택--</option> -->
<!--             <option value="question">질문내용</option> -->
<!--          </select> -->
         
         <select name ="search_option" id="search_option">
            <c:choose>
               <c:when test="${search_option == 'question' }">
                  <option value="">--선택--</option>
                  <option value="question" selected>질문내용</option>
               </c:when>
                  
               <c:otherwise>
                  <option value="" selected>--선택--</option>
                  <option value="question">질문내용</option>
               </c:otherwise>
            </c:choose>
         </select>
         
         <input type="text" name="search_data" id="search_data" value="${search_data }" style="width:120px;">
         &nbsp;
         <input type="date" name="date" id="search_date_s" value="${search_date_s }">
         ~
         <input type="date" name="date" id="search_date_e" value="${search_date_e }">
         <br>
         <input type="checkbox" name="search_date_check" id="search_date_check" value="O" onclick="checkboxChk();">
         <span style="color:blue; font-size: 9px;">(날짜 검색시 체크)</span>
         &nbsp;
         <input type="button" value="검색" onclick="search();">
      </td>
   </tr>
   
   <tr>
      <td style="padding: 10px 0px 5px;">전체 ${totalRecord }건입니다.</td>
   </tr>
   
   <tr>
      <td style="padding: 0 0 20px 0;">
         <table border="1" align="center" style="width:100%">
            <tr>
	           <th>수정/삭제</th>
               <th>순번</th>
               <th>질문</th>
               <th>기간</th>
               <th>참여수</th>
               <th>상태</th>
            </tr>
            
            <c:forEach var="dto" items="${list}">
               <tr>
               	  <td>
               		<button type="button" onclick="suntaek_proc('sujung','','${dto.no }');">수정</button> 
  				    <button type="button" onclick="suntaek_proc('sakjaeProc','','${dto.no }');">삭제</button> 
               	  </td>
                  <td>${jj}</td>
                  <td>
                     <a href="#" onclick="suntaek_proc('view','','${dto.no}');">${dto.question}</a>
                  </td>
                  <td>${dto.start_date}
                     <br>
                     ${dto.last_date }
                  </td>
                  <td>${dto.survey_counter}</td>
                  <td>${dto.status}</td>
               </tr>
               <c:set var="jj" value="${jj = jj - 1}"></c:set>
            </c:forEach>
         </table>
      </td>
   </tr>
   
   <tr>
      <td colspan="7" height="50" align="center">
         <a href="#" onclick="suntaek_page('1');">[첫페이지]</a>
         &nbsp;&nbsp;
         
         <c:if test="${startPage > blockSize }">
            <a href="#" onclick="suntaek_page('${startPage - blcockSize}');">
               [이전10개]
            </a>
         </c:if>
         
         <c:if test="${startPage <= blockSize }">
               [이전10개]
         </c:if>
         &nbsp;
         
         <c:forEach var="i" begin="${startPage }" end="${lastPage }" step="1">
            <c:if test="${i== pageNumber }">
               [${i }]
            </c:if>
            <c:if test="${i != pageNumber }">
               <a href="#" onclick="suntaek_page('${i}');">
                  ${i }
               </a>
            </c:if>
         </c:forEach>
         &nbsp;
         <c:if test="${lastPage < totalPage }">
            <a href="#" onclick="suntaek_page('${startPage + blcockSize}');">
               [다음10개]
            </a>
         </c:if>
         <c:if test="${startPage >= blockSize }">
               [다음10개]
         </c:if>
         &nbsp;&nbsp;
         <a href="#" onclick="suntaek_page('${totalPage}');">
            [끝페이지]
         </a>
      </td>
   </tr>
   
   <tr>
      <td colspan="7" height="50" align="right">
         <button type="button" onclick="suntaek_all();">
            전체 설문목록
         </button>
         
         <button type="button" onclick="suntaek_list('ing');">
            진행중인 설문목록
         </button>
         
         <button type="button" onclick="suntaek_list('end');">
            종료된 설문목록
         </button>
          <button type="button" onclick="suntaek_list('future');">
            진행예정 설문목록
         </button>
         <button type="button" onclick="suntaek_proc('chuga','','');">
            등록하기
         </button>
         <button type="button" onclick="suntaek_proc('list_2','','');">
           	문제풀이
         </button>
      </td>
   </tr>
</table>

<script>
   function search() {
      $("#span_search_option").text($("#search_option").val());
      $("#span_search_data").text($("#search_data").val())
      
      $("#span_search_date_s").text($("#search_date_s").val());
      $("#span_search_date_e").text($("#search_date_e").val())
      
      suntaek_page('1');
   }
   
   function checkboxChk() {
      if($("input:checkbox[id=search_date_check]").is(":checked") == true) {
         $("#span_search_date_check").text($("#search_date_check").val());
         $("#span_search_date_s").text($("#search_date_s").val());
         $("#span_search_date_e").text($("#search_date_e").val());
      } else {
         $("#span_search_date_check").text('');
         $("#span_search_date_s").text("");
         $("#span_search_date_e").text("");
         $("#search_date_s").text("");
         $("#search_date_e").text("");

      }
   }
</script>