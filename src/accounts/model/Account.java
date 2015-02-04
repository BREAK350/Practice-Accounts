package accounts.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {
	private SimpleIntegerProperty id = new SimpleIntegerProperty();
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleIntegerProperty ownDay = new SimpleIntegerProperty();
	private SimpleIntegerProperty hospitalDay = new SimpleIntegerProperty();
	private SimpleDoubleProperty salary = new SimpleDoubleProperty();

	public Account(int id, String name, int hospitalDay, int ownDay,
			double salary) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.hospitalDay = new SimpleIntegerProperty(hospitalDay);
		this.ownDay = new SimpleIntegerProperty(ownDay);
		this.salary = new SimpleDoubleProperty(salary);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public int getHospitalDay() {
		return hospitalDay.get();
	}

	public void setHospitalDay(int hospitalDay) {
		this.hospitalDay.set(hospitalDay > 0 ? hospitalDay : 0);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id > 0 ? id : 0);
	}

	public int getOwnDay() {
		return ownDay.get();
	}

	public void setOwnDay(int ownDay) {
		this.ownDay.set(ownDay > 0 ? ownDay : 0);
	}

	public double getSalary() {
		return salary.get();
	}

	public void setSalary(double salary) {
		this.salary.set(salary > 0 ? salary : 0);
	}

	public static double round(double value, int accuracy) {
		return ((int) (value * accuracy)) / ((double) accuracy);
	}

	public double getEURSumm() {
		double salaryInDay = getSalary() / GlobalData.getWorkingDay();
		double worked = getWorkedDays() * salaryInDay;
		double hospitaled = getHospitalDay() * salaryInDay * 0.50;
		double res = round(worked + hospitaled, 100);
		return res;
	}

	public double getUAHSumm() {
		return round(getEURSumm() * GlobalData.getRate(), 100);
	}

	public int getWorkedDays() {
		return GlobalData.getWorkingDay() - getOwnDay() - getHospitalDay();
	}
}
