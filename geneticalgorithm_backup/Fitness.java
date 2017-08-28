import java.io.*;
import java.util.Scanner;
float fitness()
{
		String genotype="";//nb
		int i;
		int C[]=new int[5];
		int W[]=new int[5];
		for(int z=0;z<5;z++)
		{
			if(z==0||z==4)
				W[z]=9;
			else if(z==1||z==3)
				W[z]=4;
			else
				W[z]=1;
		}

 	/*
         Compute Severity Index using this function C[i] is the count array
         that stores how many genes in the current chromosome belong to class
         'i'
         */

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
		int zeros=0;
		for(i=0;i<5;i++)
		{
			if(C[i]==0)
				zeros++;
		}
		/*System.out.println("");
		System.out.println(zeros);*/
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
 
}