import java.sql.*;

public class DataBaseTest
{
	public static void main(String args[])
	{
		
      System.out.println("Initializing");
		 
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  	   
		final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";
	    final String USER="root";
	    final String PASS ="oneadmin"; 
		
	    Connection conn = null;
		Statement stmt = null;
		  
		  try
		   {
			  System.out.println("driver call");
		       Class.forName(JDBC_DRIVER);
		       System.out.println("conn set");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      System.out.println("stmt set");
		      stmt = conn.createStatement();
		      
		      
		      String sql="SELECT gene from gene";
		      System.out.println("resultset set");
		      ResultSet rs = stmt.executeQuery(sql);
		      System.out.println("getstring"+rs); 
		      rs.next();
		      String gene1=rs.getString(1);
		      
		    	  System.out.println("gene1 VALUE::"+gene1);
		   		   }
		   catch(Exception e)
		   {
			   System.out.println(e);
		   }
		
		
	}
	
	
	}
	
	