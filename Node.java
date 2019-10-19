import java.util.List;

public class Node {
	
	public boolean leafNode=false;

	public List<Double> keys;
	
	public boolean Overflowed() {
		if(keys == null)
			return false;
		
		return keys.size() > BTree.o-1;
	}
	public boolean UnderFlowed() {
		if(keys == null)
			return true;
		
		return keys.size() < Math.floor(BTree.o/2) - 1;
	}
	
}
