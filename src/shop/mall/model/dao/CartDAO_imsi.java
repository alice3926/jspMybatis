package shop.mall.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import shop.mall.model.dto.CartDTO;

public class CartDAO_imsi {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="cart";
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	
	  public int setInsert(CartDTO dto) {
	      int result = 0;
	      conn = getConn();
	      try {
	         String sql = "insert into " + tableName01 + " values(seq_cart.nextval,?,?,?,current_timestamp)"; //작업할때는 sysdate로 쓰세요
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, dto.getMemberNo());
	         pstmt.setInt(2, dto.getProductNo());
	         pstmt.setInt(3, dto.getAmount());
	         result = pstmt.executeUpdate();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         getConnClose(rs, pstmt, conn);
	      }
	      return result;
	   }
	  
	  public ArrayList<CartDTO> getList(int startRecord,int lastRecord){
			ArrayList<CartDTO> list = new ArrayList<>();
			conn = getConn();
			try {
				String basicSql = "";
				basicSql+="select c.no, p.no productNo, c.memberNo, p.product_img, p.name,p.price,c.amount,(p.price * c.amount) buy_money, c.regi_date from cart c, product p  where c.productNo = p.no  and c.no > ? order by c.no desc";
				
				String sql = "";
				sql +="select * from (select A.*, Rownum Rnum from (" + basicSql + ") A) ";
				sql +="where Rnum >= ? and Rnum <= ?";
				
				int k=0;
				System.out.println("sql:"+sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++k, 0);
				pstmt.setInt(++k, startRecord);
				pstmt.setInt(++k, lastRecord);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) { 
					CartDTO dto2= new CartDTO();
					dto2.setNo(rs.getInt("no"));
					dto2.setMemberNo(rs.getInt("memberNo"));
					dto2.setProductNo(rs.getInt("productNo"));
					dto2.setAmount(rs.getInt("amount"));
					dto2.setRegi_date(rs.getTimestamp("regi_date"));
					dto2.setProduct_name(rs.getString("name"));
					dto2.setProduct_price(rs.getInt("price"));
					dto2.setProduct_img(rs.getString("product_img"));
					dto2.setBuy_money(rs.getInt("buy_money"));
					list.add(dto2);
				}
			} catch(Exception e) {
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
				String sql = "select count(*) from " + tableName01;
				
				
				int k=0;
				//System.out.println("tottal record:"+sql);
				pstmt = conn.prepareStatement(sql);
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
	  
	  public boolean setDeleteBatch(String[] array ) {
		  	int[] count = new int[array.length];
		  	conn=getConn();
		  	try {
		  		conn.setAutoCommit(false);  //오토커밋을 끈다
		  		
		  		String sql = "delete from cart where no = ?";
		  		pstmt = conn.prepareStatement(sql);
		  		
		  		for(int i=0; i<array.length; i++) {
		  			if(array[i].equals("on")) {
		  				continue;
		  			}
		  			pstmt.setInt(1, Integer.parseInt(array[i])); 
		  			pstmt.addBatch(); //반복문이 돌면서 배치파일 실행을 배치를 만듬
		  		}
				count = pstmt.executeBatch(); //모아놓은 문장(배치)를 실행. 즉 count가 여러개가 된다. 인텍스 갯수만큼
				conn.commit();
			}catch(SQLException e) {
				try {
					conn.rollback(); //실패시 롤백해서 그 전 상태로 커밋
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				try {
					conn.setAutoCommit(true); //오토커밋을 켜준다.
				}catch(SQLException e2) {
					e2.printStackTrace();
				}
				getConnClose(rs,pstmt,conn);
			}
			//리턴 값 -2는 성공은 했지만, 변경된 row의 갯수를 알 수 없을때 리턴되는 값이다
			boolean result = true;
			for(int i=0; i<count.length; i++) {
				System.out.println(i + ". "+ count[i]);
				if(count[i] != -2) {
					result = false;
					break;
				}
			}
			return result;
			
	  }
	  public ArrayList<CartDTO> getListCartProductGroup(){
			ArrayList<CartDTO> list = new ArrayList<>();
			conn = getConn();
			try {
				String sql = "";
				sql += "select p.name product_name, sum(c.amount * p.price) buy_money ";
				sql += "from cart c inner join product p on c.productNo = p.no ";
				sql += "group by p.name ";
				sql += "order by product_name asc";
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					CartDTO dto = new CartDTO();
					dto.setProduct_name(rs.getString("product_name"));
					dto.setBuy_money(rs.getInt("buy_money"));
					list.add(dto);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return list;
	  
	  }
}