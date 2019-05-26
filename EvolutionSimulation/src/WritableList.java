import java.io.Serializable;
import java.util.ArrayList;

public class WritableList implements Serializable {
	
	private static final long serialVersionUID = 5747209287737497255L;
	private ArrayList<Stats> list;

	public WritableList() {
		list = new ArrayList<Stats>();
	}
	
	public WritableList(ArrayList<Stats> list) {
		this.list = list;
	}
	
	public void add(Stats s) {
		list.add(s);
	}
	
	public ArrayList<Stats> getList() {
		return list;
	}

}
