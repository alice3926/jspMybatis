<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>

리스트 사이즈는 ${list.size() }입니다.
total record 는 ${totalRecord }입니다.


<table border="1" align="center" width="80%">
	<tr>
		<td colspan="15"><h2>회원목록</h2></td>
	</tr>
	<tr>
		<td colspan="15" >			
			<select name="search_option" id="search_option">
			<c:choose>
				<c:when test="${search_option =='id' }">
							<option value="">-선택-</option>
							<option value="id" selected>아이디</option>
							<option value="name">이름</option>
							<option value="gender">성별</option>
							<option value="id_name_gender">아이디+이름+성별</option>
				</c:when>
				<c:when test="${search_option =='name' }">
							<option value="">-선택-</option>
							<option value="id">아이디</option>
							<option value="name" selected>이름</option>
							<option value="gender">성별</option>
							<option value="id_name_gender">아이디+이름+성별</option>
				</c:when>
				<c:when test="${search_option =='gender' }">
							<option value="">-선택-</option>
							<option value="id">아이디</option>
							<option value="name">이름</option>
							<option value="gender" selected>성별</option>
							<option value="id_name_gender">아이디+이름+성별</option>
				</c:when>
				<c:when test="${search_option =='id_name_gender' }">
							<option value="">-선택-</option>
							<option value="id">아이디</option>
							<option value="name">이름</option>
							<option value="gender">성별</option>
							<option value="id_name_gender"  selected>아이디+이름+성별</option>
				</c:when>
				<c:otherwise>
							<option value=""  selected>-선택-</option>
							<option value="id">아이디</option>
							<option value="name">이름</option>
							<option value="gender">성별</option>
							<option value="id_name_gender">아이디+이름+성별</option>
				</c:otherwise>
			</c:choose>
			</select>
			
			<input type="text" name="search_data" id="search_data" value="${search_data }" style="width:150px;">
			&nbsp;
			<input type="button" onclick="search();" value="검색">
			<script>
				function search(){
					if(confirm('검색 OK?')){
						$("#span_search_option").text($("#search_option").val());
						$("#span_search_data").text($("#search_data").val());
						suntaek_page('1');
					}
				}
			</script>
		</td>
	</tr>	
	<tr>
		<td>회원번호</td>
		<td>아이디</td>
		<td>이름</td>
		<td>성별</td>
		<td>태어난년도</td>
		<td>주소</td>
		<td>우편번호</td>
		<td>가입일</td>
		<td>마지막 수정일</td>
	</tr>

<c:forEach var="dto" items="${list}">
	<tr>
		<td>${dto.no} </td>
		<td><a href="#" onclick="suntaek_proc('view','','${dto.no}');">${dto.id}</a></td>
		<td>${dto.name}  </td>
		<td>${dto.gender}  </td>
		<td>${dto.bornYear}  </td>
		<td>${dto.address }${dto.detailAddress }${dto.extraAddress }</td>
		<td>${dto.postcode }</td>
		<td>${dto.regiDate}  </td>
		<td>${dto.lastupDate }</td>
		
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
		<c:set var="value1" value="list"></c:set>
			<a href="#" onclick="suntaek_proc('${value1}','1','');">[첫페이지]</a>
			&nbsp;&nbsp;
			<c:if test="${startPage > blockSize }">
				<a href="#" onclick="suntaek_proc('${value1}','${startPage-blockSize}','');">[이전10개]</a>
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
					<a href="#" onclick="suntaek_proc('${value1}','${i}','');">${i }</a>
				</c:if>
			</c:forEach>		
			&nbsp;
			<c:if test="${lastPage<totalPage }">
				<a href="#" onclick="suntaek_proc('${value1}','${startPage+blockSize}','');">[다음10개]</a>
			</c:if>
			<c:if test="${lastPage >=totalPage }">
				[다음10개]
			</c:if>
			&nbsp;&nbsp;
			<a href="#" onclick="suntaek_proc('${value1}','${totalPage}','');">[끝페이지]</a>
		</td>
	</tr>
	</c:if>
	<tr>
		<td colspan="15" align="right" style="height:50px;">
			<button type="button" onclick="suntaek_all();">전체목록</button>
			<c:if test="cookNo==null || cookNo=='0'">
				<button type="button" onclick="suntaek_proc('chuga','1','');">가입하기</button>
			</c:if>
			
			
			
		</td>
	</tr>
</table>
