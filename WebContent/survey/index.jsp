<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>

<%@ include file ="../include/inc_header.jsp" %>


<input type="text" name="a" style="display: ;"><br><br>
<%-- pageNumber : <span id= "span_pageNumber">${pageNumber }</span><br> --%>


span_list_gubun : <span style="display: ;"  id="span_list_gubun">${list_gubun}</span><br>
span_pageNumber : <span style="display: ;" id="span_pageNumber">${pageNumber}</span><br>
span_no : <span style="display: ;"  id="span_no">${no }</span><br>
span_search_option : <span style="display: ;" id="span_search_option">${search_option }</span><br>
span_search_data : <span style="display: ;" id="span_search_data">${search_data }</span><br>
span_search_date_check : <span style="display: ;" id="span_search_date_check">${search_date_check }</span><br>
span_search_date_s : <span style="display: ;" id="span_search_date_s">${search_date_s }</span><br>
span_search_date_e : <span style="display: ;" id="span_search_date_e">${search_date_e }</span><br>
path : <span id="span_path">${path }</span><br>
결과 : <span id="span_passwd"></span><br>




<div id ="result" style="border: 1px solid red;">
<script type="text/javascript" src="${path }/survey/_survey2.js"></script>
</div>

<c:if test="${menu_gubun == 'survey_index'}">
   <script>
      $(document).ready(function(){
    	  suntaek_proc('list','1','');
      });   
   </script>
</c:if>
<c:if test="${menu_gubun == 'questionBank_index'}">
   <script>
      $(document).ready(function(){
    	  suntaek_proc('list_2','1','');
      });   
   </script>
</c:if>

