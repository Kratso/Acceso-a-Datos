package ej1.controlador;

public class Persona {
	private String nombre;
	private String CP;
	private String pais;
	private String email;
	
	
	public Persona(String nombre, String cP, String pais, String email) {
		super();
		this.nombre = nombre;
		CP = cP;
		this.pais = pais;
		this.email = email;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getCP() {
		return CP;
	}



	public void setCP(String cP) {
		CP = cP;
	}



	public String getPais() {
		return pais;
	}



	public void setPais(String pais) {
		this.pais = pais;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String toString() {
		return nombre + "\t" + CP + "\t" + pais + "\t" + email;
	}
}