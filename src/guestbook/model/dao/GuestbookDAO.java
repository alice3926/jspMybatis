package guestbook.model.dao;

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
import guestbook.model.dto.GuestbookDTO;
import member.model.dto.MemberDTO;
import sqlmap.MybatisManager;

public class GuestbookDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="guestbook";
	
	
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	public int setInsert(GuestbookDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.insert("guestbook.setInsert",map);
		session.commit();
		session.close();
		return result;
	}
	
	
	public List<GuestbookDTO> getList(int startRow,int endRow,String search_option, String search_data){
		Map<String, Object> map = new HashMap<>();
		map.put("startRecord", startRow);
		map.put("lastRecord", endRow);
		map.put("search_option",search_option);
		map.put("search_data", search_data);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		List<GuestbookDTO> list = session.selectList("guestbook.getList", map);
		session.close();
		return list;
		
	}
	
	  public int getTotalRecord(String search_option, String search_data) {
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search_data", search_data);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.selectOne("guestbook.getTotalRecord",map);
		session.close();
		return result;
	
			
	}
	  public GuestbookDTO getOne(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		GuestbookDTO dto2 = session.selectOne("guestbook.getOne",map);
		session.close();
		return dto2;
		  
		  
		  
		  
	  }
			
		public int setSujung(GuestbookDTO dto2) {
			Map<String, Object> map = new HashMap<>();
			map.put("dto", dto2);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.update("guestbook.setSujung",map);
			session.commit();
			session.close();
			return result;
		}
			
		public int setSakjae(GuestbookDTO dto2) {
			Map<String, Object> map = new HashMap<>();
			map.put("dto",dto2);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.update("guestbook.setSakjae",map);
			session.commit();
			session.close();
			return result;
		}
	
}
