<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>

<%@ include file="../include/inc_header.jsp" %>






<table border="1" align="center" width="100%">
 <tr>
      <td colspan="7">
         <h2>설문조사 상세보기</h2>
      </td>
   </tr>
    
   <tr> 
   정답 : <span style="display: ;" id="span_answer"></span> 
     <td style="padding: 10px 0px 5px;"><input type="hidden" name="no" value="${dto.no }">질문번호 ${dto.no }</td>
   </tr> 
   <tr>
    <td>Q) ${dto.question }</td>
   </tr>
   <tr>
    <td><a href="#" onclick="check_answer('1');"><font style="font_family:'MS Gothic';"><span id="mun1">①</span></font></a>
 	   <a href="#" onclick="check_answer('1');">${dto.ans1 }</a>
    </td>
   </tr>
   <tr>
    <td><a href="#" onclick="check_answer('2');"><font style="font_family:'MS Gothic';"><span id="mun2">②</span></font></a>
 	   <a href="#" onclick="check_answer('2');">${dto.ans2 }</a></td>
   </tr>
   <tr>
    <td><a href="#" onclick="check_answer('3');"><font style="font_family:'MS Gothic';"><span id="mun3">③</span></font></a>
 	   <a href="#" onclick="check_answer('3');">${dto.ans3 }</a></td>
   </tr>
   <tr>
    <td><a href="#" onclick="check_answer('4');"><font style="font_family:'MS Gothic';"><span id="mun4">④</span></font></a>
 	   <a href="#" onclick="check_answer('4');">${dto.ans4 }</a></td>
   </tr>
   <tr>
    <td>${dto.status }</td>
   </tr>
   <tr>
    <td>${dto.start_date}&nbsp;~&nbsp;${dto.last_date }</td>
   </tr>
  
   <tr>
      <td colspan="15" align="right" style="height:50px;">
         <button type="button" onclick="GoViewProc2();">설문조사 저장하기</button>
         <button type="button" onclick="suntaek_proc('list','${pageNumber}','');">목록으로</button>
         <button type="button" onclick="suntaek_proc('sujung','','${dto.no}');">수정하기</button>
         <button type="button" onclick="suntaek_proc('sakjaeProc','','${dto.no}');">삭제하기</button>
      </td>
   </tr>
</table>

