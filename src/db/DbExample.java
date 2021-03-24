package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//DB연결하는 실행클래스
public class DbExample {
	public static Connection dbConn() {
		Db db = new DbImpleOracle();
		return db.dbConn();
		
	}
	
	public static void dbConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		Db db = new DbImpleOracle();
		db.dbConnClose(rs, pstmt, conn);
	
	}
}
