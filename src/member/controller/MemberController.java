package member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
//member_servlet 들어가는 모든 파일은 여기서 처리.
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import member.model.dao.MemberDAO;
import member.model.dto.MemberDTO;
import member.util.memberUtil;

@WebServlet("/member_servlet/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doProc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		memberUtil util = new memberUtil();

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

		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();

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
		if (url.indexOf("index.do") != -1) {
			System.out.println("인덱스 들어옴");
			String word = request.getParameter("word");
			System.out.println(word);
			request.setAttribute("menu_gubun", "member_index");
			request.setAttribute("word", word);
			RequestDispatcher rd_ = request.getRequestDispatcher(page);
			rd_.forward(request, response);
			
			
		} else if (url.indexOf("list.do") != -1) {
			int pageSize = 5;
			int blockSize = 10;
			int totalRecord = dao.getTotalRecord(search_option, search_data);
			int[] pageArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
			int jj = pageArray[0];
			int startRecord = pageArray[1];
			int lastRecord = pageArray[2];
			int totalPage = pageArray[3];
			int startPage = pageArray[4];
			int lastPage = pageArray[5];

			List<MemberDTO> list = dao.getList(startRecord, lastRecord, search_option, search_data);
			request.setAttribute("menu_gubun", "member_list");
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

			page = "/member/list.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			
		} else if (url.indexOf("chuga.do") != -1) {
			request.setAttribute("menu_gubun", "member_chuga");
			page = "/member/chuga.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("id_check.do") != -1) {
			String id = request.getParameter("id");
			// System.out.println(id+"MC");
			int result = dao.getIdCheck(id);

			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(result+"MC");
			out.println(result);
			out.flush();
			out.close();

		} else if (url.indexOf("id_check_win.do") != -1) {
			// response.sendRedirect(path+"/member/id_check.jsp?a=0");

			page = "/member/id_check.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("id_check_win_Proc.do") != -1) {
			String id = request.getParameter("id");
			System.out.println(id + "MC");
			String result = dao.getIdCheckWin(id);
			System.out.println(result + "MCresult");

			if (result == null || result.equals("")) {
				result = id;
			} else {
				result = "";
			}

			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();

		} else if (url.indexOf("chugaProc.do") != -1) {
			System.out.println("추가프록 들어옴");
			
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			String passwdChk = request.getParameter("passwdChk");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String bornYear_ = request.getParameter("bornYear");
			int bornYear = Integer.parseInt(bornYear_);
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String detailAddress = request.getParameter("detailAddress");
			String extraAddress = request.getParameter("extraAddress");
			System.out.println("id:"+id);
			System.out.println("passwd:"+passwd);
			System.out.println("passwdChk:"+passwdChk);
			System.out.println("name:"+name);
			System.out.println("gender:"+gender);
			System.out.println("bornYear:"+bornYear);
			System.out.println("postcode:"+postcode);
			System.out.println("address:"+address);
			System.out.println("detailAddress:"+detailAddress);
			System.out.println("extraAddress:"+extraAddress);
			
			dto.setId(id);
			dto.setPasswd(passwd);
			dto.setName(name);
			dto.setGender(gender);
			dto.setBornYear(bornYear);
			dto.setPostcode(postcode);
			dto.setAddress(address);
			dto.setDetailAddress(detailAddress);
			dto.setExtraAddress(extraAddress);

			int result = dao.setInsert(dto);
			
			PrintWriter out = response.getWriter();
	    	if(result>0) {
	    	  out.println("<script>$('#span_passwd').text('T');</script>");
	    	}else {
	    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
	    	}
	    	out.flush();
	    	out.close();
	    	
		} else if (url.indexOf("login.do") != -1) {
			request.setAttribute("menu_gubun", "member_login");

			page = "/member/login.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("loginProc.do") != -1) {
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");

			dto.setId(id);
			dto.setPasswd(passwd);

			MemberDTO resultDto = dao.setlogin(dto);

			if (resultDto.getNo() == 0) {
				temp = path + "/member_servlet/login.do";
			} else {// 성공
				HttpSession session = request.getSession();
				session.setAttribute("cookNo", resultDto.getNo());
				session.setAttribute("cookId", resultDto.getId());
				session.setAttribute("cookName", resultDto.getName());

				temp = path;
			}
			response.sendRedirect(temp);

		} else if (url.indexOf("logout.do") != -1) {

			HttpSession session = request.getSession();
			session.invalidate();

			temp = path;
			// response.sendRedirect(temp);

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃되었습니다.\\n즐거운하루되세요.')");
			out.println("location.href='" + path + "';");
			out.println("</script>");

		} else if (url.indexOf("view.do") != -1) {
			if (no == 0) {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('정상적인 접속이 아닙니다..')");
				out.println("history.back();");
				out.println("</script>");
			}
			System.out.println("no:" + no);
			dto = dao.getOne(no,search_option,search_data);
			System.out.println("MemberController의 dto id" + dto.getId());

			request.setAttribute("menu_gubun", "member_view");
			request.setAttribute("dto", dto);

			page = "/member/view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("sujung.do") != -1) {
			if (no == 0) {
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('정상적인 접속이 아닙니다..')");
				out.println("location.href='" + path + "';");
				out.println("</script>");
			}

			System.out.println("MemberController의 no" + no);

			dto = dao.getOne(no,search_option,search_data);

			request.setAttribute("menu_gubun", "member_sujung");
			request.setAttribute("dto", dto);
			page = "/member/sujung.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("sujungProc.do") != -1) {
			System.out.println("수정프록 들어옴");
			/*
			 * if(no==0) { response.setContentType("text/html;charset=utf-8"); PrintWriter
			 * out = response.getWriter(); out.println("<script>");
			 * out.println("alert('정상적인 접속이 아닙니다..')");
			 * out.println("location.href='"+path+"';"); out.println("</script>"); }
			 */
			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String bornYear_ = request.getParameter("bornYear");
			System.out.println(bornYear_);
			int bornYear = Integer.parseInt(bornYear_);
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String detailAddress = request.getParameter("detailAddress");
			String extraAddress = request.getParameter("extraAddress");

			dto.setNo(no);
			dto.setId(id);
			dto.setPasswd(passwd);
			dto.setName(name);
			dto.setGender(gender);
			dto.setBornYear(bornYear);
			dto.setPostcode(postcode);
			dto.setAddress(address);
			dto.setDetailAddress(detailAddress);
			dto.setExtraAddress(extraAddress);

			int result = dao.setSujung(dto);
			
          PrintWriter out = response.getWriter();
    	  if(result>0) {
    		  out.println("<script>$('#span_passwd').text('T');</script>");
    	  }else {
    		  out.println("<script>$('#span_passwd').text('F');</script>"); 
    	  }
    	  out.flush();
    	  out.close();
	          
			

		} else if (url.indexOf("sakjae.do") != -1) {
//			String no_ = request.getParameter("no");
//			if(no_==null||no_.length()==0) {
//				response.setContentType("text/html;charset=utf-8");
//				PrintWriter out = response.getWriter();
//				out.println("<script>");
//				out.println("alert('정상적인 접속이 아닙니다..')");
//				out.println("location.href='"+path+"';");
//				out.println("</script>");
//				}
//			no = Integer.parseInt(no_);
//			
//			HttpSession session = request.getSession();
//			cookNo = (Integer)session.getAttribute("cookNo");
//			System.out.println("cookNo"+cookNo+"??");
//			if(cookNo==2) {
//				response.setContentType("text/html;charset=utf-8");
//				PrintWriter out = response.getWriter();
//				out.println("<script>");
//				out.println("alert('관리자는 삭제할수 없습니다.')");
//				out.println("location.href='"+path+"';");
//				out.println("</script>");
//			}else {
//			
			dto = dao.getOne(no,search_option,search_data);

			request.setAttribute("menu_gubun", "member_sakjae");
			request.setAttribute("dto", dto);
			page = "/member/sakjae.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
			// }
			
		} else if (url.indexOf("sakjaeProc.do") != -1) {

			String id = request.getParameter("id");
			String passwd = request.getParameter("passwd");
			// System.out.println("MemberController id"+id);
			// System.out.println(passwd);

			dto.setId(id);
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
