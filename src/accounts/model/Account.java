package accounts.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {
	private SimpleIntegerProperty index;
	private SimpleStringProperty name;
	private SimpleIntegerProperty worked;
	private SimpleIntegerProperty own;
	private SimpleIntegerProperty hospital;
	private SimpleDoubleProperty salary;
	private SimpleDoubleProperty eur;
	private SimpleDoubleProperty uah;

	public Account(int index, String name, int worked, int own, int hospital,
			double salary, double eur, double uah) {
		super();
		this.index = new SimpleIntegerProperty(index);
		this.name = new SimpleStringProperty(name);
		this.worked = new SimpleIntegerProperty(worked);
		this.own = new SimpleIntegerProperty(own);
		this.hospital = new SimpleIntegerProperty(hospital);
		this.salary = new SimpleDoubleProperty(salary);
		this.eur = new SimpleDoubleProperty(eur);
		this.uah = new SimpleDoubleProperty(uah);
	}

	public Account(int index, String name, int own, int hospital,
			double salary, int working, double rate) {
		super();
		this.index = new SimpleIntegerProperty(index);
		this.name = new SimpleStringProperty(name);
		this.worked = new SimpleIntegerProperty(working - own - hospital);
		this.own = new SimpleIntegerProperty(own);
		this.hospital = new SimpleIntegerProperty(hospital);
		this.salary = new SimpleDoubleProperty(salary);
		this.eur = new SimpleDoubleProperty(getEUR(working, worked.get(),
				hospital, salary));
		this.uah = new SimpleDoubleProperty(eur.get() * rate);
	}

	public SimpleIntegerProperty indexProperty() {
		return index;
	}

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public SimpleIntegerProperty workedProperty() {
		return worked;
	}

	public SimpleIntegerProperty ownProperty() {
		return own;
	}

	public SimpleIntegerProperty hospitalProperty() {
		return hospital;
	}

	public SimpleDoubleProperty salaryProperty() {
		return salary;
	}

	public SimpleDoubleProperty eurProperty() {
		return eur;
	}

	public SimpleDoubleProperty uahProperty() {
		return uah;
	}

	public void setOwn(int newOwn, double rate) {
		int free = worked.get() + own.get();
		if (newOwn < 0) {
			newOwn = 0;
			this.own.set(1);
			this.own.set(0);
		}
		this.own.set(newOwn <= free ? newOwn : free);
		worked.set(free - own.get());
		calculateSalary(rate);
	}

	public int getWorking() {
		return worked.get() + own.get() + hospital.get();
	}

	public void calculateSalary(double rate) {
		int working = getWorking();
		double salaryInEUR = getEUR(working, worked.get(), hospital.get(),
				salary.get());
		eur.set(round(salaryInEUR, 100));
		uah.set(round(salaryInEUR * rate, 100));
	}

	public void setHospital(int newHospital, double rate) {
		int free = worked.get() + hospital.get();
		if (newHospital < 0) {
			newHospital = 0;
			this.hospital.set(1);
			this.hospital.set(0);
		}
		this.hospital.set(newHospital <= free ? newHospital : free);
		worked.set(free - hospital.get());
		calculateSalary(rate);
	}

	public void setWorkingDay(int newWorking, double rate) {
		if (newWorking > 0) {
			int working = getWorking();
			if (newWorking > working) {
				worked.set(newWorking - own.get() - hospital.get());
			} else if (newWorking < working) {
				int diff = working - newWorking;
				int min = Math.min(worked.get(), diff);
				worked.set(worked.get() - min);
				diff -= min;
				if (min > 0) {
					min = Math.min(own.get(), diff);
					own.set(own.get() - min);
					diff -= min;
				}
				if (min > 0) {
					min = Math.min(hospital.get(), diff);
					hospital.set(hospital.get() - min);
					diff -= min;
				}
			}
			calculateSalary(rate);
		}
	}

	public static double getEUR(int working, int worked, int hospital,
			double salary) {
		double sd = salary / working;
		return worked * sd + hospital * sd / 2;
	}

	public static double round(double value, int dec) {
		return (double) ((int) (value * dec)) / dec;
	}
}
