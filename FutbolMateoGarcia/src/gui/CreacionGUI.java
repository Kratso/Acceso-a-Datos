/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

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

import dao.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

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
		Jugador jugador = null;
		if (b) {
			try {
				jugador = MainGUI.con.getJugadorById(j.getLicencia());
			} catch (SQLException e1) {
				MainGUI.notificaError(null, "Jugador Inválido", e1, "Los datos del jugador son inválidos");
			}
		}
		List<Jugador> lj = new ArrayList<>();
		try {
			lj = new ArrayList<>(MainGUI.con.getJugadoresEquipo(j.getEquipo()));
		} catch (SQLException e) {
			MainGUI.notificaError(null, "Jugador Inválido", e, "Los datos del jugador son inválidos");
		}
		lj = lj.stream().filter((j1) -> {
			return !j1.equals(j);
		}).filter((j2) -> {
			return j2.getDorsal() == j.getDorsal();
		}).collect(Collectors.toList());
		return (b && jugador != null) && lj.size() == 0 && j.getDorsal() > 0
				&& j.getNombre().matches("([a-zA-Z\\.\\-]+[ ]?)+");
	};

	public static Utilidades.Validator<Posicion> validarPosicion = (p, b) -> {

		return (p.getId() > 0) || b;
	};

	public static Utilidades.Validator<Partido> validarPartido = (p, b) -> {
		return Utilidades.entreFechas(p.getFechaHora(), p.getCompeticion().getFechaComienzo(),
				p.getCompeticion().getFechaFin()) && !p.getEquipoByIdLocal().equals(p.getEquipoByIdVisitante());
	};

	/**
	 * Creates new form CreacionGUI
	 */
	public CreacionGUI() {
		initComponents();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	public CreacionGUI(MainGUI padre, Class<? extends T> c) throws SQLException {
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
			lblCompeticin.setVisible(false);
			lblLocal.setVisible(false);
			lblVisitante.setVisible(false);
			comboBox.setVisible(false);
			comboBox_1.setVisible(false);
			comboBox_2.setVisible(false);

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
			lblCompeticin.setVisible(false);
			lblLocal.setVisible(false);
			lblVisitante.setVisible(false);
			comboBox.setVisible(false);
			comboBox_1.setVisible(false);
			comboBox_2.setVisible(false);
			lblCompeticin.setVisible(false);
			lblLocal.setVisible(false);
			lblVisitante.setVisible(false);
			comboBox.setVisible(false);
			comboBox_1.setVisible(false);
			comboBox_2.setVisible(false);
		}
		if (t instanceof Competicion) {
			descL.setVisible(false);
			descTF.setVisible(false);
			posicionesCB.setVisible(false);
			posicionesL.setVisible(false);
			dorsalL.setVisible(false);
			dorsalTF.setVisible(false);
			equiposJugadoresList.setVisible(false);
			lblCompeticin.setVisible(false);
			lblLocal.setVisible(false);
			lblVisitante.setVisible(false);
			comboBox.setVisible(false);
			comboBox_1.setVisible(false);
			comboBox_2.setVisible(false);
		}
		if (t instanceof Jugador) {
			descL.setVisible(false);
			descTF.setVisible(false);
			fechaFinDC.setVisible(false);
			fechaFinL.setVisible(false);
			fechaIniL.setVisible(false);
			fechaInicioDC.setVisible(false);
			lblCompeticin.setVisible(false);
			lblLocal.setVisible(false);
			lblVisitante.setVisible(false);
			comboBox.setVisible(false);
			comboBox_1.setVisible(false);
			comboBox_2.setVisible(false);
			posicionesCB.setModel(new DefaultComboBoxModel() {

				boolean selectionAllowed = true;

				@Override
				public void setSelectedItem(Object anObject) {
					if (!anObject.equals(MainGUI.posicionNoSeleccionable)) {
						super.setSelectedItem(anObject);
					} else if (selectionAllowed) {
						selectionAllowed = false;
						super.setSelectedItem(anObject);
					}
				}
			});
			List<Posicion> lp = MainGUI.con.getPosiciones();
			lp.forEach((p)->{
				posicionesCB.addItem(p);
			});
			List<Equipo> le = MainGUI.con.getListaEquipos();
			DefaultListModel model = new DefaultListModel();
			le.forEach((e)->{
				model.addElement(e);
			});
			equiposJugadoresList.setModel(model);
		}
		if (t instanceof Partido) {
			descL.setVisible(false);
			descTF.setVisible(false);
			fechaFinDC.setVisible(false);
			fechaFinL.setVisible(false);
			posicionesCB.setVisible(false);
			posicionesL.setVisible(false);
			dorsalL.setVisible(false);
			dorsalTF.setVisible(false);
			equiposJugadoresList.setVisible(false);
			nombreL.setVisible(false);
			nombreTF.setVisible(false);
			posicionesCB.setVisible(false);
			posicionesL.setVisible(false);
			dorsalL.setVisible(false);
			dorsalTF.setVisible(false);
			equiposL.setVisible(false);
			equiposJugadoresList.setVisible(false);
			comboBox_1.setModel(new DefaultComboBoxModel<Equipo>() {

				boolean selectionAllowed = true;

				@Override
				public void setSelectedItem(Object anObject) {
					if (!anObject.equals(MainGUI.equipoNoSeleccionable)) {
						super.setSelectedItem(anObject);
					} else if (selectionAllowed) {
						selectionAllowed = false;
						super.setSelectedItem(anObject);
					}
				}
			});
			comboBox_2.setModel(new DefaultComboBoxModel<Equipo>() {

				boolean selectionAllowed = true;

				@Override
				public void setSelectedItem(Object anObject) {
					if (!anObject.equals(MainGUI.equipoNoSeleccionable)) {
						super.setSelectedItem(anObject);
					} else if (selectionAllowed) {
						selectionAllowed = false;
						super.setSelectedItem(anObject);
					}
				}
			});
			comboBox.setModel(new DefaultComboBoxModel<Competicion>() {

				boolean selectionAllowed = true;

				@Override

				public void setSelectedItem(Object anObject) {
					if (!anObject.equals(MainGUI.competicionNoSeleccionable)) {
						super.setSelectedItem(anObject);
					} else if (selectionAllowed) {
						selectionAllowed = false;
						super.setSelectedItem(anObject);
					}
				}
			});
			List<Equipo> le = MainGUI.con.getListaEquipos();
			le.forEach((e)->{
				comboBox_1.addItem(e);
				comboBox_2.addItem(e);
			});
			comboBox.addItem(MainGUI.competicionNoSeleccionable);
			try {
				MainGUI.con.getCompeticiones().stream().forEach((comp) -> {
					comboBox.addItem(comp);
				});
			} catch (Exception e) {
				MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		nombreTF = new javax.swing.JTextField();
		nombreTF.setColumns(50);
		nombreL = new javax.swing.JLabel();
		descL = new javax.swing.JLabel();
		descTF = new javax.swing.JTextField();
		descTF.setColumns(50);
		jScrollPane1 = new javax.swing.JScrollPane();
		equiposJugadoresList = new javax.swing.JList<>();
		equiposJugadoresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

		comboBox = new JComboBox();

		lblCompeticin = new JLabel("Competici\u00F3n");

		comboBox_1 = new JComboBox();

		lblLocal = new JLabel("Local");

		comboBox_2 = new JComboBox();

		lblVisitante = new JLabel("Visitante");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(24)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addGroup(layout.createSequentialGroup().addComponent(fechaIniL)
														.addPreferredGap(ComponentPlacement.RELATED, 189,
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
														.addGap(0, 30, Short.MAX_VALUE)))
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
										.addGap(0, 49, Short.MAX_VALUE)))
						.addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(salirB)
						.addPreferredGap(ComponentPlacement.RELATED, 118, Short.MAX_VALUE).addComponent(guardarB)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(descartarB).addGap(76))
				.addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(35)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addGap(55)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(lblCompeticin)
								.addGap(108).addComponent(lblLocal)))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(lblVisitante)
								.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(lblCompeticin)
								.addComponent(lblLocal).addComponent(lblVisitante))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(nombreL).addComponent(
								nombreTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(descL).addComponent(
								descTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(layout
										.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
												.addGroup(layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(posicionesCB, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(posicionesL))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(dorsalL).addComponent(
																dorsalTF, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(guardarB)
								.addComponent(descartarB).addComponent(salirB))
						.addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	protected void guardar() {
		System.out.println("En guardar");
		if (t instanceof Competicion) {
			System.out.println("HOLA");
			if (nombreTF.getText().equals("")) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (fechaFinDC.getDate() == null) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (fechaInicioDC.getDate() == null) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
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

					MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());
				}
				padre.setEnabled(true);
				this.dispose();
			} else {
				MainGUI.notificaError(this, "Competicion Inválida", null,
						"Los datos de la competicion son inválidos");
			}
		}
		if (t instanceof Partido) {
			Partido partido = (Partido) t;
			if (((Competicion) comboBox.getSelectedItem()).equals(MainGUI.competicionNoSeleccionable)) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (((Equipo) comboBox_1.getSelectedItem()).equals(MainGUI.equipoNoSeleccionable)) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (((Equipo) comboBox_2.getSelectedItem()).equals(MainGUI.equipoNoSeleccionable)) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			partido.setCompeticion((Competicion) comboBox.getSelectedItem());
			partido.setEquipoByIdLocal((Equipo) comboBox_1.getSelectedItem());
			partido.setEquipoByIdVisitante((Equipo) comboBox_2.getSelectedItem());

			if (validarPartido.validate(partido, false)) {

				try {
					MainGUI.con.insertOrUpdatePartido(partido);
				} catch (SQLException e1) {
					MainGUI.notificaError(this, "ERROR", e1, "Ha ocurrido un error \n" + e1.getMessage());

				}

				MainGUI.notifica(this, "CREACIÓN CORRECTA", null, "Se ha creado el partido correctamente",
						JOptionPane.INFORMATION_MESSAGE);
				try {
					padre.refrescarJCombo();
				} catch (SQLException e) {

					MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());
				}
				padre.setEnabled(true);
				this.dispose();
			} else {
				MainGUI.notificaError(this, "Partido Inválida", null, "Los datos del partido son inválidos");
			}
		}
		if (t instanceof Jugador) {
			Jugador jugador = (Jugador) t;
			if (equiposJugadoresList.getSelectedValue() == null) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (nombreTF.getText().equals("")) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (dorsalTF.getText().equals("")) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			if (((Posicion) posicionesCB.getSelectedItem()).equals(MainGUI.posicionNoSeleccionable)) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			jugador.setNombre(nombreTF.getText());
			jugador.setEquipo((Equipo) equiposJugadoresList.getSelectedValue());
			try {
				jugador.setDorsal(Integer.parseInt(dorsalTF.getText()));
			} catch (Exception e) {
				MainGUI.notificaError(this, "ERROR", null, "Datos invalidos");
				return;
			}
			jugador.setPosicion((Posicion) posicionesCB.getSelectedItem());
			try {
				if (validarJugador.validate(jugador, false)) {
					MainGUI.con.insertOrUpdateJugador(jugador);
					padre.refrescarJCombo();
					padre.setEnabled(true);
					this.dispose();
				} else {
					MainGUI.notificaError(this, "Jugador Inválida", null, "Los datos del jugador son inválidos");
					return;
				}
			} catch (Exception e) {
				MainGUI.notificaError(this, "Jugador Inválida", null, "Los datos del jugador son inválidos");
			}
		}
		if (t instanceof Posicion) {
			if (descTF.getText().equals("")) {
				MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
				return;
			}
			Posicion p = (Posicion) t;
			p.setDescripcion(descTF.getText());
			try {
				if (validarPosicion.validate(p, true)) {
					MainGUI.con.insertOrUpdatePosicion(p);
					padre.refrescarJCombo();
					padre.setEnabled(true);
					this.dispose();
				} else {
					MainGUI.notificaError(this, "Posici�n Inválida", null, "Los datos de la posici�n son inválidos");
					return;
				}
			} catch (Exception e) {
				MainGUI.notificaError(this, "Posici�n Inválida", null, "Los datos de la posici�n son inválidos");
			}
		}
		if (t instanceof Equipo) {
			try {
				
				if (nombreTF.getText().equals("")) {
					MainGUI.notificaError(this, "ERROR", null, "Campos vac�os");
					return;
				}
				Equipo e = new Equipo(nombreTF.getText());
				if (validarEquipo.validate(e, false)) {
					MainGUI.con.insertOrUpdateEquipo(e);
				}
			} catch (SQLException e1) {
				MainGUI.notificaError(this, "ERROR", e1, "Ha ocurrido un error\n" + e1.getMessage());

			}
		}

	}

	private void salirBActionPerformed(java.awt.event.ActionEvent evt) {
		padre.setEnabled(true);
		this.dispose();
	}

	private void descartarBActionPerformed(java.awt.event.ActionEvent evt) {
		descTF.setText("");
		dorsalTF.setText("");
		equiposJugadoresList.setSelectedIndex(0);
		fechaFinDC.setDate(null);
		fechaInicioDC.setDate(null);
		nombreTF.setText("");

	}

	public static void main(String args[]) {

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

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					new CreacionGUI(null, Jugador.class).setVisible(true);
				} catch (SQLException e) {
					MainGUI.notificaError(null, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());
					
				}
			}
		});
	}

	private javax.swing.JLabel descL;

	private javax.swing.JTextField descTF;

	private javax.swing.JButton descartarB;

	private javax.swing.JLabel dorsalL;

	private javax.swing.JTextField dorsalTF;

	private javax.swing.JList<Object> equiposJugadoresList;

	private javax.swing.JLabel equiposL;

	private com.toedter.calendar.JDateChooser fechaFinDC;

	private javax.swing.JLabel fechaFinL;

	private javax.swing.JLabel fechaIniL;

	private com.toedter.calendar.JDateChooser fechaInicioDC;

	private javax.swing.JButton guardarB;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JLabel nombreL;

	private javax.swing.JTextField nombreTF;

	private javax.swing.JComboBox<Posicion> posicionesCB;

	private javax.swing.JLabel posicionesL;

	private javax.swing.JButton salirB;
	private JComboBox comboBox_1;
	private JLabel lblLocal;
	private JComboBox comboBox;
	private JComboBox comboBox_2;
	private JLabel lblVisitante;
	private JLabel lblCompeticin;

	public JComboBox getComboBox_1() {
		return comboBox_1;
	}

	public JLabel getLblLocal() {
		return lblLocal;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public JComboBox getComboBox_2() {
		return comboBox_2;
	}

	public JLabel getLblVisitante() {
		return lblVisitante;
	}

	public JLabel getLblCompeticin() {
		return lblCompeticin;
	}
}
