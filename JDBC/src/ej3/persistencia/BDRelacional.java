package ej3.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class BDRelacional {
	public abstract BDRelacional conectar(String idRuta, String usuario, String pass);
	
	public abstract ArrayList<String> listarBases() throws SQLException;
	
	public abstract ArrayList<String> listarTablas(String base) throws SQLException;
	
	public abstract ArrayList<Columna> listarColumnas(String base, String tabla) throws SQLException;
}
