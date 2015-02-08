package accounts.file;

import java.io.File;

import accounts.model.AccountsData;

public interface FileWriter {
	public void writeIn(AccountsData accsData, File file);
}
