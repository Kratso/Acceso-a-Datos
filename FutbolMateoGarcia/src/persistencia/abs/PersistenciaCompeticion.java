package persistencia.abs;

import java.sql.SQLException;
import java.util.List;

import dao.Competicion;

public interface PersistenciaCompeticion {
	Competicion getCompeticionById(int id) throws SQLException;
	
	List<Competicion> getCompeticionesByNombre(String nombre) throws SQLException;
	
	void insertOrUpdateCompeticion(Competicion competicion) throws SQLException;
	
	List<Competicion> getCompeticiones() throws SQLException;
	
	void deleteCompeticionById(int id) throws SQLException;
}
