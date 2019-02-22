package persistencia.abs;

import java.sql.SQLException;
import java.util.List;

import dao.Estadistica;
import dao.EstadisticaId;
import dao.Partido;

public interface PersistenciaEstadistica {
	Estadistica getEstadisticaById(EstadisticaId id) throws SQLException;
	
	List<Estadistica> getEstadisticasByPartido(Partido partido) throws SQLException;
	
	void insertEstadistica(Estadistica estadistica) throws SQLException;
	
	void deleteEstadistica(EstadisticaId id) throws SQLException;
}
