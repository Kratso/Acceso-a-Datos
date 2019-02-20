package persistencia.abs;

import dao.Posicion;

public interface PersistenciaPosicion {
	Posicion getPosicionById(int id);
	
	void insertOrUpdatePosicion(Posicion posicion);
	
	void deletePosicionById(int id);
}
