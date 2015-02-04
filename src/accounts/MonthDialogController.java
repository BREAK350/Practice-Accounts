package accounts;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MonthDialogController {
	@FXML
	private Button okButton;
	@FXML
	private ComboBox comboBoxMonth;
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
		stage.close();
	}
}
