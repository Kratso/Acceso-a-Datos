package persistencia.abs;

import java.sql.SQLException;
import java.util.List;

import dao.Equipo;

public interface PersistenciaEquipo {
	Equipo getEquipoById(int id) throws SQLException;
	
	List<Equipo> getEquiposByNombre(String nombre) throws SQLException;
	
	void insertOrUpdateEquipo(Equipo equipo) throws SQLException;
	
	void deleteEquipoById(int id) throws SQLException;
	
	List<Equipo> getListaEquipos() throws SQLException;
}
