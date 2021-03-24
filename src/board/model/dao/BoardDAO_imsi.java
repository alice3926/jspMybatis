package board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import board.model.dto.BoardDTO;
import board.model.dto.CommentDTO;
import db.DbExample;
import sqlmap.MybatisManager;

public class BoardDAO_imsi {
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
		int result = 0;
		conn=getConn();
		try {
			String sql="select nvl(max(num),0) from "+tableName01;
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	  public int getMaxRefNo() {
	      int result = 0;
	      conn = getConn();
	      try {
	         String sql = "select nvl(max(refNo),0) from " + tableName01;
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            result = rs.getInt(1);
	         }
	      }catch(Exception e) {
	         e.printStackTrace();
	      } finally{
	         getConnClose(rs, pstmt, conn);
	      }
	      return result;
	   }
	  public int getMaxNoticeNo(String tbl) {
		  int result = 0;
		  conn=getConn();
		  try {
		         String sql = "select nvl(max(noticeNo),0) from " + tableName01 + " where tbl = ?";
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, tbl);
		         rs = pstmt.executeQuery();
		         if(rs.next()) {
		            result = rs.getInt(1);
		         }
		      }catch(Exception e) {
		         e.printStackTrace();
		      } finally{
		         getConnClose(rs, pstmt, conn);
		      }
		      return result;
	  }
	  
	  public int setInsert(BoardDTO dto) {
	      int result = 0;
	      conn = getConn();
	      try {
	         String sql = "insert into " + tableName01 + " values(seq_board.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, dto.getNum());
	         pstmt.setString(2, dto.getTbl());
	         pstmt.setString(3, dto.getWriter());
	         pstmt.setString(4, dto.getSubject());
	         pstmt.setString(5, dto.getContent());
	         pstmt.setString(6, dto.getEmail());
	         pstmt.setString(7, dto.getPasswd());
	         pstmt.setInt(8, dto.getRefNo());
	         pstmt.setInt(9, dto.getStepNo());
	         pstmt.setInt(10, dto.getLevelNo());
	         pstmt.setInt(11, dto.getParentNo());
	         pstmt.setInt(12, dto.getHit());
	         pstmt.setString(13, dto.getIp());
	         pstmt.setInt(14, dto.getMemberNo());
	         pstmt.setInt(15, dto.getNoticeNo());
	         pstmt.setString(16, dto.getSecretGubun());

	         result = pstmt.executeUpdate();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         getConnClose(rs, pstmt, conn);
	      }
	      
	      return result;
	   }
	  public int getTotalRecord(String tbl, String search_option, String search_data) {
			int result=0;
			conn=getConn();
			try {
				String sql = "select count(*) from " + tableName01+ " where tbl = ? ";
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("writer")||search_option.equals("subject")||search_option.equals("content")) {
						sql+=" and "+search_option+" like ? ";
					}else if(search_option.equals("writer_subject_content")) {
						sql+=" and (writer like ? or subject like ? or content like ?) ";
					}
				}
				int k=0;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(++k, tbl);
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("writer")||search_option.equals("subject")||search_option.equals("content")) {
						pstmt.setString(++k, search_data + '%');
					}else if(search_option.equals("writer_subject_content")) {
						pstmt.setString(++k, search_data + '%');
						pstmt.setString(++k, search_data + '%');
						pstmt.setString(++k, search_data + '%');
					}
				}
				
				
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
	  
	  public ArrayList<BoardDTO> getList(int startRecord,int lastRecord, String tbl, String search_option, String search_data){
			ArrayList<BoardDTO> list = new ArrayList<>();
			conn = getConn();
			try {
				String basicSql = "";
				basicSql+="select t1.*, ";
				basicSql+="(select count(*) from " +tableName01 + " t2 where t2.parentNo = t1.no) child_counter ";
				basicSql+="from " +tableName01 + " t1 where tbl = ? ";
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("writer") || search_option.equals("subject") || search_option.equals("content")) {
						basicSql+= " and " +search_option + " like ? ";
					}else if(search_option.equals("writer_subject_content")) {
						basicSql+= " and (writer like ? or subject like ? or content like ? ) ";
					}
				}
				basicSql+= " order by noticeNo desc, refNo desc, levelNo asc";
				
				String sql = "";
				sql +="select * from (select A.*, Rownum Rnum from (" + basicSql + ") A) ";
				sql +="where Rnum >= ? and Rnum <= ?";
				System.out.println(sql);
				int k=0;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(++k,tbl);
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("writer") || search_option.equals("subject") || search_option.equals("content")) {
						pstmt.setString(++k, search_data +'%');
					}else if(search_option.equals("writer_subject_content")) {
						pstmt.setString(++k, search_data +'%');
						pstmt.setString(++k, search_data +'%');
						pstmt.setString(++k, search_data +'%');
					}
				}
				pstmt.setInt(++k, startRecord);
				pstmt.setInt(++k, lastRecord);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) { 
					BoardDTO dto2= new BoardDTO();
					dto2.setNo(rs.getInt("no"));
					dto2.setNum(rs.getInt("num"));
					dto2.setTbl(rs.getString("tbl"));
					dto2.setWriter(rs.getString("writer"));
					dto2.setSubject(rs.getString("subject"));
					dto2.setContent(rs.getString("content"));
					dto2.setEmail(rs.getString("email"));
					dto2.setPasswd(rs.getString("passwd"));
					dto2.setRefNo(rs.getInt("refNo"));
					dto2.setStepNo(rs.getInt("stepNo"));
					dto2.setLevelNo(rs.getInt("levelNo"));
					dto2.setParentNo(rs.getInt("parentNo"));
					dto2.setHit(rs.getInt("hit"));
					dto2.setIp(rs.getString("ip"));
					dto2.setMemberNo(rs.getInt("memberNo"));
					dto2.setNoticeNo(rs.getInt("noticeNo"));
					dto2.setSecretGubun(rs.getString("secretGubun"));
					dto2.setRegiDate(rs.getDate("regiDate"));
					//dto2.setNoticeGubun(rs.getString("noticeGubun"));
					dto2.setChild_counter(rs.getInt("child_counter"));
//					dto2.setPreNo(rs.getInt("preNo"));
//					dto2.setPreSubject(rs.getString("preSubject"));
//					dto2.setNxtNo(rs.getInt("nxtNo"));
//					dto2.setNxtSubject(rs.getString("nxtSubject"));
					list.add(dto2);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return list;
		
			
		}
		public BoardDTO getView(int no) {
			BoardDTO dto2 = new BoardDTO();
			conn = getConn();
			try {
				
				String sql = "";
		         sql += "select * from ";
		         sql += "(";
		         sql += "select b.*, ";
		         sql += " (select count(*) from " +tableName01+ " where refNo = b.refNo and stepNo = (b.stepNo + 1) and levelNo = (b.levelNo + 1)) child_counter, ";
		         sql += " LAG(no) over (order by noticeNo  desc, refNo desc, levelNo asc) preNo, ";
		         sql += " LAG(subject) over (order by noticeNo desc, refNo desc, levelNo asc) preSubject, ";
		         sql += " LEAD(no) over (order by noticeNo desc, refNo desc, levelNo asc) nxtNo, ";
		         sql += " LEAD(subject) over (order by noticeNo desc, refNo desc, levelNo asc) nxtSubject ";
		         sql += " from " + tableName01 + " b order by noticeNo desc, refNo desc, levelNo asc ";
		         sql += ") where no = ?";

				
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,no);
				rs=pstmt.executeQuery();

				if(rs.next()) { 
					dto2.setNo(rs.getInt("no"));
					dto2.setNum(rs.getInt("num"));
					dto2.setTbl(rs.getString("tbl"));
					dto2.setWriter(rs.getString("writer"));
					dto2.setSubject(rs.getString("subject"));
					dto2.setContent(rs.getString("content"));
					dto2.setEmail(rs.getString("email"));
					dto2.setPasswd(rs.getString("passwd"));
					dto2.setRefNo(rs.getInt("refNo"));
					dto2.setStepNo(rs.getInt("stepNo"));
					dto2.setLevelNo(rs.getInt("levelNo"));
					dto2.setParentNo(rs.getInt("parentNo"));
					dto2.setHit(rs.getInt("hit"));
					dto2.setIp(rs.getString("ip"));
					dto2.setMemberNo(rs.getInt("memberNo"));
					dto2.setNoticeNo(rs.getInt("noticeNo"));
					dto2.setSecretGubun(rs.getString("secretGubun"));
					dto2.setRegiDate(rs.getDate("regiDate"));
					//dto2.setNoticeGubun(rs.getString("noticeGubun"));
					dto2.setChild_counter(rs.getInt("child_counter"));
					dto2.setPreNo(rs.getInt("preNo"));
					dto2.setPreSubject(rs.getString("preSubject"));
					dto2.setNxtNo(rs.getInt("nxtNo"));
					dto2.setNxtSubject(rs.getString("nxtSubject"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return dto2;
		}
		
		public void setUpdatHit(int no) {
			conn = getConn();
			try {
				String sql = "update "+tableName01+" set hit = (hit +1) where no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,no);
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
	  
		public void setUpdateReLevel(BoardDTO dto) {
			conn = getConn();
			try {
				String sql = "update "+tableName01+" set levelNo = (levelNo +1) where refNo = ? and levelNo > ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,dto.getRefNo());
				pstmt.setInt(2,dto.getLevelNo());
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		public int setUpdate(BoardDTO dto) {
			int result=0;
			conn = getConn();
			try {
				String sql = "update "+tableName01+" set  writer=?, subject=?, content=?, email=? where no=?";
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,dto.getWriter());
				pstmt.setString(2,dto.getSubject());
				pstmt.setString(3,dto.getContent());
				pstmt.setString(4,dto.getEmail());
				pstmt.setInt(5,dto.getNo());
				result = pstmt.executeUpdate();
				
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return result;	
		}
		public int setDelete(BoardDTO dto) {
			int result=0;
			conn = getConn();
			try {
				String sql = "delete from "+tableName01+" where no=? and passwd=?";
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,dto.getNo());
				pstmt.setString(2,dto.getPasswd());
				result = pstmt.executeUpdate();
				
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return result;	
		}
		
		
//Comment관련
		  public int setCommmentInsert(CommentDTO dto) {
		      int result = 0;
		      conn = getConn();
		      try {
		         String sql = "insert into " + tableName02 + " values(seq_board_comment.nextval,?,?,?,?,?,?,sysdate)";
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setInt(1, dto.getBoard_no());
		         pstmt.setString(2, dto.getWriter());
		         pstmt.setString(3, dto.getContent());
		         pstmt.setString(4, dto.getPasswd());
		         pstmt.setInt(5, dto.getMemberNo());
		         pstmt.setString(6, dto.getIp());

		         result = pstmt.executeUpdate();
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         getConnClose(rs, pstmt, conn);
		      }
		      
		      return result;
		   }
		  
		  
		  public ArrayList<CommentDTO> getCommentList(int board_no,int startRow,int lastRow){
				ArrayList<CommentDTO> commentList = new ArrayList<>();
				conn = getConn();
				try {
					String basic_sql = "select * from "+ tableName02 +" where board_no=? order by comment_no desc";
					String sql = "";
					sql +="select * from (select A.*, Rownum Rnum from ";
					sql +="("+basic_sql+") A";
					sql +=") where Rnum >= ? and Rnum <= ?";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, board_no);
					pstmt.setInt(2, startRow);
					pstmt.setInt(3, lastRow);
					rs = pstmt.executeQuery();
					
					while(rs.next()) { 
						CommentDTO dto2= new CommentDTO();
						dto2.setComment_no(rs.getInt("comment_no"));
						dto2.setBoard_no(rs.getInt("board_no"));
						dto2.setWriter(rs.getString("writer"));
						dto2.setContent(rs.getString("content"));
						dto2.setPasswd(rs.getString("passwd"));
						dto2.setMemberNo(rs.getInt("memberNo"));
						dto2.setIp(rs.getString("ip"));
						dto2.setRegiDate(rs.getDate("regiDate"));
						commentList.add(dto2);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}finally {
					getConnClose(rs,pstmt,conn);
				}
				return commentList;
			}
		  
		  
		  
		  public int commentTotalRecord(int board_no) {
				int result=0;
				conn=getConn();
				try {
					String sql = "select count(*) from " + tableName02+ " where board_no = ? ";
				
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, board_no);
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

		  public CommentDTO getCommmentView(int comment_no) {
			  CommentDTO dto2 = new CommentDTO();
				conn = getConn();
				try {
					
					String sql = "select * from board_comment where comment_no=?";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,comment_no);
					rs=pstmt.executeQuery();

					if(rs.next()) { 
						dto2.setComment_no(rs.getInt("comment_no"));
						dto2.setBoard_no(rs.getInt("board_no"));
						dto2.setWriter(rs.getString("writer"));
						dto2.setContent(rs.getString("content"));
						dto2.setPasswd(rs.getString("passwd"));
						dto2.setIp(rs.getString("ip"));
						dto2.setMemberNo(rs.getInt("memberNo"));
						dto2.setRegiDate(rs.getDate("regiDate"));
					}
				} catch(Exception e) {
					e.printStackTrace();
				}finally {
					getConnClose(rs,pstmt,conn);
				}
				return dto2;
			  
		  }
		  public int setCommmentDelete(int comment_no) {
			  int result=0;
				conn = getConn();
				try {
					String sql = "delete from board_comment where comment_no=?";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,comment_no);
					result = pstmt.executeUpdate();
					
				} catch(Exception e) {
					e.printStackTrace();
				}finally {
					getConnClose(rs,pstmt,conn);
				}
				return result;	
		  }
		  public int setCommmentUpdate(CommentDTO dto) {
			  int result=0;
				conn = getConn();
				try {
					String sql = "update board_comment set  writer=?, content=? where comment_no=? and passwd=?";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,dto.getWriter());
					pstmt.setString(2,dto.getContent());
					pstmt.setInt(3,dto.getComment_no());
					pstmt.setString(4,dto.getPasswd());
					result = pstmt.executeUpdate();
					
				} catch(Exception e) {
					e.printStackTrace();
				}finally {
					getConnClose(rs,pstmt,conn);
				}
				return result;	
		  }
		  
		  
		//tblChk관련
		  
		  public int tblCheck(String tbl) {
			  System.out.println("tblcheck.dao:"+tbl);
			  int result=0;
				conn=getConn();
				try {
					String sql = "select count(*) from boardChk where tbl=? and serviceGubun='T'";
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, tbl);
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
	
}
