package jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String user = "scott", pwd = "tiger";

	static {
		try {
			Class.forName(driver);
			System.out.println("Driver loading success..");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private DBUtil() {
		
	}
	
	public static Connection getCon() throws SQLException {
		Connection con = DriverManager.getConnection(url, user, pwd);
		return con;
	}
}
