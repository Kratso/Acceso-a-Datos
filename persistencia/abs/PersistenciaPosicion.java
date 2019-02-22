package persistencia.abs;

import java.sql.SQLException;

import dao.Posicion;

public interface PersistenciaPosicion {
	Posicion getPosicionById(int id) throws SQLException;
	
	void insertOrUpdatePosicion(Posicion posicion) throws SQLException;
	
	void deletePosicionById(int id) throws SQLException;
}
