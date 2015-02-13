package break350.accounts.dao;

import javafx.collections.ObservableList;
import break350.accounts.model.Account;
import break350.accounts.service.AccountLoaderFactory;

public class AccountDaoImpl implements AccountDao {
	ObservableList<Account> data;

	public AccountDaoImpl() {
		data = AccountLoaderFactory.getAccountLoader().load();
	}

	@Override
	public ObservableList<Account> getAllAccounts() {
		return data;
	}
}
