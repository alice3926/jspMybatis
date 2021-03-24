<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

<h2>한줄메모장</h2>
<table border="0" align="center" width="80%">
	<tr>
		<td>
		<input type="hidden" name="oneno" id="oneno" value="${onedto.no }">
			이름 : <input type="text" name="writerName" id="writerName" value="${onedto.writerName }">
			<br>
			메모 : <input type="text"  name="content" id="content" style="width:500px;" value="${onedto.memo }">
			<c:if test="${onedto.no == null }">
				<button type="button" onclick="suntaek_proc('insertProc','','${dto.no}');">작성하기</button>
			</c:if>
			<c:if test="${onedto.no != null }">
				<button type="button" onclick="suntaek_proc('sujungProc','','${dto.no}');">수정하기</button>
			</c:if>
		</td>
	</tr>
</table>

<br>

총 레코드 수는 ${totalRecord }입니다.
<table border="1" align="center" width="80%">
	<tr>
		<td colspan="15"><h2>메모장 리스트</h2></td>
	</tr>
	<tr>
		<td>cookId</td>
		<td>작성자</td>
		<td>메모</td>
		<td>작성일</td>
		<td>수정/삭제</td>
	</tr>

<c:forEach var="dto" items="${list}">
	<tr>
		<td>${dto.writerId} </td>
		<td>${dto.writerName}</td>
		<td>${dto.memo}  </td>
		<td>${dto.writeDate}  </td>
		<td>
			<a href="#" onclick="suntaek_proc('sujung','','${dto.no}');">[수정]</a>
			&nbsp;
			<a href="#" onclick="suntaek_proc('sakjaeProc','','${dto.no}');">[삭제]</a>
		
		</td>
	</tr>
</c:forEach>

	<c:if test="${totalRecord==0 }">
	<tr>
		<td colspan="15" height="200" align="center">
			 등록된 회원이 없습니다.
		</td>
	</tr>
	</c:if>
	<c:if test="${totalRecord>0 }">
	<tr>
		<td colspan="15" height="50" align="right">
			lastRecord:${lastRecord }<br>
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

</table>
