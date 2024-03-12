package bcdk.item;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * constructor of keys that get data from Item class
 */
public class Key extends Item {
	/**
	 * create the chance for the game to be used in spanish
	 * "es" to play in spanish 		"en" to play in english
	 */
	static Locale currentLocale = new Locale("es"); // Spanish locale
	
	/**
	 * create a connection to the files that will be used to provide text to the game
	 */
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
	
    /**
     * initiates key instance
     */
	public Key() {
		super("Key", messages.getString("key_description"));
	}
}
