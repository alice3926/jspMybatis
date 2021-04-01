

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>



    

<h1>방명록 목록</h1>
<select id="search_option" name="search_option">
	<c:choose>
		<c:when test="${search_option == 'name' }">
			<option value="">선택</option>
			<option value="name" selected>이름</option>
			<option value="email">이메일</option>
			<option value="content">내용</option>
			<option value="name_email_content">이름+이메일+내용</option>
		</c:when>
		<c:when test="${search_option == 'email' }">
			<option value="">선택</option>
			<option value="name">이름</option>
			<option value="email" selected>이메일</option>
			<option value="content">내용</option>
			<option value="name_email_content">이름+이메일+내용</option>
		</c:when>
		<c:when test="${search_option == 'content' }">
			<option value="">선택</option>
			<option value="name">이름</option>
			<option value="email">이메일</option>
			<option value="content" selected>내용</option>
			<option value="name_email_content">이름+이메일+내용</option>
		</c:when>
		<c:when test="${search_option == 'name_email_content' }">
			<option value="">선택</option>
			<option value="name">이름</option>
			<option value="email">이메일</option>
			<option value="content" selected>내용</option>
			<option value="name_email_content">이름+이메일+내용</option>
		</c:when>
		<c:otherwise>
			<option value="" selected>선택</option>
			<option value="name">이름</option>
			<option value="email">이메일</option>
			<option value="content">내용</option>
			<option value="name_email_content">이름+이메일+내용</option>
		</c:otherwise>
	</c:choose>
</select>
&nbsp;
<input type="text" name="search_data" id="search_data" value="${search_data }">
<button type="button" onclick="search()">검색</button>

<script>
function search(){
	$("#span_search_option").text($("#search_option").val());
	$("#span_search_data").text($("#search_data").val());
	
	suntaek_proc('list','1','');
	
}


</script>




${list.size() }개의 글이 있습니다.
<table border="0" align="center" width="80%">
<c:if test="${totalRecord==0 }">
	<tr>
		<td colspan="4" height="200" width="80%" align="center">
			 등록된 회원이 없습니다.
		</td>
	</tr>
</c:if>
	<tr>
		<td>
		<c:forEach var="dto" items="${list}">
			<table border="1" align="center" width="80%">
				<tr>
					<td>이름</td>
					<td>${dto.name }</td>
					<td>날짜</td>
					<td>${dto.regi_date }</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td colspan="3">${dto.email }</td>
				</tr>
				<tr>
					<td colspan="4">${dto.content }</td>
				</tr>
				<tr>
					<td colspan="4">
						<a href="#" onclick="suntaek_proc('sujung','','${dto.no}')">[수정]</a>
						&nbsp;
						<a href="#" onclick="suntaek_proc('sakjae','','${dto.no}')">[삭제]</a>
					</td>
				</tr>
				
			</table>
				<br>
		</c:forEach>
		</td>
	</tr>		

<c:if test="${totalRecord>0 }">
	<tr>
		<td colspan="15" height="50" align="center">
			<a href="#" onclick="suntaek_page('1');">[첫페이지]</a>
			&nbsp;&nbsp;
			<c:if test="${startPage > blockSize }">
				<a href="#" onclick="suntaek_page('${startPage-blockSize}');">[이전10개]</a>
			</c:if>
			<c:if test="${startPage <=blockSize }">
			[이전10개]
			</c:if>
			&nbsp;
			<c:forEach var="i" begin="${startPage }" end="${lastPage }" step="1">
				<c:if test="${i==pageNumber }">
					[${i }]
				</c:if>
				<c:if test="${i!=pageNumber }">
					<a href="#" onclick="suntaek_page('${i}');">${i }</a>
				</c:if>
			</c:forEach>		
			&nbsp;
			<c:if test="${lastPage<totalPage }">
				<a href="#" onclick="suntaek_page('${startPage+blockSize}');">[다음10개]</a>
			</c:if>
			<c:if test="${lastPage >=totalPage }">
				[다음10개]
			</c:if>
			&nbsp;&nbsp;
			<a href="#" onclick="suntaek_page('${totalPage}');">[끝페이지]</a>
		</td>
	</tr>
	</c:if>
	<tr>
		<td colspan="15" align="center" style="height:50px;">
			<button type="button" onclick="suntaek_proc('chuga','','');">글쓰기</button>
			&nbsp;
			<button type="button" onclick="suntaek_all();">전체목록</button>
			
		</td>
	</tr>
</table>

