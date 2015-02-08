package accounts.model;

import javafx.collections.ObservableList;

public interface AbstractLoader {
	public ObservableList<Account> load();
}
