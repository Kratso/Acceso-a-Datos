package persistencia.abs;

import java.util.List;

import dao.Estadistica;
import dao.EstadisticaId;
import dao.Partido;

public interface PersistenciaEstadistica {
	Estadistica getEstadisticaById(EstadisticaId id);
	
	List<Estadistica> getEstadisticasByPartido(Partido partido);
	
	void insertEstadistica(Estadistica estadistica);
	
	void deleteEstadistica(EstadisticaId id);
}
