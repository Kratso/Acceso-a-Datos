package xmlSAX;

import java.io.*;
import java.util.Scanner;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class ManejadorBiblioteca extends DefaultHandler {
	
	private String tituloS;
	private String lineaS;
	private static String resul;
	
	private boolean titulo;
	private boolean linea;
	

	public ManejadorBiblioteca(String tituloS, String lineaS) {
		super();
		this.tituloS = tituloS;
		this.lineaS = lineaS;
		this.titulo = false;
		this.linea = false;
		this.resul = null;
	}

	@Override
	public void startDocument() throws SAXException {
		
	}

	public void endDocument() throws SAXException {
		
	}

	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		
		if(name.equals("libro"))
			if (attributes.getValue("titulo").equals(tituloS))
				titulo = true;
		if(titulo && name.equals("linea"))
			if(attributes.getValue("num").equals(lineaS))
				linea = true;
		
		
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(linea && titulo) {
			try {
				RandomAccessFile raf = new RandomAccessFile(new File("/home/alumno/biblioteca/" + tituloS), "r");
				char[] nch = new char[length];
				for(int i = 0; i < length; i++)
					nch[i] = ch[i+start];
				raf.seek(Long.parseLong(new String(nch)));
				resul = raf.readLine();
				linea = false;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		
	}

	public static String getResul() {
		return resul;
	}

	public static void setResul(String resul) {
		ManejadorBiblioteca.resul = resul;
	}

	public String getTituloS() {
		return tituloS;
	}

	public void setTituloS(String tituloS) {
		this.tituloS = tituloS;
	}

	public String getLineaS() {
		return lineaS;
	}

	public void setLineaS(String lineaS) {
		this.lineaS = lineaS;
	}

	public boolean isTitulo() {
		return titulo;
	}

	public void setTitulo(boolean titulo) {
		this.titulo = titulo;
	}

	public boolean isLinea() {
		return linea;
	}

	public void setLinea(boolean linea) {
		this.linea = linea;
	}
}

public class Ej2SAX {
	
	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		String titulo;
		String linea;
		System.out.println("Titulo del libro");
		titulo = s.nextLine();
		System.out.println("Linea del libro");
		linea = s.nextLine();
		try {
			// Creamos la factoria analizadores por defecto
			XMLReader reader = XMLReaderFactory.createXMLReader();
			// Añadimos nuestro manejador al reader
			reader.setContentHandler(new ManejadorBiblioteca(titulo, linea));
			// Procesamos el xml de ejemplo
			reader.parse(new InputSource("/home/alumno/biblioteca/index.xml"));
			System.out.println(ManejadorBiblioteca.getResul());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.close();
	}

}
