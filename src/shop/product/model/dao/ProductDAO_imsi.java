package shop.product.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import shop.product.model.dto.ProductDTO;

public class ProductDAO_imsi {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	String tableName01="product";
	
	
	public Connection getConn() {
		conn = DbExample.dbConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt,Connection conn) {
		DbExample.dbConnClose(rs,pstmt,conn);
	}
	

	 
	  public int setInsert(ProductDTO dto) {
	      int result = 0;
	      conn = getConn();
	      try {
	         String sql = "insert into " + tableName01 + " values(seq_product.nextval,?,?,?,?,current_timestamp)"; //작업할때는 sysdate로 쓰세요
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, dto.getName());
	         pstmt.setInt(2, dto.getPrice());
	         pstmt.setString(3, dto.getDescription());
	         pstmt.setString(4, dto.getProduct_img());
	         result = pstmt.executeUpdate();
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         getConnClose(rs, pstmt, conn);
	      }
	      return result;
	   }
	  
	  public int getTotalRecord(String search_option, String search_data) {
			int result=0;
			conn=getConn();
			try {
				String sql = "select count(*) from " + tableName01 +" where no>0";
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("name")||search_option.equals("description")) {
						sql+=" and "+search_option+" like ? ";
					}else if(search_option.equals("name+description")) {
						sql+=" and (name like ? or description like ?) ";
					}
				}
				int k=0;
				//System.out.println("tottal record:"+sql);
				pstmt = conn.prepareStatement(sql);
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("name")||search_option.equals("description")) {
						pstmt.setString(++k, search_data + '%');
					}else if(search_option.equals("name+description")) {
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
	  
	  public ArrayList<ProductDTO> getList(int startRecord,int lastRecord, String search_option, String search_data){
			ArrayList<ProductDTO> list = new ArrayList<>();
			conn = getConn();
			try {
				String basicSql = "";
				basicSql+="select t1.*,(select nvl(sum(amount),0) from cart t2 where t2.productNo = t1.no) buy_counter from product t1 where no > ? ";
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("name")||search_option.equals("description"))  {
						basicSql+= " and " +search_option + " like ? ";
					}else if(search_option.equals("name+description")) {
						basicSql+= " and (name like ? or description like ? ) ";
					}
				}
				basicSql+= " order by no desc";
				
				String sql = "";
				sql +="select * from (select A.*, Rownum Rnum from (" + basicSql + ") A) ";
				sql +="where Rnum >= ? and Rnum <= ?";
				
				int k=0;
				System.out.println("getList:"+sql);
				pstmt = conn.prepareStatement(sql);
				
				if (search_option.length()>0 && search_data.length()>0) {
					if(search_option.equals("name")||search_option.equals("description"))  {
						pstmt.setString(++k, search_data +'%');
					}else if(search_option.equals("name+description")) {
						pstmt.setString(++k, search_data +'%');
						pstmt.setString(++k, search_data +'%');
					}
				}
				pstmt.setInt(++k, 0);
				pstmt.setInt(++k, startRecord);
				pstmt.setInt(++k, lastRecord);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) { 
					ProductDTO dto2= new ProductDTO();
					dto2.setNo(rs.getInt("no"));
					dto2.setName(rs.getString("name"));
					dto2.setPrice(rs.getInt("price"));
					dto2.setDescription(rs.getString("description"));
					dto2.setRegi_date(rs.getDate("regi_date"));
					dto2.setProduct_img(rs.getString("product_img"));
					dto2.setBuy_counter(rs.getInt("buy_counter"));
					list.add(dto2);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return list;
		
			
		}
		public ProductDTO getView(int no) {
			ProductDTO dto2 = new ProductDTO();
			conn = getConn();
			try {
				
				String sql = "";
		         sql += "select * from (select b.*,  LAG(no) over (order by no asc) preNo,  LAG(name) over (order by no asc) preName,  LEAD(no) over (order by no asc) nxtNo,  LEAD(name) over (order by no asc) nxtName ";
				 sql+="from "+tableName01+ " b order by no asc ) where no = ?";

				
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,no);
				rs=pstmt.executeQuery();

				if(rs.next()) { 
					dto2.setNo(rs.getInt("no"));
					dto2.setName(rs.getString("name"));
					dto2.setPrice(rs.getInt("price"));
					dto2.setDescription(rs.getString("description"));
					dto2.setProduct_img(rs.getString("Product_img"));
					dto2.setRegi_date(rs.getDate("regi_date"));
					
					dto2.setPreNo(rs.getInt("preNo"));
					dto2.setPreSubject(rs.getString("preName"));
					dto2.setNxtNo(rs.getInt("nxtNo"));
					dto2.setNxtSubject(rs.getString("nxtName"));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return dto2;
		}
		


		public int setUpdate(ProductDTO dto) {
			int result=0;
			System.out.println(dto.getName());
			System.out.println(dto.getPrice());
			System.out.println(dto.getDescription());
			System.out.println(dto.getProduct_img());
			conn = getConn();
			try {
				String sql = "update "+tableName01+" set name=?, price=?, description=?, product_img=? where no=?";
				System.out.println(sql);
				int k=0;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(++k,dto.getName());
				pstmt.setInt(++k,dto.getPrice());
				pstmt.setString(++k,dto.getDescription());
				pstmt.setString(++k,dto.getProduct_img());
				pstmt.setInt(++k,dto.getNo());
				pstmt.executeUpdate();
				
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return result;	
		}
		public int setDelete(ProductDTO dto) {
			int result=0;
			conn = getConn();
			try {
				String sql = "delete from "+tableName01+" where no=?";
				System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,dto.getNo());
				System.out.println("dao no:"+dto.getNo());				
				result = pstmt.executeUpdate();
				System.out.println("result:"+result);		
			} catch(Exception e) {
				e.printStackTrace();
			}finally {
				getConnClose(rs,pstmt,conn);
			}
			return result;	
		}
		

	
}
