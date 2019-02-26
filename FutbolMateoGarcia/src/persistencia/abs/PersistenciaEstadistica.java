package persistencia.abs;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import dao.Estadistica;
import dao.EstadisticaId;
import dao.Jugador;
import dao.Partido;

public interface PersistenciaEstadistica {
	Estadistica getEstadisticaById(EstadisticaId id) throws SQLException;
	
	List<Estadistica> getEstadisticasByPartido(Partido partido) throws SQLException;
	
	Set<Estadistica> getEstadisticasByJugador(Jugador jugador) throws SQLException;
	
	void insertOrUpdateEstadistica(Estadistica estadistica) throws SQLException;
	
	void deleteEstadistica(EstadisticaId id) throws SQLException;
}
