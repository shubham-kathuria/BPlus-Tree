import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class Leaf extends Node{
	
	List<Double> values;  // check double was string before
	Leaf nextNode;	
	Leaf prevNode;	
	KeyValue keyVal;	
	List<KeyValue> keyvalues;
	
	public Leaf(double frstKey, double frstValue){  //check
		leafNode=true;
		keyVal=new KeyValue(frstKey, frstValue);
		keyvalues=new ArrayList<>();
		keyvalues.add(keyVal);
	}
	
	public Leaf(List<KeyValue> newkeyValues) {
		leafNode = true;
		keyvalues = new ArrayList<KeyValue>(newkeyValues);
	}
	
	public void insrtSortd(KeyValue kv){
		if (kv.compareTo(keyvalues.get(0))<0){
			keyvalues.add(0,kv);
		}else if (kv.compareTo(keyvalues.get(keyvalues.size()-1))>0){
			keyvalues.add(kv);
		}
		else{
			ListIterator<KeyValue> iterator=keyvalues.listIterator();
			while(iterator.hasNext()){	
				if(iterator.next().compareTo(kv)>0){
					int position=iterator.previousIndex();
					keyvalues.add(position, kv);
					break;
				}			
			}
		}
	}
	
	public KeyValue getKeyValue(int i){
		return keyvalues.get(i);		//getter to return the KeyValue Pair
	}
	public boolean isOverflowed() {			//to check if Leaf Node is full
		return keyvalues.size() > BTree.o-1;
	}
}
