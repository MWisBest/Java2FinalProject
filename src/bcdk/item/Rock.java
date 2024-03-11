package bcdk.item;

import java.util.Locale;
import java.util.ResourceBundle;

public class Rock extends Item {
	static Locale currentLocale = new Locale("en"); // Spanish locale
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
	// 1.8 - use of overridden constructor
	public Rock() {
		super("Rock", messages.getString("rock_description"));
	}
}
