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

	public static double getEUR(int working, int worked, int hospital,
			double salary) {
		double sd = salary / working;
		return worked * sd + hospital * sd / 2;
	}

}
