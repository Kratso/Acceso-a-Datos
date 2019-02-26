package persistencia.abs;

import java.sql.SQLException;
import java.util.Set;

import dao.Equipo;
import dao.Jugador;

public interface PersistenciaJugador {
	
	public Jugador getJugadorById(int id) throws SQLException;
	
	public Set<Jugador> getJugadoresEquipo(Equipo equipo) throws SQLException;
	
	public void insertJugador(Jugador jugador);
	
	public void deleteJugador(int idJugador);
	
	
	
}
