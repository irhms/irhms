package classification;
import java.sql.*;
public class Classify_SPO2 {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 /*  static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";

	   //  Database credentials
	   static final String USER="root";
	   static final String PASS = "";*/
	
	final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";
    final String USER="root";
    final String PASS ="oneadmin"; 
	
	   String gene="";
	   	   String current_class="";

	
	   public String spo2_class(String[] data)
		{
			char sex=data[0].charAt(0);
			int age=Integer.parseInt(data[1]);
			int spo2=Integer.parseInt(data[2]); 
			//float pn,ph,pvh,pl,pvl;
		
					if(spo2>105)
					{
						current_class="Very High";
						gene="10000";
					}
					else if(spo2>=101&&spo2<=105)
					{
						current_class="High";
						gene="01000";
					}
					else if(spo2>=95&&spo2<=100)
					{
						current_class="Normal";
						gene="00100";
					}
					else if(spo2>=46&&spo2<=94)
					{
						current_class="Low";
						gene="00010";
					}
					else if(spo2<=45)
					{
						current_class="Very Low";
						gene="00001";
					}
					System.out.println("Name: "+data[3]+"  Age : "+age+"  Sex: "+sex+"  Dissolved Oxygen: "+spo2);
					System.out.println("Class : "+current_class+"  Chromosome : "+gene);
					
			  
		return current_class;
		}

}
