package xstreamtester;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Tester2 {

	public static void main(String[] args) throws IOException {
		XStream xstream = new XStream(new DomDriver());
		BufferedReader bfr = new BufferedReader(new FileReader(new File("Personas.xml")));
		String linea = "", xml = "";
		do {
			xml += linea + "\n";
			linea = bfr.readLine();
		} while (linea != null);
		System.out.println(xml);
		xstream.alias("personas", ArrayList.class);

		xstream.alias("persona", Persona.class);
		xstream.alias("trabajo", Trabajo.class);
		xstream.addImplicitCollection(Persona.class, "trabs", Trabajo.class);
		ArrayList<Persona> p = (ArrayList<Persona>) xstream.fromXML(xml);
		for(Persona per : p)
			System.out.println(per.toString());
	}

}
