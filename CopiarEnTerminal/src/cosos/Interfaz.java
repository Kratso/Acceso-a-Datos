package cosos;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import utilidades.Utilidades;

public class Interfaz {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		if (args.length == 2) {
			File in, out;
			String s = "s";
			in = new File(args[0]);
			out = new File(args[1]);
			if (in.exists()) {
				if (out.exists()) {
					System.out.println("El fichero de salida ya existe.");
					System.out.println("Esta es una operación destructiva.");
					System.out.println("Desea sobreescribir el fichero " + args[1] + "?[S/n]");

					do {
						s = sc.next();
					} while (!s.matches("^[nNsS]?$"));

				}
				if (s.toUpperCase().equals("S") || s.equals("")) {
					try {
						Utilidades.copiarFichero(in, out);
					} catch (IOException ioe) {
						System.out.println(ioe.getStackTrace());
						System.out.println(ioe.getMessage());
					}
				}
			} else {
				System.out.println("El fichero de entrada no existe");
			}
		} else {
			System.out.println("Número de argumentos inválido");
		}
	}
}
