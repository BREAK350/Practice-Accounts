package accounts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import accounts.model.Account;

public class MainController implements Initializable {
	@FXML
	private TableView<Account> table;

	@FXML
	private TableColumn<Account, Integer> index;
	@FXML
	private TableColumn<Account, String> name;
	@FXML
	private TableColumn<Account, Integer> worked;
	@FXML
	private TableColumn<Object, Integer> own;
	@FXML
	private TableColumn<Object, Integer> hospital;
	@FXML
	private TableColumn<Account, Double> salary;
	@FXML
	private TableColumn<Account, Double> eur;
	@FXML
	private TableColumn<Account, Double> uah;

	private ObservableList<Account> data = FXCollections.observableArrayList();

	public void initialize(URL location, ResourceBundle resources) {
		setCellsValueFactory();
		setCellsFactory();
		loadFromFile("Employees.txt", 20, 10.0);
		table.setItems(data);
	}

	private void setCellsFactory() {
		own.setCellFactory(TextFieldTableCell
				.forTableColumn(new StringConverter<Integer>() {

					@Override
					public String toString(Integer object) {
						return object.toString();
					}

					@Override
					public Integer fromString(String string) {
						return Integer.decode(string);
					}
				}));
		own.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Object, Integer>>() {

			public void handle(CellEditEvent<Object, Integer> event) {
				((Account) event.getTableView().getItems()
						.get(event.getTablePosition().getRow())).ownProperty()
						.set(event.getNewValue());
			}
		});

		hospital.setCellFactory(TextFieldTableCell
				.forTableColumn(new StringConverter<Integer>() {

					@Override
					public String toString(Integer object) {
						return object.toString();
					}

					@Override
					public Integer fromString(String string) {
						return Integer.decode(string);
					}
				}));
		hospital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Object, Integer>>() {

			public void handle(CellEditEvent<Object, Integer> event) {
				((Account) event.getTableView().getItems()
						.get(event.getTablePosition().getRow()))
						.hospitalProperty().set(event.getNewValue());
			}
		});
	}

	private void loadFromFile(String fileName, int working, double rate) {
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
				int worked = working - own - hospital;
				double salary = Double.parseDouble(spl[3]);
				double eur = Account.getEUR(working, worked, hospital, salary);
				double uah = eur * rate;
				Account ac = new Account(index++, name, worked, own, hospital,
						salary, eur, uah);
				data.add(ac);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setCellsValueFactory() {
		index.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Account, Integer> param) {
				return param.getValue().indexProperty().asObject();
			}
		});

		name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {

			public ObservableValue<String> call(
					CellDataFeatures<Account, String> param) {
				return param.getValue().nameProperty();
			}
		});

		worked.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Account, Integer> param) {
				return param.getValue().workedProperty().asObject();
			}
		});

		own.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Object, Integer> param) {
				return ((Account) param.getValue()).ownProperty().asObject();
			}
		});

		hospital.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Integer>, ObservableValue<Integer>>() {

			public ObservableValue<Integer> call(
					CellDataFeatures<Object, Integer> param) {
				return ((Account) param.getValue()).hospitalProperty()
						.asObject();
			}
		});

		salary.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Double>, ObservableValue<Double>>() {

			public ObservableValue<Double> call(
					CellDataFeatures<Account, Double> param) {
				return param.getValue().salaryProperty().asObject();
			}
		});

		eur.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Double>, ObservableValue<Double>>() {

			public ObservableValue<Double> call(
					CellDataFeatures<Account, Double> param) {
				return param.getValue().eurProperty().asObject();
			}
		});

		uah.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, Double>, ObservableValue<Double>>() {

			public ObservableValue<Double> call(
					CellDataFeatures<Account, Double> param) {
				return param.getValue().uahProperty().asObject();
			}
		});
	}

}
