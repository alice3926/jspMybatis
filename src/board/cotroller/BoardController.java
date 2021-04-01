package board.cotroller;

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

import board.common.UtilBoard;
import board.model.dao.BoardDAO;
import board.model.dto.BoardDTO;
import board.model.dto.CommentDTO;

@WebServlet("/board_servlet/*")
public class BoardController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProc(request, response);
   }


   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProc(request, response);
   }
   
   protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      
      UtilBoard util = new UtilBoard();
      
      int[] nalja = util.getDateTime();
      Map<String, Integer> naljaMap = new HashMap<>();
      naljaMap.put("now_y", nalja[0]);
      naljaMap.put("now_m", nalja[1]);
      naljaMap.put("now_d", nalja[2]);
      
      String serverInfo[] = util.getServerInfo(request);  // request.getContextPath();
      String refer = serverInfo[0];
      String path = serverInfo[1];
      String url = serverInfo[2];
      String uri = serverInfo[3];
      String ip = serverInfo[4];
      //String ip6 = serverInfo[5];
      
      BoardDAO dao = new BoardDAO();
      BoardDTO dto = new BoardDTO();
      
      
      String temp;
      
      temp = request.getParameter("pageNumber");
      int pageNumber = util.numberCheck(temp, 1);
      
      
      temp = request.getParameter("commentPageNumber");
      System.out.println(temp+"코멘트페이지넘버");
      int commentPageNumber  = util.numberCheck(temp, 1);
     
      
      
      temp = request.getParameter("tbl");
      String tbl = util.tblCheck(temp, "freeboard");
      System.out.println("tbl:"+tbl);
      int tblresult=dao.tblCheck(tbl);
      System.out.println("tblresult"+tblresult);
      if (tblresult==1) {
    	 
		
		
      }else {
    	  response.setContentType("text/html; charset=utf-8");
    	  PrintWriter out = response.getWriter();
  
		  out.println("<script>");
		  out.println("alert('잘못된 게시판 접근.');");
		  out.println("history.back();");
		  out.println("</script>");
		  out.flush();
		  out.close();
      }
      
      
      temp = request.getParameter("no");
      int no = util.numberCheck(temp, 0);
      
      String search_option = request.getParameter("search_option");
      String search_data = request.getParameter("search_data");
      String[] searchArray = util.searchCheck(search_option, search_data);
      search_option = searchArray[0];
      search_data = searchArray[1];
      
      String[] sessionArray = util.sessionCheck(request);
      int cookNo = Integer.parseInt(sessionArray[0]);
      String cookId = sessionArray[1];
      String cookName = sessionArray[2];
      
      request.setAttribute("naljaMap", naljaMap);
      request.setAttribute("ip", ip);
      request.setAttribute("tbl", tbl);
      request.setAttribute("pageNumber", pageNumber);
      request.setAttribute("commentPageNumber", commentPageNumber);
      //request.setAttribute("no", no);
      request.setAttribute("search_option", search_option);
      request.setAttribute("search_data", search_data);

    
      
      String page = "/main/main.jsp";      
      if (url.indexOf("index.do") != -1) {
         request.setAttribute("menu_gubun", "board_index");
         RequestDispatcher rd = request.getRequestDispatcher(page);
         rd.forward(request, response);
      } else if (url.indexOf("chuga.do") != -1 || url.indexOf("reply.do") != -1) {
         request.setAttribute("menu_gubun", "board_chuga");
         
         if (no>0) {//답변
        	 dto = dao.getView(no);
        	 
        	 temp+="["+dto.getWriter()+"]님이 작성한 글입니다.\n";
        	 temp+=dto.getContent();
        	 temp= temp.replace("\n", "\n> ");
        	 temp+="\n--------------------------\n";

        	 dto.setContent(temp);
        	 request.setAttribute("dto", dto);
        	 
         }
         
         page = "/board/chuga.jsp";
         RequestDispatcher rd = request.getRequestDispatcher(page);
         rd.forward(request, response);
      
      } else if (url.indexOf("chugaProc.do") != -1) {
         System.out.println("여기까지 왔음");
         String writer = request.getParameter("writer");
         String email = request.getParameter("email");
         String passwd = request.getParameter("passwd");
         String subject = request.getParameter("subject");
         String content = request.getParameter("content");
         String noticeGubun = request.getParameter("noticeGubun");         
         
         int noticeNo;
         if (noticeGubun == null || noticeGubun.trim().equals("") || !noticeGubun.equals("T")) {
            noticeNo = 0;
         } else {
            noticeNo = dao.getMaxNoticeNo(tbl) + 1;
         }
         
         String secretGubun = request.getParameter("secretGubun");
         if (secretGubun == null || secretGubun.trim().equals("") || !secretGubun.equals("T")) {
            secretGubun = "F";
         } else {
            secretGubun = "T";
         }
         
         int num = dao.getMaxNum() + 1;
         int refNo = dao.getMaxRefNo() + 1; // 글 그룹을 의미 = 쿼리를 실행시켜서 가장 큰 ref 값을 가져온 후 +1을 해주면 됨.
         int stepNo = 1;
         int levelNo = 1;
         int parentNo = 0;
         
         if(no>0) {//답변글
        	 BoardDTO dto2 = dao.getView(no);
        	 dao.setUpdateReLevel(dto2);//답변글 .//부모글보다 큰 re_level의 값을 전부 1씩 증가시켜준다.
        	 refNo = dto2.getRefNo();
        	 stepNo =dto2.getStepNo() +1;
        	 levelNo = dto2.getLevelNo()+1;
        	 parentNo = dto2.getNo();
         }
         
         int hit = 0;
         
         dto.setNo(no);
         dto.setNum(num);
         dto.setTbl(tbl);
         dto.setWriter(writer);
         dto.setSubject(subject);
         dto.setContent(content);
         dto.setPasswd(passwd);
         dto.setEmail(email);
         
         dto.setRefNo(refNo);
         dto.setStepNo(stepNo);
         dto.setLevelNo(levelNo);
         dto.setParentNo(parentNo);
         dto.setHit(hit);
         dto.setIp(ip);
         
         dto.setMemberNo(cookNo);
         dto.setNoticeNo(noticeNo);
         dto.setSecretGubun(secretGubun);
         
         int result = dao.setInsert(dto);
      
         
         
      }else if (url.indexOf("list.do") != -1) {
    	  int pageSize=5;
    	  int blockSize=10;
    	  int totalRecord = dao.getTotalRecord(tbl, search_option, search_data);
    	  int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
    	  int jj = pagerArray[0];
    	  int startRecord = pagerArray[1];
    	  int lastRecord = pagerArray[2];
    	  int totalPage = pagerArray[3];
    	  int startPage = pagerArray[4];
    	  int lastPage = pagerArray[5];
    	  
    	  List<BoardDTO> list =dao.getList(startRecord,lastRecord,tbl,search_option, search_data);
    	  
    	  request.setAttribute("menu_gubun", "board_list");
    	  request.setAttribute("list",list);
    	 
    	  //request.setAttribute("pageNumber",pageNumber);
    	  request.setAttribute("pageSize",pageSize);
    	  request.setAttribute("blockSize",blockSize);
    	  request.setAttribute("totalRecord",totalRecord);
    	  request.setAttribute("jj",jj);
     	 
    	  
    	  request.setAttribute("startRecord",startRecord);
    	  request.setAttribute("lastRecord",lastRecord);
     	 
    	  request.setAttribute("totalPage",totalPage);
    	  request.setAttribute("startPage",startPage);
    	  request.setAttribute("lastPage",lastPage);
     	 
    	  page="/board/list.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
    	  
      }else if (url.indexOf("view.do") != -1) {
    	  System.out.println("들어옴");

		if(cookNo == 0) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 이용하세요.')");
			temp = path+"/member_servlet/index.do?word=login";
			out.println("location.href='"+temp+"';");
			out.println("</script>");
		}else {
	    	  dao.setUpdatHit(no);
	    	  dto = dao.getView(no);
	    	  
	    	  String imsiPage = "viewPage";
	    	  if(dto.getSecretGubun().equals("T")) {//비밀글이면
	    		  String view_passwd = util.nullCheck(request.getParameter("view_passwd"));
	    		  if(dto.getPasswd().equals(view_passwd) && !dto.getPasswd().equals("")) {
	    			  
	    		  }else {
	    			  imsiPage = "viewPasswdPage";
	    		  }
	    	  }

    	  request.setAttribute("menu_gubun", "board_view");
    	  request.setAttribute("dto", dto);
    	  request.setAttribute("imsiPage", imsiPage);

    	  page="/board/view.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
		} 
      }else if (url.indexOf("commentSave.do") != -1) {
          System.out.println("commentSave 왔음");
          String board_no_ = request.getParameter("no");
          int board_no = Integer.parseInt(board_no_);
          
          String writer = request.getParameter("writer");
          String content = request.getParameter("content");
          String passwd = request.getParameter("passwd");
          System.out.println(board_no);
          System.out.println(writer);
          System.out.println(content);
          System.out.println(passwd+"4개값찍어야 정상");
          
          CommentDTO commentDto = new CommentDTO();
          
          commentDto.setBoard_no(board_no);
          commentDto.setWriter(writer);
          commentDto.setContent(content);
          commentDto.setPasswd(passwd);
          commentDto.setMemberNo(cookNo);
          commentDto.setIp(ip);
          
          int result = dao.setCommmentInsert(commentDto);
       
       }else if (url.indexOf("commentSujung.do") != -1) {
    	   System.out.println("commentSujung 왔음");
           
           dto = dao.getView(no);
           String comment_no_ = request.getParameter("commentNo");
           int comment_no = Integer.parseInt(comment_no_);

           CommentDTO commentDto= dao.getCommmentView(comment_no);
           request.setAttribute("commentDto", commentDto);
           
           
           
           
     	  request.setAttribute("menu_gubun", "board_view");
     	  request.setAttribute("dto", dto);

     	  page="/board/view.jsp";
     	  RequestDispatcher rd = request.getRequestDispatcher(page);
     	  rd.forward(request, response);
     	  
           
        }else if (url.indexOf("commentSakjaeProc.do") != -1) {
            System.out.println("commentSakjaeProc 왔음");
            String comment_no_ = request.getParameter("commentNo");
            int comment_no = Integer.parseInt(comment_no_);
            
            System.out.println(comment_no);
            
            int result = dao.setCommmentDelete(comment_no);
            PrintWriter out = response.getWriter();
         	  if(result>0) {
        		  out.println("<script>$('#span_passwd').text('T');</script>");
        	  }else {
        		  out.println("<script>$('#span_passwd').text('F');</script>"); 
        	  }
        	  out.flush();
        	  out.close();
            
            
         }else if (url.indexOf("commentmodifyProc.do") != -1) {
             System.out.println("commentmodifyProc 왔음");
             String comment_no_ = request.getParameter("commentNo");
             int comment_no = Integer.parseInt(comment_no_);
                     
             String board_no_ = request.getParameter("no");
             int board_no = Integer.parseInt(board_no_);
             
             String writer = request.getParameter("writer");
             String content = request.getParameter("content");
             String passwd = request.getParameter("passwd");
             
             System.out.println("comment_no"+comment_no);
             System.out.println(board_no);
             System.out.println(writer);
             System.out.println(content);
             System.out.println(passwd+"5개값찍어야 정상");
             System.out.println(ip);
   
             
             CommentDTO commentDto = new CommentDTO();
             commentDto.setComment_no(comment_no);
             commentDto.setBoard_no(board_no);
             commentDto.setWriter(writer);
             commentDto.setContent(content);
             commentDto.setPasswd(passwd);
             commentDto.setIp(ip);
             
             
             
             int result = dao.setCommmentUpdate(commentDto);
             PrintWriter out = response.getWriter();
          	  if(result>0) {
        		  out.println("<script>$('#span_passwd').text('T');</script>");
        	  }else {
        		  out.println("<script>$('#span_passwd').text('F');</script>"); 
        	  }
        	  out.flush();
        	  out.close();
             
             
          }else if (url.indexOf("sujung.do") != -1) {
    	  request.setAttribute("menu_gubun", "board_sujung");
    	  
    	  dto = dao.getView(no);
    	  request.setAttribute("dto", dto);
    	  
    	  page="/board/sujung.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
      }else if (url.indexOf("sujungProc.do") != -1) {
    	  System.out.println("sujungProc까지 왔음");
    	
          String writer = request.getParameter("writer");
          String email = request.getParameter("email");
          String dbPasswd = request.getParameter("passwd");
          String subject = request.getParameter("subject");
          System.out.println(subject);
          
          
          
          String content = request.getParameter("content");
          String noticeGubun = request.getParameter("noticeGubun");   
          
          int noticeNo;
          if (noticeGubun == null || noticeGubun.trim().equals("") || !noticeGubun.equals("T")) {
             noticeNo = 0;
          } else {
             noticeNo = dao.getMaxNoticeNo(tbl) + 1;
          }
          
          String secretGubun = request.getParameter("secretGubun");
          if (secretGubun == null || secretGubun.trim().equals("") || !secretGubun.equals("T")) {
             secretGubun = "F";
          } else {
             secretGubun = "T";
          }
          
          dto.setNo(no);
          dto.setWriter(writer);
          dto.setSubject(subject);
          dto.setContent(content);
         
          dto.setEmail(email);
          dto.setMemberNo(cookNo);
          dto.setNoticeNo(noticeNo);
          dto.setSecretGubun(secretGubun);
          
          BoardDTO chkDto = dao.getView(no);
          String passwd = chkDto.getPasswd();
          
          PrintWriter out = response.getWriter();
    	  if(passwd.equals(dbPasswd)) {
    		  int result = dao.setUpdate(dto);
    		  out.println("<script>$('#span_passwd').text('T');</script>");
    	  }else {
    		  out.println("<script>$('#span_passwd').text('F');</script>"); 
    	  }
    	  out.flush();
    	  out.close();
          
    	  
      }else if (url.indexOf("sakje.do") != -1) {
    	  request.setAttribute("menu_gubun", "board_sakje");
    	  
    	  dto = dao.getView(no);
    	  request.setAttribute("dto", dto);
    	  
    	  page="/board/sakje.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
      
      }else if (url.indexOf("sakjeProc.do") != -1) {
    	  String dbPasswd = request.getParameter("passwd");
		  response.setContentType("text/html; charset=utf-8");
		  PrintWriter out = response.getWriter();
		  BoardDTO dto3 = dao.getView(no);
		  //System.out.println(dbPasswd);
		  //System.out.println(dto3.getPasswd());
		  
		  if(!dbPasswd.equals(dto3.getPasswd())) {
				  out.println("<script>");
				  out.println("alert('비밀번호가 다릅니다.');");
				  out.println("GoPage('view','"+no+"');");
				  out.println("</script>");
		  
		
		  }else {	  
			  dao.setDelete(dto3);
			  
			  out.println("<script>");
			  out.println("alert('삭제성공.');");
			  out.println("GoPage('list','');");
			  out.println("</script>");
			  		  
			  
		  }
		  out.flush();
		  out.close();
      }else if (url.indexOf("commentList.do") != -1) {
    	  System.out.println("코멘트 리스트 두 들어옴");
    	  temp=request.getParameter("commentPageNumber");
    	  commentPageNumber = util.numberCheck(temp, 1);
    	  
    	  temp = request.getParameter("commentNo");
    	  
    	  
    	  if(temp!=null) {
    		 int commentNo  = Integer.parseInt(temp);
    		 CommentDTO commentDto = dao.getCommmentView(commentNo);
    		 request.setAttribute("commentDto",commentDto);
    	  }
    	  
    	  
    	  
    	  
    	  
    	  int pageSize=5;
    	  int blockSize=5;
    	  int totalRecord = dao.commentTotalRecord(no);
    	  int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, commentPageNumber);
    	  int jj = pagerArray[0];
    	  int startRecord = pagerArray[1];
    	  int lastRecord = pagerArray[2];
    	  int totalPage = pagerArray[3];
    	  int startPage = pagerArray[4];
    	  int lastPage = pagerArray[5];
    	  
    	  List<CommentDTO> commentList =dao.getCommentList(no, startRecord, lastRecord);
    	  
    	  request.setAttribute("menu_gubun", "board_comment_list");
    	  request.setAttribute("list",commentList);
    	 
    	  request.setAttribute("commentPageNumber",commentPageNumber );
    	  request.setAttribute("pageSize",pageSize);
    	  request.setAttribute("blockSize",blockSize);
    	  request.setAttribute("totalRecord",totalRecord);
    	  request.setAttribute("jj",jj);
     	 
    	  
    	  request.setAttribute("startRecord",startRecord);
    	  request.setAttribute("lastRecord",lastRecord);
     	 
    	  request.setAttribute("totalPage",totalPage);
    	  request.setAttribute("startPage",startPage);
    	  request.setAttribute("lastPage",lastPage);
    	  
    	  request.setAttribute("cookName", cookName);
    	  
    	  page="/board/comment_list.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
      
    	  
      }
      
      
      
   }

}