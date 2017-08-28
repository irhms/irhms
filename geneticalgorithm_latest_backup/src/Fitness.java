
import java.io.*;
import java.util.Scanner;
public class Fitness{
public static void main(String args[]){
String genotype="";
Scanner ip=new Scanner(System.in);
genotype=ip.nextLine();

 int i;
		int C[]=new int[5];
		int W[]=new int[5];
		
		//for(int j=0;j<5;j++){
			for (i = 0; i < 5; i++) {
				if (genotype.charAt(i)=='1')
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
				if (genotype.charAt(i)=='1')
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
				if (genotype.charAt(i)=='1')
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
				if (genotype.charAt(i)=='1')
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
				if (genotype.charAt(i)=='1')
				{
					if(i%5==0)
						C[4]=1;
					if(i%5==1)
						C[4]=2;
					if(i%5==2)
						C[4]=3;
					if(i%5==3)
						C[4]=4;
					if(i%5==4)
						C[4]=5;
				}
			}
			System.out.println("C");
			for(i=0;i<5;i++)
			{
				System.out.println(C[i]+" ");
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
			System.out.println("CV");
			for(i=0;i<5;i++)
			{
				System.out.println(CV[i]+" ");
			}
		//}

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
			System.out.println("W");
			for(i=0;i<5;i++)
			{
				System.out.println(W[i]+" ");
			}
		float AC=0;
		float SI=0;
			for(i=0;i<5;i++)
			{
				AC+=(W[i]*C[i]);
				System.out.println("AC "+AC);
				if(i==0||i==4)
					SI+=(CV[i]*4);
				else if(i==1||i==3)
					SI+=(CV[i]*2);
				else
					SI+=(CV[i]);
				System.out.println("SI "+SI);
			}
			AC/=25;
			SI*=5;
			SI=(AC+SI)/2;
			float sum=1/SI;
			SI=1/SI;
			//SI*=5;
			//float sum=(AC+SI)/2;
		System.out.println("SEVERITY INDEX DAWW "+SI);
	}
}