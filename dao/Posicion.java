package dao;
// Generated 31-ene-2019 8:52:33 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Posicion generated by hbm2java
 */
public class Posicion implements java.io.Serializable {

	private int id;
	private String descripcion;
	private Set jugadors = new HashSet(0);

	public Posicion() {
	}

	public Posicion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Posicion(String descripcion, Set jugadors) {
		this.descripcion = descripcion;
		this.jugadors = jugadors;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getJugadors() {
		return this.jugadors;
	}

	public void setJugadors(Set jugadors) {
		this.jugadors = jugadors;
	}

}