
package util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashSet;

import dao.Competicion;
import dao.Equipo;
import dao.Partido;


public final class Utilidades {
	
	public static int DIA = 24;
	
	public static int MEDIO = 12;
	
	private Utilidades() {
		throw new IllegalArgumentException();
	}
	
	public static boolean entreFechas(Date aComparar, Date inicio, Date fin) {
		return aComparar.compareTo(inicio) >= 0 && aComparar.compareTo(
				fin) <= 0;
	}
	
	public static List<Partido> generarCompeticion(List<Equipo> le,
			int horasEntrePartidos, Competicion comp) {
		List<Partido> lp = new ArrayList<>();
		le.stream().forEach((e) -> {
			le.stream().filter((ea) -> {
				return !ea.equals(e);
			}).forEach((ea) -> {
				Partido aux = new Partido();
				aux.setCompeticion(comp);
				aux.setEquipoByIdLocal(e);
				aux.setEquipoByIdVisitante(ea);
				Calendar c = new GregorianCalendar();
				c.setTimeInMillis(comp.getFechaComienzo().getTime());
				c.add(Calendar.HOUR_OF_DAY, horasEntrePartidos * lp.size());
				aux.setFechaHora(c.getTime());
				lp.add(aux);
			});
		});
		return lp;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Equipo, Integer> generarTablaCompeticion(
			Competicion comp) {
		Map<Equipo, Integer> res = new HashMap<>();
		Set<Partido> partidos = comp.getPartidos();
		System.out.println(comp);
		System.out.println(partidos.size());
		partidos.stream().forEach(p -> System.out.println(p));
		partidos.stream().forEach((p) -> {
			System.out.println(p);
			System.out.println(p.getId());
			boolean jugado = p.getEstadisticas().size() != 0;
			boolean isLocalGanador = p.getGolesLocal() > p.getGolesVisitante();
			boolean isEmpate = p.getGolesLocal() == p.getGolesVisitante();
			res.put(p.getEquipoByIdLocal(), jugado ? isLocalGanador ? res.get(p
					.getEquipoByIdLocal()) == null ? 3
							: res.get(p.getEquipoByIdLocal()) + 3
					: isEmpate ? res.get(p.getEquipoByIdLocal()) == null ? 1
							: res.get(p.getEquipoByIdLocal()) + 1
							: res.get(p.getEquipoByIdLocal()) == null ? 0
									: res.get(p.getEquipoByIdLocal()) + 0
					: res.get(p.getEquipoByIdLocal()) == null ? 0
							: res.get(p.getEquipoByIdLocal()));
			res.put(p.getEquipoByIdVisitante(), jugado ? isLocalGanador ? res
					.get(p.getEquipoByIdVisitante()) == null ? 0
							: res.get(p.getEquipoByIdVisitante()) + 0
					: isEmpate ? res.get(p.getEquipoByIdVisitante()) == null ? 1
							: res.get(p.getEquipoByIdVisitante()) + 1
							: res.get(p.getEquipoByIdVisitante()) == null ? 3
									: res.get(p.getEquipoByIdVisitante()) + 3
					: res.get(p.getEquipoByIdVisitante()) == null ? 0
							: res.get(p.getEquipoByIdVisitante()));
		});
		List<Entry<Equipo, Integer>> a = new ArrayList<>(res.entrySet());
		a.sort((e1, e2) -> {
			Set<Partido> ep1 = new HashSet<>(e1.getKey()
					.getPartidosForIdLocal());
			ep1.addAll(e1.getKey().getPartidosForIdVisitante());
			Set<Partido> ep2 = new HashSet<>(e2.getKey()
					.getPartidosForIdLocal());
			ep2.addAll(e2.getKey().getPartidosForIdVisitante());
			int gol1 = 0;
			gol1 += ep1.stream().mapToInt((es) -> {
				return es.getEquipoByIdLocal().getId() == e1.getKey().getId()
						? es.getGolesLocal()
						: es.getGolesVisitante();
			}).reduce(0, (c, b) -> {
				return c + b;
			});
			return e1.getValue() - e2.getValue() == 0 ? 0
					: e1.getValue() - e2.getValue();
		});
		return res;
	}
}

@FunctionalInterface
interface Validator{
	public boolean validate(Serializable o);
}
