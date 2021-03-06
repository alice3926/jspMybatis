package shop.product.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import oracle.net.aso.e;
import shop.common.Constants;
import shop.common.UtilProduct;
import shop.product.model.dao.ProductDAO;
import shop.product.model.dto.ProductDTO;


@WebServlet("/product_servlet/*")
public class ProductController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProc(request, response);
   }


   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProc(request, response);
   }
   
   protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");
      
      UtilProduct util = new UtilProduct();
      
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
      
    
      
      String temp;
      
      temp = request.getParameter("pageNumber");
      int pageNumber = util.numberCheck(temp, 1);
      
      
      
      temp = request.getParameter("no");
      int no = util.numberCheck(temp, 0);
      
      ProductDAO dao = new ProductDAO();
      ProductDTO dto = new ProductDTO();
      
      String search_option = request.getParameter("search_option");
      String search_data = request.getParameter("search_data");
      System.out.println(search_option+"+"+search_data);
      System.out.println(search_option+"+"+search_data);
      
      String[] searchArray = util.searchCheck(search_option, search_data);
      search_option = searchArray[0];
      search_data = searchArray[1];
      
      String[] sessionArray = util.sessionCheck(request);
      int cookNo = Integer.parseInt(sessionArray[0]);
      String cookId = sessionArray[1];
      String cookName = sessionArray[2];
      
      request.setAttribute("naljaMap", naljaMap);
      request.setAttribute("ip", ip);
      request.setAttribute("pageNumber", pageNumber);
      //request.setAttribute("no", no);
      request.setAttribute("search_option", search_option);
      request.setAttribute("search_data", search_data);

    
      
      String page = "/main/main.jsp";      
      if (url.indexOf("index.do") != -1) {
    	  if(cookNo>0) {
	         request.setAttribute("menu_gubun", "product_index");
	         RequestDispatcher rd = request.getRequestDispatcher(page);
	         rd.forward(request, response);
    	  }else {
			System.out.println("????????? ????????????");
			url=path+"/member_servlet/index.do?word=login";
			PrintWriter out = response.getWriter();
	    	out.println("<script>alert('????????? ????????????');location.href='"+ url+"';</script>");
	    	out.flush();
	    	out.close();
    	  }
	         
      } else if (url.indexOf("chuga.do") != -1) {
    	 System.out.println("????????? ?????????");
    	 
         request.setAttribute("menu_gubun", "product_chuga");
         
         page = "/shop/product/chuga.jsp";
         RequestDispatcher rd = request.getRequestDispatcher(page);
         rd.forward(request, response);
      
//      } else if (url.indexOf("chugaProc.do") != -1) {
//         System.out.println("???????????? ??????");
//         
//         String img_path01 = request.getSession().getServletContext().getRealPath("/attach/product_img/");
//         String img_path02 = img_path01.replace("\\", "/");
//         String img_path03 = img_path01.replace("\\", "\\\\");
//         
//         System.out.println("img_path01:"+img_path01);
////         System.out.println("img_path02:"+img_path02);
////         System.out.println("img_path03:"+img_path03);
//         int max_size=10*1024*1024; //10MB, ????????? ?????? ??????
//         
//    //?????? ????????? ??????     
////         MultipartRequest multi = new MultipartRequest(request, img_path03,
////        		 Constants.MAX_UPLOAD,"utf-8", new DefaultFileRenamePolicy());
////        
//         MultipartRequest multi = new MultipartRequest(request, img_path03,
//        		 max_size,"utf-8", new DefaultFileRenamePolicy());
//         //?????? ????????? ????????? ????????? ?????????
//         
//         String name = multi.getParameter("name");
//         String price_ = multi.getParameter("price");
//         int price=Integer.parseInt(price_);
//         String description = multi.getParameter("description");
//         //String product_img = request.getParameter("subject");
//
////         System.out.println("name:"+name);
////         System.out.println("price:"+price);
////         System.out.println("description:"+description);
//         
//         String[] array = new String[3];
//         for (int i=0; i<array.length; i++) {
//        	 array[i]="-";
//         }
//         Enumeration files = multi.getFileNames();
//         while(files.hasMoreElements()) {
//        	 String formName = (String) files.nextElement();
//        	 String filename = multi.getFilesystemName(formName);
//
////        	 String fileOrgName=multi.getOriginalFileName(formName);
////        	 String fileType=multi.getContentType(formName);
//        	 
//        	 //System.out.println("formName:"+formName);
//        	 //System.out.println("filename:"+filename);
////        	 System.out.println(formName+":"+fileName);
////        	 System.out.println(fileOrgName+":"+fileType);
//        	 
//        	 int k = Integer.parseInt(formName);
//        	 array[k] = filename;
//        	 //System.out.println("kk"+array[k]);
//         }	 
//         	String filename;
//        	 java.io.File f1;
// 	 String str = "";
//    for (int i=0; i<array.length; i++) {
//        		 filename=array[i];
//        		 if (filename== null) {//???????????? ???????????? null??? ??????
//        			 filename="-";
//        			 continue;
//        		 }
//        		 
//        		 String old_path = img_path03+filename;
//        		 f1 = new java.io.File(old_path);
//        		 if(!f1.exists()) {//?????? ????????? ????????? ???????????? ?????? ??????.
//        			 continue;
//        		 }
//        		//????????? ?????????
//        		 int point_index = filename.lastIndexOf(".");
//            	 if (point_index==-1) {
//            		 f1.delete(); //?????? ????????? ?????? ??????
//            		 System.out.println("?????? ????????? ?????? ??????");
//            		 array[i] = "-";
//            		 continue;
//            	 }
//            	 String ext="";
//            	 ext = filename.substring(point_index+1).toLowerCase();
//            	 System.out.println("ext:" + ext);
//            	 if (!(ext.equals("jpg")||ext.equals("jpeg")||ext.equals("gif")||ext.equals("png"))) {
//            		 f1.delete(); //?????? ????????? ?????? ??????
//          		 System.out.println("????????? ?????? ????????? ?????? ??????");
//          		 array[i] = "-";
//          		 continue;
//            	 }     
//            	 
//               	 String uuid=util.create_uuid();
//              	 System.out.println("uuid:"+uuid);
//              	 String new_filename = util.getDateTimeType()+"_"+uuid+"."+ext;
//              	 //String new_filename = util.todayTime()+"_"+uuid+"."+ext;
//              	 //System.out.println("new_filename:"+new_filename);
//            	java.io.File newFile = new java.io.File(img_path03+new_filename);
//            	f1.renameTo(newFile); //????????????
//            	array[i] = array[i]+"|"+new_filename;
//        	
//	        	 
//	        	 for (int j=0; j<array.length; j++) {
//	        		 str+=","+array[j];
//	        	 }
//	        	 System.out.println(str);//mybatis??? ""??? ?????? ???????????? ????????? -??? ???????????? ????????? 
//	        	 str=str.substring(1);
//	        	 System.out.println(str);
//    }     	 
////        	 temp="";
////        	 for (int i=0; i<array.length; i++) {
////        		 //System.out.println("array["+i+"]"+array[i]);
////        		 String imsi= array[i];
////        		 if (imsi==null) {
////        			 imsi="-";
////        		 }
////        		temp+=","+imsi;
////        		
////        	 }
////        	 System.out.println(temp);
////        	 temp=temp.substring(1);
////        	 System.out.println(temp);
//
//        	 dto.setName(name);
//             dto.setPrice(price);
//             dto.setDescription(description);
//        	 //dto.setProduct_img(temp);
//        	 dto.setProduct_img(str);
//    	 
//        	 int result = dao.setInsert(dto);
//             System.out.println("product chuga result:"+result);
//        
     }else if (url.indexOf("list.do") != -1) {
    	  int pageSize=10;
    	  int blockSize=10;
     	  int totalRecord = dao.getTotalRecord(search_option, search_data);
    	  int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
    	  int jj = pagerArray[0];
    	  int startRecord = pagerArray[1];
    	  int lastRecord = pagerArray[2];
    	  int totalPage = pagerArray[3];
    	  int startPage = pagerArray[4];
    	  int lastPage = pagerArray[5];
    	  
    	  List<ProductDTO> list =dao.getList(startRecord,lastRecord,search_option, search_data);
    	  
    	  
    	  request.setAttribute("menu_gubun", "product_list");
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
     	 
    	  page="/shop/product/list.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
    	  
      }else if (url.indexOf("view.do") != -1) {
    	  System.out.println("?????????");
    	  dto = dao.getView(no);
    	  
    
    	  request.setAttribute("menu_gubun", "product_view");
    	  request.setAttribute("dto", dto);

    	  page="/shop/product/view.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
    	  
    	  
      }else if (url.indexOf("sujung.do") != -1) {
    	  System.out.println("????????? ?????????");
    	  request.setAttribute("menu_gubun", "product_sujung");
    	  
    	  dto = dao.getView(no);
    	  request.setAttribute("dto", dto);
    	  
    	  page="/shop/product/sujung.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
     
      }else if (url.indexOf("chugaProc.do") != -1 || url.indexOf("sujungProc.do") != -1) {
    	  System.out.println("Proc?????? ??????");
          
          String img_path01 = request.getSession().getServletContext().getRealPath("/attach/product_img/");
          //???????????? ????????? ????????? ????????? ?????? ???
          java.io.File isDir = new java.io.File(img_path01);
          if(!isDir.isDirectory()) {
        	  System.out.println("??????????????? ???????????? ????????????. ??????????????? ???????????????.");
        	  isDir.mkdir();
          }
          
          
          String img_path02 = img_path01.replace("\\", "/");
          String img_path03 = img_path01.replace("\\", "\\\\");
          
          System.out.println("img_path01:"+img_path01);
          int max_size=10*1024*1024; //10MB, ????????? ?????? ?????? ??????
          
     //?????? ????????? ??????     
          MultipartRequest multi = new MultipartRequest(request, img_path03,
         		 max_size,"utf-8", new DefaultFileRenamePolicy());
          //?????? ????????? ????????? ????????? ?????????

     	  int arrayCounter=3;
          String[] array = new String[arrayCounter];
          for (int i=0; i<array.length; i++) {
         	 array[i]="-";
          }
          
          Enumeration files = multi.getFileNames();
          while(files.hasMoreElements()) {//??????????????? ?????????
         	 String formName = (String) files.nextElement();//?????? ??????
         	 String filename = multi.getFilesystemName(formName);//????????????
         	 String fileType= multi.getContentType(formName); //????????????
         	 if(filename ==null ||filename.trim().equals("")) {
         		filename="-"; 
         	 }
         	 int k = Integer.parseInt(formName);
         	 array[k] = filename;
             //System.out.println(array[k]);
          }
//          System.out.println(array[0]);
//          System.out.println(array[1]);
//          System.out.println(array[2]);
	 String str = "";
     for (int i=0; i<array.length; i++) {
         		 temp=array[i];
         		 if (temp.equals("-")) {//???????????? ???????????? -??? ??????
         			 continue;
         		 }
         		 
         		 String old_path = img_path03+temp; //????????? ???????????? ?????? ????????? ???????????? ?????????.
         		 java.io.File f1 = new java.io.File(old_path);
         		 if(!f1.exists()) {//?????? ????????? ????????? ???????????? ?????? ??????.
         			 array[i]="-"; //????????? ???????????? ????????? ?????? -??? ?????????
         			 continue;
         		 }
         		//????????? ?????????
         		 int point_index = temp.lastIndexOf(".");
             	 if (point_index==-1) {//-1??? .??? ????????? ?????? ???????????? ?????? ??????.
             		 f1.delete(); //?????? ????????? ?????? ??? ???????????? ??????
             		 array[i] = "-";
             		 continue;
             	 }
             	 String ext="";
             	 ext = temp.substring(point_index+1).toLowerCase(); //+1??? ????????? .??? ??????????????? .jpf .jpeg .gif .png
             	 System.out.println("ext:" + ext);
             	 if (!(ext.equals("jpg")||ext.equals("jpeg")||ext.equals("gif")||ext.equals("png"))) {
             		 f1.delete(); //?????? ????????? ?????? ??????
	           		 //System.out.println("????????? ?????? ????????? ?????? ??????");
	           		 array[i] = "-";
	           		 continue;
             	 }     
             	 
                 String uuid=util.create_uuid(); //?????????????
               	 //System.out.println("uuid:"+uuid);
               	 String new_filename = util.getDateTimeType()+"_"+uuid+"."+ext; //????????? ????????? ??????????????? ????????? ?????????.
               	 //String new_filename = util.todayTime()+"_"+uuid+"."+ext;
               	 //System.out.println("new_filename:"+new_filename);
             	java.io.File newFile = new java.io.File(img_path03+new_filename);
             	f1.renameTo(newFile); //????????????
             	array[i] = array[i]+"|"+new_filename;
         	
 	        	 
 	        	 for (int j=0; j<array.length; j++) {
 	        		 str+=","+array[j];
 	        	 }
 	        	 //System.out.println(str);//mybatis??? ""??? ?????? ???????????? ????????? -??? ???????????? ????????? 
 	        	 str=str.substring(1); //?????? ?????? ????????????
 	        	 System.out.println(str);
 	         
     }
   temp = multi.getParameter("no");
   no=util.numberCheck(temp, 0);
   String name = multi.getParameter("name");
   String price_ = multi.getParameter("price");
   int price=Integer.parseInt(price_);
   String description = multi.getParameter("description");      	 

   
	dto.setNo(no);
	dto.setName(name);
	dto.setPrice(price);
	dto.setDescription(description);
	dto.setMemberNo(cookNo);
	//---------------------??????????????? ?????? ?????? ??????
	int result;
	if(url.indexOf("chugaProc.do")!=-1) {
		request.setAttribute("menu_gubun", "product_chuagaProc");
		dto.setProduct_img(str);
		result = dao.setInsert(dto);
	}else {
		request.setAttribute("menu_gubun", "product_sujungProc");
		ProductDTO dto2 = dao.getView(no);
		String db_product_img = dto2.getProduct_img();
		String deleteFileName="";
			if(str.trim().equals("-,-,-")) {//??????????????? ?????? ??????
				dto.setProduct_img(db_product_img);
				
			}else {//??????????????? ????????????, ?????? ??????, ?????????
				temp="";
				String[] dbArray = db_product_img.split(",");
				for (int i=0; i<array.length; i++) {
					if(array[i].equals("-")) {
						temp +=","+dbArray[i];
					}else {
						temp+=","+array[i];
						deleteFileName+=","+
						dbArray[i].substring(dbArray[i].lastIndexOf("|")+1);//?????? ???????????? ???????????? ?????????
					}
				}
			deleteFileName=deleteFileName.substring(1);
			System.out.println("deleteFileName:"+deleteFileName);
			temp=temp.substring(1);
			System.out.println("temp:"+temp);
			dto.setProduct_img(temp);
		}
		result=dao.setUpdate(dto);
		
		//???????????? ???????????? ?????? ?????????
		String[] arrayDelete = deleteFileName.split(",");
		for(int i=0; i<arrayDelete.length; i++) {
				if(!arrayDelete[i].trim().equals("-")) {
					java.io.File f1 = new java.io.File(img_path03+arrayDelete[i]);
					f1.delete();
				}
		}
	}	


      
      
      
      
      
      
      //else if (url.indexOf("commentProc.do") != -1) {
//          System.out.println("commentProc ??????");
//          String board_no_ = request.getParameter("no");
//          int board_no = Integer.parseInt(board_no_);
//          
//          String writer = request.getParameter("writer");
//          String content = request.getParameter("content");
//          String passwd = request.getParameter("passwd");
//          System.out.println(board_no);
//          System.out.println(writer);
//          System.out.println(content);
//          System.out.println(passwd+"4??????????????? ??????");
//          
//          
//          
//          CommentDTO commentDto = new CommentDTO();
//          
//          commentDto.setBoard_no(board_no);
//          commentDto.setWriter(writer);
//          commentDto.setContent(content);
//          commentDto.setPasswd(passwd);
//          commentDto.setMemberNo(cookNo);
//          commentDto.setIp(ip);
//          
//          int result = dao.setCommmentInsert(commentDto);
//       
//          

//          
//    	  
      }else if (url.indexOf("sakje.do") != -1) {
    	  request.setAttribute("menu_gubun", "product_sakje");
    	  
    	  dto = dao.getView(no);
    	  request.setAttribute("dto", dto);
    	  
    	  page="/shop/product/sakje.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
      
      }else if (url.indexOf("sakjeProc.do") != -1) {
    	  System.out.println("?????????????????????"+no);
		  response.setContentType("text/html; charset=utf-8");
		  PrintWriter out = response.getWriter();
		 
		  ProductDTO dto3 = dao.getView(no);
		  //System.out.println(dbPasswd);
		  //System.out.println(dto3.getPasswd());
	  
		  int result = dao.setDelete(dto3);
		  System.out.println("?????? result???::"+result);
		  String deleteFileName="";
		  
		  if(result>0) {
			  		System.out.println("??????????????? ?????? ?????????");
			  		temp="";
					String[] dbArray = dto3.getProduct_img().split(",");
					for (int i=0; i<dbArray.length; i++) {
						if(dbArray[i].equals("-")) {
							continue;
						}else {
							deleteFileName+=","+dbArray[i].substring(dbArray[i].lastIndexOf("|")+1);//?????? ???????????? ???????????? ?????????
						}
					}
				deleteFileName=deleteFileName.substring(1);
				System.out.println("deleteFileName:"+deleteFileName);
				
				String img_path01 = request.getSession().getServletContext().getRealPath("/attach/product_img/");
		        String img_path03 = img_path01.replace("\\", "\\\\");
				
		        //???????????? ???????????? ?????? ?????????
		        if(deleteFileName.contains(",")) {
					String[] arrayDelete = deleteFileName.split(",");
					for(int i=0; i<arrayDelete.length; i++) {
							if(!arrayDelete[i].trim().equals("-")) {
								java.io.File f1 = new java.io.File(img_path03+arrayDelete[i]);
								f1.delete();
							}
					}
		        }else {
		        	java.io.File f1 = new java.io.File(img_path03+deleteFileName);
					f1.delete();
		        }
		        out.println("<script>$('#span_result').text('T');</script>");
		
		  }else {
			  out.println("<script>$('#span_result').text('F');</script>");
		  }
		  out.flush();
		  out.close();
      }  
//      }else if (url.indexOf("commentList.do") != -1) {
//    	  System.out.println("????????? ????????? ??? ?????????");
//    	  temp=request.getParameter("commentPageNumber");
//    	  commentPageNumber = util.numberCheck(temp, 1);
//    	  
//    	  int pageSize=5;
//    	  int blockSize=5;
//    	  int totalRecord = dao.commentTotalRecord(no);
//    	  int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, commentPageNumber);
//    	  int jj = pagerArray[0];
//    	  int startRecord = pagerArray[1];
//    	  int lastRecord = pagerArray[2];
//    	  int totalPage = pagerArray[3];
//    	  int startPage = pagerArray[4];
//    	  int lastPage = pagerArray[5];
//    	  
//    	  
//    	  
//    	  ArrayList<CommentDTO> commentList =dao.getCommentList(no, startRecord, lastRecord);
//    	  
//    	  request.setAttribute("menu_gubun", "board_comment_list");
//    	  request.setAttribute("list",commentList);
//    	 
//    	  request.setAttribute("commentPageNumber",commentPageNumber );
//    	  request.setAttribute("pageSize",pageSize);
//    	  request.setAttribute("blockSize",blockSize);
//    	  request.setAttribute("totalRecord",totalRecord);
//    	  request.setAttribute("jj",jj);
//     	 
//    	  
//    	  request.setAttribute("startRecord",startRecord);
//    	  request.setAttribute("lastRecord",lastRecord);
//     	 
//    	  request.setAttribute("totalPage",totalPage);
//    	  request.setAttribute("startPage",startPage);
//    	  request.setAttribute("lastPage",lastPage);
//    	  
//    	  request.setAttribute("cookName", cookName);
//    	  
//    	  page="/board/comment_list.jsp";
//    	  RequestDispatcher rd = request.getRequestDispatcher(page);
//    	  rd.forward(request, response);
//      
//    	  
//      }
//      
//      
      
  }

}