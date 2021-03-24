package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import db.DbExample;
import member.model.dto.MemberDTO;
import sqlmap.MybatisManager;

public class MemberDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="member";
	String table_1 = "member";
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	public int getIdCheck(String id) {
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.selectOne("member.getIdCheck",id);
		 session.close();
		 return result;
	}
	public String getIdCheckWin(String id) {
		 SqlSession session = MybatisManager.getInstance().openSession();
		 String result = session.selectOne("member.getIdCheckWin",id);
		 session.close();
		 return result;
		
	}
	public int setInsert(MemberDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
			/*
			 * map.put("id", dto.getId()); map.put("passwd", dto.getPasswd());
			 * map.put("name", dto.getName()); map.put("gender", dto.getGender());
			 * map.put("bornYear", dto.getBornYear()); map.put("postcode",
			 * dto.getPostcode()); map.put("address", dto.getAddress());
			 * map.put("detailAddress", dto.getDetailAddress()); map.put("extraAddress",
			 * dto.getExtraAddress()); map.put("table_1", table_1);
			 */
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.insert("member.setInsert",map);
		 session.commit();
		 session.close();
		 return result;
	}
	
	public MemberDTO setlogin(MemberDTO dto) {
		Map<String,Object> map = new HashMap<>();
		 map.put("id", dto.getId());
		 map.put("passwd", dto.getPasswd());
		 map.put("table_1", table_1);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 MemberDTO dto1 = session.selectOne("member.setlogin",map);
		 //session.commit();
		 session.close();
		 return dto1;
	}	
	 public int getTotalRecord(String search_option, String search_data) {
		 Map<String,String> map = new HashMap<>();
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("table_1", table_1);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.selectOne("member.getTotalRecord",map);
		 session.close();
		 return result;
	 }
	  
	public List<MemberDTO> getList(int startRecord,int lastRecord, String search_option, String search_data){
		 Map<String,String> map = new HashMap<>();
		 map.put("startRecord", startRecord+"");
		 map.put("lastRecord", lastRecord+"");
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("table_1", table_1);
		 
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 List<MemberDTO> list = session.selectList("member.getList",map);
		 session.close();
		 return list;
	}
	
	
	public MemberDTO getOne(int no, String search_option, String search_data) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("no", no);
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("table_1", table_1);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 MemberDTO dto2 = session.selectOne("member.getOne",map);
		 session.close();
		 return dto2;
	}
	public int setSujung(MemberDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
			/*
			 * map.put("id", dto.getId()); map.put("passwd", dto.getPasswd());
			 * map.put("name", dto.getName()); map.put("gender", dto.getGender());
			 * map.put("bornYear", dto.getBornYear()); map.put("postcode",
			 * dto.getPostcode()); map.put("address", dto.getAddress());
			 * map.put("detailAddress", dto.getDetailAddress()); map.put("extraAddress",
			 * dto.getExtraAddress()); map.put("table_1", table_1);
			 */
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.update("member.setSujung",map);
		 session.commit();
		 session.close();
		 return result;
	}
	public int setSakjae(MemberDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
			/*
			 * map.put("id", dto.getId()); map.put("passwd", dto.getPasswd());
			 * map.put("table_1", table_1);
			 */
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.delete("member.setSakjae",map);
		 session.commit();
		 session.close();
		 return result;
	}
	
}
