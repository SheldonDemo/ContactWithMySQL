package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {

	private static String url = null;
	private static String user = null;
	private static String password = null;
	private static String driverClass = null;
	
	static{
		try {
			Properties prpo = new Properties();
			InputStream in = JDBCUtil.class.getResourceAsStream("/db.properties");
			prpo.load(in);
			url = prpo.getProperty("url");
			user = prpo.getProperty("user");
			password = prpo.getProperty("password");
			driverClass = prpo.getProperty("driverClass");
			
			Class.forName(driverClass);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Çý¶¯³ÌÐò×¢²áÊ§°Ü");
		}		
	}
	public static Connection getConnection(){
		
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public static void close(Connection conn,Statement stat){
		if(stat!=null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);

			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);

			}
		}
		
	}
	public static void close(Connection conn,Statement stmt,ResultSet rs){
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
