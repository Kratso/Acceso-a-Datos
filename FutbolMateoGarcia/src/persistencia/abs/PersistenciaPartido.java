package persistencia.abs;

import java.util.Date;
import java.util.List;

import dao.Competicion;
import dao.Equipo;
import dao.Partido;

public interface PersistenciaPartido {
	Partido getPartido(int id);
	
	List<Partido> getPartidosByEquipo(Equipo equipo);
	
	List<Partido> getPartidosByCompeticion(Competicion competicion);
	
	List<Partido> getPartidosBetweenFechas(Date fecha1, Date fecha2);
	
	void insertPartido(Partido partido);
	
	void borrarPartido(int id);
	
	List<Partido> getEnfrentamientos(Equipo equipo1, Equipo equipo2);

}
