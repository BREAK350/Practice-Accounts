package accounts.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TableRow extends Account {
	private SimpleIntegerProperty index = new SimpleIntegerProperty();
	private SimpleIntegerProperty workedDays = new SimpleIntegerProperty();
	private SimpleDoubleProperty EURSumm = new SimpleDoubleProperty();
	private SimpleDoubleProperty UAHSumm = new SimpleDoubleProperty();

	public TableRow(int index, String name, int workedDays, int ownDay,
			int hospitalDay, double salary, double EURSumm, double UAHSumm) {
		super(name, ownDay, hospitalDay, salary);
		setIndex(index);
		setWorkedDays(workedDays);
		setEURSumm(EURSumm);
		setUAHSumm(UAHSumm);
	}

	public TableRow(int index, Account account, int workedDays, double EURSumm,
			double UAHSumm) {
		super(account.getName(), Integer.parseInt(account.getOwnDay()), Integer
				.parseInt(account.getHospitalDay()), account.getSalary());
		setIndex(index);
		setWorkedDays(workedDays);
		setEURSumm(EURSumm);
		setUAHSumm(UAHSumm);
	}

	public int getIndex() {
		return index.get();
	}

	public void setIndex(int index) {
		this.index.set(index > 0 ? index : 0);
	}

	public int getWorkedDays() {
		return workedDays.get();
	}

	public void setWorkedDays(int workedDays) {
		this.workedDays.set(workedDays);
	}

	public double getEURSumm() {
		return EURSumm.get();
	}

	public void setEURSumm(double EURsumm) {
		this.EURSumm.set(EURsumm);
	}

	public double getUAHSumm() {
		return UAHSumm.get();
	}

	public void setUAHSumm(double UAHsumm) {
		this.UAHSumm.set(UAHsumm);
	}
}
