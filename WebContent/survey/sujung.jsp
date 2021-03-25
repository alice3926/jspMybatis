<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>

<%@ include file="../include/inc_header.jsp" %>



<table border="1">
	<tr>
		<td colspan="2"><h2>설문조사 등록</h2></td>
	</tr>
	<tr>
		<td style="align:center;">question</td>
		<td><input type="text" name="question" id="question" value="${dto.question }"></td>
	</tr>
	<tr>
		<td style="align:center;">ans1</td>
		<td><input type="text" name="ans1" id="ans1" value="${dto.ans1 }"></td>
	</tr>
	<tr>
		<td style="align:center;">ans2</td>
		<td><input type="text" name="ans2" id="ans2" value="${dto.ans2 }"></td>
	</tr>
	<tr>
		<td style="align:center;">ans3</td>
		<td><input type="text" name="ans3" id="ans3" value="${dto.ans3 }"></td>
	</tr>
	<tr>
		<td style="align:center;">ans4</td>
		<td><input type="text" name="ans4" id="ans4" value="${dto.ans4 }"></td>
	</tr>
	<tr>
		<td style="align:center;">status</td>
		<td>
			<input type="radio" name="genderRadio" value="1" checked 
			onclick="suntaek_status('1');">진행중
			&nbsp;&nbsp;&nbsp;
			<input type="radio" name="genderRadio" value="0"
			onclick="suntaek_status('0');">종료
			<br>
			<input type="hidden" name="gender" id="gender" value="1">
		</td>
	</tr>
	<tr>
		<td style="align:center;">start_date</td>	
		<td>
		
			<select name="syear" id="syear">
		<c:forEach var="i" begin="${naljaMap['now_y'] -1}" end="${naljaMap['now_y'] +1}" step="1">
				<c:if test="${syear ==i }">			
				<option value="${i }" selected>${i }</option>
				</c:if>
				<c:if test="${syear!=i }">			
				<option value="${i }">${i }</option>
				</c:if>
			</c:forEach>
			</select>년 
			<select name="smonth" id="smonth">
			<c:forEach var="i" begin="1" end="12" step="1">
				<c:if test="${smonth==i }">
					<c:if test="${i<10 }">			
				<option value="0${i }" selected>0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${smonth!=i }">
					<c:if test="${i<10 }">			
				<option value="0${i }">0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${smonth==i }">	
					<c:if test="${i>=10 }">			
				<option value="${i }" selected>${i }</option>
					</c:if>
				</c:if>
				<c:if test="${smonth!=i }">		
					<c:if test="${i>=10 }">		
				<option value="${i }">${i }</option>
					</c:if>
				</c:if>
			</c:forEach>
			</select>월 
			<select name="sday" id="sday">
			<c:forEach var="i" begin="1" end="31" step="1">
				<c:if test="${sday==i }">
					<c:if test="${i<10 }">			
				<option value="0${i }" selected>0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${sday!=i }">
					<c:if test="${i<10 }">			
				<option value="0${i }">0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${sday==i }">	
					<c:if test="${i>=10 }">			
				<option value="${i }" selected>${i }</option>
					</c:if>
				</c:if>
				<c:if test="${sday!=i }">		
					<c:if test="${i>=10 }">		
				<option value="${i }">${i }</option>
					</c:if>
				</c:if>
			</c:forEach>
			</select>일
			
		</td>
	</tr>
	<tr>
		<td style="align:center;">last_date</td>	
		<td>
			<select name="lyear" id="lyear">
			<c:forEach var="i" begin="${naljaMap['now_y'] -1}" end="${naljaMap['now_y'] +1}" step="1">
				<c:if test="${lyear==i }">			
				<option value="${i }" selected>${i }</option>
				</c:if>
				<c:if test="${lyear!=i }">			
				<option value="${i }">${i }</option>
				</c:if>
			</c:forEach>
			</select>년 
			<select name="lmonth" id="lmonth">
			<c:forEach var="i" begin="1" end="12" step="1">
				<c:if test="${lmonth==i }">
					<c:if test="${i<10 }">			
				<option value="0${i }" selected>0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${lmonth!=i }">
					<c:if test="${i<10 }">			
				<option value="0${i }">0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${lmonth==i }">	
					<c:if test="${i>=10 }">			
				<option value="${i }" selected>${i }</option>
					</c:if>
				</c:if>
				<c:if test="${lmonth!=i }">		
					<c:if test="${i>=10 }">		
				<option value="${i }">${i }</option>
					</c:if>
				</c:if>
			</c:forEach>
			</select>월 
			<select name="lday" id="lday">
			<c:forEach var="i" begin="1" end="31" step="1">
				<c:if test="${lday==i }">
					<c:if test="${i<10 }">			
				<option value="0${i }" selected>0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${lday!=i }">
					<c:if test="${i<10 }">			
				<option value="0${i }">0${i }</option>
					</c:if>
				</c:if>
				<c:if test="${lday==i }">	
					<c:if test="${i>=10 }">			
				<option value="${i }" selected>${i }</option>
					</c:if>
				</c:if>
				<c:if test="${lday!=i }">		
					<c:if test="${i>=10 }">		
				<option value="${i }">${i }</option>
					</c:if>
				</c:if>
			</c:forEach>
			</select>일
			
		</td>
	</tr>
<tr>
		<td colspan="15" align="right" style="height:50px;">
			<button type="button" onclick="suntaek_proc('sujungProc','','${dto.no}');">수정하기</button>
			<button type="button" onclick="suntaek_proc('list','1','');">목록으로</button>
		</td>
	</tr>
</table>


   
