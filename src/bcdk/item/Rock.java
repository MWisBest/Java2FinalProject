package bcdk.item;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * rock constructor with data from the Item class
 */
public class Rock extends Item {
	/**
	 * create the chance for the game to be used in spanish
	 * "es" to play in spanish 		"en" to play in english
	 */
	static Locale currentLocale = new Locale("es"); // Spanish locale
	
	/**
	 * create a connection to the files that will be used to provide text to the game
	 */
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
	// 1.8 - use of overridden constructor
    
    /**
     * initiates rock instance
     */
	public Rock() {
		super("Rock", messages.getString("rock_description"));
	}
}
