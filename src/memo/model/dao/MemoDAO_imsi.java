package memo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import member.model.dto.MemberDTO;
import memo.model.dto.MemoDTO;

public class MemoDAO_imsi {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	public int setInsert(MemoDTO dto) {
		int result=0;
		conn = getConn();
		try {
			String sql="insert into memo values(seq_memo.nextval,?,?,?,current_timestamp)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,dto.getWriterId());
			pstmt.setString(2,dto.getWriterName());
			pstmt.setString(3,dto.getMemo());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	
	public ArrayList<MemoDTO> getList(int startRow,int endRow) {
		ArrayList<MemoDTO> list = new ArrayList<>();
		conn=getConn();
		try {
			String basic_sql = "select * from memo where no>? order by writerId desc";
			String sql = "";
			sql +="select * from (select A.*, Rownum Rnum from ";
			sql +="("+basic_sql+") A";
			sql +=") where Rnum >= ? and Rnum <= ?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,0);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				MemoDTO dto = new MemoDTO();
				dto.setWriterId(rs.getString("writerId"));
				dto.setWriterName(rs.getString("writerName"));
				dto.setMemo(rs.getString("memo"));
				dto.setWriteDate(rs.getTimestamp("writeDate"));
				list.add(dto);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return list;
	}
	
	public int getTotalRecord() {
		int result=0;
		conn=getConn();
		try {
			String sql = "select count(*) from memo where no > ?";
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
	
	public MemoDTO getOne(int no) {
		MemoDTO dto = new MemoDTO();
		conn=getConn();
		try {
			String sql = "select * from memo where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dto.setNo(rs.getInt("no"));
				dto.setWriterId(rs.getString("writerId"));
				dto.setWriterName(rs.getString("writerName"));
				dto.setMemo(rs.getString("memo"));
				dto.setWriteDate(rs.getTimestamp("writeDate"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return dto;
	}
	public int setSujung(MemoDTO dto2) {
		int result=0;
		conn=getConn();
		try {
			String sql = "update memo set writerName=?, memo=? where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto2.getWriterName());
			pstmt.setString(2, dto2.getMemo());
			pstmt.setInt(3, dto2.getNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	public int setSakjae(MemoDTO dto2) {
		int result=0;
		conn=getConn();
		try {
			String sql = "delete from memo where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto2.getNo());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	
	
}
