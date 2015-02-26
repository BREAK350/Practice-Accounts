package break350.accounts;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configs {
	public final static String configFileName = "config.properties";
	public final static String pathToMainFXML = "path_to_main.fxml";
	public final static String pathToMonthFXML = "path_to_month.fxml";
	public final static String pathToMainCSS = "path_to_main.css";
	public final static String pathToMonthCSS = "path_to_month.css";

	public final static String pathToEmployeesFile = "path_to_employees_file";
	public final static String pathToMonthsFile = "path_to_months_file";

	public final static String encodingForEmployees = "encodingForEmployees";

	private static Properties properties = null;

	public static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream(configFileName);
				properties.load(input);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					createDefaultProperties();
					saveProperties();
				}
			}
		}
		return properties;
	}

	public static void saveProperties() {
		if (properties != null) {
			OutputStream output = null;
			try {
				output = new FileOutputStream(configFileName);
				properties.store(output, null);
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void createDefaultProperties() {
		properties.setProperty(pathToMainFXML, "design/fxml/Main.fxml");
		properties.setProperty(pathToMonthFXML, "design/fxml/MonthDialog.fxml");
		properties.setProperty(pathToMainCSS, "design/style/main.css");
		properties.setProperty(pathToMonthCSS, "design/style/month.css");

		properties.setProperty(pathToEmployeesFile, "Employees.txt");
		properties.setProperty(pathToMonthsFile, "Months.txt");

		properties.setProperty(encodingForEmployees, "UTF-8");
	}
}
