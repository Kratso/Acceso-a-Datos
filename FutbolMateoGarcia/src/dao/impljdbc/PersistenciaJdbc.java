package dao.impljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Transaction;
import org.hibernate.sql.HSQLCaseFragment;

import dao.Competicion;
import dao.Equipo;
import dao.Estadistica;
import dao.EstadisticaId;
import dao.Jugador;
import dao.Partido;
import dao.Posicion;
import dao.abs.PersistenciaGeneral;
import dao.impljdbc.*;

public class PersistenciaJdbc implements PersistenciaGeneral {

	private Connection con;

	public PersistenciaJdbc(String connectionString, String usuario, String pass)
			throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		con = null;
		con = DriverManager.getConnection("jdbc:mysql://" + connectionString, usuario, pass);
	}

	@Override
	public Equipo getEquipoById(int id) throws SQLException {
		String sql = "SELECT * FROM equipo WHERE id = " + id;
		ResultSet rs = con.createStatement().executeQuery(sql);
		Equipo res = null;
		if (rs.next()) {
			res = new Equipo(rs.getInt(1), rs.getString(2));
			res.setJugadors(getJugadoresEquipo(res));
			final Equipo resf = res;
			res.setPartidosForIdLocal(getPartidosByEquipo(res).stream().filter((p) -> {
				return p.getEquipoByIdLocal().equals(resf);
			}).collect(Collectors.toSet()));
			res.setPartidosForIdVisitante(getPartidosByEquipo(res).stream().filter((p) -> {
				return p.getEquipoByIdVisitante().equals(resf);
			}).collect(Collectors.toSet()));
		}
		return res;
	}

	public Equipo getEquipoSimple(int id) throws SQLException {
		String sql = "SELECT * FROM equipo WHERE id = " + id;
		ResultSet rs = con.createStatement().executeQuery(sql);
		Equipo res = null;
		if (rs.next()) {
			res = new Equipo(rs.getInt(1), rs.getString(2));
		}
		return res;
	}

	@Override
	public List<Equipo> getEquiposByNombre(String nombre) throws SQLException {
		String sql = "SELECT * FROM equipo WHERE nombre = '" + nombre + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Equipo> res = new ArrayList<>();
		while (rs.next()) {
			Equipo e = new Equipo(rs.getInt(1), rs.getString(2));
			e.setJugadors(getJugadoresEquipo(e));
			e.setPartidosForIdLocal(getPartidosByEquipo(e).stream().filter((p) -> {
				return p.getEquipoByIdLocal().equals(e);
			}).collect(Collectors.toSet()));
			e.setPartidosForIdVisitante(getPartidosByEquipo(e).stream().filter((p) -> {
				return p.getEquipoByIdVisitante().equals(e);
			}).collect(Collectors.toSet()));
			res.add(e);
		}
		return res;
	}

	@Override
	@SuppressWarnings(value = "all")
	public void insertOrUpdateEquipo(Equipo equipo) throws SQLException {
		String select = "SELECT * FROM equipo WHERE id='" + equipo.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(select);
		if (rs.next()) {

		} else {
			String sql = "INSERT INTO equipo VALUES (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, equipo.getId());
			ps.setString(2, equipo.getNombre());
			ps.executeUpdate();
			equipo.getJugadors().stream().forEach((j) -> {
				try {
					insertOrUpdateJugador((Jugador) j);
				} catch (SQLException e) {

					e.printStackTrace();
				}
			});
			equipo.getPartidosForIdLocal().stream().forEach((p) -> {
				try {
					insertOrUpdatePartido((Partido) p);
				} catch (SQLException e) {

					e.printStackTrace();
				}
			});
		}
	}

	@Override
	public void deleteEquipoById(int id) throws SQLException {
		String sql = "DELETE FROM equipo WHERE id = '" + id + "'";
		con.createStatement().executeUpdate(sql);

	}

	@Override
	public List<Equipo> getListaEquipos() throws SQLException {
		String sql = "SELECT * FROM  equipo";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Equipo> le = new ArrayList<Equipo>();
		while (rs.next()) {
			Equipo e = new Equipo();
			e.setId(rs.getInt(1));
			e.setNombre(rs.getString(2));
			e.setJugadors(getJugadoresEquipo(e));
			e.setPartidosForIdLocal(getPartidosByEquipo(e).stream().filter((p) -> {
				return p.getEquipoByIdLocal().equals(e);
			}).collect(Collectors.toSet()));
			e.setPartidosForIdVisitante(getPartidosByEquipo(e).stream().filter((p) -> {
				return p.getEquipoByIdVisitante().equals(e);
			}).collect(Collectors.toSet()));
			le.add(e);
		}
		return le;
	}

	@Override
	public Competicion getCompeticionById(int id) throws SQLException {
		String sql = "SELECT * FROM competicion WHERE id = '" + id + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Competicion c = null;
		if (rs.next()) {
			c = new Competicion();
			c.setId(rs.getInt(1));
			c.setNombre(rs.getString(2));
			c.setFechaComienzo(rs.getDate(3));
			c.setFechaFin(rs.getDate(4));
			c.setPartidos(new HashSet<>(getPartidosByCompeticion(c)));
		}
		return c;
	}

	public Competicion getCompeticionSimple(int id) throws SQLException {
		String sql = "SELECT * FROM competicion WHERE id = '" + id + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Competicion c = null;
		if (rs.next()) {
			c = new Competicion();
			c.setId(rs.getInt(1));
			c.setNombre(rs.getString(2));
			c.setFechaComienzo(rs.getDate(3));
			c.setFechaFin(rs.getDate(4));
		}
		return c;
	}

	@Override
	public List<Competicion> getCompeticionesByNombre(String nombre) throws SQLException {
		String sql = "SELECT * FROM competicion WHERE nombre = '" + nombre + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Competicion> lc = new ArrayList<Competicion>();
		while (rs.next()) {
			Competicion c = new Competicion();
			c.setId(rs.getInt(1));
			c.setNombre(rs.getString(2));
			c.setFechaComienzo(rs.getDate(3));
			c.setFechaFin(rs.getDate(4));
			c.setPartidos(new HashSet<>(getPartidosByCompeticion(c)));
			lc.add(c);
		}
		return lc;
	}

	@Override
	public void insertOrUpdateCompeticion(Competicion competicion) throws SQLException {
		String select = "SELECT * FROM competicion WHERE id = '" + competicion.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(select);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (rs.next()) {
			String sql = "UPDATE competicion SET nombre = '" + competicion.getNombre() + "', fechaInicio = '"
					+ sdf.format(competicion.getFechaComienzo()) + "', fechaFin = '"
					+ sdf.format(competicion.getFechaFin()) + "'";
			con.createStatement().executeUpdate(sql);
		} else {
			String sql = "INSERT INTO competicion (nombre, fechaInicio, FechaFin) VALUES ('" + competicion.getNombre()
					+ "', '" + sdf.format(competicion.getFechaComienzo()) + "','"
					+ sdf.format(competicion.getFechaFin()) + "')";
			con.createStatement().executeUpdate(sql);
		}
	}

	@Override
	public List<Competicion> getCompeticiones() throws SQLException {
		String sql = "SELECT * FROM competicion";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Competicion> lc = new ArrayList<Competicion>();
		while (rs.next()) {
			Competicion c = new Competicion();
			c.setId(rs.getInt(1));
			c.setNombre(rs.getString(2));
			c.setFechaComienzo(rs.getDate(3));
			c.setFechaFin(rs.getDate(4));
			c.setPartidos(new HashSet<>(getPartidosByCompeticion(c)));
			lc.add(c);
		}
		return lc;
	}

	@Override
	public void deleteCompeticionById(int id) throws SQLException {

	}

	public Posicion getPosicionSimple(int id) throws SQLException {
		String sql = "SELECT * FROM posicion WHERE id = '" + id + "'";
		Posicion p = null;
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			p = new Posicion();
			p.setId(rs.getInt(1));
			p.setDescripcion(rs.getString(2));
		}

		return p;
	}

	@Override
	public Posicion getPosicionById(int id) throws SQLException {
		String sql = "SELECT * FROM posicion WHERE id = '" + id + "'";
		Posicion p = null;
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			p = new Posicion();
			p.setId(rs.getInt(1));
			p.setDescripcion(rs.getString(2));
			p.setJugadors(getJugadoresByPosicion(p));
		}

		return p;
	}

	@Override
	public void insertOrUpdatePosicion(Posicion posicion) throws SQLException {
		String consulta = "SELECT * FROM posicion WHERE id = '" + posicion.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(consulta);
		if (rs.next()) {
			String sql = "UPDATE posiciion SET descripcion = '" + posicion.getDescripcion() + "' WHERE id = '"
					+ posicion.getId() + "'";
			con.createStatement().executeUpdate(sql);
		} else {
			String sql = "INSERT INTO posicion (descripcion) VALUES ('" + posicion.getDescripcion() + "')";
			con.createStatement().executeUpdate(sql);
		}

	}

	@Override
	public void deletePosicionById(int id) {

	}

	@Override
	public Estadistica getEstadisticaById(EstadisticaId id) throws SQLException {
		String sql = "SELECT * FROM estadistica WHERE id='" + id + "'";
		Estadistica e = null;
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			e = new Estadistica();
			e.setId(new EstadisticaId(rs.getInt(1), rs.getInt(2)));
			e.setJugador(getJugadorSimple(rs.getInt(3)));
			e.setPartido(getPartidoSimple(rs.getInt(4)));
			e.setGoles(rs.getInt(5));
			e.setFaltas(rs.getInt(6));
			e.setTarjAmarillas(rs.getInt(7));
			e.setTarjRojas(rs.getInt(8));
		}

		return e;
	}

	@Override
	public List<Estadistica> getEstadisticasByPartido(Partido partido) throws SQLException {
		String sql = "SELECT * FROM estadistica WHERE idPartido ='" + partido.getId() + "'";
		List<Estadistica> le = new ArrayList<>();
		ResultSet rs = con.createStatement().executeQuery(sql);
		while (rs.next()) {
			Estadistica e = null;
			e = new Estadistica();
			e.setId(new EstadisticaId(rs.getInt(1), rs.getInt(2)));
			e.setJugador(getJugadorSimple(rs.getInt(3)));
			e.setPartido(getPartidoSimple(rs.getInt(4)));
			e.setGoles(rs.getInt(5));
			e.setFaltas(rs.getInt(6));
			e.setTarjAmarillas(rs.getInt(7));
			e.setTarjRojas(rs.getInt(8));
			le.add(e);
		}

		return le;
	}

	@Override
	public void insertOrUpdateEstadistica(Estadistica estadistica) throws SQLException {
		Estadistica e = getEstadisticaById(estadistica.getId());
		if (e == null) {
			String sql = "INSERT INTO estadistica (idPartido, licencia, goles, faltas, tarjAmarillas, tarjRojas) VALUES ('"
					+ estadistica.getId().getIdPartido() + "', '" + estadistica.getId().getLicencia() + "', '"
					+ estadistica.getGoles() + "', '" + estadistica.getFaltas() + "', '"
					+ estadistica.getTarjAmarillas() + "', '" + estadistica.getTarjRojas() + "')";
			con.createStatement().executeUpdate(sql);
		} else {
			String sql = "UPDATE estadistica SET idPartido = '" + estadistica.getId().getIdPartido() + "', licencia = '"
					+ estadistica.getId().getLicencia() + "', goles = '" + estadistica.getGoles() + "', faltas = '"
					+ estadistica.getFaltas() + "', tarjAmarillas = '" + estadistica.getTarjAmarillas()
					+ "', tarjRojas = '" + estadistica.getTarjRojas() + "' WHERE idPartido = '"
					+ estadistica.getId().getIdPartido() + "' AND licencia = '" + estadistica.getId().getLicencia()
					+ "'";
			con.createStatement().executeUpdate(sql);
		}

	}

	@Override
	public void deleteEstadistica(EstadisticaId id) throws SQLException {

	}

	public Jugador getJugadorSimple(int id) throws SQLException {
		String sql = "SELECT * FROM jugador WHERE licencia = '" + id + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Jugador j = new Jugador();
		if (rs.next()) {
			j.setLicencia(rs.getInt(1));
			j.setEquipo(getEquipoSimple(rs.getInt(2)));
			j.setPosicion(getPosicionById(rs.getInt(3)));
			j.setNombre(rs.getString(4));
			j.setDorsal(rs.getInt(5));

		}
		return j;
	}

	@Override
	public Jugador getJugadorById(int id) throws SQLException {
		String sql = "SELECT * FROM jugador WHERE licencia = '" + id + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Jugador j = new Jugador();
		if (rs.next()) {
			j.setLicencia(rs.getInt(1));
			j.setEquipo(getEquipoSimple(rs.getInt(2)));
			j.setPosicion(getPosicionById(rs.getInt(3)));
			j.setNombre(rs.getString(4));
			j.setDorsal(rs.getInt(5));
			j.setEstadisticas(getEstadisticasByJugador(j));
		}
		return j;
	}

	@Override
	public Set<Jugador> getJugadoresEquipo(Equipo equipo) throws SQLException {
		String sql = "SELECT * FROM jugador WHERE idEquipo = '" + equipo.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Set<Jugador> sj = new HashSet<Jugador>();
		while (rs.next()) {
			Jugador j = new Jugador();
			j.setLicencia(rs.getInt(1));
			j.setEquipo(getEquipoSimple(rs.getInt(2)));
			j.setPosicion(getPosicionById(rs.getInt(3)));
			j.setNombre(rs.getString(4));
			j.setDorsal(rs.getInt(5));
			j.setEstadisticas(getEstadisticasByJugador(j));
			sj.add(j);
		}
		return sj;
	}

	@Override
	public void insertOrUpdateJugador(Jugador jugador) throws SQLException {
		String sql = "SELECT * FROM jugador WHERE licencia = '" + jugador.getLicencia() + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			sql = "UPDATE jugador SET nombre = '" + jugador.getNombre() + "', dorsal = " + jugador.getDorsal()
					+ ", idEquipo = " + jugador.getEquipo().getId() + ", id posicion = " + jugador.getPosicion().getId()
					+ " WHERE licencia = " + jugador.getLicencia();

		} else {
			int time = (int) (System.currentTimeMillis() % 10000000);
			sql = "INSERT INTO jugador VALUES (" + time + ", " + jugador.getEquipo().getId() + ", "
					+ jugador.getPosicion().getId() + ", '" + jugador.getNombre() + "', " + jugador.getDorsal() + ")";
			con.createStatement().executeUpdate(sql);
		}

	}

	@Override
	public void deleteJugador(int idJugador) throws SQLException {

	}

	public Partido getPartidoSimple(int id) throws SQLException {
		Partido p = null;
		String sql = "SELECT * from partido WHERE id = '" + id + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			p = new Partido();
			p.setId(id);
			p.setEquipoByIdLocal(getEquipoSimple(rs.getInt(2)));
			p.setEquipoByIdVisitante(getEquipoSimple(rs.getInt(3)));
			p.setCompeticion(getCompeticionSimple(rs.getInt(4)));
			p.setFechaHora(rs.getDate(5));
			p.setGolesLocal(rs.getInt(6));
			p.setGolesVisitante(rs.getInt(7));
		}
		return p;
	}

	@Override
	public Partido getPartido(int id) throws SQLException {
		Partido p = null;
		String sql = "SELECT * from partido WHERE id = '" + id + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		if (rs.next()) {
			p = new Partido();
			p.setId(id);
			p.setEquipoByIdLocal(getEquipoSimple(rs.getInt(2)));
			p.setEquipoByIdVisitante(getEquipoSimple(rs.getInt(3)));
			p.setCompeticion(getCompeticionSimple(rs.getInt(4)));
			p.setFechaHora(rs.getDate(5));
			p.setGolesLocal(rs.getInt(6));
			p.setGolesVisitante(rs.getInt(7));
			p.setEstadisticas(new HashSet<>(getEstadisticasByPartido(p)));
		}
		return p;
	}

	@Override
	public List<Partido> getPartidosByEquipo(Equipo equipo) throws SQLException {
		Partido p = null;
		String sql = "SELECT * from partido WHERE idEquipoLocal = '" + equipo.getId() + "' OR idEquipoVisitante = '"
				+ equipo.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Partido> lp = new ArrayList<>();
		if (rs.next()) {
			p = new Partido();
			p.setId(rs.getInt(1));
			p.setEquipoByIdLocal(getEquipoSimple(rs.getInt(2)));
			p.setEquipoByIdVisitante(getEquipoSimple(rs.getInt(3)));
			p.setCompeticion(getCompeticionSimple(rs.getInt(4)));
			p.setFechaHora(rs.getDate(5));
			p.setGolesLocal(rs.getInt(6));
			p.setGolesVisitante(rs.getInt(7));
			p.setEstadisticas(new HashSet<>(getEstadisticasByPartido(p)));
			lp.add(p);
		}
		return lp;
	}

	@Override
	public List<Partido> getPartidosByCompeticion(Competicion competicion) throws SQLException {
		Partido p = null;
		String sql = "SELECT * from partido WHERE idCompeticion = '" + competicion.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Partido> lp = new ArrayList<>();
		if (rs.next()) {
			p = new Partido();
			p.setId(rs.getInt(1));
			p.setEquipoByIdLocal(getEquipoSimple(rs.getInt(2)));
			p.setEquipoByIdVisitante(getEquipoSimple(rs.getInt(3)));
			p.setCompeticion(getCompeticionSimple(rs.getInt(4)));
			p.setFechaHora(rs.getDate(5));
			p.setGolesLocal(rs.getInt(6));
			p.setGolesVisitante(rs.getInt(7));
			p.setEstadisticas(new HashSet<>(getEstadisticasByPartido(p)));
			lp.add(p);
		}
		return lp;
	}

	@Override
	public List<Partido> getPartidosBetweenFechas(Date fecha1, Date fecha2) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertOrUpdatePartido(Partido partido) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarPartido(int id) throws SQLException {
		String sql = "DELETE FROM partido WHERE id='" + id + "'";
		con.createStatement().executeUpdate(sql);
	}

	@Override
	public List<Posicion> getPosiciones() throws SQLException {
		String sql = "SELECT * FROM posicion";
		List<Posicion> lp = new ArrayList<>();
		ResultSet rs = con.createStatement().executeQuery(sql);
		while (rs.next()) {
			Posicion p = new Posicion();
			p.setId(rs.getInt(1));
			p.setDescripcion(rs.getString(2));
			p.setJugadors(getJugadoresByPosicion(p));
			lp.add(p);
		}
		return lp;
	}

	@Override
	public Set<Jugador> getJugadoresByPosicion(Posicion posicion) throws SQLException {
		String sql = "SELECT * FROM jugador WHERE idPosicion = '" + posicion.getId() + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Set<Jugador> sj = new HashSet<Jugador>();
		while (rs.next()) {
			Jugador j = new Jugador();
			j.setLicencia(rs.getInt(1));
			j.setEquipo(getEquipoSimple(rs.getInt(2)));
			j.setNombre(rs.getString(3));
			j.setDorsal(rs.getInt(4));
			j.setEstadisticas(getEstadisticasByJugador(j));
			sj.add(j);
		}
		return sj;
	}

	@Override
	public Set<Estadistica> getEstadisticasByJugador(Jugador jugador) throws SQLException {
		String sql = "SELECT * FROM estadistica";
		ResultSet rs = con.createStatement().executeQuery(sql);
		Set<Estadistica> se = new HashSet<Estadistica>();
		while (rs.next()) {
			Estadistica es = new Estadistica();
			es.setId(new EstadisticaId(rs.getInt(1), rs.getInt(2)));
			es.setJugador(new Jugador());
			es.getJugador().setLicencia(rs.getInt(3));
			es.setFaltas(rs.getInt(4));
			es.setTarjAmarillas(rs.getInt(5));
			es.setTarjRojas(rs.getInt(6));
			se.add(es);
		}
		se = se.stream().filter((j) -> {
			return j.getJugador().equals(jugador);
		}).collect(Collectors.toSet());
		return se;
	}
}