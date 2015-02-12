package break350.accounts.controllers;

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
	private MainController mainController;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	public void onClickCancelButton() {
		stage.close();
	}

	@FXML
	public void onClickOkButton() {
		int index = comboBoxMonth.getSelectionModel().getSelectedIndex();
		if (index >= 0 && index < 12) {
			mainController.setMonth(index);
		}
		stage.close();
	}
}
