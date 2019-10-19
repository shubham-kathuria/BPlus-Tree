import java.util.List;

import java.util.ArrayList;

public class BTree  {

	public Node root;
	//public boolean leafNode;  //new added
	public static int o=2;
	
	public void initialize(int ordr){
		o=ordr;
	}
	
	public List<Double> search(double key){
		
		if(root == null) {
			return null;
		}
		Leaf Leaf = (Leaf)Search(root, key);
		
		for(int i=0; i<Leaf.keyvalues.size(); i++) {
			if(key==Leaf.getKeyValue(i).getKey()) 
					return Leaf.getKeyValue(i).getValues();
			}
		
		return null;
	}

	public Node Search(Node node,double key){
	
	if(node.leafNode) {
		return node;
	}
	else {
		IntrnlNode internal = (IntrnlNode)node;
		
		if(key<internal.keys.get(0))  {
			return Search((Node)internal.chldrn.get(0), key);
		}
		
		else if(key>=internal.keys.get(node.keys.size()-1))  {
			return Search((Node)internal.chldrn.get(internal.chldrn.size()-1), key);
		}
		
		
		else {
			
			for(int i=0; i<internal.keys.size()-1; i++) {
				if(key>=internal.keys.get(i) && key < internal.keys.get(i+1)) {
					return Search((Node)internal.chldrn.get(i+1), key);
				}
			}
			}
	}
	
	return null;
				}

	public List<Pair<Double,List<Double>>> search(double key1, double key2){
			if(key1>key2)
				return null;
			
			Pair<Double,List<Double>> pair;
			List<Pair<Double,List<Double>>> list=new ArrayList<>();
			Leaf leaf = (Leaf)Search(root, key1);
 
			if (leaf != null){
				while(leaf!=null && leaf.getKeyValue(0).getKey()<=key2){
				for(int i=0; i<leaf.keyvalues.size(); i++) {
					if(key1<=leaf.getKeyValue(i).getKey() && key2>=leaf.getKeyValue(i).getKey()) {
						pair = new Pair<Double,List<Double>>(leaf.getKeyValue(i).getKey(),leaf.getKeyValue(i).getValues());	
						list.add(pair);	
					}
					}
				leaf=(Leaf)leaf.nextNode;
				}
				return list;
			}			
			
			return null;
		}
		
	public void insert(double key, Double value){
			
			//If the key is already present search the key first and 
	if(search(key)!=null){
		Leaf Leaf = (Leaf)Search(root, key);
		
		for(int i=0; i<Leaf.keyvalues.size(); i++) {
			if(key==Leaf.getKeyValue(i).getKey()) {
					Leaf.getKeyValue(i).values.add(value);
					return;
			}
		}
		
	}
	
	Leaf newleaf = new Leaf(key, value);
	
	Pair<Double,Node> entry=new Pair<Double, Node>(key,newleaf);

	if(root == null ) {
		root = entry.getValue();
	}
	

	Pair<Double, Node> newChildEntry = getChildEntry(root, entry, null);
	
	if(newChildEntry == null) {
		return;
	} else {
		IntrnlNode newRoot = new IntrnlNode(newChildEntry.getKey(), root, newChildEntry.getValue());
		root = newRoot;
		return;
	}
}

	public Pair<Double,Node> getChildEntry(Node node, Pair<Double, Node> entry, Pair<Double, Node> newChildEntry) 
	{
		
		if(!node.leafNode) {
	
			IntrnlNode index = (IntrnlNode) node;
			int i = 0;
			while(i < index.keys.size()) {
				if(entry.getKey().compareTo(index.keys.get(i)) < 0) {
					break;
				}
				i++;
			}
	
			newChildEntry = getChildEntry((Node) index.chldrn.get(i), entry, newChildEntry);
			
			if(newChildEntry == null) {
				return null;
			} 
	
			else {
				int j = 0;
				while (j < index.keys.size()) {
					if(newChildEntry.getKey().compareTo(index.keys.get(j)) < 0) {
						break;
					}
					j++;
				}
				
				index.insertSorted(newChildEntry, j);
				
				// Usual case, put newChildEntry on it, set newChildEntry to null, return
			if(!index.Overflowed()) {
				return null;
			} 
			else{
				newChildEntry = splitInternalNode(index);
				
				// Root was just split
				if(index == root) {
					// Create new node and make tree's root-node pointer point to newRoot
					
					IntrnlNode newRoot = new IntrnlNode(newChildEntry.getKey(), root, 
							newChildEntry.getValue());
					root = newRoot;
					return null;
				}
				return newChildEntry;
			}
		}
	}
	// Node pointer is a leaf node
	else {
		Leaf Leaf = (Leaf)node;
		Leaf newLeaf = (Leaf)entry.getValue();
		
		Leaf.insrtSortd(newLeaf.keyVal);
		
		// the case in which leaf has space, put entry and set newChildEntry to null and return
		if(!Leaf.Overflowed()) {
			return null;
		}
		// when the leaf is full
			else {
				newChildEntry = splitLeafNode(Leaf);
				if(Leaf == root) {
					IntrnlNode newRoot = new IntrnlNode(newChildEntry.getKey(), Leaf, 
							newChildEntry.getValue());
					root = newRoot;
					return null;
				}
				return newChildEntry;
			}
		}
	}
	
	public Pair<Double, Node> splitLeafNode(Leaf leaf) {
		
		ArrayList<KeyValue> newKeyValue=new ArrayList<>();
	
		for(int i=(int)Math.ceil((o-1)/2); i<=o-1; i++) {
			newKeyValue.add(leaf.getKeyValue(i));
		}
		
	
		for(int i=(int)Math.ceil((o-1)/2); i<=o-1; i++) {
			leaf.keyvalues.remove(leaf.keyvalues.size()-1);
		}
		
		Double splitKey = newKeyValue.get(0).getKey();
		
		Leaf rightNode=new Leaf(newKeyValue);
		
	
		Leaf temp = leaf.nextNode;
		leaf.nextNode = rightNode;
		leaf.nextNode.prevNode = rightNode;
		rightNode.prevNode = leaf;
		rightNode.nextNode = temp;
	    
		Pair<Double, Node> newChildEntry = new Pair<Double, Node>(splitKey, rightNode);
		
		return newChildEntry;
	}
	
	public Pair<Double, Node> splitInternalNode(IntrnlNode index) {
		
		ArrayList<Double> newKeys = new ArrayList<Double>();
		ArrayList<Node> newChildren = new ArrayList<Node>();
		

		
		int splitIndex=(int)Math.ceil((o-1)/2);
		Double splitKey = (Double)index.keys.get(splitIndex);
		index.keys.remove(splitIndex);
		


		newChildren.add((Node)index.chldrn.get(splitIndex+1));
		index.chldrn.remove(splitIndex+1);
		
		while(index.keys.size() > splitIndex){
			newKeys.add(index.keys.get(splitIndex));
			index.keys.remove(splitIndex);
			newChildren.add((Node)index.chldrn.get(splitIndex+1));
			index.chldrn.remove(splitIndex+1);
		}

		IntrnlNode rightNode = new IntrnlNode(newKeys, newChildren);
		Pair<Double,Node> newChildEntry = new Pair<Double,Node>(splitKey, rightNode);

		return newChildEntry;
	}

	public void remove(Double key) {
		
		if(root == null) {
			return;
		}
		
		if(search(key)!=null){
			Leaf Leaf = (Leaf)Search(root, key);
			for(int i=0; i<Leaf.keyvalues.size(); i++) {
				if(key==Leaf.getKeyValue(i).getKey()) {
						Leaf.getKeyValue(i).values.clear();
						return;
				}
			}
		}
	}
}
