package report;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import accounts.model.Account;

public class Report {
	public void print2(ObservableList<Account> data) {
		URL url = getClass().getResource("report.jasper");
		String sourceFileName = "report.jasper";
		String printFileName = null;

		Map parameters = new HashMap();
		try {
			JRDataSource dataSource = new AccountDataSource(data);
			printFileName = JasperFillManager.fillReportToFile(sourceFileName,
					parameters, dataSource);
			if (printFileName != null) {
				JasperPrintManager.printReport(printFileName, true);
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void print(ObservableList<Account> data) {
		JasperPrint jasperPrint;
		URL url = getClass().getResource("report.jrxml");
		// create a map to pass parameters to the report
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		// parameter.put("COMPANY_NR", companyID);
		try {
			// load the report from the reports plugin
			InputStream inputStream = url.openConnection().getInputStream();
			System.out.println("found jrxml file");
			// compile the report
			JasperReport report = JasperCompileManager
					.compileReport(inputStream);
			JRDataSource dataSource = new AccountDataSource(data);
			// let Jasper Reports use our already defined SQL Connection
			// JasperFillManager.fillReportToFile(url.getPath(),
			// "C:\\Users\\Дмитро\\Desktop\\test.html", null, dataSource);
			System.out.println("filled");
			// don’t create a file but let jasper put the contents in an byte
			// array
			// JasperExportManager.exportReportToHtmlFile(jasperPrint,
			// "C:\\Users\\Дмитро\\Desktop\\test.html");
			// ByteArrayOutputStream ba = new ByteArrayOutputStream();
			// JasperExportManager.exportReportToPdfStream(jasperPrint, ba);
			// return the contents to the caller
			// return ba.toByteArray();
			System.out.println("exported");
		} catch (JRException e) {
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
	}
}
