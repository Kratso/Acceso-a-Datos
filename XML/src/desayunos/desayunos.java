package desayunos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class desayunos {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("desayunos.xml");
			NodeList set = (NodeList) (XPathFactory.newInstance().newXPath().evaluate("//descripcion", doc,
					XPathConstants.NODESET));
			for (int i = 0; i < set.getLength(); i++) {
				Node h = set.item(i);
				h.getParentNode().removeChild(h);
			}

			set = (NodeList) (XPathFactory.newInstance().newXPath().evaluate("//calorias", doc,
					XPathConstants.NODESET));
			int totalCalorias = 0;
			for (int i = 0; i < set.getLength(); i++) {
				Node h = set.item(i);
				totalCalorias += Integer.parseInt(h.getTextContent());
			}
			Element hijo = doc.createElement("totalcalorias");
			hijo.setTextContent(totalCalorias + "");
			doc.getFirstChild().appendChild(hijo);

			TreeMap<String, Double> divisas = new TreeMap<>();
			BufferedReader bfr = new BufferedReader(new FileReader(new File("Divisas.csv")));
			String linea;
			while ((linea = bfr.readLine()) != null)
				divisas.put(linea.split(";")[0], Double.parseDouble(linea.split(";")[1]));
			set = (NodeList) (XPathFactory.newInstance().newXPath().evaluate("//precio", doc, XPathConstants.NODESET));
			for (int i = 0; i < set.getLength(); i++) {
				Element h = (Element) set.item(i);
				String moneda = h.getAttributes().item(0).getTextContent();
				double euro;
				if (divisas.get(moneda) != null) {
					euro = Double.parseDouble(h.getTextContent()) / divisas.get(moneda);
					h.setAttribute("moneda", "euro");
					h.setTextContent(new DecimalFormat("#.##").format(euro));
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("desayunos2.xml"));
				transformer.transform(source, result);
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
