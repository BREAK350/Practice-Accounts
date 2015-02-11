package report;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import accounts.model.Account;

public class Report {
	public void print(ObservableList<Account> data) {
		String sourceFileName = "report.jasper";
		String printFileName = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
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

	public void exportToXLS(ObservableList<Account> data, File file) {
		String sourceFileName = "report.jasper";
		String printFileName = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JRDataSource dataSource = new AccountDataSource(data);
			printFileName = JasperFillManager.fillReportToFile(sourceFileName,
					parameters, dataSource);
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
		String sourceFileName = "report.jasper";
		String printFileName = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JRDataSource dataSource = new AccountDataSource(data);
			JROdtExporter docxExporter = new JROdtExporter();
			printFileName = JasperFillManager.fillReportToFile(sourceFileName,
					parameters, dataSource);
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
