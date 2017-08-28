package geneticalgorithm;
// Genetic Algorithm for optimization process

import java.util.Collections;

import java.util.LinkedList;

import java.util.Random;

import java.util.Comparator;

import java.util.HashMap;

import java.util.Iterator;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;

import java.util.Set;

import java.sql.*;

import java.sql.*;

public class GA {

  String name;

	static String[] userinput=new String[5];

	static long BEGIN;

  int step=0,x=10;

	static final boolean _DEBUG = true;

  LinkedList<Candidate> population = new LinkedList<Candidate>();

  final Random rand = new Random();

	final int populationSize = 10;

  final int parentUsePercent = 10;

	String pid="";

  public GA(String id,String name,String userinputx) {

		this.pid=id;

		this.name=name;

    Candidate c=new Candidate();

		int ab=0;

		for(ab=0;ab<25;ab++)

		{

			   if(userinputx.charAt(ab)=='1')

				     c.genotype[ab]=true;

			    else

				      c.genotype[ab]=false;
		}

		population.add(c);

    for (int i = 1; i < populationSize; i++) {

        c = new Candidate();

        c.random();

        population.add(c);

        }
    }

    private static HashMap<Candidate, Float> sortByValues(HashMap<Candidate, Float> map) {

       List list = new LinkedList(map.entrySet());

       // Defined Custom Comparator here

       Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {

               return ((Comparable) ((Map.Entry) (o1)).getValue())

                  .compareTo(((Map.Entry) (o2)).getValue());

            }

       });

       // Here I am copying the sorted list in HashMap

       // using LinkedHashMap to preserve the insertion order

	   HashMap<Candidate, Float> sortedHashMap = new LinkedHashMap<Candidate, Float>();

	   for (Iterator it = list.iterator(); it.hasNext();) {

              Map.Entry<Candidate, Float> entry = (Map.Entry<Candidate, Float>) it.next();

              sortedHashMap.put(entry.getKey(), entry.getValue());

       }

       return sortedHashMap;

  }

	void print() {

		float localm,overall=0;

		String s="";

		System.out.println(name);

    System.out.println("-- print");

    for (Candidate c : population) {

		if(c!=null)

				System.out.println(c);

			  localm=c.getLocalMax();

				overall=c.getMax();

			  s=c.getGene();
        }

		if(overall!=0.0){

		System.out.println("-------------------------------------------------------------------------");

		 final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	   final String DB_URL = "jdbc:mysql://192.168.108.40:3306/project";

	   //  Database credentials

		 final String USER="root";

		 final String PASS = "oneadmin";

		  try{

			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			     Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			     Statement stmt = conn.createStatement();
    	      String sql = "INSERT INTO gene VALUES('"+pid+"','"+overall+"','"+s+"')";
            //STEP 4: Execute a query
			      stmt.executeUpdate(sql);
			}
		catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("Max fitness value overall as of now is "+overall +" "+s);
		System.out.println("-------------------------------------------------------------------------");
	}
		BEGIN = System.currentTimeMillis();
		long END = System.currentTimeMillis();
		System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
}

    /**

     * Selection strategy: Tournament method Replacement strategy: elitism 10%

     * and steady state find 4 random in population not same let 2 fight, and 2

     * fight the winners makes 2 children

     */

    void produceNextGen() {

      LinkedList<Candidate> newpopulation = new LinkedList<Candidate>();

      while (newpopulation.size() < populationSize/2){

			HashMap<Candidate,Float> m=new HashMap<Candidate,Float>();

			Map<Candidate, Float> map;

			try{

			for(int i=0;i<x/2;i++)

			{

				m.put(population.get(i),population.get(i).fitness());

				}}

			catch(Exception e)

			{	}

			Set set = m.entrySet();

			Iterator iterator = set.iterator();

			while(iterator.hasNext()) {

			Map.Entry me = (Map.Entry)iterator.next();

			System.out.print(me.getKey() + ": ");

			System.out.println(me.getValue());

			}

			map = sortByValues(m);

			Candidate child1, child2;

			Candidate[] w=new Candidate[2];

			Iterator it = map.entrySet().iterator();

			int g=0;

			while (it.hasNext()&&x%2==0) {

			Map.Entry pair = (Map.Entry)it.next();

			w[g++]=(Candidate)pair.getKey();// + " = " + pair.getValue());

			if(g==2)

			g=0;

			int pivot = 4; // cut interval

			if(w[0]!=null&&w[1]!=null){

			child1 = newChild(w[0],w[1],pivot);

			child2 = newChild(w[1],w[0],pivot);

			// Method uniform crossover

			Candidate[] childs = newChilds(w[0], w[1]);

			child1 = childs[0];

			child2 = childs[1];

			boolean isChild1Good = child1.fitness() >= w[0].fitness();

			boolean isChild2Good = child2.fitness() >= w[1].fitness();

 			newpopulation.add(isChild1Good ? child1 : w[0]);

			newpopulation.add(isChild2Good ? child2 : w[1]);

			}

		}
    // add top percent parent

    int j = (int) (populationSize * parentUsePercent / 100.0);

    for (int i = 0; i < j; i++) {

    newpopulation.add(population.get(i));

    }

    population = newpopulation;

    Collections.sort(population);

		x=x/2;

		}
}
   // one-point crossover random pivot

    Candidate newChild(Candidate c1, Candidate c2, int pivot) {

    Candidate child = new Candidate();

 		Random rand=new Random();

		boolean b = rand.nextFloat() >= 0.5;

		int x=5;

    for (int i = 0; i < x; i++) {

			if(b){

            child.genotype[i]= c1.genotype[i];

      	}

			else
			   child.genotype[i]=c2.genotype[i];
      }

		x=10;

		for (int i = 5; i < x; i++) {

			if(b){

            child.genotype[i]= c2.genotype[i];
			}

			else

			child.genotype[i]=c1.genotype[i];

		}

		x=15;

		for (int i = 10; i <x; i++) {

			if(b){

            child.genotype[i]= c1.genotype[i];

			}

			else

			child.genotype[i]=c2.genotype[i];

        }

		x=20;

		for (int i = 15; i <x; i++) {

			if(b){

            child.genotype[i]= c2.genotype[i];

			}

			else

			child.genotype[i]=c1.genotype[i];

        }

		x=25;

		for (int i = 20; i <x; i++) {

			if(b){

            child.genotype[i]= c1.genotype[i];

			}

			else

			child.genotype[i]=c2.genotype[i];

        }

        System.out.println("\n\nBefore Cross Over : ");

        for (int i = 0; i < Candidate.SIZE; i++) {

        if(c1.genotype[i]==true)

            	System.out.print("1");

        else

            	System.out.print("0");

        }

        System.out.println();

        for (int i = 0; i < Candidate.SIZE; i++) {

            if(c2.genotype[i]==true)

            	 System.out.print("1");

            else

            	System.out.print("0");

        }

        System.out.println("\n\nAfter Cross Over : ");

        for (int i = 0; i < Candidate.SIZE; i++) {

            if(child.genotype[i]==true)

            	System.out.print("1");

            else

            	System.out.print("0");

        }
        return child;

    }

    // Uniform crossover

        Candidate[] newChilds(Candidate c1, Candidate c2) {

        Candidate child1 = new Candidate();

        Candidate child2 = new Candidate();

        for (int i = 0; i < Candidate.SIZE; i++) {

            boolean b = rand.nextFloat() >= 0.5;

            if (b) {

                child1.genotype[i] = c1.genotype[i];

                child2.genotype[i] = c2.genotype[i];

            } else {

                child1.genotype[i] = c2.genotype[i];

                child2.genotype[i] = c1.genotype[i];

            }

        }

        return new Candidate[] { child1, child2 };

    }

    void mutate(Candidate c) {

        int i = rand.nextInt(Candidate.SIZE);

        c.genotype[i] = !c.genotype[i]; // flip

    }

    public void run() {

        final int maxSteps = 5;

        int count = 0;

        while (count < maxSteps) {

            count++;

            produceNextGen();

        }

        System.out.println("\nResult");

        print();

    }

}
