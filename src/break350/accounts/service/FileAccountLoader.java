package break350.accounts.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import break350.accounts.Configs;
import break350.accounts.model.Account;
import break350.accounts.model.Days;

public class FileAccountLoader implements AccountLoader {

	public FileAccountLoader() {
	}

	public ObservableList<Account> load() {
		ObservableList<Account> list = FXCollections.observableArrayList();
		try {
			String fileName = Configs.getProperties().getProperty(
					Configs.pathToEmployeesFile);
			String encodingForEmployees = Configs.getProperties().getProperty(
					Configs.encodingForEmployees);
			Reader r = new InputStreamReader(new FileInputStream(fileName),
					encodingForEmployees);
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(r);
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
