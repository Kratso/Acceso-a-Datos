package ej3.persistencia;

public class DBFactory {
	public static final int SQL = 0;
	public static final int DERBY = 1;
	public static final int ACCESS = 2;
	
	public static BDRelacional buildBD(int db) {
		switch (db) {
		case SQL:
			return new BDSQL();
		case DERBY:
			return new BDDerby();
		case ACCESS:
			return new BDAccess();
		default:
			throw new IllegalArgumentException("Base de Datos no soportada");
		}
	}
	
}
