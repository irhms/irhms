package classification;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class Classify_RR {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";

	   //  Database credentials
	   static final String USER="root";
	   static final String PASS = "oneadmin";
 String current_class="";
	public String rr_class(String[] data)
	{
		char sex=data[0].charAt(0);
		int age=Integer.parseInt(data[1]);
		int rr=Integer.parseInt(data[2]);
		//float hr_prob=Float.parseFloat(data[3]);
		//String class_hr=data[4];
		float pn,ph,pvh,pl,pvl;
		String gene="";
		//System.out.println("Probability of Heart Rate :"+hr_prob);
		Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		     
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      
		      //STEP 4: Execute a query
		      
		      stmt = conn.createStatement();
		      String sql = "SELECT COUNT(*) FROM training_rr";
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.next();
		      float total=(float)rs.getInt(1);
		      ////System.out.println(total);
		      pn=ph=pvh=pl=pvl=total/5;
		      sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='NORMAL'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float an=rs.getInt(1),pan=an/total;
		       //System.out.println(pan);
		       
		       //System.out.println("Probability of given sex to be  Normal");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sn=rs.getInt(1),psn=sn/total;
		      //System.out.println(psn);
		      
		      //System.out.println("Probability of heart rate Normal");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hn=rs.getInt(1),phn=hn/total;
		      
		      //float final_normal=pn*pan*psn*phn;
		      
		      
		      //System.out.println("Probability of AGE HIGH");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float ah=rs.getInt(1),pah=ah/total;
		       //System.out.println(pah);
		       
		       //System.out.println("Probability of sex HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sh=rs.getInt(1),psh=sh/total;
		      //System.out.println(psh);
		      
		      //System.out.println("Probability of heart rate HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hh=rs.getInt(1),phh=hh/total;
		      
		      
		      //System.out.println("Probability of AGE LOW");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float al=rs.getInt(1),pal=al/total;
		       //System.out.println(pah);
		       
		       //System.out.println("Probability of sex LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sl=rs.getInt(1),psl=sl/total;
		      //System.out.println(psl);
		      
		      //System.out.println("Probability of heart rate LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hl=rs.getInt(1),phl=hl/total;
		    
		      
		      //System.out.println("Probability of AGE VERY   LOW");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='VERY LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avl=rs.getInt(1),pavl=avl/total;
		       //System.out.println(pah);
		       
		       //System.out.println("Probability of sex VERY   LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svl=rs.getInt(1),psvl=svl/total;
		      //System.out.println(psl);
		      
		      //System.out.println("Probability of heart rate VERY   LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvl=rs.getInt(1),phvl=hvl/total;
		    
		      
		      
		      //System.out.println("Probability of AGE VERY   HIGH");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='VERY HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avh=rs.getInt(1),pavh=avh/total;
		       //System.out.println(pah);
		       
		       //System.out.println("Probability of sex VERY   HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svh=rs.getInt(1),psvh=svh/total;
		      //System.out.println(psl);
		      float max_prob;
		      //System.out.println("Probability of heart rate VERY   HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='VERY HIGH'";
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
			  /*int tempclass_rr;
		      if(rr<=10)
		    	  tempclass_rr=1;
		      else if(rr<=14)
		    	  tempclass_rr=2;
		      else if(rr<=18)
		    	  tempclass_rr=3;
		      else if(rr<=22)
		    	  tempclass_rr=4;
		      else
		    	  tempclass_rr=5;
		      int temp=age%10;
		       sql = "SELECT count(*) FROM training WHERE lage<"+(age+(6-temp))+" AND hage>"+(age-(5-temp))+" AND class='"+class_hr+"'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float an=rs.getInt(1);
		       
		       sql="SELECT COUNT(*) FROM training_rr WHERE lage<"+(age-5)+" AND hage>"+(age+5)+"";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sn=rs.getInt(1);
		      if(sn==0) //m-estimate
		      {
		    	  sn+=1;
		    	  an+=1;
		      }
		      else if(an==0)
		      {  
		    	  an+=1;
		    	  sn+=1;
		      }
		      
		      float res=an/sn;
		      System.out.println("AN :"+an);
		      System.out.println(sn);
		      sql="SELECT prob_vl FROM belief_rr WHERE class_hr='"+class_hr+"' AND class_rr="+tempclass_rr+"";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float pvlow=rs.getFloat(1);
		      pvlow*=res;
		      
		      sql="SELECT prob_l FROM belief_rr WHERE class_hr='"+class_hr+"' AND class_rr="+tempclass_rr+"";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float plow=rs.getFloat(1);

		      plow*=res;
		      
		      sql="SELECT prob_n FROM belief_rr WHERE class_hr='"+class_hr+"' AND class_rr="+tempclass_rr+"";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float pnormal=rs.getFloat(1);
		      

		      pnormal*=res;
		      
		      sql="SELECT prob_h FROM belief_rr WHERE class_hr='"+class_hr+"' AND class_rr="+tempclass_rr+"";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float phigh=rs.getFloat(1);

		      phigh*=res;
		      
		      
		      sql="SELECT prob_vh FROM belief_rr WHERE class_hr='"+class_hr+"' AND class_rr="+tempclass_rr+"";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float pvhigh=rs.getFloat(1);

		      pvhigh*=res;
		      
		      
		      //System.out.println("Belief Network");
		      System.out.println("Very Low :"+pvlow+"\n"+"Low :"+plow+"\n"+"Normal :"+pnormal+"\n"+"High :"+phigh+"\n"+"Very High :"+pvhigh);
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
		      
		      else{ //if(pvhigh>pvlow && pvhigh>phigh && pvhigh>pnormal && pvhigh>plow) {
		    	  current_class="VERY HIGH";
		    	  gene="10000";
		    	    
		      }
		    /*  else
		      {
		    	  Class="INVALID";
		    	  System.out.println("Max Probability is 0. Invalid Data");
		    	  gene="00000";
		      }
		      sql="INSERT INTO comparison1 values('"+Class+"')";
		      stmt.executeUpdate(sql);
		      
		      System.out.println("\nClass of Heart rate :"+ class_hr);
		      */System.out.println("\nClass of Respiratory rate :"+current_class);
		      
		      
		      /* System.out.println("Probability of given AGE to be  NORMAL");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='NORMAL'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float an=rs.getInt(1),pan=an/total;
		       ////System.out.println(pan);
		       
		       ////System.out.println("Probability of given sex to be  Normal");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sn=rs.getInt(1),psn=sn/total;
		      ////System.out.println(psn);
		      
		      ////System.out.println("Probability of Respiratory rate Normal");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='NORMAL'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hn=rs.getInt(1),phn=hn/total;
		      
		      //float final_normal=pn*pan*psn*phn;
		      
		      
		      //System.out.println("Probability of AGE HIGH");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float ah=rs.getInt(1),pah=ah/total;
		       ////System.out.println(pah);
		       
		       ////System.out.println("Probability of sex HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sh=rs.getInt(1),psh=sh/total;
		      ////System.out.println(psh);
		      
		      ////System.out.println("Probability of Respiratory rate HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hh=rs.getInt(1),phh=hh/total;
		      
		      
		      ////System.out.println("Probability of AGE LOW");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float al=rs.getInt(1),pal=al/total;
		       ////System.out.println(pah);
		       
		       ////System.out.println("Probability of sex LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float sl=rs.getInt(1),psl=sl/total;
		      ////System.out.println(psl);
		      
		      ////System.out.println("Probability of Respiratory rate LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hl=rs.getInt(1),phl=hl/total;
		    
		      
		      ////System.out.println("Probability of AGE VERY   LOW");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='VERY LOW'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avl=rs.getInt(1),pavl=avl/total;
		      // //System.out.println(pah);
		       
		       ////System.out.println("Probability of sex VERY   LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svl=rs.getInt(1),psvl=svl/total;
		      ////System.out.println(psl);
		      
		      ////System.out.println("Probability of Respiratory rate VERY   LOW");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='VERY LOW'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvl=rs.getInt(1),phvl=hvl/total;
		    
		      
		      
		      ////System.out.println("Probability of AGE VERY   HIGH");
		       sql = "SELECT count(*) FROM training_rr WHERE lage<"+age+" AND hage>"+age+" AND class='VERY HIGH'";
		       rs = stmt.executeQuery(sql);
		       rs.next();
		       float avh=rs.getInt(1),pavh=avh/total;
		      // //System.out.println(pah);
		       
		       ////System.out.println("Probability of sex VERY   HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE sex='"+sex+"' AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float svh=rs.getInt(1),psvh=svh/total;
		     // //System.out.println(psl);
		      
		      ////System.out.println("Probability of Respiratory rate VERY   HIGH");
		      sql="SELECT COUNT(*) FROM training_rr WHERE lrr<"+rr+" AND hrr>"+rr+" AND class='VERY HIGH'";
		      rs=stmt.executeQuery(sql);
		      rs.next();
		      float hvh=rs.getInt(1),phvh=hvh/total;
		    
		      
		      
		      float pnormal=pn*pan*psn*phn,plow=pl*pal*psl*phl,
		    		  phigh=ph*pah*psh*phh,pvlow=pvl*pavl*psvl*phvl,
		    				  pvhigh=pvh*pavh*psvh*phvh; 
		      String Class="";
		      if(pnormal>plow && pnormal>pvhigh && pnormal>phigh && pnormal>pvlow){
		    	  Class="NORMAL";
		    	  gene="00100";
		    	  }
		      else if(phigh>plow && phigh>pvhigh && phigh>pnormal && phigh>pvlow){
		    	  Class="HIGH";
		    	  gene="01000";
		    	  }
		      else if(plow>pnormal && plow>pvhigh && plow>phigh && plow>pvlow){
		    	  Class="LOW";
		    	  gene="00010";
		    	  }
		      else if(pvlow>plow && pvlow>pvhigh && pvlow>phigh && pvlow>pnormal){
		    	  Class="VERY LOW";
		    	  gene="00001";
		    	  }
		      else {
		    	  Class="VERY HIGH";
		    	  gene="10000";
		    	    
		      }

		   */System.out.println("Name: "+data[3]+"  Age : "+age+"  Sex: "+sex+"  Respiratory rate: "+rr);
		     System.out.println("Class : "+current_class+"  Chromosome : "+gene);
		   
		      
		      
		      
		   }
		   catch(Exception e)
		   {
			   System.out.println("Exception RR"+ e);
		   }
		      
		   return current_class;
	}
	}
