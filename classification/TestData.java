package classification;

import geneticalgorithm.GA;

import com.messagebird.MessageBirdClient;

import com.messagebird.MessageBirdService;

import com.messagebird.MessageBirdServiceImpl;

import com.messagebird.exceptions.GeneralException;

import com.messagebird.exceptions.UnauthorizedException;

import com.messagebird.objects.IfMachineType;

import com.messagebird.objects.VoiceType;

import com.messagebird.objects.VoiceMessage;

import com.messagebird.objects.VoiceMessageResponse;
import java.*;

import java.math.*;

import  java.math.BigInteger;

import java.util.ArrayList;

import java.util.List;
import java.sql.*;
import java.io.IOException;

import java.util.logging.FileHandler;

import java.util.logging.Logger;

import java.util.logging.Level;

import java.util.logging.SimpleFormatter;

public class TestData
 {
	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final static String DB_URL = "jdbc:mysql://192.168.108.40:3306/project?zeroDateTimeBehavior=convertToNull";
    final static String USER="root";
    final static String PASS ="oneadmin";

   static Connection conn = null;
   static Statement stmt = null;
   static ResultSet rs;

   static Logger logger = Logger.getLogger("MyLog");
   static FileHandler fh;
   static SimpleFormatter formatter = new SimpleFormatter();

	public static void main(String args[]) throws IOException,SecurityException
	{
		System.out.println("Initializing_classification");
		boolean append=true;
		fh = new FileHandler("jexecLog.txt", append);
	  logger.addHandler(fh);
    fh.setFormatter(formatter);
		logger.info("Abnormality checking...");
  	 long millis = System.currentTimeMillis() % 1000;
      Classify_BP bp=new Classify_BP();
	 	  Classify_HR hrc=new Classify_HR();
		  Classify_ECG ecgc=new Classify_ECG();
		  Classify_RR rrc=new Classify_RR();
		  Classify_SPO2 spo2c=new Classify_SPO2();
logger.info("classify called");
		  String gene="";
		 String sex=args[2];
		 String name=args[9];
		 int age=Integer.parseInt(args[1]);
		 int hr=Math.round(Float.parseFloat(args[3]));
		 int rr=Math.round(Float.parseFloat(args[4]));
		 int bp_s=Math.round(Float.parseFloat(args[5]));
		 int ecg=Math.round(Float.parseFloat(args[6]));
		 int spo2=Math.round(Float.parseFloat(args[7]));
		 String pid=args[0];
		 int COUNTFALL=Integer.parseInt(args[8]);

		 logger.info("classifier process start...HR= "+hr+"RR="+rr+"BP="+bp_s+"ECG="+ecg+"SPO2="+spo2);
     // vital parameter classifed based on Bayes and basiean beilf classification
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
	        logger.info("Gene Rep::"+pid+" of "+gene);
      // call genetic algorithm function
		 new GA(pid,name,gene).run();

		String gene1=gene;
		String gen_HR=gene1.substring(0, 5);
		String gen_RR=gene1.substring(5, 10);
		String gen_BP=gene1.substring(10, 15);
		String gen_ECG=gene1.substring(15, 20);
		String gen_SPO2=gene1.substring(20, 25);

		if ((gen_HR.charAt(0)=='1'||gen_HR.charAt(4)=='1') && (gen_SPO2.charAt(0)=='1'|| gen_SPO2.charAt(4)=='1')||
			(gen_RR.charAt(0)=='1'||gen_RR.charAt(4)=='1') && (gen_SPO2.charAt(0)=='1'||gen_SPO2.charAt(4)=='1') ||
			(gen_SPO2.charAt(0)=='1'||gen_SPO2.charAt(4)=='1')|| COUNTFALL>0)
			 {
				 System.out.println("VeryHigh or verylow");
				 abnormalityset(pid);
 				callme(class_hr,class_rr,class_bp,class_ecg,class_spo2,pid);
				System.out.println("done");
			 }
		else if ((gen_HR.charAt(1)=='1'||gen_HR.charAt(3)=='1')&& (gen_RR.charAt(1)=='1'|| gen_RR.charAt(3)=='1') && COUNTFALL>0 )
			 {
				 System.out.println("High or low in heartrate and respiration rate");
				 abnormalityset(pid);
				 callme(class_hr,class_rr,class_bp,class_ecg,class_spo2,pid);
			 }
		else if ((gen_HR.charAt(1)=='1'|| gen_HR.charAt(3)=='1') && (gen_SPO2.charAt(1)=='1'|| gen_SPO2.charAt(3)=='1')&& COUNTFALL>0 )
			{
				 System.out.println("High or low in heartrate and respiration rate");
				 abnormalityset(pid);
				 callme(class_hr,class_rr,class_bp,class_ecg,class_spo2,pid);
			 }
		else if ((gen_HR.charAt(1)=='1'||gen_HR.charAt(3)=='1') && (gen_RR.charAt(1)=='1'|| gen_RR.charAt(3)=='1') &&
					  (gen_SPO2.charAt(1)=='1'|| gen_SPO2.charAt(3)=='1') && COUNTFALL>0)
			 {
				 System.out.println("High or low in heartrate, respiration rate and SPO2");
				 abnormalityset(pid);
				 callme(class_hr,class_rr,class_bp,class_ecg,class_spo2,pid);
			 }
		 else if (((gen_RR.charAt(1)=='1')||(gen_RR.charAt(3)=='1'))&&((gen_SPO2.charAt(1)=='1')||(gen_SPO2.charAt(3)=='1')))
			 {
				 System.out.println("High or low in respiration rate and SPO2");
				 abnormalityset(pid);
				 callme(class_hr,class_rr,class_bp,class_ecg,class_spo2,pid);
			 }
		 else
				 System.out.println("Normal");
		}
// set abnormal value with 1
public static void abnormalityset(String pid)
{
	System.out.println("Abnormality function set calling");
	try
	   {
	      Class.forName(JDBC_DRIVER);
    	      conn = DriverManager.getConnection(DB_URL, USER, PASS);

	      stmt = conn.createStatement();
	      String sql="SELECT MAX(time) from summary where pid="+pid+";";
	      ResultSet rs = stmt.executeQuery(sql);
	      rs.next();
	      String date=rs.getString(1);
	      sql = "UPDATE summary SET abnormal=1 where time= '"+date+"' and pid="+pid+";";
	      stmt.executeUpdate(sql);
	      System.out.println("updated abnormal value");
	   }
	  catch(Exception e)
	   {
		System.out.println(e);
	   }
}
// find gene for each parameter
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

// Generate voice alert message
	public static void callme(String hr,String rr,String bp,String ecg,String spo2,String pid)
	{

       // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl("live_EdBUBLRfa5vlaHDdxL49xlamD");

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);
        int abn,len,doctorid;
        String code="91";
	System.out.println("call me function");
	//String num="918754408132";
        try {
        	System.out.println("Generating Alert:");
        	Class.forName(JDBC_DRIVER);
  	      	conn = DriverManager.getConnection(DB_URL, USER, PASS);

		    stmt = conn.createStatement();
		    String sql="SELECT abnormal,did from summary where pid="+pid+" order by time desc;";
		    ResultSet rs = stmt.executeQuery(sql);
		    rs.next();
           while(rs.next())
           {
        	   abn=Integer.parseInt(rs.getString(1));
        	   doctorid=Integer.parseInt(rs.getString(2));
        	  System.out.println("abnormal:"+abn+"doctor id :" +doctorid);

		   if (abn==1)
        		   break;
        	   else
        	   {
			System.out.println("voice call");
//			break;
	                String sql1="SELECT phone from doctor where did="+doctorid+";";
       		    	rs = stmt.executeQuery(sql1);
       		    	rs.next();
       		    	String num=rs.getString(1);
			System.out.println("phone number:"+num);
       		    	len=(num.trim()).length();
       		    	if(len==10 || len==12)
       		    	{
       		    		num=len==12 ?num :code.concat(num);
				System.out.println("phone number:"+num);

       		    		final List<BigInteger> phones = new ArrayList<BigInteger>();
       		    		for (final String phoneNumber : num.split(","))
       		    		{
       		    	            phones.add(new BigInteger(phoneNumber));
       		                }
       		        	// Send a voice message using the VoiceMessage object
		            final VoiceMessage vm = new VoiceMessage("Class of BP:"+bp+" HR:"+hr+" RR:"+rr+" ECG:"+ecg+" SPO2:"+spo2, phones);

    		            vm.setIfMachine(IfMachineType.hangup);
       		            vm.setVoice(VoiceType.male);
      		            vm.setLanguage("en-gb");

       		            final VoiceMessageResponse response = messageBirdClient.sendVoiceMessage(vm);
       		        	}
       		    	else
       		    		System.out.println("invalid phone number");

        	   }
           }
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
	catch(Exception e)
		   {
	//		throw new RuntimeException(e);
			   System.out.println(e);
		   }

    }
	}
