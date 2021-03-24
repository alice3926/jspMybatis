package memo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import db.DbExample;
import memo.model.dto.MemoDTO;
import sqlmap.MybatisManager;

public class MemoDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String table_1 = "memo";
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	public int setInsert(MemoDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.insert("memo.setInsert",map);
		 session.commit();
		 session.close();
		 return result;
	}
	
	public List<MemoDTO> getList(int startRow,int endRow) {
		 Map<String,String> map = new HashMap<>();
		 map.put("startRecord", startRow+"");
		 map.put("lastRecord", endRow+"");
		 map.put("table_1", table_1);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 List<MemoDTO> list = session.selectList("memo.getList",map);
		 session.close();
		 return list;
		
	}
	
	public int getTotalRecord() {
		Map<String,String> map = new HashMap<>();
		map.put("table_1", table_1);
		 
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.selectOne("memo.getTotalRecord",map);
		session.close();
		return result;
	}
	
	public MemoDTO getOne(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		MemoDTO dto2 = session.selectOne("memo.getOne",map);
		session.close();
		return dto2;
		
	}
	public int setSujung(MemoDTO dto2) {
		Map<String,Object> map = new HashMap<>();
		map.put("dto", dto2);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.update("memo.setSujung",map);
		session.commit();
		session.close();
		return result;
		
	}
	public int setSakjae(MemoDTO dto2) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto2);
		
		SqlSession session  = MybatisManager.getInstance().openSession();
		int result = session.delete("memo.setSakjae",map);
		session.commit();
		session.close();
		return result;
		
	}
	
	
}	
