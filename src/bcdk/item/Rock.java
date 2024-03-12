package bcdk.item;

import java.util.Locale;
import java.util.ResourceBundle;

import bcdk.BCDK;

/**
 * rock constructor with data from the Item class
 */
public class Rock extends Item {
	// 1.8 - use of overridden constructor
    
    /**
     * initiates rock instance
     */
	public Rock() {
		super("Rock", BCDK.messages.getString("rock_description"));
	}
}
