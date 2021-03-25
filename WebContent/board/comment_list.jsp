<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %> 

<table border="0" align="center" width="95%">
	<tr>
		<td style="padding:0 0 20 0;">
			이름 : <input type="text" name="comment_writer" id="comment_writer" size="10" value="${commentDto.writer }"> &nbsp;								
			비밀번호 : <input type="password" name="comment_passwd" id="comment_passwd" size="40" value="">
			<br>
			댓글 : <input type="text"  name="comment_content" id="comment_content" value="${commentDto.content }" size="40">
			
				 <span id="spanView2" style="display: none;">
					<button type="button" onclick="comment_proc('commentSave','');">확인</button>	
				 </span>
        		 <span id="spanView" style="display: none;">
		       		 <button type="button" onclick="comment_proc('commentmodifyProc','')">수정</button>
		     	</span>
		        
		        
		</td>
	</tr>
</table>

<br>
<c:if test="${totalRecord>0 }">						
	<table border="0" align="center" width="95%">
		<c:forEach var="commentDto" items="${list}">
			<tr>
				<td style="padding:0 0 10 0;">
					<table border="0" align="center" style="width:100%;">
						<tr>
							<td>
								${commentDto.writer} 
							</td>
							<td>
								${commentDto.regiDate}
							</td>
						</tr>
						<tr>
							<td>${commentDto.content}</td>
							<td>
								<a href="#" onclick="abc('${commentDto.comment_no}','${commentDto.writer}','${commentDto.content}');">[수정]</a>
								&nbsp;
								<a href="#" onclick="comment_proc('commentSakjaeProc','${commentDto.comment_no}');">[삭제]</a>
							</td>
						</tr>
					</table>		
					<hr>
				</td>
			</tr>
			<c:set var="jj" value="${jj=jj-1 }"></c:set>
		</c:forEach>
			<tr>
				<td colspan="7" height="50" align="center">
					<a href="#comment" onclick="comment_suntaek_page('1');">[첫페이지]</a>
					&nbsp;&nbsp;
					<c:if test="${startPage > blockSize }">
						<a href="#comment" onclick="comment_suntaek_page('${startPage-blockSize}');">[이전5개]</a>
					</c:if>
					<c:if test="${startPage <=blockSize }">
					[이전5개]
					</c:if>
					&nbsp;
					<c:forEach var="i" begin="${startPage }" end="${lastPage }" step="1">
						<c:if test="${i==commentPageNumber }">
							[${i }]
						</c:if>
						<c:if test="${i!=commentPageNumber }">
							<a href="#comment" onclick="comment_suntaek_page('${i}');">${i }</a>
						</c:if>
					</c:forEach>		
					&nbsp;
					<c:if test="${lastPage<totalPage }">
						<a href="#comment" onclick="comment_suntaek_page('${startPage+blockSize}');">[다음5개]</a>
					</c:if>
					<c:if test="${lastPage >=totalPage }">
						[다음5개]
					</c:if>
					&nbsp;&nbsp;
					<a href="#comment" onclick="comment_suntaek_page('${totalPage}');">[끝페이지]</a>
				</td>
			</tr>
	</table> 
</c:if>										
 
 
<script>
$(document).ready(function() {
	$("#spanView").hide();
	$("#spanView2").show();

	
	
	
	 $("#btnCommentSave").click(function(){
		 comment_save();   
	   });   
	
});

function abc(value1, value2, value3) {
	$("#spanView").show();
    $("#spanView2").hide();
    $("#span_commentNo").text(value1);
    $("#comment_writer").val(value2);
    $("#comment_content").val(value3);
    
 }   


</script> 
 