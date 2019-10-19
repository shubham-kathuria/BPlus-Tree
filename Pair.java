
public class Pair<X,Y> {
	
	public final X x;
    public final Y y;
    
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;
        if (!x.equals(pair.x)) return false;
        return y.equals(pair.y);
    }
    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
    
    public X getKey(){
    	return x;
    }
    public Y getValue(){
    	return y;
    }
}
