/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import persistencia.abs.PersistenciaGeneral;
import persistencia.implhibernate.PersistenciaHibernate;
import persistencia.impljdbc.PersistenciaJdbc;

import dao.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;


/**
 *
 * @author krats
 */
public class MainGUI extends javax.swing.JFrame {
	
	static PersistenciaGeneral con;
	
	private Competicion competicionNoSeleccionable = new Competicion(
			"-Elige una Competición", null, null);
	
	private Equipo equipoNoSeleccionable = new Equipo("-Elige un Equipo");
	
	private static void inicializar() {
		try {
			BufferedReader bfr = new BufferedReader(new InputStreamReader(
					MGUI.class.getResourceAsStream("CFG.INI")));
			String linea = null;
			while ((linea = bfr.readLine()) != null) {
				System.out.println(linea);
				if (linea.charAt(0) != '#') {
					String tipo = linea.split("=")[1];
					if (tipo.equals("mysqlJDBC")) {
						while ((linea = bfr.readLine()) != null && linea.charAt(
								0) != '#' && !linea.equals("[mysqlJDC]")) {}
						String connectionURL;
						while ((linea = bfr.readLine()).charAt(0) == '#') {}
						connectionURL = linea.split("=")[1].trim();
						String bbdd;
						while ((linea = bfr.readLine()).charAt(0) == '#') {}
						bbdd = linea.split("=")[1].trim();
						String usuario;
						while ((linea = bfr.readLine()).charAt(0) == '#') {}
						usuario = linea.split("=")[1].trim();
						String pass;
						while ((linea = bfr.readLine()).charAt(0) == '#') {}
						pass = linea.split("=")[1].trim();
						con = new PersistenciaJdbc(connectionURL + "/" + bbdd,
								usuario, pass);
						break;
					} else if (tipo.equals("hibernate")) {
						while ((linea = bfr.readLine()) != null && !linea
								.equals("[hibernate]")) {}
						String cfg;
						while ((linea = bfr.readLine()).charAt(0) == '#') {}
						cfg = linea.split("=")[1].trim();
						System.out.println(cfg);
						try {
							con = new PersistenciaHibernate(cfg);
						}
						catch (Exception e) {
							String mensaje = "El fichero CFG.INI está corrupto o mal configurado\n";
							notificaError(null,
									"ERROR EN LA CONEXION A LA BASE DE DATOS",
									e, mensaje);
							e.printStackTrace();
							System.exit(-1);
						}
						break;
					} else {
						String mensaje = "El fichero CFG.INI parece estar corrupto";
						notificaError(null,
								"ERROR EN LA CONEXION A LA BASE DE DATOS", null,
								mensaje);
						System.exit(-1);
					}
				}
			}
		}
		catch (IOException | ClassNotFoundException | SQLException e) {
			String mensaje = (e instanceof IOException)
					? "El fichero CFG.INI parece estar corrupto"
					: (e instanceof ClassNotFoundException)
							? "No se encuentra el conector a la Base de Datos"
							: "Error al conectar con la base de datos";
			notificaError(null, "ERROR EN LA CONEXION A LA BASE DE DATOS", e,
					mensaje);
			System.exit(-1);
		}
	}
	
	public static void notificaError(JFrame padre, String titulo, Exception e,
			String mensaje) {
		String contenido = "";
		if (mensaje != null) {
			contenido += mensaje;
		}
		if (e != null) {
			contenido += e.getClass().getName() + "\n" + e.getMessage(); // Tipo
																		 // y
																		 // mensaje
																		 // de
																		 // la
																		 // excepción
		}
		JOptionPane.showMessageDialog(padre, contenido, titulo,
				JOptionPane.ERROR_MESSAGE);
	}
	
	public static int notifica(JFrame padre, String titulo, Exception e,
			String mensaje, int tipo) {
		String contenido = "";
		if (mensaje != null) {
			contenido += mensaje;
		}
		if (e != null) {
			contenido += e.getClass().getName() + "\n" + e.getMessage(); // Tipo
																		 // y
																		 // mensaje
																		 // de
																		 // la
																		 // excepción
		}
		return JOptionPane.showConfirmDialog(padre, contenido, titulo, tipo);
	}
	
	/**
	 * Creates new form MainGUI
	 */
	public MainGUI() {
		competicionNoSeleccionable.setId(-1);
		equipoNoSeleccionable.setId(-1);
		initComponents();
		inicializar();
		jComboBox2.setModel(new DefaultComboBoxModel<Competicion>() {
			
			boolean selectionAllowed = true;
			
			@Override
			public void setSelectedItem(Object anObject) {
				if (!anObject.equals(equipoNoSeleccionable)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});
		jComboBox3.setModel(new DefaultComboBoxModel<Competicion>() {
			
			boolean selectionAllowed = true;
			
			@Override
			public void setSelectedItem(Object anObject) {
				if (!anObject.equals(equipoNoSeleccionable)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});
		
		jComboBox2.addItem(competicionNoSeleccionable);
		jComboBox3.addItem(competicionNoSeleccionable);
		con.getCompeticiones().stream().forEach((c) -> {
			jComboBox2.addItem(c);
			jComboBox3.addItem(c);
		});
		
		jComboBox1.setModel(new DefaultComboBoxModel<Equipo>() {
			
			boolean selectionAllowed = true;
			
			@Override
			public void setSelectedItem(Object anObject) {
				if (!anObject.equals(equipoNoSeleccionable)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});
		
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
		
		jTextField6 = new javax.swing.JTextField();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		crearB = new javax.swing.JButton();
		jComboBox2 = new javax.swing.JComboBox<>();
		jComboBox2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				competicionJComboBox2Selected((Competicion) jComboBox2
						.getSelectedItem());
			}
		});
		jButton1 = new javax.swing.JButton();
		jToggleButton1 = new javax.swing.JToggleButton();
		jDateChooser1 = new com.toedter.calendar.JDateChooser();
		jDateChooser2 = new com.toedter.calendar.JDateChooser();
		jLabel3 = new javax.swing.JLabel();
		jButton4 = new javax.swing.JButton();
		jButton4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				generarPartido(jComboBox2.getSelectedItem());
			}
		});
		jLabel5 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jPanel7 = new javax.swing.JPanel();
		crearB2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jToggleButton3 = new javax.swing.JToggleButton();
		jLabel8 = new javax.swing.JLabel();
		jComboBox9 = new javax.swing.JComboBox<>();
		jLabel26 = new javax.swing.JLabel();
		jTextField12 = new javax.swing.JTextField();
		jTextField12.setEnabled(false);
		jPanel2 = new javax.swing.JPanel();
		jComboBox1 = new javax.swing.JComboBox<>();
		jLabel1 = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList2 = new javax.swing.JList<>();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jTextField9 = new javax.swing.JTextField();
		jComboBox6 = new javax.swing.JComboBox<>();
		jLabel23 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		jTextField11 = new javax.swing.JTextField();
		jLabel25 = new javax.swing.JLabel();
		jComboBox7 = new javax.swing.JComboBox<>();
		jButton10 = new javax.swing.JButton();
		jLabel22 = new javax.swing.JLabel();
		jTextField10 = new javax.swing.JTextField();
		jButton7 = new javax.swing.JButton();
		jButton8 = new javax.swing.JButton();
		jButton9 = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList<>();
		jLabel4 = new javax.swing.JLabel();
		jComboBox3 = new javax.swing.JComboBox<>();
		jLabel9 = new javax.swing.JLabel();
		jTextField2 = new javax.swing.JTextField();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jLabel12 = new javax.swing.JLabel();
		jTextField4 = new javax.swing.JTextField();
		jLabel13 = new javax.swing.JLabel();
		jTextField5 = new javax.swing.JTextField();
		jComboBox4 = new javax.swing.JComboBox<>();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jTextField7 = new javax.swing.JTextField();
		jLabel16 = new javax.swing.JLabel();
		jTextField8 = new javax.swing.JTextField();
		jComboBox5 = new javax.swing.JComboBox<>();
		jLabel17 = new javax.swing.JLabel();
		jCheckBox1 = new javax.swing.JCheckBox();
		jLabel18 = new javax.swing.JLabel();
		jButton11 = new javax.swing.JButton();
		jButton12 = new javax.swing.JButton();
		jDateChooser3 = new com.toedter.calendar.JDateChooser();
		jLabel2 = new javax.swing.JLabel();
		jButton13 = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		jButton2 = new javax.swing.JButton();
		jButton14 = new javax.swing.JButton();
		jButton15 = new javax.swing.JButton();
		
		jTextField6.setText("jTextField6");
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(784, 576));
		setResizable(false);
		
		jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(
				javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black,
				java.awt.Color.black, java.awt.Color.black,
				java.awt.Color.black));
		jTabbedPane1.setTabLayoutPolicy(
				javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		
		jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(
				javax.swing.border.BevelBorder.RAISED, java.awt.Color.black,
				java.awt.Color.black, null, null));
		
		crearB.setText("Crear");
		crearB.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				crearBActionPerformed(evt);
			}
		});
		
		jButton1.setText("Borrar");
		
		jToggleButton1.setText("Guardar");
		
		jDateChooser1.setEnabled(false);
		
		jDateChooser2.setEnabled(false);
		
		jLabel3.setText("Fecha Inicio");
		
		jButton4.setText("Generar Partidos");
		
		jLabel5.setText("Nombre");
		
		jTextField1.setEnabled(false);
		
		jLabel6.setText("fecha Fin");
		
		jLabel7.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
		jLabel7.setText("COMPETICIONES");
		
		list = new JList();
		list.setEnabled(false);
		
		lblEquipos = new JLabel("Equipos");
		
		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
				jPanel5);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel5Layout
						.createSequentialGroup().addGroup(jPanel5Layout
								.createParallelGroup(Alignment.LEADING, false)
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addContainerGap().addComponent(
												jButton4).addGap(123)
										.addComponent(crearB).addGap(81)
										.addComponent(jButton1).addGap(18)
										.addComponent(jToggleButton1)).addGroup(
												jPanel5Layout
														.createSequentialGroup()
														.addGroup(jPanel5Layout
																.createParallelGroup(
																		Alignment.TRAILING)
																.addGroup(
																		jPanel5Layout
																				.createSequentialGroup()
																				.addComponent(
																						jLabel5)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(
																						jTextField1,
																						GroupLayout.PREFERRED_SIZE,
																						207,
																						GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		jPanel5Layout
																				.createSequentialGroup()
																				.addContainerGap()
																				.addGroup(
																						jPanel5Layout
																								.createParallelGroup(
																										Alignment.TRAILING)
																								.addGroup(
																										jPanel5Layout
																												.createSequentialGroup()
																												.addComponent(
																														jLabel7,
																														GroupLayout.DEFAULT_SIZE,
																														GroupLayout.DEFAULT_SIZE,
																														Short.MAX_VALUE)
																												.addPreferredGap(
																														ComponentPlacement.RELATED))
																								.addGroup(
																										jPanel5Layout
																												.createSequentialGroup()
																												.addComponent(
																														jComboBox2,
																														GroupLayout.PREFERRED_SIZE,
																														200,
																														GroupLayout.PREFERRED_SIZE)
																												.addGap(70)))
																				.addGroup(
																						jPanel5Layout
																								.createParallelGroup(
																										Alignment.LEADING)
																								.addComponent(
																										jDateChooser1,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addComponent(
																										jLabel3))))
														.addGap(18).addGroup(
																jPanel5Layout
																		.createParallelGroup(
																				Alignment.TRAILING)
																		.addGroup(
																				jPanel5Layout
																						.createSequentialGroup()
																						.addComponent(
																								lblEquipos)
																						.addPreferredGap(
																								ComponentPlacement.RELATED,
																								70,
																								Short.MAX_VALUE)
																						.addGroup(
																								jPanel5Layout
																										.createParallelGroup(
																												Alignment.LEADING)
																										.addComponent(
																												jLabel6)
																										.addComponent(
																												jDateChooser2,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGroup(
																				jPanel5Layout
																						.createSequentialGroup()
																						.addComponent(
																								list,
																								GroupLayout.DEFAULT_SIZE,
																								173,
																								Short.MAX_VALUE)
																						.addGap(36)))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				Alignment.TRAILING).addGroup(jPanel5Layout
						.createSequentialGroup().addGap(6).addGroup(
								jPanel5Layout.createParallelGroup(
										Alignment.TRAILING).addGroup(
												jPanel5Layout
														.createSequentialGroup()
														.addGroup(jPanel5Layout
																.createParallelGroup(
																		Alignment.BASELINE)
																.addComponent(
																		jLabel3)
																.addComponent(
																		jLabel6))
														.addGap(3).addComponent(
																jDateChooser1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel5Layout
												.createSequentialGroup()
												.addComponent(jLabel7,
														GroupLayout.PREFERRED_SIZE,
														27,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														ComponentPlacement.UNRELATED)
												.addGroup(jPanel5Layout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																jDateChooser2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jComboBox2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblEquipos)).addGroup(
												jPanel5Layout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(jPanel5Layout
																.createSequentialGroup()
																.addGap(36)
																.addGroup(
																		jPanel5Layout
																				.createParallelGroup(
																						Alignment.BASELINE)
																				.addComponent(
																						jLabel5)
																				.addComponent(
																						jTextField1,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)))
														.addGroup(jPanel5Layout
																.createSequentialGroup()
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		list,
																		GroupLayout.PREFERRED_SIZE,
																		49,
																		GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel5Layout.createParallelGroup(
								Alignment.BASELINE).addComponent(crearB)
								.addComponent(jButton1).addComponent(
										jToggleButton1).addComponent(jButton4))
						.addContainerGap()));
		jPanel5.setLayout(jPanel5Layout);
		
		jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(
				javax.swing.border.BevelBorder.RAISED, java.awt.Color.black,
				java.awt.Color.black, null, null));
		
		crearB2.setText("Crear");
		crearB2.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				crearB2ActionPerformed(evt);
			}
		});
		
		jButton3.setText("Borrar");
		
		jToggleButton3.setText("Guardar");
		
		jLabel8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
		jLabel8.setText("POSICIONES");
		
		jLabel26.setText("Descripción");
		
		jTextField12.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField12ActionPerformed(evt);
			}
		});
		
		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
				jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						jPanel7Layout.createSequentialGroup().addGroup(
								jPanel7Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel7Layout
												.createSequentialGroup()
												.addContainerGap().addComponent(
														jLabel8,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
										.addGroup(jPanel7Layout
												.createSequentialGroup()
												.addGroup(jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(jPanel7Layout
																.createSequentialGroup()
																.addGap(204,
																		204,
																		204)
																.addComponent(
																		crearB2)
																.addGap(71, 71,
																		71)
																.addComponent(
																		jButton3)
																.addGap(18, 18,
																		18)
																.addComponent(
																		jToggleButton3))
														.addGroup(jPanel7Layout
																.createSequentialGroup()
																.addGap(62, 62,
																		62)
																.addComponent(
																		jComboBox9,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		173,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(78, 78,
																		78)
																.addGroup(
																		jPanel7Layout
																				.createParallelGroup(
																						javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jLabel26)
																				.addComponent(
																						jTextField12,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						318,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
												.addGap(0, 31,
														Short.MAX_VALUE)))
								.addContainerGap()));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel7Layout.createSequentialGroup().addContainerGap()
								.addComponent(jLabel8).addGap(29, 29, 29)
								.addGroup(jPanel7Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jComboBox9,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(jPanel7Layout
												.createSequentialGroup()
												.addComponent(jLabel26)
												.addPreferredGap(
														javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jTextField12,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										83, Short.MAX_VALUE).addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(crearB2)
														.addComponent(jButton3)
														.addComponent(
																jToggleButton3))
								.addContainerGap()));
		
		panel = new JPanel();
		
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel1Layout
						.createSequentialGroup().addGroup(jPanel1Layout
								.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(jPanel7, Alignment.LEADING, 0, 0,
										Short.MAX_VALUE).addComponent(panel,
												Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE).addComponent(
														jPanel5,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel1Layout
						.createSequentialGroup().addComponent(jPanel5,
								GroupLayout.PREFERRED_SIZE, 161,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
										ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 150,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
										ComponentPlacement.RELATED)
						.addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, 146,
								GroupLayout.PREFERRED_SIZE).addContainerGap(76,
										Short.MAX_VALUE)));
		panel.setLayout(null);
		
		lblJugadores = new JLabel();
		lblJugadores.setBounds(22, 12, 144, 29);
		lblJugadores.setText("JUGADORES");
		lblJugadores.setFont(new Font("Dialog", Font.PLAIN, 24));
		panel.add(lblJugadores);
		
		button = new JButton();
		button.setText("Crear");
		button.setBounds(295, 113, 73, 25);
		panel.add(button);
		
		button_1 = new JButton();
		button_1.setText("Borrar");
		button_1.setBounds(449, 113, 79, 25);
		panel.add(button_1);
		
		toggleButton = new JToggleButton();
		toggleButton.setText("Guardar");
		toggleButton.setBounds(546, 113, 92, 25);
		panel.add(toggleButton);
		
		comboBox = new JComboBox();
		comboBox.setBounds(58, 72, 113, 24);
		panel.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setEnabled(false);
		comboBox_1.setBounds(265, 32, 131, 24);
		panel.add(comboBox_1);
		
		lblEquipo = new JLabel("Equipo");
		lblEquipo.setBounds(265, 12, 70, 15);
		panel.add(lblEquipo);
		
		lblPosicin = new JLabel("Posición");
		lblPosicin.setBounds(432, 12, 70, 15);
		panel.add(lblPosicin);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setEnabled(false);
		comboBox_2.setBounds(432, 32, 131, 24);
		panel.add(comboBox_2);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(265, 58, 70, 15);
		panel.add(lblNombre);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(234, 75, 183, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		lblDorsal = new JLabel("Dorsal");
		lblDorsal.setBounds(449, 58, 70, 15);
		panel.add(lblDorsal);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setBounds(432, 75, 114, 19);
		panel.add(textField_1);
		textField_1.setColumns(10);
		jPanel1.setLayout(jPanel1Layout);
		
		jTabbedPane1.addTab("Gestión", jPanel1);
		
		jLabel1.setText("Equipos");
		
		jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(
				javax.swing.border.BevelBorder.LOWERED, java.awt.Color.black,
				java.awt.Color.black, null, null));
		
		jScrollPane2.setViewportView(jList2);
		
		jLabel19.setText("Jugadores actuales");
		
		jLabel20.setText("Nombre");
		
		jButton5.setText("Guardar");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		
		jButton6.setText("Descartar");
		
		jLabel23.setText("Posición");
		
		jLabel24.setText("Dorsal");
		
		jLabel25.setText("Equipo");
		
		jButton10.setText("Crear nuevo");
		
		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(
				jPanel8);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(
				Alignment.TRAILING).addGroup(jPanel8Layout
						.createSequentialGroup().addContainerGap().addGroup(
								jPanel8Layout.createParallelGroup(
										Alignment.LEADING).addGroup(
												jPanel8Layout
														.createSequentialGroup()
														.addComponent(jLabel25,
																GroupLayout.PREFERRED_SIZE,
																61,
																GroupLayout.PREFERRED_SIZE)
														.addGap(2).addComponent(
																jComboBox7,
																GroupLayout.PREFERRED_SIZE,
																169,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel8Layout
												.createParallelGroup(
														Alignment.TRAILING,
														false).addComponent(
																jLabel20)
												.addComponent(jButton6)
												.addComponent(jTextField9,
														GroupLayout.PREFERRED_SIZE,
														199,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(jPanel8Layout
														.createSequentialGroup()
														.addGroup(jPanel8Layout
																.createParallelGroup(
																		Alignment.LEADING)
																.addGroup(
																		jPanel8Layout
																				.createSequentialGroup()
																				.addComponent(
																						jLabel24)
																				.addGap(0,
																						28,
																						Short.MAX_VALUE))
																.addComponent(
																		jTextField11))
														.addPreferredGap(
																ComponentPlacement.RELATED)
														.addGroup(jPanel8Layout
																.createParallelGroup(
																		Alignment.LEADING)
																.addComponent(
																		jLabel23)
																.addComponent(
																		jComboBox6,
																		GroupLayout.PREFERRED_SIZE,
																		113,
																		GroupLayout.PREFERRED_SIZE))))
										.addGroup(jPanel8Layout
												.createSequentialGroup()
												.addComponent(jButton5).addGap(
														82).addComponent(
																jButton10)))
						.addPreferredGap(ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel8Layout.createParallelGroup(
								Alignment.LEADING).addComponent(jLabel19,
										GroupLayout.PREFERRED_SIZE, 113,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane2,
										GroupLayout.PREFERRED_SIZE, 203,
										GroupLayout.PREFERRED_SIZE)).addGap(
												245)));
		jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel8Layout
						.createSequentialGroup().addContainerGap().addGroup(
								jPanel8Layout.createParallelGroup(
										Alignment.LEADING).addGroup(
												jPanel8Layout
														.createSequentialGroup()
														.addComponent(jLabel19)
														.addPreferredGap(
																ComponentPlacement.RELATED)
														.addComponent(
																jScrollPane2,
																GroupLayout.PREFERRED_SIZE,
																326,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel8Layout
												.createSequentialGroup()
												.addComponent(jLabel20)
												.addPreferredGap(
														ComponentPlacement.RELATED)
												.addComponent(jTextField9,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(10).addGroup(
														jPanel8Layout
																.createParallelGroup(
																		Alignment.BASELINE)
																.addComponent(
																		jLabel23)
																.addComponent(
																		jLabel24))
												.addPreferredGap(
														ComponentPlacement.RELATED)
												.addGroup(jPanel8Layout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																jComboBox6,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jTextField11,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
												.addGap(18).addGroup(
														jPanel8Layout
																.createParallelGroup(
																		Alignment.BASELINE)
																.addComponent(
																		jComboBox7,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(
																		jLabel25))
												.addGap(32).addGroup(
														jPanel8Layout
																.createParallelGroup(
																		Alignment.BASELINE)
																.addComponent(
																		jButton5)
																.addComponent(
																		jButton10))
												.addPreferredGap(
														ComponentPlacement.UNRELATED)
												.addComponent(jButton6)))
						.addContainerGap(22, Short.MAX_VALUE)));
		jPanel8.setLayout(jPanel8Layout);
		
		jLabel22.setText("Nombre");
		
		jButton7.setText("Cambiar");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});
		
		jButton8.setText("Descartar");
		
		jButton9.setText("Crear nuevo");
		
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						jPanel2Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel2Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel2Layout
												.createSequentialGroup()
												.addComponent(jPanel8,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addContainerGap()).addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														jPanel2Layout
																.createSequentialGroup()
																.addGroup(
																		jPanel2Layout
																				.createParallelGroup(
																						javax.swing.GroupLayout.Alignment.TRAILING)
																				.addGroup(
																						jPanel2Layout
																								.createSequentialGroup()
																								.addGap(0,
																										0,
																										Short.MAX_VALUE)
																								.addComponent(
																										jButton9))
																				.addGroup(
																						jPanel2Layout
																								.createSequentialGroup()
																								.addComponent(
																										jLabel1,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										61,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addGap(2,
																										2,
																										2)
																								.addComponent(
																										jComboBox1,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										169,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										jButton7)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(
																										jButton8)))
																.addGap(46, 46,
																		46))))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel2Layout.createSequentialGroup().addContainerGap(
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addComponent(jLabel22)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTextField10,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										221,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(39, 39, 39)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						jPanel2Layout.createSequentialGroup().addGap(22, 22, 22)
								.addGroup(jPanel2Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel22).addComponent(
												jTextField10,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel2Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel2Layout
												.createSequentialGroup()
												.addPreferredGap(
														javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addGroup(jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jComboBox1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel1)))
										.addGroup(jPanel2Layout
												.createSequentialGroup().addGap(
														28, 28, 28).addGroup(
																jPanel2Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jButton7)
																		.addComponent(
																				jButton8))))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jButton9).addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addComponent(jPanel8,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(25, 25, 25)));
		
		jTabbedPane1.addTab("Equipos", jPanel2);
		
		jScrollPane1.setEnabled(false);
		
		jScrollPane1.setViewportView(jList1);
		
		jLabel4.setText("Partidos");
		
		jLabel9.setText("Competición");
		
		jLabel10.setText("Local");
		
		jLabel11.setText("Visitante");
		
		jLabel12.setText("Resultado:");
		
		jLabel13.setText("-");
		
		jLabel14.setText("Jugador");
		
		jLabel15.setText("Goles");
		
		jLabel16.setText("Faltas");
		
		jTextField8.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField8ActionPerformed(evt);
			}
		});
		
		jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] {
						"0", "1", "2"
				}));
				
		jLabel17.setText("Amarillas");
		
		jLabel18.setText("Roja");
		
		jButton11.setText("Guardar");
		
		jButton12.setText("Descargar");
		
		jLabel2.setText("fecha");
		
		jButton13.setText("Nuevo");
		jButton13.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton13ActionPerformed(evt);
			}
		});
		
		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel3Layout
						.createSequentialGroup().addContainerGap().addGroup(
								jPanel3Layout.createParallelGroup(
										Alignment.LEADING).addComponent(jLabel9)
										.addGroup(jPanel3Layout
												.createSequentialGroup()
												.addGroup(jPanel3Layout
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																jScrollPane1,
																GroupLayout.DEFAULT_SIZE,
																222,
																Short.MAX_VALUE)
														.addComponent(jLabel4)
														.addGroup(jPanel3Layout
																.createSequentialGroup()
																.addComponent(
																		jComboBox3,
																		GroupLayout.PREFERRED_SIZE,
																		239,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		ComponentPlacement.RELATED)))
												.addGroup(jPanel3Layout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(jPanel3Layout
																.createSequentialGroup()
																.addGroup(
																		jPanel3Layout
																				.createParallelGroup(
																						Alignment.LEADING)
																				.addGroup(
																						jPanel3Layout
																								.createSequentialGroup()
																								.addGap(29)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.LEADING,
																														false)
																												.addGroup(
																														jPanel3Layout
																																.createSequentialGroup()
																																.addComponent(
																																		jLabel11)
																																.addPreferredGap(
																																		ComponentPlacement.RELATED)
																																.addComponent(
																																		jTextField3))
																												.addGroup(
																														jPanel3Layout
																																.createSequentialGroup()
																																.addComponent(
																																		jLabel10)
																																.addGap(30)
																																.addComponent(
																																		jTextField2,
																																		GroupLayout.PREFERRED_SIZE,
																																		166,
																																		GroupLayout.PREFERRED_SIZE))))
																				.addGroup(
																						jPanel3Layout
																								.createSequentialGroup()
																								.addGap(100)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.LEADING)
																												.addComponent(
																														jButton13)
																												.addGroup(
																														jPanel3Layout
																																.createSequentialGroup()
																																.addComponent(
																																		jButton11)
																																.addPreferredGap(
																																		ComponentPlacement.UNRELATED)
																																.addComponent(
																																		jButton12)))))
																.addGap(0, 80,
																		Short.MAX_VALUE))
														.addGroup(jPanel3Layout
																.createSequentialGroup()
																.addGroup(
																		jPanel3Layout
																				.createParallelGroup(
																						Alignment.TRAILING)
																				.addGroup(
																						Alignment.LEADING,
																						jPanel3Layout
																								.createSequentialGroup()
																								.addGap(38)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.LEADING)
																												.addGroup(
																														jPanel3Layout
																																.createParallelGroup(
																																		Alignment.TRAILING)
																																.addGroup(
																																		jPanel3Layout
																																				.createParallelGroup(
																																						Alignment.LEADING)
																																				.addComponent(
																																						jLabel14)
																																				.addComponent(
																																						jComboBox4,
																																						GroupLayout.PREFERRED_SIZE,
																																						99,
																																						GroupLayout.PREFERRED_SIZE))
																																.addComponent(
																																		jLabel15))
																												.addGroup(
																														jPanel3Layout
																																.createSequentialGroup()
																																.addGap(70)
																																.addGroup(
																																		jPanel3Layout
																																				.createParallelGroup(
																																						Alignment.LEADING)
																																				.addComponent(
																																						jLabel17)
																																				.addComponent(
																																						jLabel18)
																																				.addComponent(
																																						jLabel16)))))
																				.addGroup(
																						Alignment.LEADING,
																						jPanel3Layout
																								.createSequentialGroup()
																								.addGap(43)
																								.addComponent(
																										jLabel12)
																								.addGap(4)
																								.addComponent(
																										jTextField4,
																										GroupLayout.PREFERRED_SIZE,
																										39,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel13)
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(
																										jTextField5,
																										GroupLayout.PREFERRED_SIZE,
																										39,
																										GroupLayout.PREFERRED_SIZE)))
																.addPreferredGap(
																		ComponentPlacement.RELATED,
																		8,
																		Short.MAX_VALUE)
																.addGroup(
																		jPanel3Layout
																				.createParallelGroup(
																						Alignment.LEADING)
																				.addGroup(
																						jPanel3Layout
																								.createParallelGroup(
																										Alignment.LEADING,
																										false)
																								.addComponent(
																										jTextField7)
																								.addComponent(
																										jComboBox5,
																										0,
																										91,
																										Short.MAX_VALUE)
																								.addComponent(
																										jTextField8)
																								.addComponent(
																										jCheckBox1))
																				.addGroup(
																						jPanel3Layout
																								.createSequentialGroup()
																								.addGap(48)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.LEADING)
																												.addComponent(
																														jLabel2)
																												.addComponent(
																														jDateChooser3,
																														GroupLayout.PREFERRED_SIZE,
																														GroupLayout.DEFAULT_SIZE,
																														GroupLayout.PREFERRED_SIZE))))))))
						.addGap(169)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel3Layout
						.createSequentialGroup().addGap(13).addComponent(
								jLabel9).addPreferredGap(
										ComponentPlacement.RELATED).addGroup(
												jPanel3Layout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																jComboBox3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jTextField2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel10))
						.addPreferredGap(ComponentPlacement.RELATED).addGroup(
								jPanel3Layout.createParallelGroup(
										Alignment.BASELINE).addComponent(
												jLabel11).addComponent(
														jTextField3,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
						.addGap(7).addComponent(jLabel4).addPreferredGap(
								ComponentPlacement.RELATED).addGroup(
										jPanel3Layout.createParallelGroup(
												Alignment.LEADING).addGroup(
														jPanel3Layout
																.createSequentialGroup()
																.addGroup(
																		jPanel3Layout
																				.createParallelGroup(
																						Alignment.BASELINE)
																				.addComponent(
																						jLabel12)
																				.addComponent(
																						jTextField4,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabel13)
																				.addComponent(
																						jTextField5,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(18)
																.addGroup(
																		jPanel3Layout
																				.createParallelGroup(
																						Alignment.BASELINE)
																				.addComponent(
																						jLabel14)
																				.addComponent(
																						jLabel2))
																.addGap(2)
																.addGroup(
																		jPanel3Layout
																				.createParallelGroup(
																						Alignment.LEADING)
																				.addGroup(
																						jPanel3Layout
																								.createSequentialGroup()
																								.addComponent(
																										jComboBox4,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addGap(18)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.TRAILING)
																												.addGroup(
																														jPanel3Layout
																																.createSequentialGroup()
																																.addGroup(
																																		jPanel3Layout
																																				.createParallelGroup(
																																						Alignment.BASELINE)
																																				.addComponent(
																																						jLabel15)
																																				.addComponent(
																																						jTextField7,
																																						GroupLayout.PREFERRED_SIZE,
																																						GroupLayout.DEFAULT_SIZE,
																																						GroupLayout.PREFERRED_SIZE))
																																.addPreferredGap(
																																		ComponentPlacement.RELATED)
																																.addComponent(
																																		jLabel16))
																												.addComponent(
																														jTextField8,
																														GroupLayout.PREFERRED_SIZE,
																														GroupLayout.DEFAULT_SIZE,
																														GroupLayout.PREFERRED_SIZE))
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.BASELINE)
																												.addComponent(
																														jComboBox5,
																														GroupLayout.PREFERRED_SIZE,
																														GroupLayout.DEFAULT_SIZE,
																														GroupLayout.PREFERRED_SIZE)
																												.addComponent(
																														jLabel17))
																								.addGap(8)
																								.addGroup(
																										jPanel3Layout
																												.createParallelGroup(
																														Alignment.LEADING)
																												.addGroup(
																														jPanel3Layout
																																.createSequentialGroup()
																																.addComponent(
																																		jLabel18)
																																.addGap(61)
																																.addGroup(
																																		jPanel3Layout
																																				.createParallelGroup(
																																						Alignment.BASELINE)
																																				.addComponent(
																																						jButton11)
																																				.addComponent(
																																						jButton12))
																																.addGap(18)
																																.addComponent(
																																		jButton13))
																												.addComponent(
																														jCheckBox1)))
																				.addComponent(
																						jDateChooser3,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)))
												.addComponent(jScrollPane1,
														GroupLayout.PREFERRED_SIZE,
														355,
														GroupLayout.PREFERRED_SIZE))
						.addContainerGap(79, Short.MAX_VALUE)));
		jPanel3.setLayout(jPanel3Layout);
		
		jTabbedPane1.addTab("Partidos", jPanel3);
		
		jButton2.setText("jButton2");
		
		jButton14.setText("jButton14");
		
		jButton15.setText("jButton15");
		
		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel4Layout
						.createSequentialGroup().addGap(136).addComponent(
								jButton2).addGap(50).addComponent(jButton14)
						.addGap(60).addComponent(jButton15).addContainerGap(
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				Alignment.LEADING).addGroup(jPanel4Layout
						.createSequentialGroup().addGap(45).addGroup(
								jPanel4Layout.createParallelGroup(
										Alignment.BASELINE).addComponent(
												jButton2).addComponent(
														jButton14).addComponent(
																jButton15))
						.addContainerGap(475, Short.MAX_VALUE)));
		jPanel4.setLayout(jPanel4Layout);
		
		jTabbedPane1.addTab("Informes", jPanel4);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addContainerGap().addComponent(
								jTabbedPane1).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addContainerGap().addComponent(
								jTabbedPane1,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		
		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	protected void competicionJComboBox2Selected(Competicion selectedItem) {
		if (selectedItem.equals(competicionNoSeleccionable)) {
			jDateChooser1.setEnabled(false);
			jDateChooser2.setEnabled(false);
			list.setEnabled(false);
			jTextField1.setEnabled(false);
		} else {
			DefaultListModel<Equipo> model = new DefaultListModel<>();
			try {
				con.getListaEquipos().stream().forEach((e)->{
					model.addElement(e);
				});
				list.setModel(model);
				con.getListaEquipos().stream().filter((e)->{
					@SuppressWarnings("unchecked")
					Set<Equipo> s = new HashSet<>(selectedItem.getPartidos());
					int size = s.size();
					s.add(e);
					return size == s.size(); 
				}).forEach((e)->{
					
				});
			}
			catch (SQLException e) {
				
				e.printStackTrace();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(selectedItem.getFechaComienzo());
			jDateChooser1.setCalendar(c);
			jDateChooser2.setEnabled(true);
			c.setTime(selectedItem.getFechaFin());
			jDateChooser2.setCalendar(c);
			jDateChooser2.setEnabled(true);
			
		}
	}
	
	protected void generarPartido(Object selectedItem) {
		
	}
	
	private void crearBActionPerformed(java.awt.event.ActionEvent evt) {
		new CreacionGUI<>(this, Competicion.class).setVisible(true);
		this.setEnabled(false);
	}
	
	private void crearB2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_crearB2ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_crearB2ActionPerformed
	
	private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField8ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextField8ActionPerformed
	
	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jButton5ActionPerformed
	
	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jButton7ActionPerformed
	
	private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField12ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jTextField12ActionPerformed
	
	private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton13ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jButton13ActionPerformed
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel.
		 * For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				new MainGUI().setVisible(true);
			}
		});
	}
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton crearB;
	
	private javax.swing.JButton crearB2;
	
	private javax.swing.JButton jButton1;
	
	private javax.swing.JButton jButton10;
	
	private javax.swing.JButton jButton11;
	
	private javax.swing.JButton jButton12;
	
	private javax.swing.JButton jButton13;
	
	private javax.swing.JButton jButton14;
	
	private javax.swing.JButton jButton15;
	
	private javax.swing.JButton jButton2;
	
	private javax.swing.JButton jButton3;
	
	private javax.swing.JButton jButton4;
	
	private javax.swing.JButton jButton5;
	
	private javax.swing.JButton jButton6;
	
	private javax.swing.JButton jButton7;
	
	private javax.swing.JButton jButton8;
	
	private javax.swing.JButton jButton9;
	
	private javax.swing.JCheckBox jCheckBox1;
	
	private javax.swing.JComboBox<Equipo> jComboBox1;
	
	private javax.swing.JComboBox<Competicion> jComboBox2;
	
	private javax.swing.JComboBox<Competicion> jComboBox3;
	
	private javax.swing.JComboBox<Jugador> jComboBox4;
	
	private javax.swing.JComboBox<String> jComboBox5;
	
	private javax.swing.JComboBox<Posicion> jComboBox6;
	
	private javax.swing.JComboBox<Equipo> jComboBox7;
	
	private javax.swing.JComboBox<Posicion> jComboBox9;
	
	private com.toedter.calendar.JDateChooser jDateChooser1;
	
	private com.toedter.calendar.JDateChooser jDateChooser2;
	
	private com.toedter.calendar.JDateChooser jDateChooser3;
	
	private javax.swing.JLabel jLabel1;
	
	private javax.swing.JLabel jLabel10;
	
	private javax.swing.JLabel jLabel11;
	
	private javax.swing.JLabel jLabel12;
	
	private javax.swing.JLabel jLabel13;
	
	private javax.swing.JLabel jLabel14;
	
	private javax.swing.JLabel jLabel15;
	
	private javax.swing.JLabel jLabel16;
	
	private javax.swing.JLabel jLabel17;
	
	private javax.swing.JLabel jLabel18;
	
	private javax.swing.JLabel jLabel19;
	
	private javax.swing.JLabel jLabel2;
	
	private javax.swing.JLabel jLabel20;
	
	private javax.swing.JLabel jLabel22;
	
	private javax.swing.JLabel jLabel23;
	
	private javax.swing.JLabel jLabel24;
	
	private javax.swing.JLabel jLabel25;
	
	private javax.swing.JLabel jLabel26;
	
	private javax.swing.JLabel jLabel3;
	
	private javax.swing.JLabel jLabel4;
	
	private javax.swing.JLabel jLabel5;
	
	private javax.swing.JLabel jLabel6;
	
	private javax.swing.JLabel jLabel7;
	
	private javax.swing.JLabel jLabel8;
	
	private javax.swing.JLabel jLabel9;
	
	private javax.swing.JList<String> jList1;
	
	private javax.swing.JList<String> jList2;
	
	private javax.swing.JPanel jPanel1;
	
	private javax.swing.JPanel jPanel2;
	
	private javax.swing.JPanel jPanel3;
	
	private javax.swing.JPanel jPanel4;
	
	private javax.swing.JPanel jPanel5;
	
	private javax.swing.JPanel jPanel7;
	
	private javax.swing.JPanel jPanel8;
	
	private javax.swing.JScrollPane jScrollPane1;
	
	private javax.swing.JScrollPane jScrollPane2;
	
	private javax.swing.JTabbedPane jTabbedPane1;
	
	private javax.swing.JTextField jTextField1;
	
	private javax.swing.JTextField jTextField10;
	
	private javax.swing.JTextField jTextField11;
	
	private javax.swing.JTextField jTextField12;
	
	private javax.swing.JTextField jTextField2;
	
	private javax.swing.JTextField jTextField3;
	
	private javax.swing.JTextField jTextField4;
	
	private javax.swing.JTextField jTextField5;
	
	private javax.swing.JTextField jTextField6;
	
	private javax.swing.JTextField jTextField7;
	
	private javax.swing.JTextField jTextField8;
	
	private javax.swing.JTextField jTextField9;
	
	private javax.swing.JToggleButton jToggleButton1;
	
	private javax.swing.JToggleButton jToggleButton3;
	
	private JPanel panel;
	
	private JTextField textField;
	
	private JTextField textField_1;
	
	private JComboBox<Posicion> comboBox_2;
	
	private JComboBox<Jugador> comboBox;
	
	private JLabel lblJugadores;
	
	private JLabel lblPosicin;
	
	private JLabel lblDorsal;
	
	private JButton button_1;
	
	private JComboBox<Equipo> comboBox_1;
	
	private JButton button;
	
	private JToggleButton toggleButton;
	
	private JLabel lblNombre;
	
	private JLabel lblEquipo;
	
	private JList<Equipo> list;
	
	private JLabel lblEquipos;
	
	public JButton getJButton15() {
		return jButton15;
	}
	
	public JPanel getJPanel4() {
		return jPanel4;
	}
	
	public JButton getJButton2() {
		return jButton2;
	}
	
	public JButton getJButton14() {
		return jButton14;
	}
	
	public JComboBox getComboBox_2() {
		return comboBox_2;
	}
	
	public JComboBox getComboBox() {
		return comboBox;
	}
	
	public JLabel getLblJugadores() {
		return lblJugadores;
	}
	
	public JTextField getTextField() {
		return textField;
	}
	
	public JLabel getLblPosicin() {
		return lblPosicin;
	}
	
	public JLabel getLblDorsal() {
		return lblDorsal;
	}
	
	public JButton getButton_1() {
		return button_1;
	}
	
	public JComboBox getComboBox_1() {
		return comboBox_1;
	}
	
	public JTextField getTextField_1() {
		return textField_1;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public JToggleButton getToggleButton() {
		return toggleButton;
	}
	
	public JLabel getLblNombre() {
		return lblNombre;
	}
	
	public JLabel getLblEquipo() {
		return lblEquipo;
	}
}
