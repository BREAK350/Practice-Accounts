package break350.accounts.report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import break350.accounts.model.Account;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;

public class Report {
	public static String REPORT_FILE_NAME = "report/report.jasper";

	public void print(ObservableList<Account> data) {
		String printFileName = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JRDataSource dataSource = new AccountDataSource(data);
			printFileName = JasperFillManager.fillReportToFile(
					REPORT_FILE_NAME, parameters, dataSource);
			if (printFileName != null) {
				JasperPrintManager.printReport(printFileName, true);
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void exportToXLS(ObservableList<Account> data, File file) {
		String printFileName = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JRDataSource dataSource = new AccountDataSource(data);
			printFileName = JasperFillManager.fillReportToFile(
					REPORT_FILE_NAME, parameters, dataSource);
			if (printFileName != null) {
				JRXlsExporter exporter = new JRXlsExporter();

				exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
						printFileName);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						file.getAbsolutePath());

				exporter.exportReport();
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void exportToODT(ObservableList<Account> data, File file) {
		String printFileName = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JRDataSource dataSource = new AccountDataSource(data);
			JROdtExporter docxExporter = new JROdtExporter();
			printFileName = JasperFillManager.fillReportToFile(
					REPORT_FILE_NAME, parameters, dataSource);
			if (printFileName != null) {
				docxExporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
						printFileName);
				docxExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						file.getAbsolutePath());
				docxExporter.exportReport();
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
