package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilidades {
	public static void menu(String mensaje, boolean numerico, String[] opciones) {
		char index = numerico? (char)('1' - 1) :(char)('A' - 1);
		System.out.println(mensaje);
		List<String> opcionesList = new ArrayList<String>(Arrays.asList(opciones));
		for(String s : opcionesList)
			System.out.println(++index + ".-" + s);
		System.out.println("0.- Salir");
		
	}
	
}
