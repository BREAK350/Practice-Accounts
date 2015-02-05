package accounts;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import accounts.file.FileWriter;
import accounts.file.xls.XLSWriter;
import accounts.model.Account;
import accounts.model.FileLoader;
import accounts.model.GlobalData;
import accounts.model.TableRow;

public class MainController {
	private Stage stage;
	@FXML
	private Button btnMonths;
	@FXML
	private TableView<TableRow> tableAccounts;
	@FXML
	private TableColumn<TableRow, String> tcNumber;
	@FXML
	private TableColumn<TableRow, String> tcName;
	@FXML
	private TableColumn<TableRow, String> tcWorkedDays;
	@FXML
	private TableColumn<TableRow, String> tcOwnDay;
	@FXML
	private TableColumn<TableRow, String> tcHospitalDay;
	@FXML
	private TableColumn<TableRow, String> tcSalary;
	@FXML
	private Label lblWorkingDays;
	@FXML
	private TextField txtRate;
	@FXML
	private TableColumn<TableRow, String> tcEuroSumm;
	@FXML
	private TableColumn<TableRow, String> tcUAHSumm;
	@FXML
	private Button btnGetRate;
	@FXML
	private ComboBox cmbPrintFormat;

	private ObservableList<TableRow> items = FXCollections
			.observableArrayList();

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	@FXML
	public void onClickBtnMonths() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("MonthDialog.fxml"));
		Parent content;
		try {
			content = (Parent) loader.load();
			MonthDialogController mdc = loader.getController();
			Stage stage = new Stage();
			mdc.setStage(stage);
			stage.setScene(new Scene(content));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			changeMonth();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCellValueFactory() {
		tcNumber.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
				"index"));
		tcName.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
				"name"));
		tcOwnDay.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
				"ownDay"));
		tcHospitalDay
				.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
						"hospitalDay"));
		tcSalary.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
				"salary"));
		tcEuroSumm
				.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
						"EURSumm"));
		tcUAHSumm
				.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
						"UAHSumm"));
		tcWorkedDays
				.setCellValueFactory(new PropertyValueFactory<TableRow, String>(
						"workedDays"));
		// tcName.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	@FXML
	private void initialize() {
		onClickBtnGetRate();
		GlobalData.loadWorkingDays();
		int month = getCurrMonth();
		GlobalData.setMonth(month);
		changeMonth();
		setCellValueFactory();
		fillTable();
	}

	private int getCurrMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH);
	}

	@FXML
	private void onClickBtnGetRate() {
		GlobalData.loadRateFromWeb();
		txtRate.setText(String.valueOf(GlobalData.getRate()));
		reloadTable();
	}

	@FXML
	private void onClickBtnPrint() {
		Object type = cmbPrintFormat.getValue();
		if (type != null) {
			String stype = (String) type;
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
					stype.toUpperCase() + " files (*." + stype + ")", "*."
							+ stype);
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			File file = fileChooser.showSaveDialog(stage);
			System.out.println("file is " + file.getPath());
			if (stype.equals("xls")) {
				// save as xls file
				saveAsXLS(file);
			} else if (stype.equals("odt")) {
				// save as odt file
			}
		} else {
			// show error
		}
	}

	private void saveAsXLS(File file) {
		FileWriter xls = new XLSWriter();
		xls.writeIn(file);
	}

	public Account setValueWithReplace(int row, TableRow tableRow) {
		TableRow old = getValue(row);
		items.remove(row);
		tableRow.setIndex(row + 1);
		items.add(row, tableRow);
		return old;
	}

	public TableRow getValue(int row) {
		return items.get(row);
	}

	private void reloadTable() {
		if (items != null) {
			int n = items.size();
			System.out.println(GlobalData.getWorkingDay());
			for (int i = 0; i < n; i++) {
				TableRow tr = getValue(i);
				tr.setWorkedDays(getWorkedDays(tr));
				tr.setEURSumm(getEURSumm(tr));
				tr.setUAHSumm(getUAHSumm(tr.getEURSumm()));
				setValueWithReplace(i, tr);
				System.out.println(tr);
			}
		}
	}

	private void changeMonth() {
		lblWorkingDays.setText(String.valueOf(GlobalData.getWorkingDay()));
		reloadTable();
	}

	private int getWorkedDays(int ownDays, int hospitalDays) {
		return GlobalData.getWorkingDay() - ownDays - hospitalDays;
	}

	private int getWorkedDays(Account account) {
		return getWorkedDays(account.getOwnDay(), account.getHospitalDay());
	}

	public static double round(double value, int accuracy) {
		return ((int) (value * accuracy)) / ((double) accuracy);
	}

	private double getEURSumm(double salary, int ownDays, int hospitalDays) {
		double salaryInDay = salary / GlobalData.getWorkingDay();
		double worked = getWorkedDays(ownDays, hospitalDays) * salaryInDay;
		double hospitaled = hospitalDays * salaryInDay * 0.50;
		double res = round(worked + hospitaled, 100);
		return res;
	}

	private double getEURSumm(Account account) {
		return getEURSumm(account.getSalary(), account.getOwnDay(),
				account.getHospitalDay());
	}

	private double getUAHSumm(double EURSumm) {
		return round(EURSumm * GlobalData.getRate(), 100);
	}

	public void fillTable() {
		FileLoader fl = new FileLoader("Employees.txt");
		ObservableList<Account> accs = fl.load();
		int n = accs.size();
		for (int i = 0; i < n; i++) {
			Account acc = accs.get(i);
			double EURSumm = getEURSumm(acc);
			TableRow tr = new TableRow(i + 1, acc, getWorkedDays(acc), EURSumm,
					getUAHSumm(EURSumm));
			items.add(tr);
		}
		tableAccounts.setItems(items);
	}
}
