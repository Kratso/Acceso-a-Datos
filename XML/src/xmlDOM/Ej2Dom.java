package xmlDOM;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class Ej2Dom {

	public static void main(String[] args) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse("/home/alumno/biblioteca/index.xml");
			Scanner sc = new Scanner(System.in);
			System.out.println("Titulo del libro");
			String titulo = sc.nextLine();
			System.out.println("Numero de linea:");
			String linea = sc.nextLine();
			Element des = (Element) (XPathFactory.newInstance().newXPath().evaluate(
					"//libro[@titulo=\'" + titulo + "\']/linea[@num=\'" 
					+ linea + "\']", doc, XPathConstants.NODE));
			if(des!=null) {
				File f = new File("/home/alumno/biblioteca/" + titulo);
				RandomAccessFile raf = new RandomAccessFile(f, "r");
				String resul;
				raf.seek(Long.parseLong(des.getTextContent()));
				System.out.println(raf.readLine());
				
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
		}

	}

}
