package bcdk;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.ResourceBundle;

public class SeasonCalendar {
	static Locale currentLocale = new Locale("en"); // Spanish locale
    static ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);
	
	public enum Season {
        WINTER,
        SPRING,
        SUMMER,
        FALL
    }
	
	public Season getSeason(LocalDate date) {
        Month month = date.getMonth();
        //int day = date.getDayOfMonth();

        // Determine the season based on the month and day
        if (month == Month.DECEMBER || month == Month.JANUARY || month == Month.FEBRUARY) {
            return Season.WINTER;
        } else if (month == Month.MARCH || month == Month.APRIL || month == Month.MAY) {
            return Season.SPRING;
        } else if (month == Month.JUNE || month == Month.JULY || month == Month.AUGUST) {
            return Season.SUMMER;
        } else {
            return Season.FALL; // September, October, November
        }
    }
	
	public String getSeasonMessage(Season season) {
        switch (season) {
            case WINTER:
                return " " + messages.getString("winter");
            case SPRING:
                return " " + messages.getString("spring");
            case SUMMER:
                return " " + messages.getString("summer");
            case FALL:
                return " " + messages.getString("fall");
            default:
                return " " + messages.getString("season_default");
        }
	}
}
