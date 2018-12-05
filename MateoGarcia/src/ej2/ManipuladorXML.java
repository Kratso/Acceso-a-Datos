package ej2;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

public class ManipuladorXML {
	
	static File xml = new File("comprobados.xml");
	static DocumentBuilderFactory dbf;
	static DocumentBuilder db;
	static Document doc;
	
	public static void inicializar() throws Exception{
		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		if(xml.exists())
			doc = db.parse(xml);
		else {
			doc = db.newDocument();
			Element root = doc.createElement("comprobados");
			
			root.setAttribute("sorteo", "navidad2018");
			
			doc.appendChild(root);
			root.appendChild(doc.createElement("numeros"));
		}
	}

	public static void poner(int numero) throws XPathExpressionException {
		Node raiz = (Node)(XPathFactory.newInstance().newXPath().evaluate("//numeros", doc,
				XPathConstants.NODE));
		Element e = doc.createElement("numero");
		e.setAttribute("valor", numero + "");
		e.setAttribute("premio", "NO");
		raiz.appendChild(e);
	}

	public static void poner(int num, String descripcion, int premio) throws XPathExpressionException {
		Node raiz = (Node)(XPathFactory.newInstance().newXPath().evaluate("//numeros", doc,
				XPathConstants.NODE));
		Element e = doc.createElement("numero");
		e.setAttribute("valor", num + "");
		e.setAttribute("premio", "SI");
		Element e2 = doc.createElement("descripcion");
		e2.setTextContent(descripcion);
		e.appendChild(e2);
		e2 = doc.createElement("cantidad");
		e2.setTextContent(premio + "");
		e.appendChild(e2);
		raiz.appendChild(e);
	}

	public static void guardar() throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xml);
		t.transform(source, result);
		
	}

}
