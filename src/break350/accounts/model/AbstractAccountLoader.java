package break350.accounts.model;

import javafx.collections.ObservableList;

public interface AbstractAccountLoader {
	public ObservableList<Account> load();
}
