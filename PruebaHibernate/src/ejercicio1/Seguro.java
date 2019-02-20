package ejercicio1;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seguro")
public class Seguro {
	
	@Id
	@Column(name="idSeguro")
	private int idSeguro;
	

	@Column(name="nif")
	private String nif;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="ape1")
	private String ape1;
	
	@Column(name="ape2")
	private String ape2;
	
	@Column(name="edad")
	private int edad;
	
	@Column(name="numHijos")
	private int numHijos;
	
	@Column(name="fechaCreacion")
	private Date fechaCreacion;

	public Seguro() {
		super();
	}
	
	
	public Seguro(int idSeguro, String nif, String nombre, String ape1, String ape2, int edad, int numHijos,
			Date fechaCreacion) {
		super();
		this.idSeguro = idSeguro;
		this.nif = nif;
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
		this.edad = edad;
		this.numHijos = numHijos;
		this.fechaCreacion = fechaCreacion;
	}

	public int getIdSeguro() {
		return idSeguro;
	}

	public void setIdSeguro(int idSeguro) {
		this.idSeguro = idSeguro;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApe1() {
		return ape1;
	}

	public void setApe1(String ape1) {
		this.ape1 = ape1;
	}

	public String getApe2() {
		return ape2;
	}

	public void setApe2(String ape2) {
		this.ape2 = ape2;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
}
