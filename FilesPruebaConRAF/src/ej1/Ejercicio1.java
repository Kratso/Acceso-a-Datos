package ej1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio1 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		File csv;
		String in;
		System.out.println("Ruta del fichero csv:");
		in = sc.nextLine();
		csv = new File(in);
		if (csv.exists()) {
			BufferedReader bfr = new BufferedReader(new FileReader(csv));
			String lin;
			System.out.println("Contenido del fichero");
			while ((lin = bfr.readLine()) != null) {
				String[] cont = lin.split("[;,]");
				for (int i = 0; i < cont.length; i++)
					System.out.print(cont[i] + "\t");
				System.out.println("");
			}
		} else {
			System.out.println("El archivo no existe");
		}

	}

}
