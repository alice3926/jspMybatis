<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/inc_header.jsp" %>

	<table border="1" align="center" width="80%">
		<tr>
			<td colspan="2">
				<h1>상품 삭제페이지</h1>
			</td>
		</tr>
		<tr>
			<td width="150">상품코드</td>
			<td>${dto.no }</td>	
		</tr>
		<tr>
			<td width="150">상품사진</td>
			<td>
				<c:choose>
					<c:when test="${dto.product_img =='-,-,-' }">이미지
					</c:when>
					<c:otherwise>
						<c:set var="temp1" value="${fn:split(dto.product_img,',')[0] }"></c:set>
						<c:set var="real_temp1" value="${fn:split(temp1,'|')[1] }"></c:set>
							<img src="${path }/attach/product_img/${real_temp1}" alt="${dto.name }" title="${dto.name }" style="width:50px; height:50px;">
					</c:otherwise>
				</c:choose>
			</td>	
		</tr>	
		<tr>
			<td>상품명</td>
			<td>${dto.name }</td>	
		</tr>
		
		<tr>
			<td>가격</td>
			<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.price}"/></td>	
		</tr>
		<tr>
			<td style="align: center;">상품설명</td>
			<td id="content">${dto.description}</td>	
		</tr>
		<tr>
			<td>파일명</td>
			<td>${fn:split(dto.product_img,'|')[0] }</td>	
		</tr>
		<tr>
			<td>등록일</td>
			<td>${dto.regi_date }</td>	
		</tr>	
		<tr>
			<td >장바구니수량</td>
			<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${dto.buy_counter }"/></td>	
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" onclick="suntaek_proc('chuga','1','');">상품등록</button>
					
				<button type="button" onclick="suntaek_proc('sakjeProc','','${dto.no}');">삭제하기</button>
								
				<button type="button" onclick="suntaek_proc('list','1','');">목록으로</button>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="50px">
				<table border="1" width="100%" align="center">
					<tr>
						<td width="100px">이전글 : </td>
						<td>
							<c:if test="${dto.preSubject == null }">
								이전글이 없습니다.
							</c:if>
							
							<c:if test="${dto.preSubject != null }">
								<a href="#" onclick="suntaek_proc('view','',${dto.preNo})">${dto.preSubject }</a>
							</c:if>	
						</td>
					</tr>
					<tr>
						<td width="100px">다음글 : </td>
						<td>
							<c:if test="${dto.nxtSubject == null }">
								다음글이 없습니다.
							</c:if>
							
							<c:if test="${dto.nxtSubject != null }">
								<a href="#" onclick="suntaek_proc('view','',${dto.nxtNo})">${dto.nxtSubject }</a>
							</c:if>	
						</td>
					</tr>				
				</table>
			</td>
		</tr>
		
	</table>


