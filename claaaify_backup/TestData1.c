package classification;
import geneticalgorithm.GA;
import java.sql.*;


public class TestData {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/project";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   static int i=100;
	public static void main(String args[])
	{
		
      System.out.println("Initializing");
	
      Connection conn = null;
      Statement stmt = null;
   try
   {
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      long millis = System.currentTimeMillis() % 1000;
     
      String sql = "SELECT * FROM generated";
      ResultSet rs = stmt.executeQuery(sql);
      //STEP 5: Extract data from result set
      Classify_BP bp=new Classify_BP();
	  Classify_HR hrc=new Classify_HR();
	  Classify_ECG ecg=new Classify_ECG();
	  Classify_RR rrc=new Classify_RR();
	  Classify_SPO2 spo2c=new Classify_SPO2();
	  
      while(rs.next()){
    	  i--;
    	  if(i==0)
    		  break;
         //Retrieve by column name
         int hr  = rs.getInt("hr");
         int bp_s = rs.getInt("bps");
         int rr=rs.getInt("rr");
         int ecg1=rs.getInt("ecg");
         int age=rs.getInt("age");
         int id=rs.getInt("p_id");
         int spo2=rs.getInt("spo2");
         String name = rs.getString("name");
		 String sex=rs.getString("sex");
		 String gene="";
		 
		 String[] data={""+sex,""+age,""+hr,name,""+rr};
		 gene+=hrc.hr_class(data);
		 
		 String [] data1 = {""+sex,""+age,""+bp_s,name};
		 gene+=bp.bp_class(data1);
		 
		 String [] data2 = {""+sex,""+age,""+rr,name};
		 //gene+=rrc.rr_class(data1);
		 
		 String [] data3 = {""+sex,""+age,""+ecg,name};
		 gene+=ecg.ecg_class(data1);
		 
		 String [] data4 = {""+sex,""+age,""+spo2,name};
		 gene+=ecg.ecg_class(data1);
		 
		 System.out.println("\nName : "+name+"\nGene Representation : "+gene+"\n");
		 
		// new GA(name,gene).run();
	       
		 
		
	
   }
      System.out.println(millis);
      System.out.println(System.currentTimeMillis() % 1000);
      //System.out.println("Time Taken :"+millis-System.currentTimeMillis() % 1000);
	
		
	}
   catch(Exception e)
   {
	   System.out.println("Error "+e);
   }

}
	}
