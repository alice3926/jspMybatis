package guestbook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import guestbook.common.GuestbookUtil;
import guestbook.model.dao.GuestbookDAO;
import guestbook.model.dto.GuestbookDTO;



@WebServlet("/guestbook_servlet/*")
public class guestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		GuestbookUtil util = new GuestbookUtil();

		int[] nalja = util.getDateTime();
		Map<String, Integer> naljaMap = new HashMap<>();
		naljaMap.put("now_y", nalja[0]);
		naljaMap.put("now_m", nalja[1]);
		naljaMap.put("now_d", nalja[2]);

		String serverInfo[] = util.getServerInfo(request); // request.getContextPath();
		String refer = serverInfo[0];
		String path = serverInfo[1];
		String url = serverInfo[2];
		String uri = serverInfo[3];
		String ip = serverInfo[4];
		// String ip6 = serverInfo[5];

		GuestbookDAO dao = new GuestbookDAO();
		GuestbookDTO dto = new GuestbookDTO();

		String temp;

		temp = request.getParameter("pageNumber");
		int pageNumber = util.numberCheck(temp, 1);

		temp = request.getParameter("no");
		System.out.println("no 넘어옴?:" + temp);
		int no = util.numberCheck(temp, 0);

		String search_option = request.getParameter("search_option");
		String search_data = request.getParameter("search_data");

		// System.out.println("입력search_option:"+search_option);
		// System.out.println("입력search_data:"+search_data);

		String[] searchArray = util.searchCheck(search_option, search_data);
		search_option = searchArray[0];
		search_data = searchArray[1];

		// System.out.println("배열search_option:"+search_option);
		// System.out.println("배열search_data:"+search_data);

		String[] sessionArray = util.sessionCheck(request);
		int cookNo = Integer.parseInt(sessionArray[0]);
		String cookId = sessionArray[1];
		String cookName = sessionArray[2];

		request.setAttribute("naljaMap", naljaMap);
		request.setAttribute("ip", ip);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("no", no);
		request.setAttribute("search_option", search_option);
		request.setAttribute("search_data", search_data);

		String page = "/main/main.jsp";
		if(url.indexOf("index.do")!=-1) {
			request.setAttribute("menu_gubun", "guestbook_index");
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		
		}else if(url.indexOf("chuga.do")!=-1) {
			//request.setAttribute("menu_gubun", "guestbook_write");
			page = "/guestbook/write.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		
		}else if(url.indexOf("chugaProc.do")!=-1) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			String content = request.getParameter("content");
			
			dto.setName(name);
			dto.setEmail(email);
			dto.setPasswd(passwd);
			dto.setContent(content);
			System.out.println("3");
			int result = dao.setInsert(dto);
			System.out.println("4");
			PrintWriter out = response.getWriter();
			if(result>0) {
		    	  out.println("<script>$('#span_passwd').text('T');</script>");
		    	}else {
		    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
		    	}
		    	out.flush();
		    	out.close();
	
		}else if(url.indexOf("list.do")!=-1) {
			int pageSize = 3;
			int blockSize = 10;
			int totalRecord = dao.getTotalRecord(search_option, search_data);
			int[] pageArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
			int jj = pageArray[0];
			int startRecord = pageArray[1];
			int lastRecord = pageArray[2];
			int totalPage = pageArray[3];
			int startPage = pageArray[4];
			int lastPage = pageArray[5];
			
			List<GuestbookDTO> list = dao.getList(startRecord, lastRecord, search_option, search_data);
			//request.setAttribute("menu_gubun", "guestbook_list");
			request.setAttribute("list", list);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("blockSize", blockSize);
			request.setAttribute("totalRecord", totalRecord);
			request.setAttribute("jj", jj);

			request.setAttribute("startRecord", startRecord);
			request.setAttribute("lastRecord", lastRecord);

			request.setAttribute("totalPage", totalPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("lastPage", lastPage);

			page = "/guestbook/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		
			
		} else if (url.indexOf("sujung.do") != -1) {
			System.out.println("guestbookController의 no" + no);

			dto = dao.getOne(no);

			//request.setAttribute("menu_gubun", "guestbook_sujung");
			request.setAttribute("dto", dto);
			page = "/guestbook/sujung.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("sujungProc.do") != -1) {
			System.out.println("수정프록 들어옴");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			String content = request.getParameter("content");
			
			dto.setNo(no);
			dto.setName(name);
			dto.setEmail(email);
			dto.setPasswd(passwd);
			dto.setContent(content);
			System.out.println(dto.getNo());
			System.out.println(dto.getName());
			System.out.println(dto.getPasswd());
			System.out.println(dto.getEmail());
			System.out.println(dto.getContent());
			int result = dao.setSujung(dto);
			System.out.println("4");
			PrintWriter out = response.getWriter();
			if(result>0) {
		    	  out.println("<script>$('#span_passwd').text('T');</script>");
		    	}else {
		    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
		    	}
		    	out.flush();
		    	out.close();
	
			

		} else if (url.indexOf("sakjae.do") != -1) {
			dto = dao.getOne(no);

			//request.setAttribute("menu_gubun", "guestbook_sakjae");
			request.setAttribute("dto", dto);
			page = "/guestbook/sakjae.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (url.indexOf("sakjaeProc.do") != -1) {

			String passwd = request.getParameter("passwd");
			dto.setNo(no);
			dto.setPasswd(passwd);

			int result = dao.setSakjae(dto);
			
			
          PrintWriter out = response.getWriter();
    	  if(result>0) {
    		  out.println("<script>$('#span_passwd').text('T');</script>");
    	  }else {
    		  out.println("<script>$('#span_passwd').text('F');</script>"); 
    	  }
    	  out.flush();
    	  out.close();
	          
			
			
			
			
			

		}
	
	}
	
	

}
