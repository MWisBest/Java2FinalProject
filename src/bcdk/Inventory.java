package bcdk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
	
	// the List that will store all the player items 
	private List<String> items;
	
	/**
	 * class constructor
	 */
	public Inventory() {
		this.items = new ArrayList<>();
	}
	
	/**
	 *  method to add a new item to the player inventory
	 * @param item -  the name of the item to be added
	 */
	public void AddItem(String item) {
		items.add(item);
	}
	
	/**
	 * method to delete a item from the player inventory 
	 * @param item - the name of the item to be removed
	 */
	public void RemoveItem(String item) {
		boolean deleted = false; 
		for(String i : items) {
			if(i == item) {
				items.remove(item);
				deleted = true;
				break;
			} else {
				deleted = false;
			}
		}
		
		if(deleted) {
			System.out.println("Deleted Successfully");
		} else {
			System.out.println("Deletion Failed");
		}
		
	}
	
	/**
	 * Class that gets all the items that the player currently has 
	 * @return the list of items stored 
	 */
	public List<String> CheckInventory() {
		return items;
	}
	
	/**
	 * Saves the items in the List to the text file so user can retrieve his items in another game session
	 * @param fileName -  the name of the txt file to retrieve the data from 
	 */
	public void SaveInventory(String fileName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for(String item : items) {
				writer.write(item);
				writer.newLine();
			}
		} catch(IOException e) {
			
		}
	}
	
	/**
	 *  Retrieves the player items from a text file to put into the items list. 
	 * @param fileName - the name of the txt file with inventory data
	 */
	public void LoadInventory(String fileName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while((line = reader.readLine()) != null) {
				items.add(line);
			}
		} catch(IOException e) {
			
		}
	} 
	
}