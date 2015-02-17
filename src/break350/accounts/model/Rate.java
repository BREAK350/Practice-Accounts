package break350.accounts.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import break350.accounts.utils.Util;

public class Rate {
	public final static String CURR_RATE_URL = "http://bank-ua.com/export/currrate.xml";
	public final static String EUR = "EUR";
	public final static String USD = "USD";

	private static String CURRENCY = EUR;

	private static double rate = -1;
	private static List<Rateable> rateables = new ArrayList<Rateable>();

	public static void addRateable(Rateable rateable) {
		if (rateable != null) {
			rateables.add(rateable);
		}
	}

	public static void removeRateable(Rateable rateable) {
		rateables.remove(rateable);
	}

	public static void removeAllRateable() {
		rateables.clear();
	}

	private static void rateChanged() {
		for (Rateable rateable : rateables) {
			rateable.setRate(getRate());
		}
	}

	public static double getRate() {
		return rate;
	}

	public static String getCurrency() {
		return CURRENCY;
	}

	public static void setCurrency(String currency) {
		if (currency != null && !currency.isEmpty()) {
			if (currency.compareToIgnoreCase(EUR) == 0) {
				Rate.CURRENCY = EUR;
				loadFromWeb();
			} else if (currency.compareToIgnoreCase(USD) == 0) {
				Rate.CURRENCY = USD;
				loadFromWeb();
			}
		}
	}

	private static double searhRate(Document doc, String currency) {
		double r = 0;
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
					eur = itemNode.getTextContent().equals(currency);
				}
				if (eur && itemNode.getNodeName().equals("rate")
						&& itemNode.getTextContent() != null) {
					try {
						r = Double.parseDouble(itemNode.getTextContent());
					} catch (NumberFormatException e) {
						r = -1;
						e.printStackTrace();
					}
				}
				++j;
			}
			++i;
		}
		return r;
	}

	public static double loadFromWeb() {
		double r = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			try {
				Document doc = db.parse(new URL(CURR_RATE_URL).openStream());
				r = searhRate(doc, getCurrency());
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
		_setRate(Util.round(r / 100, 10000));
		return getRate();
	}

	private static void _setRate(double newRate) {
		rate = newRate;
		rateChanged();
	}

	public static void setRate(double newRate) {
		if (newRate <= 0) {
			loadFromWeb();
		} else {
			_setRate(newRate);
		}
	}

	public static void addAllRateable(Rateable... rateables) {
		for (int i = 0; i < rateables.length; i++) {
			addRateable(rateables[i]);
		}
	}
}
