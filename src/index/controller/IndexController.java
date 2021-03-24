package index.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//url-mapping

@WebServlet("/index.do")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//실제 필요한 내용 적는 곳
		request.setCharacterEncoding("UTF-8"); //post로 넘어올때 한글 안깨지게하는 코드
		
		String path = request.getContextPath();
		String url = request.getRequestURL().toString();
		//System.out.println(path+"//"+url);  //출력값 : /jspStudy / /http://localhost:8090/jspStudy/index.do
		request.setAttribute("menu_gubun", "index"); //menu_gubun으로 페이지를 구분할 것임.
		
		String page = "/main/main.jsp"; //webcontent폴더 까지
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
		
	}

}
