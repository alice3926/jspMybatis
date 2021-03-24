<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>

<%@ include file="../include/inc_header.jsp" %>


<table border="1" align="center" width="80%">
	<tr>
		<td colspan="2"><h2>회원상세정보</h2></td>
	</tr>
	<tr>
		<td>회원번호</td>
		<td>${dto.no} </td>
	</tr>	
	<tr>	
		<td>아이디</td>
		<td>${dto.id}</td>
	</tr>	
	<tr>
		<td>이름</td>
		<td>${dto.name}  </td>
	</tr>	
	<tr>
		<td>성별</td>
		<td>${dto.gender}  </td>
	</tr>	
	<tr>
		<td>태어난년도</td>
		<td>${dto.bornYear}  </td>
		
	</tr>
		<tr>
		<td>주소</td>
		<td>${dto.address}${dto.detailAddress}${dto.extraAddress}  </td>
		
	</tr>
		<tr>
		<td>우편번호</td>
		<td>${dto.postcode}  </td>
		
	</tr>
		
	<tr>
		<td>가입일</td>
		<td>${dto.regiDate}  </td>
	</tr>
	<tr>
		<td>마지막 수정일</td>
		<td>${dto.lastupDate}  </td>
		
	</tr>
	<tr>
		<td colspan="2" height="50px">
						<table border="1" width="100%" align="center">
							<tr>
								<td width="100px">이전글 : </td>
								<td>
									<c:if test="${dto.preId == null }">
										이전글이 없습니다.
									</c:if>
									
									<c:if test="${dto.preId != null }">
										<a href="#" onclick="suntaek_proc('view','',${dto.preNo})">${dto.preId }</a>
									</c:if>	
								</td>
							</tr>
							<tr>
								<td width="100px">다음글 : </td>
								<td>
									<c:if test="${dto.nxtId == null }">
										다음글이 없습니다.
									</c:if>
									
									<c:if test="${dto.nxtId != null }">
										<a href="#" onclick="suntaek_proc('view','',${dto.nxtNo})">${dto.nxtId }</a>
									</c:if>	
								</td>
							</tr>				
						</table>
					</td>
	</tr>
	<tr>
		<td colspan="15" align="right" style="height:50px;">
			<button type="button" onclick="suntaek_proc('list','${pageNumber}','');">목록으로</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" onclick="suntaek_proc('sujung','','${dto.no}');">수정하기</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" onclick="suntaek_proc('sakjae','','${dto.no}');">삭제하기</button>
		</td>
	</tr>
</table>




