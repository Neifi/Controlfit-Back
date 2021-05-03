package es.neifi.controlfit.registrohorario.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

public class TimeRegistrySerice {
	
	/**
	 *  Calcula las horas de diferencia en funcion de la hora de entrada y salida de un registro horario
	 * @param format
	 * @param entry
	 * @param exit
	 * @return
	 */
	public static String calculateGymTimeSpent(SimpleDateFormat format, String entry, String exit) {
		String difference = "";
		try {
			Date start = format.parse(entry);
			Date end = format.parse(exit);

			DateTime startTime = new DateTime(start);
			DateTime exitTime = new DateTime(end);
			int hours = Hours.hoursBetween(startTime, exitTime).getHours() % 24;
			int minutes = Minutes.minutesBetween(startTime, exitTime).getMinutes() % 60;
			int seconds = Seconds.secondsBetween(startTime, exitTime).getSeconds() % 60;

			difference = String.valueOf(hours + ":" + minutes + ":" + seconds);

			return difference;
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return difference;
	}
}
