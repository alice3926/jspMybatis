package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Db {
	public Connection dbConn(); //실행단이 없는 추상메소드
	public void dbConnClose(ResultSet rs,PreparedStatement pstmt,Connection conn);
}
