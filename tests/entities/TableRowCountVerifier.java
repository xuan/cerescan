package entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.loquatic.cerescan.api.persistence.managers.CerescanPersistenceManager;

public class TableRowCountVerifier {
	
	private static String driverName = "com.mysql.jdbc.Driver" ;
	private static String username = "sysman" ;
	private static String password = "sysman" ;
	private static String databaseUrl = "jdbc:mysql://localhost:3306/cerescan?autoReconnect=true" ;
	
	private static Connection getConnection() {
		Connection conn = null ;
		try {
			Class.forName( driverName ) ;
			conn = DriverManager.getConnection(databaseUrl, username, password ) ; 
 		} catch( Exception e ) {
			e.printStackTrace() ;
		}

 		return conn ;
	}
	
	
	
	public static int getRowCount( String tableName ) throws SQLException {
		String sql = "select * from " + tableName ;
		
		Statement stmt = getConnection().createStatement() ;
		
		ResultSet rs = stmt.executeQuery( sql ) ;
		
		int x = 0 ;
		
		if( rs != null ) {
			while( rs.next() ) {
				x++ ;
			}
		}
		stmt.close() ;
		return x ;
	}
	

}
