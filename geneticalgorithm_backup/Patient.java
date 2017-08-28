package geneticalgorithm;
/**/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient {
	String name,gene;
	int age,ecg,hr,bp_s,bp_d,rr;
	Map<String,String> candidate=new HashMap<String,String>();
	Patient(String name,List<Integer> parameters){
		this.name=name;
		this.age=parameters.get(5);
		this.ecg=parameters.get(0)-1;
		this.hr=parameters.get(1)-1;
		this.bp_s=parameters.get(2)-1;
		this.bp_d=parameters.get(3)-1;
		this.rr=parameters.get(4)-1;
	}
Map<String,String> geneRepresentation(){
	
String temp="00000";
temp=temp.substring(0, ecg) + '1' + temp.substring(ecg + 1);
	
	gene=temp;
	temp="00000";
	temp=temp.substring(0, hr) + '1' + temp.substring(hr + 1);
	gene+=temp;
	temp="00000";
	temp=temp.substring(0, bp_s) + '1' + temp.substring(bp_s + 1);
	gene+=temp;
	temp="00000";
	temp=temp.substring(0, bp_d) + '1' + temp.substring(bp_d + 1);
	gene+=temp;
	temp="00000";
	temp=temp.substring(0, rr) + '1' + temp.substring(rr + 1);
	gene+=temp;
	candidate.put(name, gene);
	return candidate;
	
}
}
