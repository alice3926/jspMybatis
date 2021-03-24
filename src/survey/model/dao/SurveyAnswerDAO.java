package survey.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import db.DbExample;
import sqlmap.MybatisManager;
import survey.model.dto.SurveyAnswerDTO;

public class SurveyAnswerDAO {
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
	
	
	public int setInsert(SurveyAnswerDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result  = session.insert("survey.setInsert", map);
		session.commit();
		session.close();
		return result;
		
		
	}
	public int getSurvey_counter(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		SqlSession session = MybatisManager.getInstance().openSession();
		int result   = session.insert("survey.getSurvey_counter",map);
		session.close();
		return result;
		
	}
	
	
	
}
