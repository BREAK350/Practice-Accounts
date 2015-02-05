package accounts.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GlobalData {
	private static int month;
	private static int[] workingDays = new int[12];
	private static double rate;

	public static int getWorkingDay() {
		return workingDays[month];
	}

	public static void setMonth(int month) {
		GlobalData.month = month;
	}

	public static double getRate() {
		return rate;
	}

	public static void setRate(double rate) {
		GlobalData.rate = rate;
	}

	public static void loadWorkingDays() {
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

	public static void loadRateFromWeb() {
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
