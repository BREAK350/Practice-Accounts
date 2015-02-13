package break350.accounts.dao;

import java.util.Iterator;

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

	@Override
	public void setNewRate(double rate) {
		
	}

	@Override
	public void setNewWorking(int working) {
		
	}
	
	private void calculateSalary() {
		double rate = getRate();
		for (Iterator<Account> iterator = data.iterator(); iterator.hasNext();) {
			Account account = iterator.next();
			account.calculateSalary(rate);
		}
	}
}
