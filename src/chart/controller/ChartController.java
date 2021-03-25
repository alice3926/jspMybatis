package chart.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import chart.service.ChartService;
import common.Util;

@WebServlet("/chart_servlet/*")
public class ChartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Util util = new Util(); 
		
		int[] nalja = util.getDateTime();
		Map<String, Integer> naljaMap = new HashMap<>();
		naljaMap.put("now_y", nalja[0]);
		naljaMap.put("now_m", nalja[1]);
		naljaMap.put("now_d", nalja[2]);
		

		String temp;
		temp=request.getParameter("pageNumber");
		int pageNumber=util.numberCheck(temp,1);
		
		temp=request.getParameter("no");
		int no = util.numberCheck(temp, 0);
		
		
		temp=request.getParameter("list_gubun");
		String list_gubun=util.list_gubunCheck(temp);
		
		
		String search_option=request.getParameter("search_option");
		String search_data=request.getParameter("search_data");
		String search_date_s=request.getParameter("search_date_s");
		String search_date_e=request.getParameter("search_date_e");
		String search_date_check=request.getParameter("search_date_check");
			
		
		//System.out.println(search_date_check+"1111111111111111111111");
		String[] searchArray = util.searchCheck(search_option,search_data,search_date_s,search_date_e,search_date_check);
		search_option = searchArray[0];
		search_data=searchArray[1];
		search_date_s=searchArray[2];
		search_date_e=searchArray[3];
		search_date_check=searchArray[4];
		//System.out.println(search_date_check+"222222222222222222");
		
		request.setAttribute("naljaMap", naljaMap);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("list_gubun", list_gubun);
		request.setAttribute("search_option", search_option);
		request.setAttribute("search_data", search_data);
		request.setAttribute("search_date_s", search_date_s);
		request.setAttribute("search_date_e", search_date_e);
		request.setAttribute("search_date_check", search_date_check);
		
		String path = request.getContextPath();
		String url=request.getRequestURL().toString();
		
		String page = "/main/main.jsp";
		
		if (url.indexOf("index.do")!=-1) {
			request.setAttribute("menu_gubun", "chart_index");
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request,response);
		}else if (url.indexOf("googleChartJson.do")!=-1) {
			System.out.println("컨트롤러들어옴");
			request.setAttribute("menu_gubun", "chart_googleChartJson");
			page = "/chart/googleChartJson.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request,response);
		}else if (url.indexOf("createJson.do")!=-1) {
			System.out.println("createJson 컨트롤러들어옴");
			request.setAttribute("menu_gubun", "chart_createJson");
			
			ChartService service = new ChartService();
			JSONObject json = service.getChartData();
			request.setAttribute("data", json);
			System.out.println("createJson 컨트롤러들어옴1");
			
			String img_path01 = request.getSession().getServletContext().getRealPath("/attach/json/");
			java.io.File isDir = new java.io.File(img_path01);
			if(!isDir.isDirectory()) {
				isDir.mkdir();
			}
			String img_path02 = img_path01.replace("\\","/");
			String img_path03 = img_path01.replace("\\","\\\\");
			System.out.println("createJson 컨트롤러들어옴2");
			
			util.fileDelete(request,img_path03);
			
			
			
			String newFileName = util.getDateTimeType()+"_"+ util.create_uuid() + ".json";
			File file = new File(img_path03 + newFileName);
			file.createNewFile();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(json.toString());
			bufferedWriter.close();
			System.out.println("createJson 컨트롤러들어옴3 ");
			request.setAttribute("menu_gubun", "chart_myChart");
			request.setAttribute("chart_subject", "chart 제목임.");
			request.setAttribute("chart_type", "PieChart"); //차트종류를 linechart나 columnChart로 바꿀수 있음.
			request.setAttribute("chart_jsonFileName", newFileName);
			
			System.out.println("createJson 컨트롤러들어옴4");
			page = "/chart/myChart.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request,response);
		}
		
	
	
	}

}
