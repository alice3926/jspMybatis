<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>


<table border="0" align="center" width="80%">
	<tr>
		<td colspan="7">
			
			
			
			
			
			<h2>장바구니 목록</h2>
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
					<td align="center">장바구니에 들어있는 상품이 없습니다.</td>
				</tr>
			</table>
		</td>
	</tr>
</c:if>
<c:if test="${totalRecord>0 }">
	<tr>
		<td style="padding : 0 0 20px 0;">
			<table  border="1" align="center" style="width:100%;">
				<tr>
					<td>
						<input type="checkbox" name="checkAll" id="checkAll">전체선택
					</td>
					<td>상품사진</td>
					<td>상품명</td>
					<td>가격</td>
					<td>구매수량</td>
					<td>금액</td>
					<td>등록일</td>
				</tr>
				<c:set var ="hab_money" value="0"/>
				<c:set var ="i" value="1"/>
				<c:forEach var="dto" items="${list}">
					<span id="b_${dto.no }" style="display: n;">${dto.memberNo },${dto.productNo },${dto.amount }</span>
				<tr>
					<td align="center">
						<input type="checkbox" name="chk" id="chk" value="${dto.no}">${dto.no } <!-- 값확인을 위해 찍은거 나중에 지워야됨 -->
					</td>
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
									<img src="${path }/attach/product_img/${temp3}" alt="${dto.product_name }" title="${dto.product_name }" style="width:50px; height:50px;">
								</a>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${dto.product_name }</td>
					<td>
						<fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.product_price }" />
					</td>
					<td>
						<select name ="cart_amount${dto.no}" id="cart_amount${dto.no}" onchange="suntaek_proc('cart_sujung','','${dto.no}')">
							<c:forEach var="i" begin="1" end="10" step="1">
								<c:if test="${dto.amount == i }">			
									<option value="${i }" selected>${i }</option>
								</c:if>
								<c:if test="${dto.amount != i }">			
									<option value="${i }">${i }</option>
								</c:if>
							</c:forEach>
						</select>
						
						
					</td>
					<td>
						<fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.buy_money}" />
<%-- 						<fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.product_price * dto.amount}" /> --%>
<%-- 						<fmt:parseNumber value="${hab_money}" var="imsisum" /> --%>
<%-- 						<c:set var ="hab_money" value="${imsisum +(dto.product_price * dto.amount)}"/> --%>
					</td>
					
					<td>${dto.regi_date }</td>
				</tr>
				<c:set var="i" value="${i = i + 1 }"/>
				<c:set var ="hab_money" value="${hab_money = hab_money + dto.buy_money}"/>
<%-- 				<c:set var="jj" value="${jj = jj- 1 }"></c:set> --%>
				</c:forEach>
				<tr>
					<td colspan="7" align="right" style="padding: 10px 20px;">
					 	합계금액 : <fmt:formatNumber type="number" maxFractionDigits="3" value="${hab_money}" />
					</td>
				</tr>
			</table>
			
		</td>
	</tr>
	
</c:if>	
	<tr>
		<td colspan = "7" height = "50" align = "center">
		<c:set var="tempGubun" value="cart_list"/>
			<a href = "#" onclick = "suntaek_proc('${tempGubun}','1','');">[첫페이지]</a>
			&nbsp;&nbsp;
			<c:if test = "${startPage > blockSize }">
				<a href = "#" onclick = "suntaek_proc('${tempGubun}','${startPage - blockSize}','');">[이전 10개]</a>
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
					<a href = "#" onclick = "suntaek_proc('${tempGubun}','${i}','');">${i }</a>
				</c:if>
			</c:forEach>
			&nbsp;
			<c:if test = "${lastPage < totalPage }">
				<a href = "#" onclick = "suntaek_proc('${tempGubun}','${startPage + blockSize}','');">[다음 10개]</a>
			</c:if>
			<c:if test = "${lastPage >= totalPage }">
				[다음 10개]
			</c:if>
			&nbsp;&nbsp;
				<a href = "#" onclick = "suntaek_proc('${tempGubun}','${totalPage}','');">[끝페이지]</a>
		</td>
	</tr>
	<tr>
		<td colspan="7" height="50" align="right">
			<button type = "button" onclick = "suntaek_proc('cart_clear');">장바구니 비우기</button>
			<button type = "button" onclick = "suntaek_proc('mall_list','1','');">쇼핑하기</button>
			<button type = "button" onclick = "suntaek_proc('buy','','');">주문하기</button>
		</td>
	</tr>	
</table>

<hr>
<div id="a" style="display:none;">aaaaa</div>
<div id="b" style="display:block;">bbbbb</div>

<script>
$(document).ready(function(){
	$("#checkAll").click(function(){
		if($("#checkAll").prop("checked")){
			$("input[name=chk]").prop("checked",true);
		}else{
			$("input[name=chk]").prop("checked",false);
		}
	});
	$("#a").show();
	$("#b").hide();
	//$("#result").hide();
	
});




</script>
