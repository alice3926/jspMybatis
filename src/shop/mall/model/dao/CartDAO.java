package shop.mall.model.dao;

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
import shop.mall.model.dto.CartDTO;
import sqlmap.MybatisManager;

public class CartDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="cart";
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	  public int setInsert(CartDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.insert("mall.setInsert",map);
		 session.commit();
		 session.close();
		 return result;
	   }
	  
	  public List<CartDTO> getList(int startRecord,int lastRecord){
		  Map<String,Object> map = new HashMap<>();
			 map.put("startRecord", startRecord);
			 map.put("lastRecord", lastRecord);
			 
			 SqlSession session = MybatisManager.getInstance().openSession();
			 List<CartDTO> list = session.selectList("mall.getList",map);
			 session.close();
			 return list;
		}
	  public int getTotalRecord() {
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.selectOne("mall.getTotalRecord");
			session.close();
			return result;
		}
	  
	  public void setDeleteBatch(String[] array ) {
		  	Map<String, Object> map = new HashMap<>();
		  	map.put("array", array);
		  	
		  	SqlSession session = MybatisManager.getInstance().openSession();
		  	session.delete("mall.setDeleteBatch",map);
		  	session.commit();
		  	session.close();
			
	  }
	  public List<CartDTO> getListCartProductGroup(){
		  SqlSession session = MybatisManager.getInstance().openSession();
		  List<CartDTO> list = session.selectList("mall.getListCartProductGroup");//네임스페이스...
		  session.close();
		  return list;
	  }
	  
	  public int setSujung(int no, int jumunsu) {
		  Map<String, Object> map = new HashMap<>();
		  map.put("no", no);
		  map.put("jumunsu", jumunsu);
		  
		  SqlSession session = MybatisManager.getInstance().openSession();
		  int result = session.update("mall.setSujung",map);
		  session.commit();
		  session.close();
		  return result;
		}
	  
}