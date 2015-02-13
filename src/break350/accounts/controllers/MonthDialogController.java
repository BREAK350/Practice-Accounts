package break350.accounts.controllers;

import break350.accounts.model.Days;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MonthDialogController {
	@FXML
	private Button okButton;
	@FXML
	private ComboBox<String> comboBoxMonth;
	@FXML
	private Button cancelButton;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void onClickCancelButton() {
		stage.close();
	}

	@FXML
	public void onClickOkButton() {
		int month = comboBoxMonth.getSelectionModel().getSelectedIndex();
		Days.setMonth(month);
		stage.close();
	}
}
