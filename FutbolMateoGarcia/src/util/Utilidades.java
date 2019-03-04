
package util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import dao.Competicion;
import dao.Equipo;
import dao.Partido;

import java.util.HashSet;


public final class Utilidades {
	
	public static int DIA = 24;
	
	public static int MEDIO = 12;
	
	@FunctionalInterface
	public static interface Validator<T>{
		public boolean validate(T t, boolean actualizar);
	}

	
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
		Collections.shuffle(lp);
		List<Partido> res = lp.stream().filter((p)->{return entreFechas(p.getFechaHora(), comp.getFechaComienzo(), comp.getFechaFin());}).collect(Collectors.toList());
		return res;
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
		res.put(p.getEquipoByIdLocal(), 0);
		res.put(p.getEquipoByIdVisitante(), 0);
		});
		partidos.stream().forEach((p) -> {
			Integer punLocal = res.get(p.getEquipoByIdLocal());
			Integer punVisitante = res.get(p.getEquipoByIdVisitante());
			if(p.getGolesLocal() - p.getGolesVisitante() > 0) {
				punLocal += 3;
			}else if(p.getGolesLocal() - p.getGolesVisitante() < 0) {
				punVisitante += 3;
			} else {
				punLocal += 1;
				punVisitante += 1;
			}
			res.put(p.getEquipoByIdLocal(), punLocal);
			res.put(p.getEquipoByIdVisitante(), punVisitante);
		});
		return res;
	}
}


