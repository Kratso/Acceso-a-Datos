package xstreamtester;

import java.util.ArrayList;

public class Persona {
	int edad; // >0
	String nombre;
	ArrayList<Trabajo> trabs; // Trabajos en su vida laboral (0..n)
	char sexo;

	public Persona(int edad, String nombre, char sexo) {
		this.edad = edad;
		this.nombre = nombre;
		this.sexo = sexo;
		this.trabs = new ArrayList<Trabajo>();
	}

	public String toString() {
		return "Persona [edad=" + edad + ", nombre=" + nombre + ", trabs=" + trabs + ", sexo=" + sexo + "]";
	}

	public void añadirTrabajo(Trabajo t) {
		trabs.add(t);
	}
}