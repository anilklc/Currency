package DB;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Models.XMLView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class XMLHelper {
	private String forexSell;
	private String forexBuy;

	public String getForexSell() {
		return forexSell;
	}

	public void setForexSell(String forexSell) {
		this.forexSell = forexSell;
	}

	public String getForexBuy() {
		return forexBuy;
	}

	public void setForexBuy(String forexBuy) {
		this.forexBuy = forexBuy;
	}

	public ObservableList<XMLView> currencyList = FXCollections.observableArrayList();
	public ObservableList<String> currencyCodeList = FXCollections.observableArrayList();
	public ObservableList<String> currencyStringList = FXCollections.observableArrayList();

	// XML den veri çekme
	public void XMLGet() {
		try {
			URL url = new URL("https://www.tcmb.gov.tr/kurlar/today.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url.openStream());
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Currency");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String unit = element.getElementsByTagName("Unit").item(0).getTextContent();
					String currencyCode = element.getAttribute("CurrencyCode");
					String currencyName = element.getElementsByTagName("CurrencyName").item(0).getTextContent();
					String forexBuying = element.getElementsByTagName("ForexBuying").item(0).getTextContent();
					String forexSelling = element.getElementsByTagName("ForexSelling").item(0).getTextContent();
					currencyList.add(new XMLView(unit, currencyName, forexBuying, forexSelling, currencyCode));
					currencyStringList.add(currencyName);
					currencyCodeList.add(currencyCode);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// XML den sadece istenilen paranın alış satış fiyatını çekme
	public void XMLBuyAndSell(String currencyCode) {
		try {
			URL url = new URL("https://www.tcmb.gov.tr/kurlar/today.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url.openStream());
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Currency");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					String code = element.getAttribute("CurrencyCode");

					if (code.equals(currencyCode)) {
						setForexSell(element.getElementsByTagName("ForexSelling").item(0).getTextContent());
						setForexBuy(element.getElementsByTagName("ForexBuying").item(0).getTextContent());
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
