import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class IntrnlNode extends Node{
	protected ArrayList<Node> chldrn;
	
	public IntrnlNode(Double key, Node child0, Node child1) {
		leafNode = false;	
		keys = new ArrayList<Double>(); 	//Initialize keys
		keys.add(key);				//add First Key
		chldrn = new ArrayList<Node>();	//Initialize childrens
		chldrn.add(child0);			//add child1
		chldrn.add(child1);			//add child2
	}
	public IntrnlNode(List<Double> newKeys, List<Node> newChldrn) {
		leafNode = false;				
		keys = new ArrayList<Double>(newKeys);
		chldrn = new ArrayList<Node>(newChldrn);

	}
	public void insertSorted(Pair<Double,Node> e, int index) {
		Double key = e.getKey();  		//sort the Nodes according to the key Value
		Node child = e.getValue();
		
		if (index >= keys.size()) {
			keys.add(key);
			chldrn.add(child);
		} else {
			keys.add(index, key);
			chldrn.add(index+1, child);
		}
	}

}
