package persistencia.abs;

import java.util.List;

import dao.Competicion;

public interface PersistenciaCompeticion {
	Competicion getCompeticionById(int id);
	
	List<Competicion> getCompeticionesByNombre(String nombre);
	
	void insertOrUpdateCompeticion(Competicion competicion);
	
	List<Competicion> getCompeticiones();
	
	void deleteCompeticionById(int id);
}
