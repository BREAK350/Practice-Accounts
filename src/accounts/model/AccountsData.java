package accounts.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AccountsData {
	private ObservableList<TableRow> items = FXCollections
			.observableArrayList();
	private int month;
	private int[] workingDays = new int[12];
	private double rate;

	public int getWorkingDay() {
		return workingDays[month];
	}

	public ObservableList<TableRow> getItems() {
		return items;
	}

	public void setItems(ObservableList<TableRow> items) {
		this.items = items;
	}

	public static double round(double value, int accuracy) {
		return ((int) (value * accuracy)) / ((double) accuracy);
	}

	private double getEURSumm(double salary, int ownDays, int hospitalDays) {
		double salaryInDay = salary / getWorkingDay();
		double worked = getWorkedDays(ownDays, hospitalDays) * salaryInDay;
		double hospitaled = hospitalDays * salaryInDay * 0.50;
		double res = round(worked + hospitaled, 100);
		return res;
	}

	private int getWorkedDays(int ownDays, int hospitalDays) {
		return getWorkingDay() - ownDays - hospitalDays;
	}

	private int getWorkedDays(Account account) {
		return getWorkedDays(Integer.parseInt(account.getOwnDay()),
				Integer.parseInt(account.getHospitalDay()));
	}

	private double getEURSumm(Account account) {
		return getEURSumm(account.getSalary(),
				Integer.parseInt(account.getOwnDay()),
				Integer.parseInt(account.getHospitalDay()));
	}

	private double getUAHSumm(double EURSumm) {
		return round(EURSumm * getRate(), 100);
	}

	public void setItems(AbstractLoader loader) {
		items.clear();
		ObservableList<Account> accs = loader.load();
		int n = accs.size();
		for (int i = 0; i < n; i++) {
			Account acc = accs.get(i);
			double EURSumm = getEURSumm(acc);
			TableRow tr = new TableRow(i + 1, acc, getWorkedDays(acc), EURSumm,
					getUAHSumm(EURSumm));
			items.add(tr);
		}
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public void loadWorkingDays() {
		String fileName = "Months.txt";
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(fileName));
			for (int i = 0; i < 12; i++) {
				workingDays[i] = Integer.parseInt(inputStream.readLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadRateFromWeb() {
		String url = "http://bank-ua.com/export/currrate.xml";
		double r = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			try {
				Document doc = db.parse(new URL(url).openStream());
				NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
				int i = 0;
				boolean eur = false;
				while (r == 0 && i < nodeList.getLength()) {
					Node node = nodeList.item(i);
					NodeList itemList = node.getChildNodes();
					int j = 0;
					while (r == 0 && j < itemList.getLength()) {
						Node itemNode = itemList.item(j);
						if (itemNode.getNodeName().equals("char3")
								&& itemNode.getTextContent() != null) {
							eur = itemNode.getTextContent().equals("EUR");
						}
						if (eur && itemNode.getNodeName().equals("rate")
								&& itemNode.getTextContent() != null) {
							r = Double.parseDouble(itemNode.getTextContent());
						}
						++j;
					}
					++i;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		setRate(r / 100);
	}
}
