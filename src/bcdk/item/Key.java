package bcdk.item;

import java.util.Locale;
import java.util.ResourceBundle;

public class Key extends Item {
	static Locale currentLocale = new Locale("en"); // Spanish locale
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
	
	public Key() {
		super("Key", messages.getString("key_description"));
	}
}
