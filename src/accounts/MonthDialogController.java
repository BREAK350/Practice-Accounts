package accounts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import accounts.model.AccountsData;

public class MonthDialogController {
	@FXML
	private Button okButton;
	@FXML
	private ComboBox<String> comboBoxMonth;
	@FXML
	private Button cancelButton;

	private Stage stage;
	private AccountsData accsData;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setAccsData(AccountsData accsData) {
		this.accsData = accsData;
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
			accsData.setMonth(i);
		}
		stage.close();
	}
}
