package accounts.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleIntegerProperty ownDay = new SimpleIntegerProperty();
	private SimpleIntegerProperty hospitalDay = new SimpleIntegerProperty();
	private SimpleDoubleProperty salary = new SimpleDoubleProperty();

	public Account(String name, int ownDay, int hospitalDay, double salary) {
		setName(name);
		setOwnDay(ownDay);
		setHospitalDay(hospitalDay);
		setSalary(salary);
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
}
