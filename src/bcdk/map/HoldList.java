package bcdk.map;
import java.util.ArrayList;

public class HoldList <T>{
	
	public ArrayList<T> list = new ArrayList<T>();
	
	public void SetList(ArrayList<T> list) {
		this.list = list;
	}
	
	public ArrayList<T> GetList(){
		return list;
	}

}
