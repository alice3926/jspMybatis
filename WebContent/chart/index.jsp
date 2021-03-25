<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/inc_header.jsp" %>    

<!-- 구글 차트 라이브러리 로딩 -->
<script src="http://www.google.com/jsapi"></script>

<button type="button" id="btnCreateJson">구글차트(create)</button>
&nbsp;&nbsp;&nbsp;
<button type="button" id="btnGoogleChartJson">구글차트(json)</button>
<!-- &nbsp;&nbsp;&nbsp;
<button type="button" id="btnGoogleChartDb">구글차트(db)</button>
&nbsp;&nbsp;&nbsp;
<button type="button" id="btnJfreeChartPng">JfreeChart(png)</button>
&nbsp;&nbsp;&nbsp;
<button type="button" id="btnJfreeChartPdf">JfreeChart(pdf)</button> -->

<br><br>

<div id="result" style="border:1px solid red; position: relative;"></div>

<script>
	$(document).ready(function(){
		$("#btnCreateJson").click(function(){
			GoPage('createJson');
		});
		$("#btnGoogleChartJson").click(function(){
			GoPage('googleChartJson');
		});
		/* $("#btnGoogleChartDb").click(function(){
			GoPage('googleChartDb');
		});
		$("#btnJfreeChartPng").click(function(){
			//GoPage('JfreeChartPng');
			location.href = '${path}/chart_servlet/jfreeChartPng.do';
		});
		$("#btnJfreeChartPdf").click(function(){
			GoPage('jfreeChartPdf');
		}); */
	});
	
	function GoPage(value1){
		var method_type = "post";
		var param = {}
		var url = "${path}/chart_servlet/"+value1+".do";
		
		$.ajax({
			type: method_type,
			data: param,
			url: url,
			success: function(data){
				$("#result").html(data);
			}
		});
	}
</script>