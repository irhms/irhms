package classification;

import geneticalgorithm.GA;
/*import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.IfMachineType;
import com.messagebird.objects.VoiceType;
import com.messagebird.objects.VoiceMessage;
import com.messagebird.objects.VoiceMessageResponse;*/
import java.math.BigInteger;
import java.sql.*;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestData
{
	
	public static void main(String args[])
	{
		
      System.out.println("Initializing");
	
      long millis = System.currentTimeMillis() % 1000;
      Classify_BP bp=new Classify_BP();
     
	  Classify_HR hrc=new Classify_HR();
	  System.out.println("called hr class");
	  Classify_ECG ecgc=new Classify_ECG();
	  Classify_RR rrc=new Classify_RR();
	  Classify_SPO2 spo2c=new Classify_SPO2();
	  
	  final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 	   /* final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
	    final String USER="root";
	    final String PASS =""; */
		
		final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";
	    final String USER="root";
	    final String PASS ="oneadmin"; 
	    System.out.println("set the values");
	    Connection conn = null;
		Statement stmt = null;
		  
		  try
		   {
			  System.out.println("jdbc connection");
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      System.out.println("connect the connection");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      System.out.println("connected");
		      //STEP 4: Execute a query
		      
		      stmt = conn.createStatement();
		      
		      
		      String sql1="SELECT * from summary ; ";
		     // String nullvalue="NULL";
		     // String sql="SELECT gene from gene where pid="+pid+ "order by gene desc limit 1; ";
		      
		      ResultSet rs1 = stmt.executeQuery(sql1);
		      System.out.println("get arguments");
	// String gene="",sex=args[2],name=args[0];
	  
	 
		 
	/*	 int age=Integer.parseInt(args[1]);
		 int hr=Integer.parseInt(args[3]);
		 int rr=Integer.parseInt(args[4]);
		 int bp_s=Integer.parseInt(args[5]);
		 int ecg=Integer.parseInt(args[6]);
		 int spo2=Integer.parseInt(args[7]);
		 String pid=args[8];	*/  
		  
     	String gene="",sex="f",name="akshaya	";
	   int age=37;
		 int hr=26;
		 int rr=0;
		 int bp_s=0;
		 int ecg=414;
		 int spo2=0;
		 String pid="8232";  
	
		 String class_hr="",class_rr="",class_ecg="",class_bp="",class_spo2="";
		 String[] data={""+sex,""+age,""+hr,name,""+rr};
		 class_hr=hrc.hr_class(data);
		 gene+=find_gene(class_hr);
		 		 
		 String [] data1 = {""+sex,""+age,""+bp_s,name};
		 class_bp=bp.bp_class(data1);
		 gene+=find_gene(class_bp);
		 		 
		 String [] data2 = {""+sex,""+age,""+rr,name};
		 class_rr=rrc.rr_class(data2);
		 gene+=find_gene(class_rr);
		 
		 String [] data3 = {""+sex,""+age,""+ecg,name};
		 class_ecg=ecgc.ecg_class(data3);
		 gene+=find_gene(class_ecg);
		 
		 String [] data4 = {""+sex,""+age,""+spo2,name};
		 class_spo2=spo2c.spo2_class(data4);
		 gene+=find_gene(class_spo2);
		 
		 System.out.println("\nName : "+name+"\nGene Representation : "+gene+"\n"); 
		// System.out.println("Genetic Algorithm Started");
		new GA(pid,name,gene).run();
		 
		 
	//	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
  	   /* final String DB_URL = "jdbc:mysql://127.0.0.1:3306/project";
	    final String USER="root";
	    final String PASS =""; */
		
	/*	final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";
	    final String USER="root";
	    final String PASS ="oneadmin"; 
		
	    Connection conn = null;
		Statement stmt = null;
		  
		  try
		   {
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		     
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      
		      //STEP 4: Execute a query
		      
		      stmt = conn.createStatement();*/
		      
		      
		      String sql="SELECT gene from gene where pid=" +pid+"; ";
		     // String nullvalue="NULL";
		     // String sql="SELECT gene from gene where pid="+pid+ "order by gene desc limit 1; ";
		      
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.last();
		      
		     // while(rs.next())
		     // {
		    	  String gene1=rs.getString(1);
		    	  System.out.println("gene1 VALUE::"+gene1);
		    	  
		    	  if((gene1.equals("10000100000001000010000100")|| gene1.equals("1000010000100001000000100")|| 
		    		  gene1.equals("0100001000001000010001000")|| gene1.equals("1000010000100000000100001")|| 
		    		  gene1.equals("0000100001000010000100001")|| gene1.equals("0000100001000100001000010")|| 
		    		  gene1.equals("1000001000000010000101000")||gene1.equals("1000010000100001000000001"))&& !gene1.equals("null"))
		    	  {
		      
		    		  sql="SELECT MAX(time) from summary where pid="+pid+";";
		    		  rs = stmt.executeQuery(sql);
		    		  rs.next();
		    		  String date=rs.getString(1);
		    		  sql = "UPDATE summary SET abnormal=1 where time= '"+date+"' and pid="+pid+";";
		    		  stmt.executeUpdate(sql);
		    		  System.out.println("updated abnormal value");
		          }
		    // }
		   }
		   catch(Exception e)
		   {
			   System.out.println(e);
		   }
		
		//callme(class_hr,class_rr,class_bp,class_ecg,class_spo2);
		 //}
	       
      System.out.println(millis);
      System.out.println(System.currentTimeMillis() % 1000);
      //System.out.println("Time Taken :"+millis-System.currentTimeMillis() % 1000);
	
	}
	public static String find_gene(String clas)
	{
		String gene="";
		switch(clas)
		{
			case "Very High":
			case "VERY HIGH":
						gene="10000";
						break;
			case "High":
			case "HIGH":
						gene="01000";
						break;
			case "Normal":
			case "NORMAL":
						gene="00100";
						break;
			case "Low":
			case "LOW":
						gene="00010";
						break;
			case "Very Low":
			case "VERY LOW":
						gene="00001";
						break;
			default:
					gene="00000";
						break;
					
		}
		return gene;
	}
	
	
	
/*	 public static void callme(String hr,String rr,String bp,String ecg,String spo2) {
        
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl("live_P9uy00etxy7MA68u5SrY06wkt");

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
		String num="918754408132";
        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Generating Alert:");
            final List<BigInteger> phones = new ArrayList<BigInteger>();
            for (final String phoneNumber : num.split(",")) {
                phones.add(new BigInteger(phoneNumber));
            }

            // Send a voice message using the VoiceMessage object
            final VoiceMessage vm = new VoiceMessage("Class of BP:"+bp+" HR:"+hr+" RR:"+rr+" ECG:"+ecg+" SPO2:"+spo2, phones);
            vm.setIfMachine(IfMachineType.hangup);
            vm.setVoice(VoiceType.male);
            vm.setLanguage("en-gb");

            final VoiceMessageResponse response = messageBirdClient.sendVoiceMessage(vm);
            System.out.println(response.toString());

        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors() != null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() != null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        }
    }
 */
	
	/*public static void callme1(String Bp) {
        
        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl("live_fzIaSs2TDA0Kt4Mjw7TnIqQSw");

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
		String num="918807926821";
        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Sending message:");
            final List<BigInteger> phones = new ArrayList<BigInteger>();
            for (final String phoneNumber : num.split(",")) {
                phones.add(new BigInteger(phoneNumber));
            }

           final MessageResponse response = messageBirdClient.sendMessage("MessageBird", ""+Bp, phones);
            System.out.println(response.toString());

        } catch (UnauthorizedException unauthorized) {
            if (unauthorized.getErrors() != null) {
                System.out.println(unauthorized.getErrors().toString());
            }
            unauthorized.printStackTrace();
        } catch (GeneralException generalException) {
            if (generalException.getErrors() != null) {
                System.out.println(generalException.getErrors().toString());
            }
            generalException.printStackTrace();
        }*/
    

	
	
	
	}
	
	
