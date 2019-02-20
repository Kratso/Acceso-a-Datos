
package persistencia.implhibernate;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.Transaction;

import dao.*;

import persistencia.abs.PersistenciaGeneral;


@SuppressWarnings("all")
public class PersistenciaHibernate implements PersistenciaGeneral {
	
	Session session;
	
	public PersistenciaHibernate(String configureFile) {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure(configureFile);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
	}
	
	@Override
	public Equipo getEquipoById(final int id) throws SQLException {
		return (Equipo) session.get(Equipo.class, id);
	}
	
	@Override
	public List<Equipo> getEquiposByNombre(final String nombre)
			throws SQLException {
		final String hql = "from Equipo";
		final List<Equipo> equipos = session.createQuery(hql).list();
		List<Equipo> res = new ArrayList();
		equipos.forEach((l) -> {
			if (l.getNombre().equals(nombre)) res.add(l);
		});
		return res;
	}
	
	@Override
	public void insertOrUpdateEquipo(final Equipo equipo) throws SQLException {
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(equipo);
		t.commit();
	}
	
	@Override
	public void deleteEquipoById(final int id) throws SQLException {
		Transaction t = session.beginTransaction();
		session.delete(getEquipoById(id));
		t.commit();
	}
	
	@Override
	public List<Equipo> getListaEquipos() throws SQLException {
		final String hql = "from Equipo";
		final List<Equipo> equipos = session.createQuery(hql).list();
		return equipos;
	}
	
	@Override
	public Competicion getCompeticionById(int id) {
		return (Competicion) session.get(Competicion.class, id);
	}
	
	@Override
	public List<Competicion> getCompeticionesByNombre(String nombre) {
		final String hql = "from Competicion";
		final List<Competicion> competiciones = session.createQuery(hql).list();
		List<Competicion> res = new ArrayList();
		competiciones.forEach((l) -> {
			if (l.getNombre().equals(nombre)) res.add(l);
		});
		return res;
	}
	
	@Override
	public void insertOrUpdateCompeticion(Competicion competicion) {
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(competicion);
		t.commit();
	}
	
	@Override
	public List<Competicion> getCompeticiones() {
		final String hql = "from Competicion";
		final List<Competicion> res = session.createQuery(hql).list();
		return res;
	}
	
	@Override
	public void deleteCompeticionById(int id) {
		Transaction t = session.beginTransaction();
		session.delete(getCompeticionById(id));
		t.commit();
	}
	
	@Override
	public Posicion getPosicionById(int id) {
		return (Posicion) session.get(Posicion.class, id);
	}
	
	@Override
	public void insertOrUpdatePosicion(Posicion posicion) {
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(posicion);
		t.commit();
	}
	
	@Override
	public void deletePosicionById(int id) {
		Transaction t = session.beginTransaction();
		session.delete(getPosicionById(id));
		t.commit();
	}
	
	@Override
	public Estadistica getEstadisticaById(EstadisticaId id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Estadistica> getEstadisticasByPartido(Partido partido) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insertEstadistica(Estadistica estadistica) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteEstadistica(EstadisticaId id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Partido getPartido(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Partido> getPartidosByEquipo(Equipo equipo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Partido> getPartidosByCompeticion(Competicion competicion) {
		final String hql = "from Partido";
		List<Partido> lp = (session.createQuery(hql).list());
		return lp.stream().filter((p) -> {
			return p.getCompeticion().equals(competicion);
		}).collect(Collectors.toList());
		
	}
	
	@Override
	public List<Partido> getPartidosBetweenFechas(Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insertPartido(Partido partido) {
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(partido);
		t.commit();
		
	}
	
	@Override
	public void borrarPartido(int id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Partido> getEnfrentamientos(Equipo equipo1, Equipo equipo2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Jugador getJugadorById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Set<Jugador> getJugadoresEquipo(Equipo equipo) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void insertJugador(Jugador jugador) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deleteJugador(int idJugador) {
		// TODO Auto-generated method stub
		
	}
}
