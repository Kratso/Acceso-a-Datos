package ej1;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ManejadorXML {
	static File xml;

	public ManejadorXML(String file) {
		xml = new File(file);
	}

	public String getNombre() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);

		Node root = doc.getChildNodes().item(0);
		String nombre = root.getAttributes().getNamedItem("nombre").getTextContent();
		String anio = root.getAttributes().getNamedItem("anio").getTextContent();

		return nombre + anio;
	}

	public int maxPulls() throws ParserConfigurationException, SAXException, IOException, NumberFormatException,
			DOMException, XPathExpressionException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);

		return (int) Math.floor((Double) (XPathFactory.newInstance().newXPath().evaluate("sum(//bolasExtraidas)", doc,
				XPathConstants.NUMBER)));
	}

	public int[][] getBolasPremios() throws ParserConfigurationException, SAXException, IOException,
			NumberFormatException, DOMException, XPathExpressionException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		int[][] result = new int[(int) Math.floor((Double) (XPathFactory.newInstance().newXPath()
				.evaluate("count(//premio[./bolasExtraidas > 0])", doc, XPathConstants.NUMBER)))][];
		for (int i = 0; i < result.length; i++) {
			System.out.println(((Node) (XPathFactory.newInstance().newXPath()
					.evaluate("//premio[@tipo = \'" + i + "\']/bolasExtraidas", doc, XPathConstants.NODE)))
							.getTextContent());
			result[i] = new int[Integer.parseInt(((Node) (XPathFactory.newInstance().newXPath()
					.evaluate("//premio[@tipo = \'" + i + "\']/bolasExtraidas", doc, XPathConstants.NODE)))
							.getTextContent())];
		}
		return result;
	}

	public int getPremio(int i) throws ParserConfigurationException, SAXException, IOException, NumberFormatException,
			DOMException, XPathExpressionException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		return Integer.parseInt(((Node) (XPathFactory.newInstance().newXPath()
				.evaluate("//premio[@tipo = \'" + i + "\']/bolasExtraidas", doc, XPathConstants.NODE)))
						.getTextContent());
	}

	public static String getDesc(int tipo)
			throws SAXException, IOException, DOMException, XPathExpressionException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);
		return ((Node) (XPathFactory.newInstance().newXPath().evaluate("//descripcion[../@tipo=\'" + tipo + "\']", doc,
				XPathConstants.NODE))).getTextContent();
	}

}
