
package gui;


import persistencia.abs.*;
import persistencia.implhibernate.*;

import util.Utilidades;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dao.*;


public class PruebaCompe {
	
	static PersistenciaGeneral con = new PersistenciaHibernate(
			"hibernate.cfg.xml");
	
	public static void main(String[] args) throws SQLException {
		List<Equipo> equipos = con.getListaEquipos().stream().filter(p -> p
				.getId() < 10).collect(Collectors.toList());
		Competicion compe = con.getCompeticionById(2);
		/*
		System.out.println(compe);
		long t = System.currentTimeMillis();
		List<Partido> partidos = Utilidades.generarCompeticion(equipos, Utilidades.MEDIO,
				compe);
		
		partidos.stream().forEach((p) -> {
			final String pa = p.getEquipoByIdLocal().getNombre() + " - " + p
					.getEquipoByIdVisitante().getNombre() + " - "
					+ new SimpleDateFormat("dd/MM/yyyy").format(p
							.getFechaHora());
			Cosas c = (s -> System.out.println(s));
			c.cosas(pa);
		});
		System.out.println(System.currentTimeMillis() - t);
		*/
		Utilidades.generarTablaCompeticion(compe).entrySet().stream().forEach(e->{
			System.out.println(e.getKey().getNombre() + " - " + e.getValue());
		});
		
	}
	
}

interface Cosas {
	
	void cosas(String p);
}
