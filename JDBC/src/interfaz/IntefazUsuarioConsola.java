package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import javax.sound.midi.Soundbank;

import controlador.PersistenciaBD;
import controlador.Persona;

import java.io.FileReader;
import java.io.IOException;

import util.Utilidades;

public class IntefazUsuarioConsola {
	private static PersistenciaBD pbd;
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

	private static void accion(String in, String tabla) throws IOException, NumberFormatException, SQLException {
		switch (in) {
		case "0":
			System.exit(0);
		case "1":
			listar(tabla);
			break;
		case "2":
			alta();
			break;
		case "3":
			modificacion();
			break;
		case "4":
			baja();
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
		List<Persona> list = pbd.listadoPersonas(tabla, ordenes[Integer.parseInt(in)]);
		list.forEach(System.out::println);
	}

	private static void alta() {
		// TODO Auto-generated method stub
		
	}

	private static void modificacion() {
		// TODO Auto-generated method stub
		
	}

	private static void baja() {
		// TODO Auto-generated method stub
		
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
