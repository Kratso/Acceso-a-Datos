package ej5;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		Persona p1 = new Persona(20, "Julio Perez", 'M');
		Persona p2 = new Persona(21, "Ana Gutierrez", 'F');
		Persona p3 = new Persona(84, "Emilia Rivilla", 'F');

		grabarPersonas(p1, p2, p3);
	}

	public static void grabarPersonas(Persona... personas) throws IOException {
		File txt = new File("personas.txt");
		File bin = new File("personas.bin");
		File obj = new File("personas.obj");
		PrintWriter pw = new PrintWriter(new FileWriter(txt));
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(bin));
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(obj));

		for (Persona p : personas) {
			pw.print(p.toString());
			dos.writeInt(p.edad);
			dos.writeUTF(p.nombre);
			dos.writeChar(p.sexo);
			oos.writeObject(p);
		}
		pw.close();
		dos.close();
		oos.close();
	}

	public static ArrayList<Persona> parseTxt(File file) throws IOException {
		BufferedReader bfr = new BufferedReader(new FileReader(file));
		ArrayList<Persona> lista = new ArrayList<>();
		String linea;
		while ((linea = bfr.readLine()) != null) {
			lista.add(parsePersona(linea));
		}
		return lista;
	}

	public static ArrayList<Persona> parseBin(File file) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		ArrayList<Persona> lista = new ArrayList<>();
		try {
			for (;;)
				lista.add(new Persona(dis.readInt(), dis.readUTF(), dis.readChar()));
		} catch (EOFException eofe) {

		}
		return lista;
	}

	public static ArrayList<Persona> parseObj(File file) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		ArrayList<Persona> lista = new ArrayList<Persona>();
		try {
			for (;;)
				lista.add((Persona) ois.readObject());
		} catch (EOFException eof) {

		} catch (ClassNotFoundException cnfe) {
			throw new IllegalArgumentException("Fichero mal formado");
		}
		return lista;
	}

	private static Persona parsePersona(String toParse) {
		try {
			String[] comps = toParse.replaceAll("[\\[\\]]", "").split("[,=]");
			return new Persona(Integer.parseInt(comps[3]), comps[1], comps[5].charAt(0));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Edad mal formada");
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Linea mal formada");
		}
	}

}
