package break350.accounts.dao;

import javafx.collections.ObservableList;
import break350.accounts.model.Account;
import break350.accounts.rate.Rateable;

public interface AccountDao extends Rateable {
	public ObservableList<Account> getAllAccounts();

	public void setNewWorking(int working);
}
