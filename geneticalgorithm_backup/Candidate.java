package geneticalgorithm;
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
				return sb.toString();
			
		//	return null;
    }
	 /*
		Determine severity Index and Fitness value 
	 */
    float fitness()
{
		String genotype="";//nb
		int i;
		int C[]=new int[5];
		int W[]=new int[5];
		
		/*  W[i] stores initialized weights for each level of severity*/ 

		for(int z=0;z<5;z++)
		{
			if(z==0||z==4)
				W[z]=9;
			else if(z==1||z==3)
				W[z]=4;
			else
				W[z]=1;
		}

		/* C stores number of 1s in each position of the 5 individual genes*/
        for (i = 0; i < genotype.lengeth; i++) {
            if (genotype.charAt(i)=='1')
			{
				if(i%5==0)
					C[0]++;
				if(i%5==1)
					C[1]++;
				if(i%5==2)
					C[2]++;
				if(i%5==3)
					C[3]++;
				if(i%5==4)
					C[4]++;
			}
        }
		/*for(int m=0;m<5;m++)
		{
			System.out.println(C[m]+" ");
		}*/
                /* Count the number of zeroes in C */
		int zeros=0;
		for(i=0;i<5;i++)
		{
			if(C[i]==0)
				zeros++;
		}
		/*System.out.println("");
		System.out.println(zeros);*/
		
		/* DIstribute probablities equally based on number of zeroes*/
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
		//System.out.println(tomul);
		/* Calculate Computed abnormality and severity index based on W, C and number of zeroes */
		float AC=0;
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
			System.out.println("AC "+AC);
			System.out.println("SI "+SI);
			//if(z==1)
	float sum=(AC+SI)/2;
				
//			{
				System.out.println("\nSeverity Index : "+sum);
				//z++;
				//}
			
			//SI*=5;
			
        return sum;
    }
 
    public int compareTo(Candidate o) {
        float f1 = this.fitness();
        float f2 = o.fitness();
 
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
			}
			maxfitofcur=fitness();
			return "gene=" + gene() + " fit=" + fitness();
		}
		else
			return "fit=" + fitness();
		
}
}