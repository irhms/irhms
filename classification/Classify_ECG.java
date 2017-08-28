package classification;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Classify_ECG {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	   static final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";

	   //  Database credentials
	   static final String USER="root";
	   static final String PASS = "oneadmin";
	 String current_class="";
	 // classification ECG based on bayesian belief classification method
	public String ecg_class(String[] data)
	{
		char sex=data[0].charAt(0);
		int age=Integer.parseInt(data[1]);
		int ecg=Integer.parseInt(data[2]);
		float pn,ph,pvh,pl,pvl;
		String gene="";
		Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);

		      //STEP 4: Execute a query
		      stmt = conn.createStatement();
		      String sql = "SELECT COUNT(*) FROM training_ecg";
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.next();
		      float total=(float)rs.getInt(1);
		      pn=ph=pvh=pl=pvl=total/5;

		      //Probability of given AGE to be  NORMAL
		       sql = "SELECT count(*) FROM training_ecg WHERE lage<"+age+" AND hage>"+age+" AND class='NORMAL'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float an=rs.getInt(1),pan=an/total;

		       //Probability of given sex to be  Normal
		      sql="SELECT COUNT(*) FROM training_ecg WHERE sex='"+sex+"' AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sn=rs.getInt(1),psn=sn/total;

		      //Probability of ECG rate Normal
		      sql="SELECT COUNT(*) FROM training_ecg WHERE lecg<"+ecg+" AND hecg>"+ecg+" AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hn=rs.getInt(1),phn=hn/total;

		      //Probability of AGE HIGH
		       sql = "SELECT count(*) FROM training_ecg WHERE lage<"+age+" AND hage>"+age+" AND class='HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float ah=rs.getInt(1),pah=ah/total;

		       //Probability of sex HIGH
		      sql="SELECT COUNT(*) FROM training_ecg WHERE sex='"+sex+"' AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sh=rs.getInt(1),psh=sh/total;

		      //Probability of ECG rate HIGH
		      sql="SELECT COUNT(*) FROM training_ecg WHERE lecg<"+ecg+" AND hecg>"+ecg+" AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hh=rs.getInt(1),phh=hh/total;

		      //Probability of AGE LOW
		       sql = "SELECT count(*) FROM training_ecg WHERE lage<"+age+" AND hage>"+age+" AND class='LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float al=rs.getInt(1),pal=al/total;

		       //Probability of sex LOW
		      sql="SELECT COUNT(*) FROM training_ecg WHERE sex='"+sex+"' AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sl=rs.getInt(1),psl=sl/total;

		      //Probability of ECG rate LOW
		      sql="SELECT COUNT(*) FROM training_ecg WHERE lecg<"+ecg+" AND hecg>"+ecg+" AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hl=rs.getInt(1),phl=hl/total;

		      //Probability of AGE VERY   LOW
		       sql = "SELECT count(*) FROM training_ecg WHERE lage<"+age+" AND hage>"+age+" AND class='VERY LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avl=rs.getInt(1),pavl=avl/total;

		       //Probability of sex VERY   LOW
		      sql="SELECT COUNT(*) FROM training_ecg WHERE sex='"+sex+"' AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svl=rs.getInt(1),psvl=svl/total;

		      //Probability of ECG rate VERY   LOW
		      sql="SELECT COUNT(*) FROM training_ecg WHERE lecg<"+ecg+" AND hecg>"+ecg+" AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvl=rs.getInt(1),phvl=hvl/total;

		      //Probability of AGE VERY   HIGH
		       sql = "SELECT count(*) FROM training_ecg WHERE lage<"+age+" AND hage>"+age+" AND class='VERY HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avh=rs.getInt(1),pavh=avh/total;

		       //Probability of sex VERY   HIGH
		      sql="SELECT COUNT(*) FROM training_ecg WHERE sex='"+sex+"' AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svh=rs.getInt(1),psvh=svh/total;

		      //Probability of ECG rate VERY   HIGH
		      sql="SELECT COUNT(*) FROM training_ecg WHERE lecg<"+ecg+" AND hecg>"+ecg+" AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvh=rs.getInt(1),phvh=hvh/total;

		      float pnormal=pn*pan*psn*phn,plow=pl*pal*psl*phl,
		    		  phigh=ph*pah*psh*phh,pvlow=pvl*pavl*psvl*phvl,
		    				  pvhigh=pvh*pavh*psvh*phvh;

		      if(pnormal>plow && pnormal>pvhigh && pnormal>phigh && pnormal>pvlow){
		    	  current_class="NORMAL";
		    	  gene="00100";
		    	  }
		      else if(phigh>plow && phigh>pvhigh && phigh>pnormal && phigh>pvlow){
		    	  current_class="HIGH";
		    	  gene="01000";
		    	  }
		      else if(plow>pnormal && plow>pvhigh && plow>phigh && plow>pvlow){
		    	  current_class="LOW";
		    	  gene="00010";
		    	  }
		      else if(pvlow>plow && pvlow>pvhigh && pvlow>phigh && pvlow>pnormal){
		    	  current_class="VERY LOW";
		    	  gene="00001";
		    	  }
		      else {
		    	  current_class="VERY HIGH";
		    	  gene="10000";
		    	    }
		  		   System.out.println("Name: "+data[3]+"  Age : "+age+"  Sex: "+sex+"  Ecg: "+ecg);
		   	 		 System.out.println("Class : "+current_class+"  Chromosome : "+gene);
		   }
		   catch(Exception e)
		   {
			   System.out.println("Exception in ECG" + e);
		   }
		   return current_class;
	}
	}
