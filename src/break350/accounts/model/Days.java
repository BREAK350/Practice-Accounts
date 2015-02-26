package break350.accounts.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import break350.accounts.Configs;

public class Days {
	private static int[] days = new int[12];
	private static int month = 0;
	private static List<Daysable> daysables = new ArrayList<Daysable>();

	public static void addDaysable(Daysable daysable) {
		daysables.add(daysable);
	}

	public static void addAllDaysable(Daysable... daysables) {
		for (int i = 0; i < daysables.length; i++) {
			Days.daysables.add(daysables[i]);
		}
	}

	public static void removeDaysable(Daysable daysable) {
		daysables.remove(daysable);
	}

	public static void removeAllDaysable() {
		daysables.clear();
	}

	public static void initPastMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		_setMonth(cal.get(Calendar.MONTH));
	}

	private static void _setMonth(int month) {
		Days.month = month;
		daysChanged();
	}

	public static void setMonth(int month) {
		if (month >= 0 && month < 12) {
			_setMonth(month);
		}
	}

	public static int getWorkingDay() {
		return days[month];
	}

	public static void loadDays() {
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(Configs.getProperties().getProperty(
							Configs.pathToMonthsFile)));
			String row;
			int i = 0;
			while ((row = inputStream.readLine()) != null) {
				days[i++] = Integer.parseInt(row);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void daysChanged() {
		for (Daysable daysable : daysables) {
			daysable.setWorkingDays(getWorkingDay());
		}
	}
}
