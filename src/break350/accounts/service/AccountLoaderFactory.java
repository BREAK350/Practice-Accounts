package break350.accounts.service;

public class AccountLoaderFactory {
	public static AccountLoader getAccountLoader() {
		return new XLSAccountLoader();
	}
}
