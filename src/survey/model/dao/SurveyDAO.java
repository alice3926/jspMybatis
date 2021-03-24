package survey.model.dao;

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
import member.model.dto.MemberDTO;
import sqlmap.MybatisManager;
import survey.model.dto.SurveyAnswerDTO;
import survey.model.dto.SurveyDTO;

public class SurveyDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="survey";
	String tableName02="survey_answer";
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	
	public int setInsert(SurveyDTO dto) {
		System.out.println(dto.getQuestion());
		System.out.println(dto.getAns1());
		System.out.println(dto.getAns2());
		System.out.println(dto.getAns3());
		System.out.println(dto.getAns4());
		System.out.println(dto.getStatus());
		System.out.println(dto.getStart_date());
		System.out.println(dto.getLast_date());
		
		
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result  = session.insert("survey.setInsert", map);
		session.commit();
		session.close();
		return result;
	}
	
	 public List<SurveyDTO> getList(String list_gubun,int startRecord,int lastRecord, String search_option ,String search_data, String search_date_s,String search_date_e,String search_date_check) {
		 Map<String,String> map = new HashMap<>();
		 map.put("startRecord", startRecord+"");
		 map.put("lastRecord", lastRecord+"");
		 map.put("list_gubun", list_gubun);
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("search_date_s", search_date_s);
		 map.put("search_date_e", search_date_e);
		 map.put("search_date_e", search_date_check);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 List<SurveyDTO> list = session.selectList("survey.getList",map);
		 session.close();
		 return list;
	}
	   
	public int getTotalRecord(String list_gubun, String search_option ,String search_data ,String search_date_s, String search_date_e, String search_date_check) {
		 Map<String,String> map = new HashMap<>();
		 map.put("list_gubun", list_gubun);
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("search_date_s", search_date_s);
		 map.put("search_date_e", search_date_e);
		 map.put("search_date_e", search_date_check);
		 

		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.selectOne("survey.getTotalRecord",map);
		 session.close();
		 return result; 
	         
	   }
	
	
	
	public SurveyDTO getOne(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		SurveyDTO dto2 = session.selectOne("survey.getOne", map);
		session.commit();
		session.close();
		return dto2;
	}

	
	public int setInsertAnswer(SurveyAnswerDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result  = session.insert("survey.setInsertAnswer", map);
		session.commit();
		session.close();
		return result;
	}
	
	public int setSujung(SurveyDTO dto) {
		System.out.println(dto.getNo());
		System.out.println(dto.getQuestion());
		System.out.println(dto.getAns1());
		System.out.println(dto.getAns2());
		System.out.println(dto.getAns3());
		System.out.println(dto.getAns4());
		System.out.println(dto.getStatus());
		System.out.println(dto.getStart_date());
		System.out.println(dto.getLast_date());
		
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result  = session.update("survey.setSujung", map);
		session.commit();
		session.close();
		return result;
	}
	
	
	
	public int setSakjae(int no) {
		SqlSession session = MybatisManager.getInstance().openSession();
		int result = session.delete("survey.setSakjae",no);
		session.commit();
		session.close();
		return result;
	}
	
	
	
}
