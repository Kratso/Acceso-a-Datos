package dao.abs;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import dao.Competicion;
import dao.Equipo;
import dao.Partido;

public interface PersistenciaPartido {
	Partido getPartido(int id) throws SQLException;
	
	List<Partido> getPartidosByEquipo(Equipo equipo) throws SQLException;
	
	List<Partido> getPartidosByCompeticion(Competicion competicion) throws SQLException;
	
	List<Partido> getPartidosBetweenFechas(Date fecha1, Date fecha2) throws SQLException;
	
	void insertOrUpdatePartido(Partido partido) throws SQLException;
	
	void borrarPartido(int id) throws SQLException;
	
}
