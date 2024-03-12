package bcdk.map;
import java.util.ArrayList;

/**
 * 
 * @param <T>
 */
public class HoldList <T>{
	
	/**
	 * initiates a ArrayList of T
	 */
	public ArrayList<T> list = new ArrayList<T>();
	
	/**
	 * declares the list variable to another ArrayList of T
	 * @param list -  ArrayList
	 */
	public void SetList(ArrayList<T> list) {
		this.list = list;
	}
	
	/** 
	 * @return the list of T stores here
	 */
	public ArrayList<T> GetList(){
		return list;
	}

}
