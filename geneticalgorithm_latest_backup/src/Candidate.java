
import java.util.Arrays;
import java.util.Random; 
public class Candidate implements Comparable<Candidate> {
		
				  
	public static float maxfitofcur,maxfitoverall;
    public static final int SIZE = 25;
    public boolean[] genotype;
    public String maxfitchromosome;
    final Random rand = new Random();
 int z=1;
    public Candidate() {
    	
        genotype = new boolean[SIZE];
        
    }
	float getMax()
	{
		return maxfitoverall;
	}
	String getGene()
	{
		return maxfitchromosome;
	}
	float getLocalMax()
	{
		return maxfitofcur;
	}
    void random() {
		int i;
		for(i=0;i<25;i++){
			genotype[i]=false;
			}
        for (i = 0; i < genotype.length;) {
		{
				int randoms=rand.nextInt(5);
				genotype[i+randoms]=true;
				i=i+5;
        }
    }
	}
 
    private String gene() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genotype.length; i++) {
            sb.append(genotype[i] == true ? 1 : 0);
			
		}
		//if(sb.toString()!="null")
       // System.out.println("gene in candidate file");
				return sb.toString();
			
		//	return null;
    }
 /*
 */
    float fitness() {
        int i;
		int C[]=new int[5];
		int W[]=new int[5];
		for(i=0;i<5;i++)
		{
			if (genotype[i]==true)
			{
				if(i%5==0)
					C[0]++;
				else if(i%5==1)
					C[1]++;
				else if(i%5==2)
					C[2]++;
				else if(i%5==3)
					C[3]++;
				else if(i%5==4)
					C[4]++;
			}
		}
		//for(int j=0;j<5;j++){
			/*for (i = 0; i < 5; i++) {
				if (genotype[i]==true)
				{
					if(i%5==0)
						C[0]=5;
					if(i%5==1)
						C[0]=3;
					if(i%5==2)
						C[0]=1;
					if(i%5==3)
						C[0]=2;
					if(i%5==4)
						C[0]=4;
				}
			}
			for (i = 5; i < 10; i++) {
				if (genotype[i]==true)
				{
					if(i%5==0)
						C[1]=5;
					if(i%5==1)
						C[1]=4;
					if(i%5==2)
						C[1]=1;
					if(i%5==3)
						C[1]=2;
					if(i%5==4)
						C[1]=3;
				}
			}
			for (i = 10; i < 15; i++) {
				if (genotype[i]==true)
				{
					if(i%5==0)
						C[2]=5;
					if(i%5==1)
						C[2]=4;
					if(i%5==2)
						C[2]=1;
					if(i%5==3)
						C[2]=2;
					if(i%5==4)
						C[2]=5;
				}
			}
			for (i = 15; i < 20; i++) {
				if (genotype[i]==true)
				{
					if(i%5==0)
						C[3]=5;
					if(i%5==1)
						C[3]=4;
					if(i%5==2)
						C[3]=1;
					if(i%5==3)
						C[3]=2;
					if(i%5==4)
						C[3]=3;
				}
			}
			for (i = 20; i < 25; i++) {
				if (genotype[i]==true)
				{
					if(i%5==0)
						C[4]=1;
					if(i%5==1)
						C[4]=1;
					if(i%5==2)
						C[4]=1;
					if(i%5==3)
						C[4]=3;
					if(i%5==4)
						C[4]=4;
				}
			}
			int CV[]=new int[5];
			for(int m=0;m<5;m++)
			{
				if(C[m]==1)
					CV[0]++;
				else if(C[m]==2)
					CV[1]++;
				else if(C[m]==3)
					CV[2]++;
				else if(C[m]==4)
					CV[3]++;
				else if(C[m]==5)
					CV[4]++;
			}
		//}*/
		int zeros=0;
		for(i=0;i<5;i++)
		{
			if(C[i]==0)
				zeros++;
		}
		for(int z=0;z<5;z++)
		{
			//W[z]=(z+1)*(z+1);
			if(z==0||z==4)
				W[z]=9;
			else if(z==1||z==3)
				W[z]=4;
			else
				W[z]=1;
		}
		float tomul=0;
		if(zeros==0)
			tomul=(float)0.2;
		else if(zeros==1)
			tomul=(float)0.25;
		else if(zeros==2)
			tomul=(float)0.33;
		else if(zeros==3)
			tomul=(float)0.5;
		else if(zeros==4)
			tomul=(float)1;
		int AC=0;
		float SI=0;
			for(i=0;i<5;i++)
			{
				AC+=(W[i]*C[i]);
				if(i==0||i==4)
					SI+=(C[i]*tomul*0.4);
				if(i==1||i==3)
					SI+=(C[i]*tomul*0.15);
				else
					SI+=(C[i]*tomul*0.05);
			}
			AC/=25;
			//SI*=5;
			SI=(AC+SI)/2;
			//SI*=5;
			//float sum=(AC+SI)/2;
		//System.out.println("SEVERITY INDEX DAWW "+SI);
        return SI;
    }
 
    public int compareTo(Candidate o) {
        float f1 = this.fitness();
        float f2 = o.fitness();
      //  System.out.println("F1 VALUE::"+f1);
      //  System.out.println("F2 VALUE::"+f2);
 
        if (f1 < f2)
            return 1;
        else if (f1 > f2)
            return -1;
        else
            return 0;
    }
    String tostr()
    {
    	String s="";
    	for(int i=0;i<25;i++)
    	{
    		if(genotype[i]==true)
    			s+="1";
    		else
    			s+="0";
    	}
    	  	return s;
    }
 @Override
    public String toString() {
		if(fitness()>maxfitofcur)
		{
			if(fitness()>maxfitoverall)
			{
				maxfitoverall=fitness();
				
				maxfitchromosome=tostr();
				
				System.out.println("Maximum fitness value is "+maxfitoverall+"\n"+gene());
				//System.out.println("maxfitchromosome::"+maxfitchromosome);
			}
			maxfitofcur=fitness();
			return "gene=" + gene() + " fit=" + fitness();
		}
		else
			return "fit=" + fitness();
		
}
}