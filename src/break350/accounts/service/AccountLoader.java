package break350.accounts.service;

import break350.accounts.model.Account;
import javafx.collections.ObservableList;

public interface AccountLoader {
	public ObservableList<Account> load();
}
