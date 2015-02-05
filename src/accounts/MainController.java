package accounts;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import accounts.model.Account;
import accounts.model.FileLoader;
import accounts.model.GlobalData;

public class MainController {
	private Stage stage;
	@FXML
	private Button btnMonths;
	@FXML
	private TableView<Account> tableAccounts;
	@FXML
	private TableColumn<Account, Integer> tcNumber;
	@FXML
	private TableColumn<Account, String> tcName;
	@FXML
	private TableColumn<Account, Integer> tcWorkedDays;
	@FXML
	private TableColumn<Account, Integer> tcOwnDay;
	@FXML
	private TableColumn<Account, Integer> tcHospitalDay;
	@FXML
	private TableColumn<Account, Double> tcSalary;
	@FXML
	private Label lblWorkingDays;
	@FXML
	private TextField txtRate;
	@FXML
	private TableColumn<Account, Double> tcEuroSumm;
	@FXML
	private TableColumn<Account, Double> tcUAHSumm;
	@FXML
	private Button btnGetRate;

	public void setStage(Stage stage) {
		this.stage = stage;
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setWorkingDays(int days) {
		GlobalData.setWorkingDay(days);
	}

	public void setCellValueFactory() {
		tcNumber.setCellValueFactory(new PropertyValueFactory<Account, Integer>(
				"id"));
		tcName.setCellValueFactory(new PropertyValueFactory<Account, String>(
				"name"));
		tcOwnDay.setCellValueFactory(new PropertyValueFactory<Account, Integer>(
				"ownDay"));
		tcHospitalDay
				.setCellValueFactory(new PropertyValueFactory<Account, Integer>(
						"hospitalDay"));
		tcSalary.setCellValueFactory(new PropertyValueFactory<Account, Double>(
				"salary"));
		tcEuroSumm
				.setCellValueFactory(new PropertyValueFactory<Account, Double>(
						"EURSumm"));
		tcUAHSumm
				.setCellValueFactory(new PropertyValueFactory<Account, Double>(
						"UAHSumm"));
		tcWorkedDays
				.setCellValueFactory(new PropertyValueFactory<Account, Integer>(
						"workedDays"));
	}

	@FXML
	private void initialize() {
		onClickBtnGetRate();
		GlobalData.setWorkingDay(21);
		lblWorkingDays.setText(String.valueOf(GlobalData.getWorkingDay()));
		setCellValueFactory();
		fillTable();
	}

	@FXML
	private void onClickBtnGetRate() {
		GlobalData.getRateFromWeb();
		txtRate.setText(String.valueOf(GlobalData.getRate()));
		tableAccounts.setVisible(false);
		tableAccounts.setVisible(true);
	}

	public void fillTable() {
		FileLoader fl = new FileLoader("Employees.txt");
		ObservableList<Account> items = fl.load();
		tableAccounts.setItems(items);
	}
}
