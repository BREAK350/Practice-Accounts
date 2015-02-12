package break350.accounts.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileAccountLoader implements AbstractAccountLoader {
	private String fileName;
	private int working;
	private double rate;

	public FileAccountLoader(String fileName, int working, double rate) {
		this.fileName = fileName;
		this.working = working;
		this.rate = rate;
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
						working, rate);
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
