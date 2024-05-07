package sit707_week5;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class WeatherControllerTest {

	@Test
	public void testStudentIdentity() {
		String studentId = "224114235";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "shreya";
		Assert.assertNotNull("Student name is null", studentName);
	}

	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double minTemperature = 1000;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = wController.getTemperatureForHour(i+1); 
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);
		
		// Shutdown controller
		wController.close();		
	}
	
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double maxTemperature = -1;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = wController.getTemperatureForHour(i+1); 
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
		
		// Shutdown controller
		wController.close();
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");
		
		// Initialise controller
		WeatherController wController = WeatherController.getInstance();
		
		// Retrieve all the hours temperatures recorded as for today
		int nHours = wController.getTotalHours();
		double sumTemp = 0;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = wController.getTemperatureForHour(i+1); 
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / nHours;
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
		
		// Shutdown controller
		wController.close();
	}
	
	@Test
	public void testTemperaturePersist() {
		/*
		 * Remove below comments ONLY for 5.3C task.
		 */
		System.out.println("+++ testTemperaturePersist +++");
		
		 // Create a fixed instant for mocking
 		Instant fixedInstant = Instant.parse("2024-05-06T12:00:00Z");
        // Create a fixed clock with the fixed instant
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.systemDefault());

		// Initialise controller
        WeatherController wController = WeatherController.getInstance(fixedClock);
		
		String persistTime = wController.persistTemperature(10, 19.5);
		// String now = new SimpleDateFormat("H:m:s").format(new Date());
		System.out.println("Persist time: " + persistTime + ", now: " + fixedInstant.toString());
		
		assertEquals(fixedInstant.toString(), persistTime);
		
		wController.close();
	}
 
}
