package ej1.interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import javax.sound.midi.Soundbank;

import ej1.controlador.Persistencia;
import ej1.controlador.PersistenciaBD;
import ej1.controlador.Persona;

import java.io.FileReader;
import java.io.IOException;

import ej1.util.Utilidades;

public class IntefazUsuarioConsola {
	private static Persistencia<Persona> pbd;
	
	
	public static void main(String[] args) throws Exception {
		String[] cfgini = new String[5];
		BufferedReader br = new BufferedReader(new FileReader(new File("cfg.ini")));
		String linea;
		int i = 0;
		while((linea = br.readLine()) != null) {
			cfgini[i] = linea;
			i++;
		}
		
		
		pbd = new PersistenciaBD();
		pbd.conectarDB(cfgini[0], cfgini[1], cfgini[2], cfgini[3]);
		
		String[] opciones = { "Listar", "Alta", "Modificación", "Baja" };
		String in;
		do {
			Utilidades.menu("Bienvenido al CRUD de Personas", true, opciones);
			in = recoger("0,4");
			accion(in, cfgini[4]);
		} while (!in.equals("0"));
		pbd.desconectarDB();
	}

	private static void accion(String in, String tabla) throws Exception {
		switch (in) {
		case "0":
			System.exit(0);
		case "1":
			listar(tabla);
			break;
		case "2":
			alta(tabla);
			break;
		case "3":
			modificacion(tabla);
			break;
		case "4":
			baja(tabla);
			break;
			
		}
		
	}

	private static void listar(String tabla) throws IOException, NumberFormatException, SQLException {
		String[] opciones = { "Sin Orden", "Orden por Mail", "Orden por CP", "Orden por Nombre", "Orden por País" };
		String in;
		String[] ordenes = {null, null, "email", "CP", "nombre", "pais"};
		do {
			Utilidades.menu("Elija como listar:", true, opciones);
			in = recoger("0,5");
		} while (!in.matches("[0-5]"));
		List<Persona> list = pbd.listado(tabla, ordenes[Integer.parseInt(in)]);
		list.forEach(System.out::println);
	}

	private static void alta(String tabla) throws Exception {
		System.out.println("Bienvenido al proceso de alta");
		System.out.println("Introduzca el email de la persona a dar de alta:");
		String email = Utilidades.leerLinea();
		if(pbd.consultar(tabla, email) == null) {
			System.out.println("Introduzca el nombre:");
			String nombre = Utilidades.leerLinea();
			System.out.println("Introduzca el codigo postal");
			String cp = Utilidades.leerLinea();
			System.out.println("Introduzca el pais");
			String pais = Utilidades.leerLinea();
			pbd.guardar(tabla, new Persona(nombre, cp, pais, email));
		} else {
			System.out.println("Ya existe una persona con ese email.");
		}
	}

	private static void modificacion(String tabla) {
		// TODO Auto-generated method stub
		
	}

	private static void baja(String tabla) {
		System.out.println("Introduzca el email a borrar");
		
	}

	private static String recoger(String rango) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] parsed = rango.split(",");
		String in;
		do {
			System.out.println("Introduzca su opcion:");
			in = br.readLine();
		}while(in.charAt(0) < parsed[0].charAt(0) || in.charAt(0) > parsed[1].charAt(0));
		return in.charAt(0) + "";
		
	}
}
