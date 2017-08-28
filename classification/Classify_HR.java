package classification;
import java.sql.*;
public class Classify_HR {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	   static final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";

	   //  Database credentials
	   static final String USER="root";
	   static final String PASS = "oneadmin";
	   Classify_RR rr_c=new Classify_RR();
	   float max_prob;
	   String current_class="";
// classification Heart rate based on bayesian belief classification method
	public String hr_class(String[] data)
	{
		char sex=data[0].charAt(0);
		int age=Integer.parseInt(data[1]);
		int hr=Integer.parseInt(data[2]);
		String name=data[3];
		int rr=Integer.parseInt(data[4]);
		float pn,ph,pvh,pl,pvl;
		String gene="";
		Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection

		      //System.out.println();

		      //System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      //System.out.println("Connected database successfully...");

		      //STEP 4: Execute a query
		      //System.out.println("Creating statement...");
		      //System.out.println();
		      //System.out.println();

		      stmt = conn.createStatement();
		      String sql = "SELECT COUNT(*) FROM training";
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.next();
		      float total=(float)rs.getInt(1);
		      ////System.out.println(total);
		      pn=ph=pvh=pl=pvl=total/5;

		      //System.out.println("Probability of given AGE to be  NORMAL");
		       sql = "SELECT count(*) FROM training WHERE lage<"+age+" AND hage>"+age+" AND class='NORMAL'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float an=rs.getInt(1),pan=an/total;
		       //System.out.println(pan);

		       //System.out.println("Probability of given sex to be  Normal");
		      sql="SELECT COUNT(*) FROM training WHERE sex='"+sex+"' AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sn=rs.getInt(1),psn=sn/total;
		      //System.out.println(psn);

		      //System.out.println("Probability of heart rate Normal");
		      sql="SELECT COUNT(*) FROM training WHERE lhr<"+hr+" AND hhr>"+hr+" AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hn=rs.getInt(1),phn=hn/total;

		      //float final_normal=pn*pan*psn*phn;


		      //System.out.println("Probability of AGE HIGH");
		       sql = "SELECT count(*) FROM training WHERE lage<"+age+" AND hage>"+age+" AND class='HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float ah=rs.getInt(1),pah=ah/total;
		       //System.out.println(pah);

		       //System.out.println("Probability of sex HIGH");
		      sql="SELECT COUNT(*) FROM training WHERE sex='"+sex+"' AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sh=rs.getInt(1),psh=sh/total;
		      //System.out.println(psh);

		      //System.out.println("Probability of heart rate HIGH");
		      sql="SELECT COUNT(*) FROM training WHERE lhr<"+hr+" AND hhr>"+hr+" AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hh=rs.getInt(1),phh=hh/total;


		      //System.out.println("Probability of AGE LOW");
		       sql = "SELECT count(*) FROM training WHERE lage<"+age+" AND hage>"+age+" AND class='LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float al=rs.getInt(1),pal=al/total;
		       //System.out.println(pah);

		       //System.out.println("Probability of sex LOW");
		      sql="SELECT COUNT(*) FROM training WHERE sex='"+sex+"' AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sl=rs.getInt(1),psl=sl/total;
		      //System.out.println(psl);

		      //System.out.println("Probability of heart rate LOW");
		      sql="SELECT COUNT(*) FROM training WHERE lhr<"+hr+" AND hhr>"+hr+" AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hl=rs.getInt(1),phl=hl/total;


		      //System.out.println("Probability of AGE VERY   LOW");
		       sql = "SELECT count(*) FROM training WHERE lage<"+age+" AND hage>"+age+" AND class='VERY LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avl=rs.getInt(1),pavl=avl/total;
		       //System.out.println(pah);

		       //System.out.println("Probability of sex VERY   LOW");
		      sql="SELECT COUNT(*) FROM training WHERE sex='"+sex+"' AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svl=rs.getInt(1),psvl=svl/total;
		      //System.out.println(psl);

		      //System.out.println("Probability of heart rate VERY   LOW");
		      sql="SELECT COUNT(*) FROM training WHERE lhr<"+hr+" AND hhr>"+hr+" AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvl=rs.getInt(1),phvl=hvl/total;



		      //System.out.println("Probability of AGE VERY   HIGH");
		       sql = "SELECT count(*) FROM training WHERE lage<"+age+" AND hage>"+age+" AND class='VERY HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avh=rs.getInt(1),pavh=avh/total;
		       //System.out.println(pah);

		       //System.out.println("Probability of sex VERY   HIGH");
		      sql="SELECT COUNT(*) FROM training WHERE sex='"+sex+"' AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svh=rs.getInt(1),psvh=svh/total;
		      //System.out.println(psl);

		      //System.out.println("Probability of heart rate VERY   HIGH");
		      sql="SELECT COUNT(*) FROM training WHERE lhr<"+hr+" AND hhr>"+hr+" AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvh=rs.getInt(1),phvh=hvh/total;



		      float pnormal=pn*pan*psn*phn,plow=pl*pal*psl*phl,
		    		  phigh=ph*pah*psh*phh,pvlow=pvl*pavl*psvl*phvl,
		    				  pvhigh=pvh*pavh*psvh*phvh;

		      if(pnormal>plow && pnormal>pvhigh && pnormal>phigh && pnormal>pvlow){
		    	  current_class="NORMAL";
		    	  gene="00100";
		    	  max_prob=pnormal;
		    	  }
		      else if(phigh>plow && phigh>pvhigh && phigh>pnormal && phigh>pvlow){
		    	  current_class="HIGH";
		    	  gene="01000";
		    	  max_prob=phigh;
		    	  }
		      else if(plow>pnormal && plow>pvhigh && plow>phigh && plow>pvlow){
		    	  current_class="LOW";
		    	  gene="00010";
		    	  max_prob=plow;
		    	  }
		      else if(pvlow>plow && pvlow>pvhigh && pvlow>phigh && pvlow>pnormal){
		    	  current_class="VERY LOW";
		    	  gene="00001";
		    	  max_prob=pvlow;
		    	  }
		      else {
		    	  current_class="VERY HIGH";
		    	  gene="10000";
		    	  max_prob=pvhigh;
		      }

		   System.out.println("Name: "+data[3]+"  Age : "+age+"  Sex: "+sex+"  Heart rate: "+hr);
		   System.out.println("Class : "+current_class+"  Chromosome : "+gene);

		   }
		   catch(Exception e)
		   {}
		   String[] data1={""+sex,""+age,""+rr,""+max_prob,current_class};
		   //gene+=rr_c.rr_class(data1);
		   //System.out.println(gene);
		   return current_class;
	}
}
