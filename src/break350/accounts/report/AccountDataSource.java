package break350.accounts.report;

import break350.accounts.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class AccountDataSource implements JRDataSource {
	private int index = -1;
	private static ObservableList<Account> data;

	public static ObservableList<Account> getData() {
		return data;
	}

	public static void setData(ObservableList<Account> data) {
		AccountDataSource.data = data;
	}

	public AccountDataSource(ObservableList<Account> data) {
		AccountDataSource.data = data;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		if (field.getName().equals("index")) {
			return data.get(index).indexProperty().get();
		} else if (field.getName().equals("name")) {
			return data.get(index).nameProperty().get();
		} else if (field.getName().equals("worked")) {
			return data.get(index).workedProperty().get();
		} else if (field.getName().equals("own")) {
			return data.get(index).ownProperty().get();
		} else if (field.getName().equals("hospital")) {
			return data.get(index).hospitalProperty().get();
		} else if (field.getName().equals("salary")) {
			return data.get(index).salaryProperty().get();
		} else if (field.getName().equals("eur")) {
			return data.get(index).eurProperty().get();
		} else if (field.getName().equals("uah")) {
			return data.get(index).uahProperty().get();
		}
		return "";
	}

	@Override
	public boolean next() throws JRException {
		if (data != null && index < data.size() - 1) {
			index++;
			return true;
		}
		return false;
	}

	public static JRDataSource getDataSource() {
		int count = 1;
		ObservableList<Account> data = FXCollections.observableArrayList(
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000),
				new Account(count++, "dima", 20, 0, 0, 100, 100, 1000));
		return new AccountDataSource(data);
	}
}
