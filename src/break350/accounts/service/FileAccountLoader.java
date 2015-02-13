package break350.accounts.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import break350.accounts.days.Days;
import break350.accounts.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileAccountLoader implements AccountLoader {
	private String fileName;

	public FileAccountLoader(String fileName) {
		this.fileName = fileName;
	}

	public ObservableList<Account> load() {
		ObservableList<Account> list = FXCollections.observableArrayList();
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(fileName));
			String row;
			int index = 1;
			while ((row = inputStream.readLine()) != null) {
				String[] spl = row.split("\t");
				String name = spl[0];
				int own = Integer.parseInt(spl[1]);
				int hospital = Integer.parseInt(spl[2]);
				double salary = Double.parseDouble(spl[3]);
				Account ac = new Account(index++, name, own, hospital, salary,
						Days.getWorkingDay());
				list.add(ac);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
