package utilidades;

import java.awt.Component;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public final class Utilidades {

	private Utilidades() {
		throw new IllegalAccessError();
	}

	public static ArrayList<File> buscarArchivosPorTamanio(File carpetaDondeBuscar, long tamEnBytes, char criterio,
			boolean incluirOcultos, boolean incluirSubcarpetas) {
		if (carpetaDondeBuscar == null)
			throw new IllegalArgumentException("El primer argumento debe ser un objeto de la clase File");
		if (!carpetaDondeBuscar.isDirectory())
			throw new IllegalArgumentException("El objeto File no es una carpeta");
		boolean mayores = true;
		switch (criterio) {
		case '+':
			mayores = true;
			break;
		case '-':
			mayores = false;
			break;
		default:
			throw new IllegalArgumentException("El argumento criterio debe ser un caracter '+' o '-'");
		}

		ArrayList<File> al = new ArrayList<File>();

		File[] lista = carpetaDondeBuscar.listFiles();
		if (lista == null) // Error al listar carpeta: falta de permisos, etc
			return null;

		for (int i = 0; i < lista.length; i++) {
			boolean incluir = !lista[i].isHidden() || lista[i].isHidden() && incluirOcultos;// Ignoramos carpetas y
			// ficheros ocultos
			if (incluir) {
				if (lista[i].isFile()) {
					if (mayores && lista[i].length() >= tamEnBytes || !mayores && lista[i].length() <= tamEnBytes)
						al.add(lista[i]);
				} else {// Subcarpeta
					if (incluirSubcarpetas && lista[i].isDirectory()) {
						// Añadimos los ficheros que se encuentren en subcarpetas mediante recursividad
						ArrayList<File> al2 = buscarArchivosPorTamanio(lista[i], tamEnBytes, criterio, incluirOcultos,
								incluirSubcarpetas);
						if (al2 != null)
							al.addAll(al2);
					}
				}
			}
		}
		return al;
	}

	public static ArrayList<File> buscarArchivosPorFechaModif(File carpetaDondeBuscar, long fecha, boolean antesDE,
			boolean incluirOcultos, boolean incluirSubcarpetas) {
		if (!carpetaDondeBuscar.isDirectory())
			return null;
		Date date = new Date(fecha);
		ArrayList<File> resul = new ArrayList<>();
		for (File f : carpetaDondeBuscar.listFiles()) {
			if (f.isFile() && f.canRead()) {
				if ((f.isHidden() && incluirOcultos) || (!f.isHidden())) {
					if (antesDE && (date.compareTo(new Date(f.lastModified())) > 0))
						resul.add(f);
					else if (!antesDE && (date.compareTo(new Date(f.lastModified())) < 0))
						resul.add(f);

				}
			} else {
				if ((f.isDirectory() && incluirSubcarpetas) && ((f.isHidden() && incluirOcultos) || (!f.isHidden())))
					resul.addAll(buscarArchivosPorFechaModif(f, fecha, antesDE, incluirOcultos, incluirSubcarpetas));
			}
		}
		return resul;
	}

	public static ArrayList<File> buscarArchivosPorNombre(File carpetaDondeBuscar, String patronNombre,
			boolean incluirOcultos, boolean incluirSubcarpetas) {
		if (!carpetaDondeBuscar.isDirectory())
			return null;
		ArrayList<File> resul = new ArrayList<>();
		for (File f : carpetaDondeBuscar.listFiles()) {
			if (f.isFile()) {
				if ((f.isHidden() && incluirOcultos) || (!f.isHidden())) {
					if (f.getName().matches(patronNombre))
						resul.add(f);
				}
			} else {
				if ((f.isDirectory() && incluirSubcarpetas) && ((f.isHidden() && incluirOcultos) || (!f.isHidden())))
					resul.addAll(buscarArchivosPorNombre(f, patronNombre, incluirOcultos, incluirSubcarpetas));
			}
		}
		return resul;
	}

	public static void MostrarMensajeError(Component contexto, String titulo, Exception excepcion, String mensaje) {
		if (excepcion != null)
			JOptionPane.showMessageDialog(contexto, titulo,
					mensaje + "\n___________________________\n" + excepcion.getClass() + " " + excepcion.getMessage(),
					JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(contexto, titulo, mensaje, JOptionPane.ERROR_MESSAGE);

	}

	public static void copiarFichero(File in, File out) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(in));
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(out));
		Byte b;
		try {
			while (true) {
				b = dis.readByte();
				dos.writeByte(b);
			}
		} catch (EOFException eofe) {
			dos.close();
		} finally {
			dos.close();
		}
	}

}
