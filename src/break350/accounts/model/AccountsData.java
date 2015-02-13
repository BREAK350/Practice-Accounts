package break350.accounts.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountsData {
	private double rate;
	private ObservableList<Account> data = FXCollections.observableArrayList();
	private int[] days = new int[12];
	private int month;

	public void initDays() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		int month = cal.get(Calendar.MONTH);
		setWorkingDay(days[month]);
	}

	private void setWorkingDay(int working) {
		double rate = getRate();
		for (Iterator<Account> iterator = data.iterator(); iterator.hasNext();) {
			Account account = iterator.next();
			account.setWorkingDay(working, rate);
		}
	}

	public void setMonth(int month) {
		this.month = month;
		setWorkingDay(days[month]);
	}

	public void loadAccounts(AccountLoader abstractAccountLoader) {
		data = abstractAccountLoader.load();
	}

	public int getWorkingDay() {
		return days[month];
	}

	public ObservableList<Account> getData() {
		return data;
	}

	public void loadDays(String fileName) {
		try {
			@SuppressWarnings("resource")
			BufferedReader inputStream = new BufferedReader(
					new java.io.FileReader(fileName));
			String row;
			int i = 0;
			while ((row = inputStream.readLine()) != null) {
				days[i++] = Integer.parseInt(row);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
		calculateSalary();
	}

	public void calculateSalary() {
		double rate = getRate();
		for (Iterator<Account> iterator = data.iterator(); iterator.hasNext();) {
			Account account = iterator.next();
			account.calculateSalary(rate);
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
		rate = Account.round(r / 100, 10000);
	}
}
