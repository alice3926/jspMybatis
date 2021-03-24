package shop.mall.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import db.DbExample;
import memo.model.dto.MemoDTO;
import shop.mall.model.dto.CartDTO;
import sqlmap.MybatisManager;

public class CartDAO {
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
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
		 
		 SqlSession session = MybatisManager.getInstance().openSession();
		 int result = session.insert("mall.setInsert",map);
		 session.commit();
		 session.close();
		 return result;
	   }
	  
	  public List<CartDTO> getList(int startRecord,int lastRecord){
		  Map<String,Object> map = new HashMap<>();
			 map.put("startRecord", startRecord);
			 map.put("lastRecord", lastRecord);
			 
			 SqlSession session = MybatisManager.getInstance().openSession();
			 List<CartDTO> list = session.selectList("mall.getList",map);
			 session.close();
			 return list;
		}
	  public int getTotalRecord() {
			SqlSession session = MybatisManager.getInstance().openSession();
			int result = session.selectOne("mall.getTotalRecord");
			session.close();
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
	  public List<CartDTO> getListCartProductGroup(){
		  SqlSession session = MybatisManager.getInstance().openSession();
		  List<CartDTO> list = session.selectList("mall.getListCartProductGroup");//네임스페이스...
		  session.close();
		  return list;
	  }
}