package board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import board.model.dto.BoardDTO;
import board.model.dto.CommentDTO;
import db.DbExample;
import sqlmap.MybatisManager;

public class BoardDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="board";
	String tableName02="board_comment";
	
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	public int getMaxNum() {
		SqlSession session = MybatisManager.getInstance().openSession();
		int result =  session.selectOne("board.getMaxNum");
		session.close();
		return result;
	}
	  public int getMaxRefNo() {
		SqlSession session = MybatisManager.getInstance().openSession();
		int result =  session.selectOne("board.getMaxRefNo");
		session.close();
		return result;
	   }
	  public int getMaxNoticeNo(String tbl) {
		  SqlSession session = MybatisManager.getInstance().openSession();
		  int result =  session.selectOne("board.getMaxNoticeNo",tbl);
		  session.close();
		  return result;
			
	  }
	  
	  public int setInsert(BoardDTO dto) {
		  Map<String, Object> map = new HashMap<>();
		  map.put("dto", dto);
		  
		  SqlSession session = MybatisManager.getInstance().openSession();
		  int result = session.insert("board.setInsert",map);
		  session.commit();
		  session.close();
		  return result;
	   }
	  public int getTotalRecord(String tbl, String search_option, String search_data) {
			Map<String, Object> map = new HashMap<>();
			map.put("tbl", tbl);
			map.put("search_option", search_option);
			map.put("search_data",search_data);
		
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.selectOne("board.getTotalRecord",map);
			session.close();
			return result;
		}
	  
	  public List<BoardDTO> getList(int startRecord,int lastRecord, String tbl, String search_option, String search_data){
			Map<String, Object> map = new HashMap<>();
			map.put("startRecord",startRecord);
			map.put("lastRecord",lastRecord);
			map.put("tbl",tbl);
			map.put("search_option",search_option);
			map.put("search_data",search_data);

			SqlSession session = MybatisManager.getInstance().openSession();
			List<BoardDTO> list = session.selectList("board.getList",map);
			session.close();
			return list;
		}
		public BoardDTO getView(int no) {
			SqlSession session = MybatisManager.getInstance().openSession();
			BoardDTO dto = session.selectOne("board.getView",no);
			session.close();
			return dto;
			
		}
		
		public void setUpdatHit(int no) {
			SqlSession session = MybatisManager.getInstance().openSession();
			session.update("board.setUpdatHit",no);
			session.commit();
			session.close();
		}
	  
		public void setUpdateReLevel(BoardDTO dto) {
			Map<String, Object> map = new HashMap<>();
			map.put("dto",dto);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			session.update("board.setUpdateReLevel",dto);
			session.commit();
			session.close();
		}
		public int setUpdate(BoardDTO dto) {
			Map<String, Object> map = new HashMap<>();
			map.put("dto", dto);
			
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.update("board.setUpdate",map);
			session.commit();
			session.close();
			return result;
		}
		public int setDelete(BoardDTO dto) {
			Map<String, Object> map = new HashMap<>();
			map.put("dto", dto);
			
			
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.delete("board.setDelete",map);
			session.commit();
			session.close();
			return result;
		}
		
		
//Comment관련
		  public int setCommmentInsert(CommentDTO dto) {
			 Map<String, Object> map = new HashMap<>();
			 map.put("dto", dto);
			 
			 SqlSession  session = MybatisManager.getInstance().openSession();
			 int result = session.insert("board.setCommmentInsert",map);
			 session.commit();
			 session.close();
			 return result;
		   }
		  
		  
		  public List<CommentDTO> getCommentList(int board_no,int startRow,int lastRow){
			Map<String,Object> map = new HashMap<>();
			map.put("board_no", board_no);
			map.put("startRow", startRow);
			map.put("lastRow", lastRow);

			SqlSession session = MybatisManager.getInstance().openSession();
			List<CommentDTO> list = session.selectList("board.getCommentList",map);
			session.close();
			return list;
			  
		  }
		  
		  
		  public int commentTotalRecord(int board_no) {
			  SqlSession session = MybatisManager.getInstance().openSession();
			  int result = session.selectOne("board.commentTotalRecord",board_no);
			  session.close();
			  return result;
			}
		  
		  
		  public CommentDTO getCommmentView(int comment_no) {
				SqlSession session = MybatisManager.getInstance().openSession();
				CommentDTO commentDto = session.selectOne("board.getCommmentView",comment_no);
				session.close();
				return commentDto;
			  
		  }
		  public int setCommmentDelete(int comment_no) {
			  SqlSession session = MybatisManager.getInstance().openSession();
			  int result = session.delete("board.setCommmentDelete",comment_no);
			  session.commit();
			  session.close();
			  return result;
		  }
		  public int setCommmentUpdate(CommentDTO dto) {
			  Map<String, Object> map = new HashMap<>();
			  map.put("dto", dto);
			
			  SqlSession session = MybatisManager.getInstance().openSession();
			  int result = session.update("board.setCommmentUpdate",map);
			  session.commit();
			  session.close();
			  return result;
		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		//tblChk관련
		  
		  public int tblCheck(String tbl) {
			 Map<String, Object> map = new HashMap<>();
			 map.put("tbl", tbl);
			  
			  
			  
			 SqlSession session = MybatisManager.getInstance().openSession();
			 int result = session.selectOne("board.tblCheck",map);
			 session.close();
			 return result;
			}
	
}
