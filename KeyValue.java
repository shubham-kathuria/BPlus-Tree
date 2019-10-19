import java.util.List;

import java.util.ArrayList;

public class KeyValue implements Comparable<KeyValue> {

	double key;
	List<Double> values; 
	
	public KeyValue(double key,Double value){  //check
			this.key=key;
			this.values= new ArrayList<Double>();  //check
			values.add(value);
	}
	
	public void setValue(Double value){
		values.add(value); 
	}
	
	public double getKey(){
		return this.key; 
	}
	
	public Double getValue(){  
		return values.get(0);
	}
	public List<Double> getValues(){
		return values;		
	}
	
	@Override
	public int compareTo(KeyValue kv) { // method to compare each keys
		if (key == kv.getKey()){
			return 0;
		}
		else if (key>kv.getKey()){
			return 1;
		}
		else{
			return -1;
		}
	}
}
