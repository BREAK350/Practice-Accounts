package break350.accounts.dao;

import javafx.collections.ObservableList;
import break350.accounts.days.Daysable;
import break350.accounts.model.Account;
import break350.accounts.rate.Rateable;

public interface AccountDao extends Rateable, Daysable {
	public ObservableList<Account> getAllAccounts();
}
