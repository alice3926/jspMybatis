package shop.mall.controller;

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
import shop.mall.model.dao.CartDAO;
import shop.mall.model.dto.CartDTO;
import shop.product.model.dao.ProductDAO;
import shop.product.model.dto.ProductDTO;


@WebServlet("/mall_servlet/*")
public class MallController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProc(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doProc(request, response);
   }
   
   protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  request.setCharacterEncoding("UTF-8");
      
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
      //System.out.println(search_option+"+"+search_data);
      
      
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

      ProductDAO productDao = new ProductDAO();
      ProductDTO productDto = new ProductDTO();
      
      CartDAO cartDao = new CartDAO();
      CartDTO cartDto = new CartDTO();
      
      
      String page = "/main/main.jsp";      
      if (url.indexOf("index.do") != -1) {
         request.setAttribute("menu_gubun", "mall_index");
         RequestDispatcher rd = request.getRequestDispatcher(page);
         rd.forward(request, response);
         
      }else if (url.contains("mall_list.do")) {
    	  int pageSize=12; //페이지에 보여질 게시글 갯수
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
    	  
    	  request.setAttribute("menu_gubun", "mall_list");
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
     	 
    	  page="/shop/mall/mall_list.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
    	  
      }else if (url.contains("mall_view.do")) {
    	  System.out.println("들어옴");
    	  dto = dao.getView(no);
    	  
    
    	  request.setAttribute("menu_gubun", "mall_view");
    	  request.setAttribute("dto", dto);

    	  page="/shop/mall/mall_view.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
    	  
    	  
      }else if (url.indexOf("cart_chuga.do") != -1) {
    	System.out.println("cart_chuga까지 왔음");
    	
    	temp = request.getParameter("buy_counter");
    	int jumun_su = util.numberCheck(temp, 0);
    	
    	cartDto.setProductNo(no);
    	cartDto.setAmount(jumun_su);
    	cartDto.setMemberNo(cookNo);
    	
		int result = cartDao.setInsert(cartDto);
      
      }else if (url.contains("cart_list.do")) {
    	  System.out.println("cart_list.do 들어옴");
    	  int pageSize=5; //페이지에 보여질 게시글 갯수
    	  int blockSize=10;
     	  int totalRecord = cartDao.getTotalRecord();
    	  int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
    	  int jj = pagerArray[0];
    	  int startRecord = pagerArray[1];
    	  int lastRecord = pagerArray[2];
    	  int totalPage = pagerArray[3];
    	  int startPage = pagerArray[4];
    	  int lastPage = pagerArray[5];
    	  
    	  List<CartDTO> list =cartDao.getList(startRecord,lastRecord);
    	  
    	  request.setAttribute("menu_gubun", "cart_list");
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
     	 
    	  page="/shop/mall/cart_list.jsp";
    	  RequestDispatcher rd = request.getRequestDispatcher(page);
    	  rd.forward(request, response);
    	  
      }else if (url.contains("cart_sujung.do")) {
    	  System.out.println("cart_sujung두 들어옴");
    	  String jumunsu_ = request.getParameter("buy_counter");
    	  int jumunsu = Integer.parseInt(jumunsu_);
    	  System.out.println("no,jumunsu"+no+","+jumunsu);
    	  cartDao.setSujung(no,jumunsu);
    	  
      }else if (url.contains("cart_clear.do")) {
    	  temp=request.getParameter("chk_no");
    	  String[] array = temp.split(",");
    	  for(int i=0; i<array.length; i++) {
    		  System.out.println(array[i]);
    	  }
    	 
    	 
    	  cartDao.setDeleteBatch(array);
      }
 
  }

}