/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import dao.*;
import util.Utilidades;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.hibernate.loader.custom.Return;

import javax.swing.GroupLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author krats
 */
public class CreacionGUI<T> extends javax.swing.JFrame {

	T t;

	MainGUI padre;

	public static Utilidades.Validator<Competicion> validarCompeticion = (c, b) -> {
		List<Competicion> competiciones = new ArrayList<>();
		try {
			competiciones = MainGUI.con.getCompeticiones().stream().filter((co) -> {
				return co.getNombre().equals(c.getNombre());
			}).collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (competiciones.size() == 0 || b) && c.getNombre().matches("([a-zA-Z\\.\\-]+[ ]?)+")
				&& c.getFechaComienzo().before(c.getFechaFin());
	};

	public static Utilidades.Validator<Equipo> validarEquipo = (e, b) -> {
		List<Equipo> lp = new ArrayList();
		try {
			lp = MainGUI.con.getEquiposByNombre(e.getNombre());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (e.getNombre().matches("([a-zA-Z\\.\\-]+[ ]?)+")) {
			if (b && lp.size() == 0) {
				return true;
			} else if (!b && lp.size() == 1) {
				return true;
			}
		}
		return false;
	};

	public static Utilidades.Validator<Jugador> validarJugador = (j, b) -> {
		Jugador jugador = new Jugador();
		try {
			 jugador = MainGUI.con.getJugadorById(j.getLicencia());
		} catch (SQLException e1) {
			MainGUI.notificaError(null, "Jugador Inválido", e1,
					"Los datos del jugador son inválidos");
		}
		List<Jugador> lj = new ArrayList<>();
		try {
			lj = new ArrayList<>(MainGUI.con.getJugadoresEquipo(j.getEquipo()));
		} catch (SQLException e) {
			MainGUI.notificaError(null, "Jugador Inválido", e,
					"Los datos del jugador son inválidos");
		}
		lj = lj.stream().filter((j1)->{
			return ! j1.equals(j);
		}).filter((j2)->{
			return j2.getDorsal() == j.getDorsal();
		}). collect(Collectors.toList());
		return (b && j == null) && lj.size() == 0 && j.getDorsal() > 0 && j.getNombre().matches("([a-zA-Z\\.\\-]+[ ]?)+");
	};

	public static Utilidades.Validator<Posicion> validarPosicion = (p, b) -> {
		
		return p.getId() > 0;
	};
	
	public static Utilidades.Validator<Partido> validarPartido = (p,b) ->{
		return Utilidades.entreFechas(p.getFechaHora(), p.getCompeticion().getFechaComienzo(), p.getCompeticion().getFechaFin()) &&
				!p.getEquipoByIdLocal().equals(p.getEquipoByIdVisitante());
	};

	/**
	 * Creates new form CreacionGUI
	 */
	public CreacionGUI() {
		initComponents();
	}

	public CreacionGUI(MainGUI padre, Class<? extends T> c) {
		initComponents();
		this.padre = padre;
		try {
			t = c.newInstance();
		} catch (IllegalAccessException ex) {

		} catch (InstantiationException ex) {

		}

		if (t instanceof Equipo) {
			posicionesCB.setVisible(false);
			posicionesL.setVisible(false);
			descL.setVisible(false);
			descTF.setVisible(false);
			dorsalL.setVisible(false);
			dorsalTF.setVisible(false);
			equiposL.setVisible(false);
			equiposJugadoresList.setVisible(false);
			fechaFinDC.setVisible(false);
			fechaFinL.setVisible(false);
			fechaIniL.setVisible(false);
			fechaInicioDC.setVisible(false);

		}
		if (t instanceof Posicion) {
			nombreL.setVisible(false);
			nombreTF.setVisible(false);
			posicionesCB.setVisible(false);
			posicionesL.setVisible(false);
			dorsalL.setVisible(false);
			dorsalTF.setVisible(false);
			equiposL.setVisible(false);
			equiposJugadoresList.setVisible(false);
			fechaFinDC.setVisible(false);
			fechaFinL.setVisible(false);
			fechaIniL.setVisible(false);
			fechaInicioDC.setVisible(false);
			guardarB.addActionListener((evt) -> {
				System.out.println("HOLA");
				if (validarCompeticion.validate((Competicion) t, false)) {
					try {
						MainGUI.con.insertOrUpdateCompeticion((Competicion) t);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MainGUI.notifica(this, "CREACIÓN CORRECTA", null, "Se ha creado el equipo correctamente",
							JOptionPane.INFORMATION_MESSAGE);
					padre.setEnabled(true);
					this.dispose();
				}
				MainGUI.notificaError(this, "Competicion Inválida", null,
						"Los datos de la competicion son inválidos");
			});
		}
		if (t instanceof Competicion) {
			descL.setVisible(false);
			descTF.setVisible(false);
			posicionesCB.setVisible(false);
			posicionesL.setVisible(false);
			dorsalL.setVisible(false);
			dorsalTF.setVisible(false);
			equiposJugadoresList.setVisible(false);
			guardarB.addActionListener((evt) -> {

			});
		}
		if (t instanceof Jugador) {
			descL.setVisible(false);
			descTF.setVisible(false);
			fechaFinDC.setVisible(false);
			fechaFinL.setVisible(false);
			fechaIniL.setVisible(false);
			fechaInicioDC.setVisible(false);
			guardarB.addActionListener((evt) -> {

			});
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		nombreTF = new javax.swing.JTextField();
		nombreL = new javax.swing.JLabel();
		descL = new javax.swing.JLabel();
		descTF = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		equiposJugadoresList = new javax.swing.JList<>();
		equiposL = new javax.swing.JLabel();
		fechaInicioDC = new com.toedter.calendar.JDateChooser();
		guardarB = new javax.swing.JButton();
		guardarB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		descartarB = new javax.swing.JButton();
		salirB = new javax.swing.JButton();
		fechaIniL = new javax.swing.JLabel();
		fechaFinDC = new com.toedter.calendar.JDateChooser();
		fechaFinL = new javax.swing.JLabel();
		posicionesCB = new javax.swing.JComboBox<>();
		posicionesL = new javax.swing.JLabel();
		dorsalL = new javax.swing.JLabel();
		dorsalTF = new javax.swing.JTextField();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		nombreL.setText("Nombre");

		descL.setText("Descripción");

		jScrollPane1.setViewportView(equiposJugadoresList);

		equiposL.setText("Equipos");

		guardarB.setText("Guardar");

		descartarB.setText("Descartar");
		descartarB.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				descartarBActionPerformed(evt);
			}
		});

		salirB.setText("Salir");
		salirB.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				salirBActionPerformed(evt);
			}
		});

		fechaIniL.setText("Fecha de Inicio");

		fechaFinL.setText("Fecha de Fin");

		posicionesL.setText("Posición");

		dorsalL.setText("Dorsal");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup().addGap(24)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addGroup(layout.createSequentialGroup().addComponent(fechaIniL)
														.addPreferredGap(ComponentPlacement.RELATED, 139,
																Short.MAX_VALUE)
														.addComponent(equiposL))
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(fechaFinL, GroupLayout.PREFERRED_SIZE, 64,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(fechaFinDC, GroupLayout.PREFERRED_SIZE,
																		126, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(posicionesL).addComponent(dorsalL))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
																.addComponent(posicionesCB, 0, GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(dorsalTF, GroupLayout.PREFERRED_SIZE, 85,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(0, 0, Short.MAX_VALUE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.TRAILING)
														.addComponent(descL).addComponent(nombreL))
												.addGap(42)
												.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(descTF).addComponent(nombreTF,
																GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)))
										.addComponent(fechaInicioDC, GroupLayout.PREFERRED_SIZE, 126,
												GroupLayout.PREFERRED_SIZE))
										.addGap(0, 36, Short.MAX_VALUE)))
						.addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(salirB)
						.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE).addComponent(guardarB)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(descartarB).addGap(76)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(53)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(nombreL).addComponent(nombreTF,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(descL).addComponent(descTF,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(layout
										.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(posicionesCB, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(posicionesL))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(
														layout.createParallelGroup(Alignment.LEADING).addComponent(
																dorsalL).addComponent(dorsalTF,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)))
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addComponent(fechaIniL)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(fechaInicioDC, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(14).addComponent(fechaFinL))
												.addComponent(equiposL)).addGap(9).addComponent(fechaFinDC,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))))
				.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(guardarB).addComponent(descartarB)
						.addComponent(salirB))
				.addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	protected void guardar() {
		System.out.println("En guardar");
		if (t instanceof Competicion) {
			System.out.println("HOLA");
			Competicion compe = (Competicion) t;
			compe.setNombre(nombreTF.getText());
			compe.setFechaComienzo(fechaInicioDC.getDate());
			compe.setFechaFin(fechaFinDC.getDate());
			if (validarCompeticion.validate(compe, false)) {

				try {
					MainGUI.con.insertOrUpdateCompeticion(compe);
				} catch (SQLException e1) {
					MainGUI.notificaError(this, "ERROR", e1, "Ha ocurrido un error \n" + e1.getMessage());

				}

				MainGUI.notifica(this, "CREACIÓN CORRECTA", null, "Se ha creado la competición correctamente",
						JOptionPane.INFORMATION_MESSAGE);
				try {
					padre.refrescarJCombo();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				padre.setEnabled(true);
				this.dispose();
			} else {
				MainGUI.notificaError(this, "Competicion Inválida", null,
						"Los datos de la competicion son inválidos");
			}
		}

	}

	private void salirBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_salirBActionPerformed
		padre.setVisible(true);
		// TODO azd
	}// GEN-LAST:event_salirBActionPerformed

	private void descartarBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_descartarBActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_descartarBActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf. html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(CreacionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CreacionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CreacionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CreacionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new CreacionGUI().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel descL;

	private javax.swing.JTextField descTF;

	private javax.swing.JButton descartarB;

	private javax.swing.JLabel dorsalL;

	private javax.swing.JTextField dorsalTF;

	private javax.swing.JList<String> equiposJugadoresList;

	private javax.swing.JLabel equiposL;

	private com.toedter.calendar.JDateChooser fechaFinDC;

	private javax.swing.JLabel fechaFinL;

	private javax.swing.JLabel fechaIniL;

	private com.toedter.calendar.JDateChooser fechaInicioDC;

	private javax.swing.JButton guardarB;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JLabel nombreL;

	private javax.swing.JTextField nombreTF;

	private javax.swing.JComboBox<String> posicionesCB;

	private javax.swing.JLabel posicionesL;

	private javax.swing.JButton salirB;
	// End of variables declaration//GEN-END:variables
}
