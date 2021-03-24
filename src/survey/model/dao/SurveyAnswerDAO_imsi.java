package survey.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import survey.model.dto.SurveyAnswerDTO;

public class SurveyAnswerDAO_imsi {
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
		int result=0;
		getConn();
		try {
			String sql="insert into survey_answer(answer_no,no,answer) values(seq_survey_answer.nextval,?,?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNo());
			pstmt.setInt(2, dto.getAnswer());
			
			
			
			result = pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		
		System.out.println("설문조사등록 성공?"+result);
		return result;
		
	}
	public int getSurvey_counter(int no) {
		int counter=0;
		getConn();
		try {
			String sql="select count(*) survey_counter from survey_answer where no=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				counter=rs.getInt(1);
			}
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		
		return counter;
		
	}
	
	
	
}
