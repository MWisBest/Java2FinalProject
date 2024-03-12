package bcdk.item;

import java.util.Locale;
import java.util.ResourceBundle;

import bcdk.BCDK;

/**
 * constructor of keys that get data from Item class
 */
public class Key extends Item {
    /**
     * initiates key instance
     */
	public Key() {
		super("Key", BCDK.messages.getString("key_description"));
	}
}
