package accounts.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileLoader implements AbstractLoader {
	private String fileName;

	public FileLoader(String fileName) {
		this.fileName = fileName;
	}

	public ObservableList<Account> load() {
		ObservableList<Account> list = FXCollections.observableArrayList();
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(fileName));
			String row;
			Account ac;
			while ((row = inputStream.readLine()) != null) {
				String[] data = row.split("\t");
				ac = new Account(data[0], Integer.parseInt(data[1]),
						Integer.parseInt(data[2]), Double.parseDouble(data[3]));
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
