package es.neifi.controlfit.customer.timeRegistry.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.neifi.controlfit.customer.timeRegistry.repo.TimeRegistryRepository;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.springframework.stereotype.Service;

@Service
public class TimeRegistryService {


	private final TimeRegistryRepository timeRegistryRepository;

	public TimeRegistryService(TimeRegistryRepository timeRegistryRepository) {
		this.timeRegistryRepository = timeRegistryRepository;
	}

	/**
	 *  Calcula las horas de diferencia en funcion de la hora de entrada y salida de un registro horario
	 * @param format
	 * @param entry
	 * @param exit
	 * @return
	 */
	public static String calculateGymTimeSpent(SimpleDateFormat format, String entry, String exit) {
		try {
			Date start = format.parse(entry);
			Date end = format.parse(exit);

			DateTime startTime = new DateTime(start);
			DateTime exitTime = new DateTime(end);
			int hours = Hours.hoursBetween(startTime, exitTime).getHours() % 24;
			int minutes = Minutes.minutesBetween(startTime, exitTime).getMinutes() % 60;
			int seconds = Seconds.secondsBetween(startTime, exitTime).getSeconds() % 60;

			return hours + ":" + minutes + ":" + seconds;

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return "";
	}

	public void deleteById(Integer integer) {
		this.timeRegistryRepository.deleteById(integer);
	}
}
