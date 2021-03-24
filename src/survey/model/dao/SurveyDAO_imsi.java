package survey.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import member.model.dto.MemberDTO;
import survey.model.dto.SurveyAnswerDTO;
import survey.model.dto.SurveyDTO;

public class SurveyDAO_imsi {
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
		int result=0;
		getConn();
		try {
			String sql="insert into survey(no,question,ans1,ans2,ans3,ans4,status,start_date,last_date) values(seq_survey.nextval,?,?,?,?,?,?,?,?)";
			
			//sql = "insert into survey(no,question,ans1,ans2,ans3,ans4,status,start_date,last_date) values(seq_survey.nextval,?,?,?,?,?,?,to_timestamp(?),to_timestamp(?))";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getQuestion());
			pstmt.setString(2, dto.getAns1());
			pstmt.setString(3, dto.getAns2());
			pstmt.setString(4, dto.getAns3());
			pstmt.setString(5, dto.getAns4());
			pstmt.setString(6, dto.getStatus());
			pstmt.setTimestamp(7,dto.getStart_date());
			pstmt.setTimestamp(8,dto.getLast_date());
			
			
			//pstmt.setString(7, "2021-01-01 00:00:00.0");
			//pstmt.setString(8, "2021-01-31 23:59:59.9");
			result = pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		
		System.out.println("Survey_controller result"+result);
		return result;
		
	}
	
	 public ArrayList<SurveyDTO> getList(int startRecord,int lastRecord,String list_gubun, String search_option ,String search_data, String search_date_s,String search_date_e,String search_date_check) {
	      ArrayList<SurveyDTO> list = new ArrayList<>();
	      getConn();
	      
	      try {
	    	     if(search_date_s.length() > 0 && search_date_e.length() > 0) {
	 	            search_date_s = search_date_s + " 00:00:00.0";
	 	            search_date_e = search_date_e + " 23:59:59.999999";
	 	            //java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(search_date_s);
	 	            //java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(search_date_e);
	 	         }
	 	         
	    	  
	         String basic_sql = "";
	               basic_sql += "select t1.*,";
	               basic_sql += "(select count(*) from "+tableName02+" t2 where t2.no = t1.no) survey_counter ";
	               basic_sql += " from "+tableName01+" t1 where no > ? ";
	               
	               if(list_gubun.equals("ing")) {
	                  basic_sql += " and CURRENT_TIMESTAMP BETWEEN start_date AND last_date ";   
	               } else if(list_gubun.equals("end")) {
	                  basic_sql += " and CURRENT_TIMESTAMP > last_date ";     
	               } else if(list_gubun.equals("future")) {
			           basic_sql += " and CURRENT_TIMESTAMP < start_date ";     
	  		       }

	               if(search_option.length() > 0 && search_data.length() > 0) {
	                  basic_sql += " and "+ search_option + " like ? ";
	               }
	               
	               if(search_date_check.equals("o") && search_date_s.length() > 0 && search_date_e.length() > 0) {
	   	            basic_sql += " and (start_date >= to_timestamp(?) and last_date <= to_timestamp(?))";
	               }
	   	         
	               
	               
	           
	               basic_sql +=" order by no desc ";
	               
	         String sql = ""
	               + " SELECT * FROM (SELECT a.*, ROWNUM Rnum FROM "
	                  + "("+basic_sql+") a)"
	                  + "WHERE Rnum >=? and Rnum <=?";
	        
	         System.out.println(sql+"확인차");
	         
	         int k = 0;      //1부터는 k++
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(++k, 0);
	         if(search_option.length() > 0 && search_data.length() > 0) {
	            pstmt.setString(++k, '%' + search_data + '%');
	            
	         }
	         if(search_date_check.equals("o") && search_date_s.length() > 0 && search_date_e.length() > 0) {
	              pstmt.setString(++k, search_date_s);
	              pstmt.setString(++k, search_date_e);
	          }
	         
	         pstmt.setInt(++k, startRecord);
	         pstmt.setInt(++k, lastRecord);
	         
	         rs = pstmt.executeQuery();
	         
	         
	         while(rs.next()) {
	            SurveyDTO dto = new SurveyDTO();
	            dto.setNo(rs.getInt("no"));
	            dto.setQuestion(rs.getString("question"));
	            dto.setAns1(rs.getString("ans1"));
	            dto.setAns2(rs.getString("ans2"));
	            dto.setAns3(rs.getString("ans3"));
	            dto.setAns4(rs.getString("ans4"));
	            dto.setStatus(rs.getString("status"));
	            dto.setStart_date(rs.getTimestamp("start_date"));
	            dto.setLast_date(rs.getTimestamp("last_date"));
	            dto.setRegi_date(rs.getTimestamp("regi_date"));
	            dto.setSurvey_counter(rs.getInt("survey_counter"));
	            list.add(dto);
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         getConnClose(rs, pstmt, conn);
	      }
	      
	      return list;      
	   }
	   

	public int getTotalRecord() {
		int result=0;
		conn=getConn();
		try {
			String sql = "select count(*) from survey where no>?";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
						
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		
		return result;
	}
	public int getTotalRecord(String list_gubun) {
		int result=0;
		conn=getConn();
		String sql = "select count(*) from survey where no > ?";
		try {
			if(list_gubun.equals("ing")) {
				sql += " and current_timestamp between start_date and last_date";
			}else if(list_gubun.equals("end")) {
				sql += " and current_timestamp > last_date";
			}
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
						
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		System.out.println("DAORE"+result);
		return result;
	}
	
	public int getTotalRecord(String list_gubun, String search_option ,String search_data ,String search_date_s, String search_date_e, String search_date_check) {
	      getConn();
	      int result = 0;
	      try {
	         
	         if(search_date_s.length() > 0 && search_date_e.length() > 0) {
	            search_date_s = search_date_s + " 00:00:00.0";
	            search_date_e = search_date_e + " 23:59:59.999999";
	            //java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(search_date_s);
	            //java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(search_date_e);
	         }
	         
	         
	         String sql = "select count(*) from "+tableName01+" where no > ? ";
	         if(list_gubun.equals("ing")) {
	            sql += " and CURRENT_TIMESTAMP BETWEEN start_date AND last_date ";   
	         } else if(list_gubun.equals("end")) {
	            sql += " and CURRENT_TIMESTAMP > last_date ";     
	         } else if(list_gubun.equals("future")) {
		            sql += " and CURRENT_TIMESTAMP < start_date ";     
		     }

	         if(search_option.length() > 0 && search_data.length() > 0) {
	            sql += " and " +search_option+" like ? ";
	         }
	      
	         if(search_date_check.equals("o") && search_date_s.length() > 0 && search_date_e.length() > 0) {
	            sql += " and (start_date >= to_timestamp(?) and last_date <= to_timestamp(?))";
	         }
	         
	         System.out.println("sql TotalRecord : "+sql);
	         int k = 1;
	         pstmt = conn.prepareStatement(sql);   
	         pstmt.setInt(k++, 0);
	           if (search_option.length() > 0 && search_data.length() > 0) {
	               pstmt.setString(k++, '%' + search_data + '%');
	            }
	           
	           if(search_date_check.equals("o") && search_date_s.length() > 0 && search_date_e.length() > 0) {
	              pstmt.setString(k++, search_date_s);
	              pstmt.setString(k++, search_date_e);
	           }
	           
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            result = rs.getInt(1);         
	         }
	         
	         System.out.println("TotalRecord : "+ result);
	         System.out.println("sql tot2 "+sql);
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         getConnClose(rs, pstmt, conn);
	      }
	      
	      return result;
	   }
	
	
	
	public SurveyDTO getOne(int no) {
		SurveyDTO dto = new SurveyDTO();
		conn=getConn();
		try {
			String sql = "select * from survey where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dto.setNo(rs.getInt("no"));
	            dto.setQuestion(rs.getString("question"));
	            dto.setAns1(rs.getString("ans1"));
	            dto.setAns2(rs.getString("ans2"));
	            dto.setAns3(rs.getString("ans3"));
	            dto.setAns4(rs.getString("ans4"));
	            dto.setStatus(rs.getString("status"));
	            dto.setStart_date(rs.getTimestamp("start_date"));
	            dto.setLast_date(rs.getTimestamp("last_date"));
	            dto.setRegi_date(rs.getTimestamp("regi_date"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return dto;
	}

	
	public int setInsertAnswer(SurveyAnswerDTO dto) {
		System.out.println("setInsertAnswer DAO 들어왔음.");
		int result=0;
		getConn();
		try {
			String sql="insert into "+tableName02+ " values(seq_survey_answer.nextval,?,?,current_timestamp)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNo());
			pstmt.setInt(2, dto.getAnswer());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	public int setSujung(SurveyDTO dto) {
		int result=0;
		conn=getConn();
		try {
			String sql = "update survey set question=?, ans1=?, ans2=?, ans3=?, ans4=?, status=?, start_date=?, last_date=? where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getQuestion());
			pstmt.setString(2, dto.getAns1());
			pstmt.setString(3, dto.getAns2());
			pstmt.setString(4, dto.getAns3());
			pstmt.setString(5, dto.getAns4());
			pstmt.setString(6, dto.getStatus());
			pstmt.setTimestamp(7,dto.getStart_date());
			pstmt.setTimestamp(8,dto.getLast_date());
			pstmt.setInt(9, dto.getNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	
	public int setSakjae(int no) {
		int result=0;
		conn=getConn();
		try {
			String sql = "delete from survey where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	
	
}
