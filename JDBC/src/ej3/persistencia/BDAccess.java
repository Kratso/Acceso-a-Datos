package ej3.persistencia;

import java.sql.SQLException;
import java.util.ArrayList;

public class BDAccess extends BDRelacional {
	
	public BDRelacional conectar(String ruta) {
		return conectar(ruta, null, null); 
	}

	@Override
	public BDRelacional conectar(String idRuta, String usuario, String pass) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ArrayList<String> listarBases() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> listarTablas(String base) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Columna> listarColumnas(String base, String tabla) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
