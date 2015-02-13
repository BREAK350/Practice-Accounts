package break350.accounts.dao;

import javafx.collections.ObservableList;
import break350.accounts.model.Account;
import break350.accounts.model.Daysable;
import break350.accounts.model.Rateable;

public interface AccountDao extends Rateable, Daysable {
	public ObservableList<Account> getAllAccounts();
}
