package guestbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbExample;
import guestbook.model.dto.GuestbookDTO;
import member.model.dto.MemberDTO;

public class GuestbookDAO_imsi {
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
		int result=0;
		conn = getConn();
		try {
			String sql="insert into guestbook values(seq_guestbook.nextval,?,?,?,?,sysdate)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,dto.getName());
			pstmt.setString(2,dto.getEmail());
			pstmt.setString(3,dto.getPasswd());
			pstmt.setString(4,dto.getContent());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	
	
	public List<GuestbookDTO> getList(int startRow,int endRow,String search_option, String search_data){
		List<GuestbookDTO> arraylist = new ArrayList<>();
		conn = getConn();
		try {
			String basicSql = "";
			basicSql+="select t1.* ";
			basicSql+="from " +tableName01 + " t1 where 1=1 ";
			
			if (search_option.length()>0 && search_data.length()>0) {
				if(search_option.equals("name") || search_option.equals("email") || search_option.equals("content")) {
					basicSql+= " and " +search_option + " like ? ";
				}else if(search_option.equals("name_email_content")) {
					basicSql+= " and (name like ? or email like ? or content like ? ) ";
				}
			}
			basicSql+= " order by no desc";
			
			String sql = "";
			sql +="select * from (select A.*, Rownum Rnum from (" + basicSql + ") A) ";
			sql +="where Rnum >= ? and Rnum <= ?";
			System.out.println(sql);
			int k=0;
			pstmt = conn.prepareStatement(sql);
			if (search_option.length()>0 && search_data.length()>0) {
				if(search_option.equals("name") || search_option.equals("email") || search_option.equals("content")) {
					pstmt.setString(++k, search_data +'%');
				}else if(search_option.equals("writer_subject_content")) {
					pstmt.setString(++k, search_data +'%');
					pstmt.setString(++k, search_data +'%');
					pstmt.setString(++k, search_data +'%');
				}
			}
			pstmt.setInt(++k, startRow);
			pstmt.setInt(++k, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { 
				GuestbookDTO dto= new GuestbookDTO();
				dto.setNo(rs.getInt("no"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setContent(rs.getString("content"));
				dto.setRegi_date(rs.getDate("regi_date"));
				arraylist.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return arraylist;
	
		
	}
	
	  public int getTotalRecord(String search_option, String search_data) {
		int result=0;
		conn=getConn();
		try {
			String sql = "select count(*) from " + tableName01+ " where 1= 1 ";
			
			if (search_option.length()>0 && search_data.length()>0) {
				if(search_option.equals("name")||search_option.equals("email")||search_option.equals("content")) {
					sql+=" and "+search_option+" like ? ";
				}else if(search_option.equals("name_email_content")) {
					sql+=" and name like ? or email like ? or content like ?) ";
				}
			}
			int k=0;
			pstmt = conn.prepareStatement(sql);
			if (search_option.length()>0 && search_data.length()>0) {
				if(search_option.equals("name")||search_option.equals("email")||search_option.equals("content")) {
					pstmt.setString(++k, search_data + '%');
				}else if(search_option.equals("name_email_content")) {
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
	  public GuestbookDTO getOne(int no) {
		  GuestbookDTO dto = new GuestbookDTO();
			conn=getConn();
			try {
				String sql = "select * from guestbook where no=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				rs=pstmt.executeQuery();
				while(rs.next()) {
					dto.setNo(rs.getInt("no"));
					dto.setEmail(rs.getString("email"));
					dto.setPasswd(rs.getString("passwd"));
					dto.setName(rs.getString("name"));
					dto.setContent(rs.getString("content"));
					dto.setRegi_date(rs.getDate("regi_date"));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return dto;
		}
		public int setSujung(GuestbookDTO dto2) {
			int result=0;
			conn=getConn();
			try {
				String sql = "update guestbook set name=?, email=?, content=? where no=? and passwd=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, dto2.getName());
				pstmt.setString(2, dto2.getEmail());
				pstmt.setString(3, dto2.getContent());
				pstmt.setInt(4, dto2.getNo());
				pstmt.setString(5, dto2.getPasswd());
				result=pstmt.executeUpdate();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return result;
		}
		public int setSakjae(GuestbookDTO dto2) {
			int result=0;
			conn=getConn();
			try {
				String sql = "delete from guestbook where no=? and passwd=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, dto2.getNo());
				pstmt.setString(2, dto2.getPasswd());
				result=pstmt.executeUpdate();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return result;
		}
	
	
	
	
	
	
	
}
