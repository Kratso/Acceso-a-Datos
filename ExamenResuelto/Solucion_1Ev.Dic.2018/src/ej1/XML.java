package ej1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML {
	public static Document cargarXML(File f) throws ParserConfigurationException, SAXException, IOException {
		Document doc=null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(f);//Crea arbol DOM a partir de archivo XML
		return doc;
	}

	public static String nombreTabla(Document sorteo) throws XPathExpressionException {
		// Obtiene el nombre de la tabla a partir del nombre y a�o del sorteo
		Element el = (Element) (XPathFactory.newInstance().newXPath().evaluate("/sorteo", sorteo,XPathConstants.NODE));
		
		return el.getAttribute("nombre")+el.getAttribute("anio");
	}

	public static ArrayList<Premio> obtenerPremios(Document sorteo) throws XPathExpressionException {
		ArrayList<Premio> al=new ArrayList<Premio>();
		// Obtiene lista de premios
		NodeList lista= (NodeList) XPathFactory.newInstance().newXPath().evaluate("//premio", sorteo,XPathConstants.NODESET);
		for (int i = 0; i < lista.getLength(); i++) {
			Element premio=(Element)lista.item(i);
			//Creamos premio
			Premio p=new Premio();
			p.setTipo(Integer.valueOf(premio.getAttribute("tipo")));
			p.setBolasExtraidas(0); //Aquellos premios para los que no exista <bolasExtraidas> quedar�n a 0
			//Obtenemos resto de elementos del premio
			NodeList elems=premio.getChildNodes();
			for (int j = 0; j < elems.getLength(); j++) {
				if (elems.item(j).getNodeType()==Node.ELEMENT_NODE){
					Element e=(Element) elems.item(j);
					switch (e.getNodeName()) {
					case "descripcion":
						p.setDescripcion(e.getTextContent());
						break;
					case "cantidad":
						p.setCantidad(Integer.valueOf(e.getTextContent()));
						break;
					case "bolasExtraidas":
						p.setBolasExtraidas(Integer.valueOf(e.getTextContent()));
						break;
					default:
						break;
					}
				}
			}
			//A�adimos a AL
			al.add(p);
		}
		return al;
	}

	public static String obtenerMonedaPremios(Document sorteo) throws XPathExpressionException {
		// Obtiene el nombre de la tabla a partir del nombre y a�o del sorteo
		Element el = (Element) (XPathFactory.newInstance().newXPath().evaluate("/sorteo/premios", sorteo,XPathConstants.NODE));
		
		return el.getAttribute("moneda");
	}

	public static void grabarNumeroComprobado(Document comprobados, int n, Premio pr) throws TransformerException, XPathExpressionException {
		//Si el numero ya existe no se graba
		Element el = (Element) (XPathFactory.newInstance().newXPath().evaluate(String.format("//numero[@valor=\"%d\"]",n), comprobados,XPathConstants.NODE));
		if (el!=null)
			return;
		//Localiza elemento numeros
		el = (Element) (XPathFactory.newInstance().newXPath().evaluate("/comprobados/numeros", comprobados,XPathConstants.NODE));
		//Crea nuevo elemento numero
        Element numero = comprobados.createElement("numero");
        numero.setAttribute("valor", String.valueOf(n));
        if (pr==null)
        	numero.setAttribute("premio", "NO");
        else{
        	numero.setAttribute("premio", "SI");
        	Element descripcion = comprobados.createElement("descripcion");
        	descripcion.setTextContent(pr.getDescripcion());
        	Element cantidad = comprobados.createElement("cantidad");
        	cantidad.setTextContent(String.valueOf(pr.getCantidad()));
        	numero.appendChild(descripcion);
        	numero.appendChild(cantidad);
        }
        //Aniade numero
        el.appendChild(numero);
		// Escribe arbol DOM a fichero XML
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
		DOMSource source = new DOMSource(comprobados);
		StreamResult result = new StreamResult(new File("comprobados.xml"));
		transformer.transform(source, result);
	} 

	public static Document creaDOMComprobados(String tabla) throws ParserConfigurationException {
		//Creamos arbol DOM
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        //crea elemento raiz
        Element comprobados = doc.createElement("comprobados");
        comprobados.setAttribute("sorteo", tabla);
        doc.appendChild(comprobados);
       
        Element numeros = doc.createElement("numeros");
        comprobados.appendChild(numeros);  
        return doc;
	}
}
