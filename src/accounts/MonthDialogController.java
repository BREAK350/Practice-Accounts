package accounts;

import accounts.model.GlobalData;
import javafx.collections.ObservableList;
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
		ObservableList<String> items = comboBoxMonth.getItems();
		String selected = comboBoxMonth.getValue();
		int i = 0;
		while (i < 12 && items.get(i).equals(selected) == false) {
			i++;
		}
		if (i < 12) {
			GlobalData.setMonth(i);
		}
		stage.close();
	}
}
