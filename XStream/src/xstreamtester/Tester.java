package xstreamtester;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Soundbank;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Tester {

	public static void main(String[] args) throws IOException {
		Persona p1=new Persona(20,"Julio Perez",'M');
		p1.añadirTrabajo(new Trabajo(1,"Albanil"));
		p1.añadirTrabajo(new Trabajo(1,"Encofrador"));
		Persona p2=new Persona(21,"Ana Gutierrez",'F');
		p2.añadirTrabajo(new Trabajo(2,"Ingeniero"));
		Persona p3=new Persona(84,"Emilia Rivilla",'F');
		p3.añadirTrabajo(new Trabajo(2,"Profesora"));
		p3.añadirTrabajo(new Trabajo(3,"Locutora de radio")); 
		
		List<Persona> personas = new ArrayList<>();
		
		personas.add(p1);
		personas.add(p2);
		personas.add(p3);
		
		XStream xstream = new XStream(new DomDriver());
		
		xstream.alias("personas", ArrayList.class);
		
		xstream.alias("persona", Persona.class);
		xstream.alias("trabajo", Trabajo.class);
		
		xstream.addImplicitCollection(Persona.class, "trabs", Trabajo.class);
		
		String xml = xstream.toXML(personas);
		xml = xml.replaceAll("list", "personas");

		
		System.out.println(xml);

		
		PrintWriter pw=new PrintWriter(new FileWriter(new File("Personas.xml")));
		pw.println(xml);
		pw.close();
		

	}

}
