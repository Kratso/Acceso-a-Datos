package persistencia.abs;

import java.sql.SQLException;

import java.util.List;
import dao.Posicion;

public interface PersistenciaPosicion {
	Posicion getPosicionById(int id) throws SQLException;
	
	List<Posicion> getPosiciones() throws SQLException;
	
	void insertOrUpdatePosicion(Posicion posicion) throws SQLException;
	
	void deletePosicionById(int id) throws SQLException;
}
