package break350.accounts.dao;

import javafx.collections.ObservableList;
import break350.accounts.model.Account;

public interface AccountDao {
	public ObservableList<Account> getAllAccounts();

	public void setNewRate(double rate);

	public void setNewWorking(int working);
}
