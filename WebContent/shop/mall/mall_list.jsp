<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

<table border="0" align="center" width="80%">
	<tr>
		<td colspan="7">
			<h2>Shopping Mall</h2>
		</td>
	</tr>
	<tr>
		<td colspan="7">
			<select name="search_option" id="search_option">
				<c:choose>
					<c:when test="${search_option == 'name' }">
						<option value="">- 선택 -</option>
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
		$("#span_search_option").text($("#search_option").val());
		$("#span_search_data").text($("#search_data").val());
		 suntaek_proc('mall_list','1','');
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
			<c:set var="cell_counter" value="3"></c:set>
		
			<c:set var="k" value="0"></c:set>
				<table border="1" align="center" style="width:100%;">
					<c:forEach var="dto" items="${list}">
						<c:set var="k" value="${k=k+1 }"/>				
					<c:if test = "${k mod cell_counter ==1}">
						<c:set var ="imsi_counter" value="0"></c:set>
						<tr>
					</c:if>
					
							<td>
								<c:set var="imsi_counter" value="${imsi_counter = imsi_counter + 1 }"></c:set>
								<table border="1" align="center" style="width:50%;">
									<tr>
										<td>
											<c:choose>
												<c:when test="${fn:split(dto.product_img,',')[0] == '-' }">
													<a href="#" onclick="suntaek_proc('mall_view','','${dto.no }');">이미지X</a>
												</c:when>
												<c:otherwise>
													<c:set var="temp1" value="${fn:split(dto.product_img,',')[0] }"></c:set>
													<c:set var="temp2" value="${fn:split(temp1,'|')[0] }"></c:set>
													<c:set var="temp3" value="${fn:split(temp1,'|')[1] }"></c:set>
													<a href="#" onclick="suntaek_proc('mall_view','','${dto.no}');">
														<img src="${path }/attach/product_img/${temp3}" alt="${dto.name }" title="${dto.name }" style="width:50px; height:50px;">
													</a>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<td>
											${dto.name }
										</td>
									</tr>
									<tr>
										<td>
											<fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.price }"/>
										</td>
									</tr>
								</table>
							</td>
					<c:if test = "${k mod cell_counter == 0}">
						</tr>
					</c:if>
						<c:set var="jj" value="${jj = jj- 1 }"></c:set>
					</c:forEach>	
					<c:if test="${imsi_counter<cell_counter }">
						<c:forEach var="i" begin="${imsi_counter + 1 }" end="${cell_counter }" step="1">
							<td>
								{dto.buy_counter}
							</td>
						</c:forEach>	
						</tr>
					</c:if>
			</table>
		</td>
	</tr>
</c:if>	
	<tr>
		<td colspan = "7" height = "50" align = "center">
			<a href = "#" onclick = "suntaek_proc('mall_list','1','');">[첫페이지]</a>
			&nbsp;&nbsp;
			<c:if test = "${startPage > blockSize }">
				<a href = "#" onclick = "suntaek_proc('mall_list','${startPage - blockSize}','');">[이전 10개]</a>
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
					<a href = "#" onclick = "suntaek_proc('mall_list','${i}','');">${i }</a>
				</c:if>
			</c:forEach>
			&nbsp;
			<c:if test = "${lastPage < totalPage }">
				<a href = "#" onclick = "suntaek_proc('mall_list','${startPage + blockSize}','');">[다음 10개]</a>
			</c:if>
			<c:if test = "${lastPage >= totalPage }">
				[다음 10개]
			</c:if>
			&nbsp;&nbsp;
				<a href = "#" onclick = "suntaek_proc('mall_list','${totalPage}','');">[끝페이지]</a>
		</td>
	</tr>
	<tr>
		<td colspan="7" height="50" align="right">
			<button type = "button" onclick = "suntaek_proc('cart_list','1','');">장바구니</button>
		</td>
	</tr>	
</table>