package ej1;

public class Premio {
	int numero;
	int tipo;
	String descripcion;
	int cantidad;
	int bolasExtraidas;
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getBolasExtraidas() {
		return bolasExtraidas;
	}
	public void setBolasExtraidas(int bolasExtraidas) {
		this.bolasExtraidas = bolasExtraidas;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return "Premio [tipo=" + tipo + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", bolasExtraidas="
				+ bolasExtraidas + "]";
	}
}