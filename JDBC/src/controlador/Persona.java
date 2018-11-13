package controlador;

public class Persona {
	String nombre;
	String CP;
	String pais;
	String email;
	
	
	public Persona(String nombre, String cP, String pais, String email) {
		super();
		this.nombre = nombre;
		CP = cP;
		this.pais = pais;
		this.email = email;
	}



	public String toString() {
		return nombre + "\t" + CP + "\t" + pais + "\t" + email;
	}
}