package shop.product.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import db.DbExample;
import memo.model.dto.MemoDTO;
import shop.product.model.dto.ProductDTO;
import sqlmap.MybatisManager;

public class ProductDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="product";
	
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	

	 
	  public int setInsert(ProductDTO dto) {
		  Map<String,Object> map = new HashMap<>();
		  map.put("dto", dto);
			 
		  SqlSession session = MybatisManager.getInstance().openSession();
		  int result = session.insert("product.setInsert",map);
		  session.commit();
		  session.close();
		  return result; 
	   }
	  
	  public int getTotalRecord(String search_option, String search_data) {
		  Map<String,String> map = new HashMap<>();
			map.put("search_option", search_option);
			map.put("search_data", search_data);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.selectOne("product.getTotalRecord",map);
			session.close();
			return result;		}
	  
	  public List<ProductDTO> getList(int startRecord,int lastRecord, String search_option, String search_data){
			 Map<String,Object> map = new HashMap<>();
			 map.put("startRecord", startRecord);
			 map.put("lastRecord", lastRecord);
			 map.put("search_option", search_option);
			 map.put("search_data", search_data);
			 
			 SqlSession session = MybatisManager.getInstance().openSession();
			 List<ProductDTO> list = session.selectList("product.getList",map);
			 session.close();
			 return list;
		}
		public ProductDTO getView(int no) {
			Map<String, Object> map = new HashMap<>();
			map.put("no", no);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			ProductDTO dto2 = session.selectOne("product.getView",map);
			session.close();
			return dto2;
		}
		


		public int setUpdate(ProductDTO dto) {
			Map<String,Object> map = new HashMap<>();
			map.put("dto", dto);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.update("product.setUpdate",map);
			session.commit();
			session.close();
			return result;
		}
		
		public int setDelete(ProductDTO dto2) {
			Map<String, Object> map = new HashMap<>();
			map.put("dto", dto2);
			
			SqlSession session  = MybatisManager.getInstance().openSession();
			int result = session.delete("product.setDelete",map);
			session.commit();
			session.close();
			return result;
		}
	
}
