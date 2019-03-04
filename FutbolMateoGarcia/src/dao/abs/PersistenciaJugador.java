package dao.abs;

import java.sql.SQLException;
import java.util.Set;

import dao.Equipo;
import dao.Jugador;
import dao.Posicion;

public interface PersistenciaJugador {
	
	public Jugador getJugadorById(int id) throws SQLException;
	
	public Set<Jugador> getJugadoresEquipo(Equipo equipo) throws SQLException;
	
	public void insertOrUpdateJugador(Jugador jugador) throws SQLException;
	
	public void deleteJugador(int idJugador) throws SQLException;
	
	public Set<Jugador> getJugadoresByPosicion(Posicion posicion) throws SQLException;
}
