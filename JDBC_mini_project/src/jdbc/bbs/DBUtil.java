package jdbc.bbs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBUtil {
	
	private static String driver="oracle.jdbc.driver.OracleDriver";
	private static String url="jdbc:oracle:thin:@localhost:1521:XE";
	private static String user="scott", pwd="tiger";
	
	static {
		try {
			Class.forName(driver);
			System.out.println("Driver Loading Success...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//static 블럭---------
	private DBUtil() {
		//외부에서 객체 생성 못하도록 private을 붙이자.
	}
	public static Connection getCon() throws SQLException{
		Connection con=DriverManager.getConnection(url,user,pwd);
		return con;
	}
}////////////////////////////
