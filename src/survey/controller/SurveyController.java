package survey.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.mysql.jdbc.Util;

import survey.model.dao.SurveyAnswerDAO;
import survey.model.dao.SurveyDAO;
import survey.model.dto.SurveyAnswerDTO;
import survey.model.dto.SurveyDTO;

@WebServlet("/survey_servlet/*")
public class SurveyController extends HttpServlet {
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

		common.Util util = new common.Util();

		int[] nalja = util.getDateTime();
		Map<String, Integer> naljaMap = new HashMap<>();
		naljaMap.put("now_y", nalja[0]);
		naljaMap.put("now_m", nalja[1]);
		naljaMap.put("now_d", nalja[2]);

		String temp;
		temp = request.getParameter("pageNumber");
		int pageNumber = util.numberCheck(temp, 1);

		temp = request.getParameter("no");
		int no = util.numberCheck(temp, 0);

		temp = request.getParameter("list_gubun");
		String list_gubun = util.list_gubunCheck(temp);

		String search_option = request.getParameter("search_option");
		String search_data = request.getParameter("search_data");
		String search_date_s = request.getParameter("search_date_s");
		String search_date_e = request.getParameter("search_date_e");
		String search_date_check = request.getParameter("search_date_check");

		// System.out.println(search_date_check+"1111111111111111111111");
		String[] searchArray = util.searchCheck(search_option, search_data, search_date_s, search_date_e,
				search_date_check);
		search_option = searchArray[0];
		search_data = searchArray[1];
		search_date_s = searchArray[2];
		search_date_e = searchArray[3];
		search_date_check = searchArray[4];
		// System.out.println(search_date_check+"222222222222222222");

		request.setAttribute("naljaMap", naljaMap);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("list_gubun", list_gubun);
		request.setAttribute("search_option", search_option);
		request.setAttribute("search_data", search_data);
		request.setAttribute("search_date_s", search_date_s);
		request.setAttribute("search_date_e", search_date_e);
		request.setAttribute("search_date_check", search_date_check);

		String path = request.getContextPath();
		String url = request.getRequestURL().toString();

		SurveyDAO dao = new SurveyDAO();
		SurveyDTO dto = new SurveyDTO();

		String page = "/main/main.jsp";

		if (url.indexOf("index.do") != -1) {
			String moveword = request.getParameter("moveword");

			if (moveword == null || moveword.equals("")) {
				request.setAttribute("menu_gubun", "survey_index");
			} else {
				request.setAttribute("moveword", moveword);
				request.setAttribute("menu_gubun", "questionBank_index");
			}

			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("chuga.do") != -1) {
			request.setAttribute("menu_gubun", "survey_chuga");
			page = "/survey/chuga.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);

			rd.forward(request, response);

		} else if (url.indexOf("chugaProc.do") != -1) {
			String question = request.getParameter("question");
			String ans1 = request.getParameter("ans1");
			String ans2 = request.getParameter("ans2");
			String ans3 = request.getParameter("ans3");
			String ans4 = request.getParameter("ans4");
			String status = request.getParameter("status");

			String syear = request.getParameter("syear");
			String smonth = request.getParameter("smonth");
			String sday = request.getParameter("sday");

			String lyear = request.getParameter("lyear");
			String lmonth = request.getParameter("lmonth");
			String lday = request.getParameter("lday");

			String start_date_ = syear + "-" + smonth + "-" + sday + " 00:00:00.0";
			String last_date_ = lyear + "-" + lmonth + "-" + lday + " 23:59:59.9"; // 소수점 6자리까지만 됨.

			java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(start_date_);
			java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(last_date_);

			System.out.println(question + "/" + ans1 + "/" + ans2 + "/" + ans3 + "/" + ans4 + "/" + status + "/\n"
					+ start_date_ + "/" + last_date_);

			dto.setQuestion(question);
			dto.setAns1(ans1);
			dto.setAns2(ans2);
			dto.setAns3(ans3);
			dto.setAns4(ans4);
			dto.setStatus(status);
			dto.setStart_date(start_date);
			dto.setLast_date(last_date);

			int result = dao.setInsert(dto);
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.println("<script>$('#span_passwd').text('T');</script>");
			} else {
				out.println("<script>$('#span_passwd').text('F');</script>");
			}
			out.flush();
			out.close();

		} else if (url.indexOf("list.do") != -1 || url.indexOf("list_2.do") != -1) {
			int pageSize = 20;
			if (url.indexOf("list_2.do") != -1) {
				pageSize = 100;
			}
			int blockSize = 10;

			if (search_date_s.length() > 0 && search_date_e.length() > 0) {
				search_date_s = search_date_s + " 00:00:00.0";
				search_date_e = search_date_e + " 23:59:59.999999";
				// java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(search_date_s);
				// java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(search_date_e);
			}
			int totalRecord = dao.getTotalRecord(list_gubun, search_option, search_data, search_date_s, search_date_e,
					search_date_check);
			int[] pageArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
			int jj = pageArray[0];
			int startRecord = pageArray[1];
			int lastRecord = pageArray[2];
			int totalPage = pageArray[3];
			int startPage = pageArray[4];
			int lastPage = pageArray[5];

			List<SurveyDTO> list = dao.getList(list_gubun, startRecord, lastRecord, search_option, search_data,
					search_date_s, search_date_e, search_date_check);

			request.setAttribute("menu_gubun", "survey_list");
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

			if (url.indexOf("list.do") != -1) {
				page = "/survey/list.jsp";
			} else {
				page = "/survey/list2.jsp";
			}
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("view.do") != -1) {

			System.out.println("SVC view.do");
			String nono_ = request.getParameter("no");
			System.out.println(nono_);
			int nono = Integer.parseInt(nono_);
			SurveyDTO viewdto = dao.getOne(nono);

			request.setAttribute("menu_gubun", "survey_view");
			request.setAttribute("dto", viewdto);

			page = "/survey/view2.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("viewProc.do") != -1) {
			String sa_no_ = request.getParameter("no");
			int sa_no = Integer.parseInt(sa_no_);
			String sa_answer_ = request.getParameter("answer");
			int sa_answer = Integer.parseInt(sa_answer_);

			SurveyAnswerDTO sadto = new SurveyAnswerDTO();

			sadto.setNo(sa_no);
			sadto.setAnswer(sa_answer);

			int result = dao.setInsertAnswer(sadto);

			page = "/survey_servlet/index.do";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("viewProc2.do") != -1) {
			String answer_ = request.getParameter("answer");
			int answer = Integer.parseInt(answer_);

			SurveyAnswerDTO sadto = new SurveyAnswerDTO();
			sadto.setNo(no);
			sadto.setAnswer(answer);

			SurveyAnswerDAO sadao = new SurveyAnswerDAO();
			dao.setInsertAnswer(sadto);

			page = "/survey_servlet/index.do";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);
		} else if (url.indexOf("saveProc.do") != -1) {
			String answer_total = request.getParameter("answer_total");
			System.out.println("answer_total:" + answer_total);

			String[] answer_totalArr = answer_total.split("[|]");
			System.out.println("answer_totalArr.length:" + answer_totalArr.length);

			for (int i = 0; i < answer_totalArr.length; i++) {
				String[] imsiArr = answer_totalArr[i].split(":");
				int tempNo = Integer.parseInt(imsiArr[0]);
				int tempAnswer = Integer.parseInt(imsiArr[1]);

				System.out.println("tempNo:" + tempNo + "|tempAnswer" + tempAnswer);

				SurveyAnswerDTO answerDto = new SurveyAnswerDTO();
				answerDto.setAnswer(tempAnswer);
				answerDto.setNo(tempNo);
				dao.setInsertAnswer(answerDto);

			}

		} else if (url.indexOf("sujung.do") != -1) {
			dto = dao.getOne(no);

			Timestamp start_date = dto.getStart_date();
			Timestamp last_date = dto.getLast_date();
		
			
			
			String a = start_date.toString();
			String syear_ = a.substring(0, 4);
			String smonth_ = a.substring(5, 7);
			String sday_ = a.substring(8, 10);
			int syear = Integer.parseInt(syear_);
			int smonth;
			if (smonth_.subSequence(0, 1).equals("0")) {
				smonth = Integer.parseInt(smonth_.substring(1,2));				
			}else {
				smonth = Integer.parseInt(smonth_);
			}
			int sday;
			if (sday_.subSequence(0, 1).equals("0")) {
				sday = Integer.parseInt(sday_.substring(1,2));				
			}else {
				sday = Integer.parseInt(sday_);
			}

			a = last_date.toString();
			String lyear_ = a.substring(0, 4);
			String lmonth_ = a.substring(5, 7);
			String lday_ = a.substring(8, 10);
			
			int lyear = Integer.parseInt(lyear_);
			int lmonth;
			if (lmonth_.subSequence(0, 1).equals("0")) {
				lmonth = Integer.parseInt(lmonth_.substring(1,2));				
			}else {
				lmonth = Integer.parseInt(lmonth_);
			}
			int lday;
			if (lday_.subSequence(0, 1).equals("0")) {
				lday = Integer.parseInt(lday_.substring(1,2));				
			}else {
				lday = Integer.parseInt(lday_);
			}
			
			
			
	
			
			request.setAttribute("syear", syear);
			request.setAttribute("smonth", smonth);
			request.setAttribute("sday", sday);

			request.setAttribute("lyear", lyear);
			request.setAttribute("lmonth", lmonth);
			request.setAttribute("lday", lday);
			
			request.setAttribute("menu_gubun", "survey_sujung");
			request.setAttribute("dto", dto);
			page = "/survey/sujung.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

		} else if (url.indexOf("sujungProc.do") != -1) {
			System.out.println("수정프록 들어옴");
			String question = request.getParameter("question");
			String ans1 = request.getParameter("ans1");
			String ans2 = request.getParameter("ans2");
			String ans3 = request.getParameter("ans3");
			String ans4 = request.getParameter("ans4");
			String status = request.getParameter("status");

			String syear = request.getParameter("syear");
			String smonth = request.getParameter("smonth");
			String sday = request.getParameter("sday");

			String lyear = request.getParameter("lyear");
			String lmonth = request.getParameter("lmonth");
			String lday = request.getParameter("lday");

			String start_date_ = syear + "-" + smonth + "-" + sday + " 00:00:00.0";
			String last_date_ = lyear + "-" + lmonth + "-" + lday + " 23:59:59.9"; // 소수점 6자리까지만 됨.

			java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(start_date_);
			java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(last_date_);

			System.out.println(question + "/" + ans1 + "/" + ans2 + "/" + ans3 + "/" + ans4 + "/" + status + "/\n"
					+ start_date_ + "/" + last_date_);

			dto.setNo(no);
			dto.setQuestion(question);
			dto.setAns1(ans1);
			dto.setAns2(ans2);
			dto.setAns3(ans3);
			dto.setAns4(ans4);
			dto.setStatus(status);
			dto.setStart_date(start_date);
			dto.setLast_date(last_date);

			int result = dao.setSujung(dto);
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.println("<script>$('#span_passwd').text('T');</script>");
			} else {
				out.println("<script>$('#span_passwd').text('F');</script>");
			}
			out.flush();
			out.close();

		} else if (url.indexOf("sakjaeProc.do") != -1) {
			System.out.println("삭제프록 들어옴");
			System.out.println("no: " + no);
			int result = dao.setSakjae(no);

			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.println("<script>$('#span_passwd').text('T');</script>");
			} else {
				out.println("<script>$('#span_passwd').text('F');</script>");
			}
			out.flush();
			out.close();

		}

	}
}
