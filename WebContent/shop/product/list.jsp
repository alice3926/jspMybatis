<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>
    
<table border="0" align="center" width="80%">
	<tr>
		<td colspan="7">
			<h2>상품 목록</h2>
		</td>
	</tr>
	<tr>
		<td colspan="7" >			
			<select name="search_option" id="search_option">
			<c:choose>
				<c:when test="${search_option =='name' }">
							<option value="">-선택-</option>
							<option value="name" selected>상품명</option>
							<option value="description">상품설명</option>
							<option value="name+description">상품명+상품설명</option>
				</c:when>
				<c:when test="${search_option =='description' }">
							<option value="">-선택-</option>
							<option value="name">상품명</option>
							<option value="description" selected>상품설명</option>
							<option value="name+description">상품명+상품설명</option>
				</c:when>
				<c:when test="${search_option =='name+description' }">
							<option value="">-선택-</option>
							<option value="name">상품명</option>
							<option value="description">상품설명</option>
							<option value="name+description" selected>상품명+상품설명</option>
				</c:when>
				<c:otherwise>
							<option value="" selected>-선택-</option>
							<option value="name">상품명</option>
							<option value="description">상품설명</option>
							<option value="name+description">상품명+상품설명</option>
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
		<td style="padding : 10px 0px 5px;">전체 ${totalRecord }건입니다.</td>
	</tr>
<c:if test="${totalRecord==0 }">
	<tr>
		<td height="200">
			<table border="1" align="center" style="width:100%; height:200px;">
				<tr>
					<td align="center">등록된 내용이 없습니다.</td>
				</tr>
			</table>
		</td>
	</tr>
</c:if>
<c:if test="${totalRecord>0 }">
	<tr>
		<td style="padding : 0 0 20px 0;">
			<table border="1" align="center" style="width:100%;">
				<tr>
					<th>순번</th>
					<th>상품사진</th>
					<th>상품명</th>
					<th>가격</th>
					<th>파일</th>
					<th>장바구니수</th>
					<th>등록일</th>
				</tr>
				<c:forEach var="dto" items="${list}">
				<tr>
					<td>
						${jj}
					</td>
					<td>
<%-- 						${dto.product_img }<br> --%>
<%-- 						${fn:split(dto.product_img,',')[0] }<br> --%>
						<c:choose>
							<c:when test="${dto.product_img =='-,-,-' }">
								<a href="#" onclick="suntaek_proc(''view','','${dto.no }');">이미지</a>
							</c:when>
							<c:otherwise>
							
								<c:set var="temp1" value="${fn:split(dto.product_img,',')[0] }"></c:set>
								<c:set var="real_temp1" value="${fn:split(temp1,'|')[1] }"></c:set>
								<a href="#" onclick="suntaek_proc('view','0','${dto.no}');">
									<img src="${path }/attach/product_img/${real_temp1}" alt="${dto.name }" title="${dto.name }" style="width:50px; height:50px;">
								</a>
							</c:otherwise>
						</c:choose>
					
					</td>			
					<td>${dto.name }</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.price }"/></td>
					<td>${dto.product_img }</td>
					<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.buy_counter }"/></td>
					<td>${dto.regi_date }</td>
				</tr>
				<c:set var="jj" value="${jj = jj- 1 }"></c:set>
			</c:forEach>
		</table>
		</td>
	</tr>
</c:if>	
	<tr>
		<td colspan = "7" height = "50" align = "center">
			<a href = "#" onclick = "suntaek_page('1');">[첫페이지]</a>
			&nbsp;&nbsp;
			
			<c:if test = "${startPage > blockSize }">
			<a href = "#" onclick = "suntaek_page('${startPage - blockSize}');">[이전 10개]</a>
			</c:if>
			<c:if test = "${startPage <= blockSize }">
			[이전 10개]
			</c:if>
			&nbsp;
			<c:forEach var = "i" begin = "${startPage }" end = "${lastPage }" step = "1">
			<c:if test = "${i == pageNumber }">
			[${i }]
			</c:if>
			<c:if test = "${i != pageNumber }">
			<a href = "#" onclick = "suntaek_page('${i}');">${i }</a>
			</c:if>
			</c:forEach>
			&nbsp;
			<c:if test = "${lastPage < totalPage }">
			<a href = "#" onclick = "suntaek_page('${startPage + blockSize}');">[다음 10개]</a>
			</c:if>
			<c:if test = "${lastPage >= totalPage }">
			[다음 10개]
			</c:if>
			&nbsp;&nbsp;
			<a href = "#" onclick = "suntaek_page('${totalPage}');">[끝페이지]</a>
		</td>
	</tr>
	<tr>
		<td colspan="7" height="50" align="right">
			<button type = "button" onclick = "suntaek_all();">전체 목록</button>
			<button type = "button" onclick = "GoPage('chuga','');">상품등록</button>
		</td>
	</tr>	
</table>	