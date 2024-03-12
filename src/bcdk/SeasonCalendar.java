package bcdk;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * make welcome message more customizeable
 */
public class SeasonCalendar {
    /**
     * declares the seasons that exist
     */
	public enum Season {
        WINTER,
        SPRING,
        SUMMER,
        FALL
    }
	
	/**
	 * determines the current season based on the date
	 * @param date - data that determines season
	 * @return - season enum according to date
	 */
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
	
	/**
	 * gets the apporpiate message based on the season
	 * @param season - season specified
	 * @return - string message
	 */
	public String getSeasonMessage(Season season) {
        switch (season) {
            case WINTER:
                return " " + BCDK.messages.getString("winter");
            case SPRING:
                return " " + BCDK.messages.getString("spring");
            case SUMMER:
                return " " + BCDK.messages.getString("summer");
            case FALL:
                return " " + BCDK.messages.getString("fall");
            default:
                return " " + BCDK.messages.getString("season_default");
        }
	}
}
