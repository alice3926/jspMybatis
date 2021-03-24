package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import member.model.dto.MemberDTO;

public class MemberDAO_imsi {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="member";
	
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	public int getIdCheck(String id) {
		int result=0;
		conn=getConn();
		try {
			String sql="select count(*) from member where id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		
		return result;
		
	}
	public String getIdCheckWin(String id) {
		String result="";
		conn=getConn();
		try {
			String sql="select count(*) from member where id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = Integer.toString(rs.getInt(1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		if(result.equals("0")) {
			result="";
		}
		return result;
		
	}
	public int setInsert(MemberDTO dto) {
		int result=0;
		conn = getConn();
		try {
			String sql="insert into member values(seq_member.nextval,?,?,?,?,?,current_timestamp,?,?,?,?,current_timestamp)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,dto.getId());
			pstmt.setString(2,dto.getPasswd());
			pstmt.setString(3,dto.getName());
			pstmt.setString(4,dto.getGender());
			pstmt.setInt(5,dto.getBornYear());
			pstmt.setString(6,dto.getPostcode());
			pstmt.setString(7,dto.getAddress());
			pstmt.setString(8,dto.getDetailAddress());
			pstmt.setString(9,dto.getExtraAddress());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	
	public MemberDTO setlogin(MemberDTO dto) {
		int result=0;
		MemberDTO dto2 = new MemberDTO();
		conn = getConn();
		try {
			String sql="select no,id,passwd,name from member where id=? and passwd=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,dto.getId());
			pstmt.setString(2,dto.getPasswd());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto2.setNo(rs.getInt("no"));
				dto2.setId(rs.getString("id"));
				dto2.setPasswd(rs.getString("passwd"));
				dto2.setName(rs.getString("name"));
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return dto2;
	}
	
	 public int getTotalRecord(String search_option, String search_data) {
			int result=0;
			conn=getConn();
			try {
				String sql = "select count(*) from member where no>? ";
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("id")||search_option.equals("name")||search_option.equals("gender")) {
						sql+=" and "+search_option+" like ? ";
					}else if(search_option.equals("id_name_gender")) {
						sql+=" and (id like ? or name like ? or gender like ?) ";
					}
				}
				int k=0;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++k, 0);
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("id")||search_option.equals("name")||search_option.equals("gender")) {
						pstmt.setString(++k, search_data + '%');
					}else if(search_option.equals("id_name_gender")) {
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
	  
	  public ArrayList<MemberDTO> getList(int startRecord,int lastRecord, String search_option, String search_data){
			ArrayList<MemberDTO> list = new ArrayList<>();
			conn = getConn();
			try {
				String basicSql = "";
				basicSql+="select * ";
				basicSql+="from " +tableName01 + " t1 where no > ? ";
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("id")||search_option.equals("name")||search_option.equals("gender")) {
						basicSql+= " and " +search_option + " like ? ";
					}else if(search_option.equals("id_name_gender")) {
						basicSql+= " and (id like ? or name like ? or gender like ? ) ";
					}
				}
				
				String sql = "";
				sql +="select * from (select A.*, Rownum Rnum from (" + basicSql + ") A) ";
				sql +="where Rnum >= ? and Rnum <= ?";
				
				int k=0;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++k,0);
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("id")||search_option.equals("name")||search_option.equals("gender")) {
						pstmt.setString(++k, search_data +'%');
					}else if(search_option.equals("id_name_gender")) {
						pstmt.setString(++k, search_data +'%');
						pstmt.setString(++k, search_data +'%');
						pstmt.setString(++k, search_data +'%');
					}
				}
				pstmt.setInt(++k, startRecord);
				pstmt.setInt(++k, lastRecord);
				System.out.println(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) { 
					MemberDTO dto= new MemberDTO();
					dto.setNo(rs.getInt("no"));
					dto.setId(rs.getString("id"));
					dto.setPasswd(rs.getString("passwd"));
					dto.setName(rs.getString("name"));
					dto.setGender(rs.getString("gender"));
					dto.setBornYear(rs.getInt("bornYear"));
					dto.setRegiDate(rs.getTimestamp("regiDate"));
					dto.setPostcode(rs.getString("postcode"));
					dto.setAddress(rs.getString("address"));
					dto.setDetailAddress(rs.getString("detailAddress"));
					dto.setExtraAddress(rs.getString("extraAddress"));
					dto.setLastupDate(rs.getTimestamp("lastupDate"));
					list.add(dto);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return list;
		
			
		}
	
	public ArrayList<MemberDTO> getList() {
		ArrayList<MemberDTO> list = new ArrayList<>();
		conn=getConn();
		try {
			String sql = "select * from member";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setBornYear(rs.getInt("bornYear"));
				dto.setRegiDate(rs.getTimestamp("regiDate"));
				dto.setPostcode(rs.getString("postcode"));
				dto.setAddress(rs.getString("address"));
				dto.setDetailAddress(rs.getString("detailAddress"));
				dto.setExtraAddress(rs.getString("extraAddress"));
				dto.setLastupDate(rs.getTimestamp("lastupDate"));
				list.add(dto);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return list;
	}
	public MemberDTO getOne(int no) {
		MemberDTO dto = new MemberDTO();
		conn=getConn();
		try {
			String sql = "select * from member where no=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setBornYear(rs.getInt("bornYear"));
				dto.setRegiDate(rs.getTimestamp("regiDate"));
				dto.setPostcode(rs.getString("postcode"));
				dto.setAddress(rs.getString("address"));
				dto.setDetailAddress(rs.getString("detailAddress"));
				dto.setExtraAddress(rs.getString("extraAddress"));
				dto.setLastupDate(rs.getTimestamp("lastupDate"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return dto;
	}
	public int setSujung(MemberDTO dto2) {
		int result=0;
		conn=getConn();
		try {
			String sql = "update member set name=?, gender=?, bornYear=?,postcode=?,address=?,detailAddress=?,extraAddress=?, lastupDate=current_timestamp where id=? and passwd=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto2.getName());
			pstmt.setString(2, dto2.getGender());
			pstmt.setInt(3, dto2.getBornYear());
			pstmt.setString(4, dto2.getPostcode());
			pstmt.setString(5, dto2.getAddress());
			pstmt.setString(6, dto2.getDetailAddress());
			pstmt.setString(7, dto2.getExtraAddress());
			pstmt.setString(8, dto2.getId());
			pstmt.setString(9, dto2.getPasswd());
			result=pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			getConnClose(rs,pstmt,conn);
		}
		return result;
	}
	public int setSakjae(MemberDTO dto2) {
		int result=0;
		conn=getConn();
		try {
			String sql = "delete from member where id=? and passwd=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto2.getId());
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
