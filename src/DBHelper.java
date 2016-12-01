import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHelper {
	private static String urlPrefix = "jdbc:db2:";
	private static String url = "//localhost:50000/gemdb";
	private static String user = "gemuser";
	private static String password = "gemuser";
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	public static Connection con() {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			url = urlPrefix + url;
			con = DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
//			stmt = con.createStatement();
//			rs = stmt.executeQuery("SELECT CUENTA FROM CUENTA LIMIT 1");
//
//			while (rs.next()) {
//				System.out.println(rs.getString(1));
//			}
//			rs.close();
//			stmt.close();
//			con.commit();
//			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
