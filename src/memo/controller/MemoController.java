package memo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import common.Util;
import member.model.dto.MemberDTO;
import member.util.memberUtil;
import memo.model.dao.MemoDAO;
import memo.model.dto.MemoDTO;




@WebServlet("/memo_servlet/*")
public class MemoController extends HttpServlet {
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

		String serverInfo[] = util.getServerInfo(request); // request.getContextPath();
		String refer = serverInfo[0];
		String path = serverInfo[1];
		String url = serverInfo[2];
		String uri = serverInfo[3];
		String ip = serverInfo[4];
		// String ip6 = serverInfo[5];
		
		String temp;

		temp = request.getParameter("pageNumber");
		int pageNumber = util.numberCheck(temp, 1);

		
		temp = request.getParameter("no");
		//System.out.println("no 넘어옴?:" + temp);
		int no = util.numberCheck(temp, 0);
		
		String[] sessionArray = util.sessionCheck(request);
		int cookNo = Integer.parseInt(sessionArray[0]);
		String cookId = sessionArray[1];
		String cookName = sessionArray[2];

		
		request.setAttribute("naljaMap", naljaMap);
		request.setAttribute("ip", ip);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("no", no);

		MemoDTO dto = new MemoDTO();
		MemoDAO dao = new MemoDAO();
		
		String page = "/main/main.jsp";
		if (url.indexOf("index.do") != -1) {
			request.setAttribute("menu_gubun", "memo_index");
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if(url.indexOf("insert.do")!=-1) {
			//request.setAttribute("menu_gubun", "memo_insert");
			page="/memo/insert.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		}else if(url.indexOf("insertProc.do")!=-1) {
			String writerName = request.getParameter("writerName");
			String memo = request.getParameter("content");
			
			HttpSession session = request.getSession();
			cookId = (String)session.getAttribute("cookId");
			if(cookId==null) {
				cookId="0";
			}
			System.out.println("writerName:"+writerName);
			System.out.println("memo:"+memo);
						
			dto.setWriterId(cookId);
			dto.setWriterName(writerName);
			dto.setMemo(memo);
			
			System.out.println("writerid:"+dto.getWriterId());
			System.out.println("writername:"+dto.getWriterName());
			
			int result = dao.setInsert(dto);
			
			PrintWriter out = response.getWriter();
	    	if(result>0) {
	    	  out.println("<script>$('#span_passwd').text('T');</script>");
	    	}else {
	    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
	    	}
	    	out.flush();
	    	out.close();
		
		}else if(url.indexOf("list.do")!=-1 || url.indexOf("sujung.do")!=-1) {
			int pageSize = 3;
			int blockSize = 10;
			int totalRecord = dao.getTotalRecord();
			int[] pageArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
			int jj = pageArray[0];
			int startRecord = pageArray[1];
			int lastRecord = pageArray[2];
			int totalPage = pageArray[3];
			int startPage = pageArray[4];
			int lastPage = pageArray[5];

			List<MemoDTO> list = dao.getList(startRecord, lastRecord);
			
			
			if (no>0) {
				System.out.println("if no:"+no);
				MemoDTO onedto = dao.getOne(no);
				request.setAttribute("onedto", onedto);
			}
						
			//request.setAttribute("menu_gubun", "memo_list");
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

			page = "/memo/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		}else if(url.indexOf("sujungProc.do")!=-1) {
			String oneno_ = request.getParameter("oneno");
			int oneno = Integer.parseInt(oneno_);
			String writerName = request.getParameter("writerName");
			String memo = request.getParameter("content");
			
			
			
			System.out.println("no:"+oneno);
			System.out.println("writerName:"+writerName);
			System.out.println("memo:"+memo);
			
			MemoDTO sujungdto = dao.getOne(oneno);
			
			System.out.println(sujungdto.getNo());
			System.out.println(sujungdto.getWriterName());
			System.out.println(sujungdto.getMemo());
			
			sujungdto.setWriterName(writerName);
			sujungdto.setMemo(memo);
			
			
						
			int result = dao.setSujung(sujungdto);
			System.out.println("result:"+result);
			
			PrintWriter out = response.getWriter();
	    	if(result>0) {
	    	  out.println("<script>$('#span_passwd').text('T');</script>");
	    	}else {
	    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
	    	}
	    	out.flush();
	    	out.close();
		
		}else if(url.indexOf("sakjaeProc.do")!=-1) {
			String oneno_ = request.getParameter("oneno");
			int oneno = Integer.parseInt(oneno_);
			
			MemoDTO sakjaedto = dao.getOne(oneno);	
			
			int result = dao.setSakjae(sakjaedto);
			
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
