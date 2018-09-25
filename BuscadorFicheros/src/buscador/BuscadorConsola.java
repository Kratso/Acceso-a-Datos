package buscador;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import utilidades.Utilidades;

public class BuscadorConsola {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		for (;;) {
			boolean oculto = false, recursivo = false;
			char criterio;
			File ruta;
			System.out.printf("\n#");
			String in = sc.nextLine();
			String[] arguments = in.split(" ");
			if (arguments[0].equals("ls") && arguments.length == 5) {
				if (arguments[1].matches("^-(?!$)(?<let>[R])?((?<num>[h]))?$")) {
					switch (arguments[1]) {
					case "-R":
						recursivo = true;
						oculto = false;
						break;
					case "-h":
						recursivo = false;
						oculto = true;
						break;
					case "-Rh":
						recursivo = true;
						oculto = true;
						break;
					}
					criterio = arguments[2].charAt(0);

					ruta = new File(arguments[3].trim());
					if (ruta.exists() && ruta.isDirectory()) {
						if (arguments[4].matches("[0-9]+")) {
							try {
							ArrayList<File> lista = Utilidades.buscarArchivosPorTamanio(ruta,
									Long.parseLong(arguments[4]), criterio, oculto, recursivo);
							imprimir(lista);
							}catch(IllegalArgumentException iae) {
								System.out.println("\nComando inválido, para más información escriba ls -help");
								System.out.printf("\n#");
							}

						} else {
							System.out.println("\nComando inválido, para más información escriba ls -help");
						}
					}

				} else {
					System.out.println("\nComando inválido, para más información escriba ls -help");

				}
			} else {
				if (arguments.length == 4 && arguments[0].equals("ls")) {
					criterio = args[1].charAt(0);
					if ((criterio + "").matches("[+-]")) {
						ruta = new File(args[2].trim());
						if (ruta.exists() && ruta.isDirectory()) {

						}
					} else {
						System.out.println("\nComando inválido, para más información escriba ls -help");
					}
				} else {
					if (arguments[0].equals("exit"))
						return;
					else {
						if (arguments.length == 2) {
							if (arguments[1].equals("-help"))
								System.out.println("Sintáxis: ls [-Rh] (+/-) rutaDelDirectorio tamañoEnBytes\n"
										+ "-R: Incluye las subcarpetas en la búsqueda\n"
										+ "-h: Incluye archivos ocultos en la búsqueda\n"
										+ "+: La búsqueda incluirá archivos MAYORES que el tamañoEnBytes\n"
										+ "-: La búsqueda incluirá archivos MENORES que el tamañoEnBytes\n");
						} else {
							System.out.println("\nComando inválido, para más información escriba ls -help");
						}
					}
				}
			}
		}
	}

	private static void imprimir(ArrayList<File> lista) {
		System.out.println("");
		for(File f: lista) {
			System.out.println(f.getAbsolutePath() + "(" + f.length() + " bytes)\n");
		}
		System.out.println("");
		
	}

}
