package es.neifi.controlfit.timeRegistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.neifi.controlfit.services.BaseService;
import es.neifi.controlfit.timeRegistry.model.TimeRegistry;
import es.neifi.controlfit.timeRegistry.repo.TimeRegistryRepository;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

public class TimeRegistryService extends BaseService<TimeRegistry,Integer, TimeRegistryRepository> {
	
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

	@Override
	public void deleteById(Integer integer) {
		super.deleteById(integer);
	}
}
