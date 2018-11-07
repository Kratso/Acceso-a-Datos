package xmlDOM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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

public class xmlDom {
	public static void main(String[] args) {
		File[] files;

		try {
			// Creamos arbol DOM
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			// crea elemento raiz
			Element raiz = doc.createElement("biblioteca");
			doc.appendChild(raiz);
			File biblio = new File("/home/alumno/biblioteca");
			files = biblio.listFiles();
			for (File f : files) {
				if (!f.getName().matches(".*[.]xml"))
					appendLibro(f, doc, raiz);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("/home/alumno/biblioteca/index.xml"));
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void appendLibro(File f, Document doc, Element raiz) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		long i = raf.getFilePointer();
		int cnt = 1;
		Element hijo = doc.createElement("libro");
		hijo.setAttribute("titulo", f.getName());
		raiz.appendChild(hijo);
		while ((raf.readLine()) != null) {
			Element linea = doc.createElement("linea");
			linea.setAttribute("num", cnt + "");
			linea.setTextContent(i + "");
			hijo.appendChild(linea);
			i = raf.getFilePointer();
			cnt++;
		}
	}

}
