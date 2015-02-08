package accounts.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleStringProperty ownDay = new SimpleStringProperty();
	private SimpleStringProperty hospitalDay = new SimpleStringProperty();
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

	public String getHospitalDay() {
		return hospitalDay.get();
	}

	public void setHospitalDay(int hospitalDay) {
		this.hospitalDay.set(String.valueOf(hospitalDay > 0 ? hospitalDay : 0));
	}

	public String getOwnDay() {
		return ownDay.get();
	}

	public void setOwnDay(int ownDay) {
		this.ownDay.set(String.valueOf(ownDay > 0 ? ownDay : 0));
		System.out.println("set " + ownDay);
	}

	public double getSalary() {
		return salary.get();
	}

	public void setSalary(double salary) {
		this.salary.set(salary > 0 ? salary : 0);
	}

	@Override
	public String toString() {
		return "Account [name=" + name + ", ownDay=" + ownDay
				+ ", hospitalDay=" + hospitalDay + ", salary=" + salary + "]";
	}
}
