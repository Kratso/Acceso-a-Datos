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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import util.Utilidades;

import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;

import org.hibernate.service.jndi.JndiException;

import dao.*;
import dao.abs.PersistenciaGeneral;
import dao.implhibernate.PersistenciaHibernate;
import dao.impljdbc.PersistenciaJdbc;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTable;

/**
 *
 * @author krats
 */
public class MainGUI extends javax.swing.JFrame {

	public static PersistenciaGeneral con;

	public static Competicion competicionNoSeleccionable = new Competicion("-Elige una Competici贸n-", null, null);

	public static Equipo equipoNoSeleccionable = new Equipo("-Elige un Equipo");

	public static Posicion posicionNoSeleccionable = new Posicion("-Elige una Posicion-");

	public static Jugador jugadorNoSeleccionable = new Jugador(-1, equipoNoSeleccionable, posicionNoSeleccionable, "",
			-1);

	private static void inicializar() {
		try {
			BufferedReader bfr = new BufferedReader(
					new InputStreamReader(MainGUI.class.getResourceAsStream("CFG.INI")));
			String linea = null;
			while ((linea = bfr.readLine()) != null) {
				System.out.println(linea);
				if (linea.charAt(0) != '#') {
					String tipo = linea.split("=")[1];
					if (tipo.equals("mysqlJDBC")) {
						while ((linea = bfr.readLine()) != null && linea.charAt(0) != '#'
								&& !linea.equals("[mysqlJDC]")) {
						}
						String connectionURL;
						while ((linea = bfr.readLine()).charAt(0) == '#') {
						}
						connectionURL = linea.split("=")[1].trim();
						String bbdd;
						while ((linea = bfr.readLine()).charAt(0) == '#') {
						}
						bbdd = linea.split("=")[1].trim();
						String usuario;
						while ((linea = bfr.readLine()).charAt(0) == '#') {
						}
						usuario = linea.split("=")[1].trim();
						String pass;
						while ((linea = bfr.readLine()).charAt(0) == '#') {
						}
						pass = linea.split("=")[1].trim();
						con = new PersistenciaJdbc(connectionURL + "/" + bbdd, usuario, pass);
						break;
					} else if (tipo.equals("hibernate")) {
						while ((linea = bfr.readLine()) != null && !linea.equals("[hibernate]")) {
						}
						String cfg;
						while ((linea = bfr.readLine()).charAt(0) == '#') {
						}
						cfg = linea.split("=")[1].trim();
						System.out.println(cfg);
						try {
							con = new PersistenciaHibernate(cfg);
						} catch (Exception e) {
							String mensaje = "El fichero CFG.INI est谩 corrupto o mal configurado\n";
							notificaError(null, "ERROR EN LA CONEXION A LA BASE DE DATOS", e, mensaje);
							e.printStackTrace();
							System.exit(-1);
						}
						break;
					} else {
						String mensaje = "El fichero CFG.INI parece estar corrupto";
						notificaError(null, "ERROR EN LA CONEXION A LA BASE DE DATOS", null, mensaje);
						System.exit(-1);
					}
				}
			}
		} catch (IOException | ClassNotFoundException | SQLException e) {
			String mensaje = (e instanceof IOException) ? "El fichero CFG.INI parece estar corrupto"
					: (e instanceof ClassNotFoundException) ? "No se encuentra el conector a la Base de Datos"
							: "Error al conectar con la base de datos";
			notificaError(null, "ERROR EN LA CONEXION A LA BASE DE DATOS", e, mensaje);
			System.exit(-1);
		}
	}

	public static void notificaError(JFrame padre, String titulo, Exception e, String mensaje) {
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
																			// excepci贸n
		}
		JOptionPane.showMessageDialog(padre, contenido, titulo, JOptionPane.ERROR_MESSAGE);
	}

	public static int notifica(JFrame padre, String titulo, Exception e, String mensaje, int tipo) {
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
																			// excepci贸n
		}
		return JOptionPane.showConfirmDialog(padre, contenido, titulo, tipo);
	}

	/**
	 * Creates new form MainGUI
	 * 
	 * @throws SQLException
	 */
	public MainGUI() throws SQLException {
		try {
			competicionNoSeleccionable.setId(-1);
			equipoNoSeleccionable.setId(-1);
			posicionNoSeleccionable.setId(-1);
			initComponents();
			inicializar();
			refrescarJCombo();
		} catch (Exception e) {
			notificaError(this, "ERROR", e, "Ha ocurrido un error catastr骹ico al inicializar\n" + e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {

		jTextField6 = new javax.swing.JTextField();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jPanel5 = new javax.swing.JPanel();
		crearB = new javax.swing.JButton();
		jComboBox2 = new javax.swing.JComboBox<>();
		jComboBox2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				competicionJComboBox2Selected((Competicion) jComboBox2.getSelectedItem());
			}
		});
		jButton1 = new javax.swing.JButton();
		jButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				restaurarCompeticion((Competicion) jComboBox2.getSelectedItem());
			}
		});
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
		jButton3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					refrescarJCombo();
				} catch (SQLException e1) {
					notificaError(null, "ERROR", e1, "Ha ocurrido un error \n" + e1.getMessage());

				}
			}
		});
		jLabel8 = new javax.swing.JLabel();
		jComboBox9 = new javax.swing.JComboBox<>();
		jComboBox9.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jComboBox9Selected((Posicion) jComboBox9.getSelectedItem());
			}
		});
		jLabel26 = new javax.swing.JLabel();
		jTextField12 = new javax.swing.JTextField();
		jTextField12.setEnabled(false);
		jPanel2 = new javax.swing.JPanel();
		jComboBox1 = new javax.swing.JComboBox<>();
		jComboBox1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				itemSelectedJComboBox1((Equipo) jComboBox1.getSelectedItem());
			}
		});
		jLabel1 = new javax.swing.JLabel();
		jPanel8 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList2 = new javax.swing.JList<>();
		jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (jList2.getSelectedIndex() >= 0)
					selectedJList2((Jugador) jList2.getSelectedValue());
			}
		});
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		jButton5 = new javax.swing.JButton();
		jButton6 = new javax.swing.JButton();
		jButton6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					refrescarJCombo();
				} catch (SQLException e1) {
					notificaError(null, "ERROR", e1, "Ha ocurrido un error \n" + e1.getMessage());

				}
			}
		});
		jTextField9 = new javax.swing.JTextField();
		jComboBox6 = new javax.swing.JComboBox<>();
		jLabel23 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		jTextField11 = new javax.swing.JTextField();
		jLabel25 = new javax.swing.JLabel();
		jComboBox7 = new javax.swing.JComboBox<>();
		jButton10 = new javax.swing.JButton();
		jButton10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearJugador();
			}
		});
		jLabel22 = new javax.swing.JLabel();
		jTextField10 = new javax.swing.JTextField();
		jButton7 = new javax.swing.JButton();
		jButton8 = new javax.swing.JButton();
		jButton8.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					refrescarJCombo();
				} catch (SQLException e1) {
					notificaError(null, "ERROR", e1, "Ha ocurrido un error \n" + e1.getMessage());

				}
			}
		});
		jButton9 = new javax.swing.JButton();
		jButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList<>();
		jList1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (jList1.getSelectedIndex() >= 0)
					selectedJList1((Partido) jList1.getSelectedValue());
			}
		});
		jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList1.setEnabled(false);
		jLabel4 = new javax.swing.JLabel();
		jComboBox3 = new javax.swing.JComboBox<>();
		jComboBox3.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				competicionJComboBox3Selected((Competicion) jComboBox2.getSelectedItem());
			}
		});

		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jTextField4 = new javax.swing.JTextField();
		jTextField4.setEditable(false);
		jTextField4.setEnabled(false);
		jLabel13 = new javax.swing.JLabel();
		jTextField5 = new javax.swing.JTextField();
		jTextField5.setEditable(false);
		jTextField5.setEnabled(false);
		jComboBox4 = new javax.swing.JComboBox<>();
		jComboBox4.setEnabled(false);
		jComboBox4.setEditable(true);
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jTextField7 = new javax.swing.JTextField();
		jTextField7.setEnabled(false);
		jLabel16 = new javax.swing.JLabel();
		jTextField8 = new javax.swing.JTextField();
		jTextField8.setEnabled(false);
		jComboBox5 = new javax.swing.JComboBox<>();
		jComboBox5.setEnabled(false);
		jComboBox5.setEditable(true);
		jLabel17 = new javax.swing.JLabel();
		jCheckBox1 = new javax.swing.JCheckBox();
		jCheckBox1.setEnabled(false);
		jLabel18 = new javax.swing.JLabel();
		jButton11 = new javax.swing.JButton();
		jButton11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarEstadistica();
			}
		});
		jButton12 = new javax.swing.JButton();
		jButton12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					refrescarJCombo();
				} catch (SQLException e1) {
					MainGUI.notificaError(null, "ERROR", e1, "Ha ocurrido un error\n" + e1.getMessage());

				}
			}
		});
		jButton12.setEnabled(false);
		jDateChooser3 = new com.toedter.calendar.JDateChooser();
		jLabel2 = new javax.swing.JLabel();
		jButton13 = new javax.swing.JButton();
		jButton13.setEnabled(false);
		jPanel4 = new javax.swing.JPanel();
		jButton2 = new javax.swing.JButton();
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					generarListado((Competicion) comboBox_2.getSelectedItem());
				} catch (Exception e1212) {

				}
			}
		});

		jTextField6.setText("jTextField6");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(784, 576));
		setResizable(false);

		jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black));
		jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

		jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED,
				java.awt.Color.black, java.awt.Color.black, null, null));

		crearB.setText("Crear");
		crearB.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				crearBActionPerformed(evt);
			}
		});

		jButton1.setText("Descartar");

		jDateChooser1.setEnabled(false);

		jDateChooser2.setEnabled(false);

		jLabel3.setText("Fecha Inicio");

		jButton4.setText("Generar Partidos");

		jLabel5.setText("Nombre");

		jTextField1.setEnabled(false);

		jLabel6.setText("fecha Fin");

		jLabel7.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
		jLabel7.setText("COMPETICIONES");

		lblEquipos = new JLabel("Equipos");

		scrollPane = new JScrollPane();

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					guardarCompeticion();
				} catch (SQLException e1) {
					MainGUI.notificaError(null, "ERROR", e1, "Ha ocurrido un error\n" + e1.getMessage());

				}
			}

		});

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
						.addGroup(jPanel5Layout
								.createParallelGroup(Alignment.LEADING, false)
								.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(jButton4)
										.addGap(123).addComponent(crearB).addGap(81).addComponent(jButton1).addGap(18)
										.addComponent(btnGuardar))
								.addGroup(jPanel5Layout.createSequentialGroup().addGroup(jPanel5Layout
										.createParallelGroup(Alignment.TRAILING)
										.addGroup(jPanel5Layout.createSequentialGroup().addComponent(jLabel5)
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(
														jTextField1, GroupLayout.PREFERRED_SIZE, 207,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
												.addGroup(jPanel5Layout.createParallelGroup(Alignment.TRAILING)
														.addGroup(jPanel5Layout.createSequentialGroup()
																.addComponent(jLabel7, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.RELATED))
														.addGroup(jPanel5Layout.createSequentialGroup()
																.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE,
																		200, GroupLayout.PREFERRED_SIZE)
																.addGap(70)))
												.addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
														.addComponent(jDateChooser1, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel3, Alignment.TRAILING))))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(jPanel5Layout.createParallelGroup(Alignment.TRAILING)
												.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 194,
														Short.MAX_VALUE)
												.addGroup(jPanel5Layout.createSequentialGroup().addComponent(lblEquipos)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
																.addComponent(jLabel6).addComponent(jDateChooser2,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))))
										.addGap(421)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup().addGap(6)
						.addGroup(jPanel5Layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(jLabel3).addComponent(jLabel6))
										.addGap(3).addComponent(jDateChooser1, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 27,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(jComboBox2,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(
										jDateChooser2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEquipos))
						.addGroup(jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel5Layout
								.createSequentialGroup().addGap(36)
								.addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel5)
										.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(jPanel5Layout.createParallelGroup(Alignment.BASELINE).addComponent(crearB)
								.addComponent(jButton1).addComponent(jButton4).addComponent(btnGuardar))
						.addContainerGap()));

		list = new JList();
		list.setEnabled(false);
		scrollPane.setViewportView(list);
		jPanel5.setLayout(jPanel5Layout);

		jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED,
				java.awt.Color.black, java.awt.Color.black, null, null));

		crearB2.setText("Crear");
		crearB2.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				crearB2ActionPerformed(evt);
			}
		});

		jButton3.setText("Descartar");

		jLabel8.setFont(new java.awt.Font("Dialog", 0, 24));
		jLabel8.setText("POSICIONES");

		jLabel26.setText("Descripci贸n");

		jTextField12.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField12ActionPerformed(evt);
			}
		});

		button = new JButton("Guardar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!jTextField12.getText().equals("")) {
					Posicion p = new Posicion();
					p.setDescripcion(jTextField12.getText());
					if (CreacionGUI.validarPosicion.validate(p, true))
						try {
							con.insertOrUpdatePosicion(p);
						} catch (SQLException e1) {
							MainGUI.notificaError(null, "ERROR AL GUARDAR", e1,
									"Se ha producido un error\n" + e1.getMessage());
						}
				}
			}
		});

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel7Layout
				.createSequentialGroup()
				.addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(jLabel8,
								GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE))
						.addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel7Layout.createSequentialGroup().addGap(204).addComponent(crearB2)
										.addGap(71).addComponent(jButton3).addGap(18).addComponent(button,
												GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel7Layout.createSequentialGroup().addGap(62)
										.addComponent(
												jComboBox9, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
										.addGap(78)
										.addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING)
												.addComponent(jLabel26).addComponent(jTextField12,
														GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE))))
								.addGap(0, 101, Short.MAX_VALUE)))
				.addContainerGap()));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(jLabel8).addGap(29)
						.addGroup(jPanel7Layout.createParallelGroup(Alignment.LEADING)
								.addComponent(jComboBox9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel7Layout.createSequentialGroup().addComponent(jLabel26)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(jTextField12,
												GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent(crearB2)
								.addComponent(jButton3).addComponent(button))
						.addContainerGap()));
		jPanel7.setLayout(jPanel7Layout);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(jPanel7, Alignment.LEADING, 0, 0, Short.MAX_VALUE).addComponent(jPanel5,
										Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 748, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE).addGap(162)
						.addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(18, Short.MAX_VALUE)));
		jPanel1.setLayout(jPanel1Layout);

		jTabbedPane1.addTab("Gesti贸n", jPanel1);

		jLabel1.setText("Equipos");

		jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
				java.awt.Color.black, java.awt.Color.black, null, null));

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

		jLabel23.setText("Posici贸n");

		jLabel24.setText("Dorsal");

		jLabel25.setText("Equipo");

		jButton10.setText("Crear nuevo");

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel8Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel8Layout.createSequentialGroup()
								.addComponent(jLabel25, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
								.addGap(2)
								.addComponent(jComboBox7, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
						.addGroup(jPanel8Layout.createParallelGroup(Alignment.TRAILING, false).addComponent(jLabel20)
								.addComponent(jButton6)
								.addComponent(jTextField9, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel8Layout.createSequentialGroup()
										.addGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING)
												.addGroup(jPanel8Layout.createSequentialGroup().addComponent(jLabel24)
														.addGap(0, 28, Short.MAX_VALUE))
												.addComponent(jTextField11))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING)
												.addComponent(jLabel23).addComponent(jComboBox6,
														GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))))
						.addGroup(jPanel8Layout.createSequentialGroup().addComponent(jButton5).addGap(82)
								.addComponent(jButton10)))
				.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jLabel19, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
				.addGap(245)));
		jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel8Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel8Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel8Layout.createSequentialGroup().addComponent(jLabel19)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(jScrollPane2,
										GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								jPanel8Layout.createSequentialGroup().addComponent(jLabel20)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jTextField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addGroup(jPanel8Layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(jLabel23).addComponent(jLabel24))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(jPanel8Layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(jComboBox6, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(jTextField11, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(jPanel8Layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(jComboBox7, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel25))
										.addGap(32)
										.addGroup(jPanel8Layout.createParallelGroup(Alignment.BASELINE)
												.addComponent(jButton5).addComponent(jButton10))
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(jButton6)))
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

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
												.addComponent(jButton9))
										.addGroup(jPanel2Layout.createSequentialGroup()
												.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(2, 2, 2)
												.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 169,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jButton7)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jButton8)))
								.addGap(46, 46, 46))))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel22)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField10,
								javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(39, 39, 39)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(22, 22, 22)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel22).addComponent(jTextField10,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(jPanel2Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel1)))
								.addGroup(jPanel2Layout.createSequentialGroup().addGap(28, 28, 28)
										.addGroup(jPanel2Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButton7).addComponent(jButton8))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton9)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(25, 25, 25)));

		jTabbedPane1.addTab("Equipos", jPanel2);

		jScrollPane1.setEnabled(false);

		jScrollPane1.setViewportView(jList1);

		jLabel4.setText("Partidos");

		jLabel9.setText("Competici贸n");

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

		jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2" }));

		jLabel17.setText("Amarillas");

		jLabel18.setText("Roja");

		jButton11.setText("Guardar");

		jButton12.setText("DescarTar");

		jLabel2.setText("fecha");

		jButton13.setText("Nuevo");
		jButton13.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton13ActionPerformed(evt);
			}
		});

		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				seleccionarCombo3();
			}
		});

		comboBox = new JComboBox();

		comboBox_1 = new JComboBox();

		btnNewButton = new JButton("seleccionar");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				seleccionarcomboBox();
			}
		});

		button_1 = new JButton("seleccionar");
		button_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				seleccionarcomboBox_1();
			}
		});

		JButton btnSeleccionar_1 = new JButton("seleccionar");
		btnSeleccionar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarEstadisticasJugador((Jugador) jComboBox4.getSelectedItem(), (Partido) jList1.getSelectedValue());
			}
		});

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarPartido();
			}
		});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel9)
						.addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
										.addComponent(jLabel4)
										.addGroup(jPanel3Layout.createSequentialGroup()
												.addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, 239,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(
										jPanel3Layout.createSequentialGroup().addComponent(btnSeleccionar).addGap(23)))
								.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
										.createSequentialGroup()
										.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(
												jPanel3Layout.createSequentialGroup().addGap(29).addGroup(jPanel3Layout
														.createParallelGroup(Alignment.LEADING, false)
														.addGroup(jPanel3Layout.createSequentialGroup()
																.addComponent(jLabel11)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(comboBox_1, 0, GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
														.addGroup(jPanel3Layout.createSequentialGroup()
																.addComponent(jLabel10).addGap(38)
																.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 177,
																		GroupLayout.PREFERRED_SIZE)))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(jPanel3Layout
																.createParallelGroup(Alignment.LEADING, false)
																.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE,
																		GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(button_1, GroupLayout.DEFAULT_SIZE, 115,
																		Short.MAX_VALUE)))
												.addGroup(jPanel3Layout.createSequentialGroup().addGap(100)
														.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
																.addGroup(jPanel3Layout.createSequentialGroup()
																		.addComponent(jButton13).addGap(18)
																		.addComponent(btnBorrar))
																.addGroup(jPanel3Layout.createSequentialGroup()
																		.addComponent(jButton11).addGap(18)
																		.addComponent(jButton12)))))
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
										.addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout
												.createParallelGroup(Alignment.LEADING)
												.addGroup(jPanel3Layout.createSequentialGroup().addGap(38)
														.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
																.addComponent(jLabel14)
																.addGroup(jPanel3Layout.createSequentialGroup()
																		.addComponent(jComboBox4,
																				GroupLayout.PREFERRED_SIZE, 99,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(btnSeleccionar_1))
																.addGroup(jPanel3Layout.createSequentialGroup()
																		.addGap(70)
																		.addGroup(jPanel3Layout
																				.createParallelGroup(Alignment.LEADING)
																				.addComponent(jLabel17)
																				.addComponent(jLabel18)
																				.addComponent(jLabel16)
																				.addComponent(jLabel15)))))
												.addGroup(jPanel3Layout.createSequentialGroup().addGap(43)
														.addComponent(jLabel12).addGap(4)
														.addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, 39,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(jLabel13)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, 39,
																GroupLayout.PREFERRED_SIZE)))
												.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
												.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
														.addGroup(jPanel3Layout
																.createParallelGroup(Alignment.LEADING, false)
																.addComponent(jTextField7)
																.addComponent(jComboBox5, 0, 91, Short.MAX_VALUE)
																.addComponent(jTextField8).addComponent(jCheckBox1))
														.addGroup(jPanel3Layout.createSequentialGroup().addGap(48)
																.addGroup(jPanel3Layout
																		.createParallelGroup(Alignment.LEADING)
																		.addComponent(jLabel2)
																		.addComponent(jDateChooser3,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))))))))
				.addGap(169)));
		jPanel3Layout
				.setVerticalGroup(
						jPanel3Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(
										jPanel3Layout.createSequentialGroup().addGap(13).addComponent(jLabel9)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
														.addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel10)
														.addComponent(comboBox, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(btnNewButton))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(jPanel3Layout
														.createParallelGroup(Alignment.BASELINE).addComponent(jLabel11)
														.addComponent(btnSeleccionar)
														.addComponent(
																comboBox_1, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(button_1))
												.addGap(7).addComponent(jLabel4)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(
														jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout
																.createParallelGroup(Alignment.BASELINE)
																.addComponent(jLabel12)
																.addComponent(jTextField4, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(jLabel13).addComponent(
																		jTextField5, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
																.addGap(18)
																.addGroup(
																		jPanel3Layout.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(jLabel14)
																				.addComponent(jLabel2))
																.addGap(2)
																.addGroup(jPanel3Layout
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(jPanel3Layout
																				.createSequentialGroup()
																				.addGroup(jPanel3Layout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								jComboBox4,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(btnSeleccionar_1))
																				.addGap(18)
																				.addGroup(jPanel3Layout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(jPanel3Layout
																								.createSequentialGroup()
																								.addGroup(jPanel3Layout
																										.createParallelGroup(
																												Alignment.BASELINE)
																										.addComponent(
																												jTextField7,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addComponent(
																												jLabel15))
																								.addPreferredGap(
																										ComponentPlacement.RELATED)
																								.addComponent(jLabel16))
																						.addComponent(
																								jTextField8,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addGroup(jPanel3Layout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								jComboBox5,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(jLabel17))
																				.addGap(8)
																				.addGroup(jPanel3Layout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(jPanel3Layout
																								.createSequentialGroup()
																								.addComponent(jLabel18)
																								.addGap(61)
																								.addGroup(jPanel3Layout
																										.createParallelGroup(
																												Alignment.BASELINE)
																										.addComponent(
																												jButton11)
																										.addComponent(
																												jButton12))
																								.addGap(18)
																								.addGroup(jPanel3Layout
																										.createParallelGroup(
																												Alignment.BASELINE)
																										.addComponent(
																												jButton13)
																										.addComponent(
																												btnBorrar)))
																						.addComponent(jCheckBox1)))
																		.addComponent(jDateChooser3,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
														.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 355,
																GroupLayout.PREFERRED_SIZE))
												.addContainerGap(19, Short.MAX_VALUE)));
		jPanel3.setLayout(jPanel3Layout);

		jTabbedPane1.addTab("Partidos", jPanel3);

		jButton2.setText("Clasificaci贸n por Goles");

		comboBox_2 = new JComboBox();

		table = new JTable();
		table.setRowSelectionAllowed(false);

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				jPanel4Layout.createSequentialGroup().addGap(143).addComponent(comboBox_2, 0, 184, Short.MAX_VALUE)
						.addGap(18).addComponent(jButton2).addGap(213))
				.addGroup(jPanel4Layout.createSequentialGroup().addGap(89)
						.addComponent(table, GroupLayout.PREFERRED_SIZE, 503, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(160, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout
				.createSequentialGroup().addGap(45)
				.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent(jButton2).addComponent(
						comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(50).addComponent(table, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(87, Short.MAX_VALUE)));
		jPanel4.setLayout(jPanel4Layout);

		jTabbedPane1.addTab("Informes", jPanel4);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 761, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jTabbedPane1, GroupLayout.PREFERRED_SIZE, 518, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(58, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		pack();
	}

	protected void borrarPartido() {
		if ((JOptionPane.showConfirmDialog(this,
				"Esta operaci髇 es irreversible y podr韆 tener consecuencias irreparables. 緿ese continuar?") == JOptionPane.OK_OPTION))
			try {
				con.borrarPartido(jList1.getSelectedValue().getId());
			} catch (SQLException e) {
				MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());

			}
	}

	int golesUlt = 0;

	protected void guardarEstadistica() {
		Partido partido = jList1.getSelectedValue();
		if (partido == null)
			return;
		Equipo local = partido.getEquipoByIdLocal();
		Equipo visitante = partido.getEquipoByIdVisitante();
		int golesLocal = partido.getGolesLocal();
		int golesVisitante = partido.getGolesVisitante();
		Date fecha = jDateChooser3.getDate();

		if (fecha == null)
			return;
		if (jTextField7.isEnabled()) {
			try {
				Jugador j = ((Jugador) jComboBox4.getSelectedItem());
				Estadistica stat = con.getEstadisticaById(new EstadisticaId(partido.getId(), j.getLicencia()));

				if (j.getEquipo().equals(local))
					partido.setGolesLocal(Integer.parseInt(jTextField7.getText()));
				else
					partido.setGolesVisitante(Integer.parseInt(jTextField7.getText()));
				stat.setFaltas(Integer.parseInt(jTextField8.getText()));
				stat.setGoles(Integer.parseInt(jTextField7.getText()));
				stat.setTarjAmarillas(Integer.parseInt((String) jComboBox5.getSelectedItem()));
				stat.setTarjRojas(jCheckBox1.isSelected() ? 0 : 1);

				con.insertOrUpdateEstadistica(stat);
			} catch (Exception e) {
				MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());
				partido.setEquipoByIdLocal(local);
				partido.setEquipoByIdVisitante(visitante);
				partido.setGolesLocal(golesLocal);
				partido.setGolesVisitante(golesVisitante);
				partido.setFechaHora(fecha);
				return;
			}
		}
		if (CreacionGUI.validarPartido.validate(partido, true)) {
			try {
				con.insertOrUpdatePartido(partido);
			} catch (SQLException e) {
				MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());

			}
		} else {
			partido.setEquipoByIdLocal(local);
			partido.setEquipoByIdVisitante(visitante);
			partido.setFechaHora(fecha);
			partido.setGolesLocal(golesLocal);
			partido.setGolesVisitante(golesVisitante);
			MainGUI.notificaError(this, "ERROR", null, "Datos inv醠idos");
		}

	}

	protected void cargarEstadisticasJugador(Jugador selectedItem, Partido selectedValue) {
		try {
			Estadistica stat = con
					.getEstadisticaById(new EstadisticaId(selectedValue.getId(), selectedItem.getLicencia()));
			jTextField7.setEnabled(true);
			jTextField8.setEnabled(true);
			jComboBox5.setEnabled(true);
			jCheckBox1.setEnabled(true);
			if (stat != null) {
				golesUlt = stat.getGoles();
				jTextField7.setText(stat.getGoles() + "");
				jTextField8.setText(stat.getFaltas() + "");
				jComboBox5.setSelectedIndex(stat.getTarjAmarillas());
				jCheckBox1.setSelected(stat.getTarjRojas() != 0);
			} else {
				golesUlt = 0;
				jComboBox5.setSelectedIndex(0);
			}

		} catch (SQLException e) {
			MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());

		}
	}

	protected void jTextField8ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void jTextField12ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void generarListado(Competicion selectedItem) {
		Map<Equipo, Integer> mapa = Utilidades.generarTablaCompeticion(selectedItem);
		List<Entry<Equipo, Integer>> entrySet = new ArrayList<>(mapa.entrySet());
		Collections.sort(entrySet, Entry.comparingByValue());
		Object[][] data = new Object[entrySet.size()][2];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = entrySet.get(i).getKey();
			data[i][1] = entrySet.get(i).getValue();
		}
		DefaultTableModel model = new DefaultTableModel(data, new Object[] { "Equipo", "Puntos" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);

	}

	protected void crearJugador() {
		new CreacionGUI<>(this, Jugador.class).setVisible(true);
		this.setEnabled(false);

	}

	protected void seleccionarcomboBox_1() {
		try {
			Set<Jugador> lj = con.getJugadoresEquipo((Equipo) comboBox.getSelectedItem());
			lj.addAll(con.getJugadoresEquipo((Equipo) comboBox_1.getSelectedItem()));
			DefaultComboBoxModel<Jugador> model = new DefaultComboBoxModel<Jugador>();
			model.addAll(lj);
			jComboBox4.setModel(model);
		} catch (SQLException e) {
			notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());
		}
	}

	protected void seleccionarcomboBox() {
		try {
			Set<Jugador> lj = con.getJugadoresEquipo((Equipo) comboBox.getSelectedItem());
			lj.addAll(con.getJugadoresEquipo((Equipo) comboBox_1.getSelectedItem()));
			DefaultComboBoxModel<Jugador> model = new DefaultComboBoxModel<Jugador>();
			model.addAll(lj);
			jComboBox4.setModel(model);
		} catch (SQLException e) {
			notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());
		}

	}

	@SuppressWarnings(value = "all")
	protected void selectedJList1(Partido selectedValue) {
		if (selectedValue != null) {
			DefaultComboBoxModel<Equipo> model = new DefaultComboBoxModel<Equipo>() {

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
			};
			model.addElement(selectedValue.getEquipoByIdLocal());
			try {
				con.getListaEquipos().stream().filter((e) -> {
					return !e.equals(selectedValue.getEquipoByIdLocal());
				}).forEach((e) -> {
					model.addElement(e);
				});
			} catch (SQLException e) {
				notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());

			}
			comboBox.setModel(model);
			DefaultComboBoxModel<Equipo> model2 = new DefaultComboBoxModel<Equipo>() {

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
			};
			model2.addElement(selectedValue.getEquipoByIdVisitante());
			try {
				con.getListaEquipos().stream().filter((e) -> {
					return !e.equals(selectedValue.getEquipoByIdVisitante());
				}).forEach((e) -> {
					model2.addElement(e);
				});
			} catch (SQLException e) {
				notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());

			}
			comboBox_1.setModel(model2);

			DefaultComboBoxModel<Jugador> modelj = new DefaultComboBoxModel<Jugador>() {

				boolean selectionAllowed = true;

				@Override
				public void setSelectedItem(Object anObject) {
					if (!anObject.equals(jugadorNoSeleccionable)) {
						super.setSelectedItem(anObject);
					} else if (selectionAllowed) {
						selectionAllowed = false;
						super.setSelectedItem(anObject);
					}
				}
			};

			selectedValue.getEquipoByIdLocal().getJugadors().stream().forEach((j) -> {
				modelj.addElement((Jugador) j);
			});
			selectedValue.getEquipoByIdVisitante().getJugadors().stream().forEach((j) -> {
				modelj.addElement((Jugador) j);
			});

			comboBox.setEnabled(true);
			comboBox_1.setEnabled(true);
			jTextField4.setEnabled(true);
			jTextField5.setEnabled(true);
			jComboBox4.setEnabled(true);
			jDateChooser3.setEnabled(true);
		}
	}

	@SuppressWarnings(value = "all")
	protected void seleccionarCombo3() {
		Competicion compe = (Competicion) jComboBox3.getSelectedItem();
		if (compe.getNombre().equals("-Elige una Competici贸n-")) {
			System.out.println("NO ELEGIBLE");
			jList1.setEnabled(false);
			comboBox.setEnabled(false);
			comboBox_1.setEnabled(false);
			jTextField4.setEnabled(false);
			jTextField5.setEnabled(false);
			jComboBox4.setEnabled(false);
			jTextField7.setEnabled(false);
			jTextField8.setEnabled(false);
			jComboBox5.setEnabled(false);
			jCheckBox1.setEnabled(false);
			jButton12.setEnabled(false);
			jDateChooser3.setEnabled(false);
			jButton13.setEnabled(false);
		} else {
			System.out.println("SI ELEGIBLE");
			DefaultListModel<Partido> model = new DefaultListModel<Partido>();
			System.out.println("Numero Partidos:" + compe.getPartidos().size());
			compe.getPartidos().stream().forEach((e) -> {
				System.out.println("Partido " + e);
				model.addElement((Partido) e);
			});
			jList1.setModel(model);

			jList1.setEnabled(true);
			jButton13.setEnabled(true);
		}

	}

	protected void jComboBox9Selected(Posicion selectedItem) {
		if (selectedItem.equals(posicionNoSeleccionable)) {
			jButton3.setEnabled(false);
			button.setEnabled(false);
			jTextField12.setEnabled(false);
		} else {
			crearB2.setEnabled(true);
			jButton3.setEnabled(true);
			button.setEnabled(true);
			jTextField12.setEnabled(true);
			jTextField12.setText(selectedItem.getDescripcion());
		}

	}

	protected void selectedJList2(Jugador selectedValue) {
		if (selectedValue != null) {
			jButton10.setEnabled(true);
			jButton6.setEnabled(true);
			jButton5.setEnabled(true);
			jTextField9.setEnabled(true);
			jTextField11.setEnabled(true);
			jComboBox6.setEnabled(true);
			jComboBox7.setEnabled(true);

			System.out.println("DORSAL: " + selectedValue.getDorsal());

			jTextField9.setText(selectedValue.getNombre());
			jTextField11.setText(selectedValue.getDorsal() + "");
			DefaultComboBoxModel<Equipo> model = new DefaultComboBoxModel<Equipo>() {

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
			};
			model.addElement(selectedValue.getEquipo());
			try {
				con.getListaEquipos().stream().filter((e) -> {
					return !e.equals(selectedValue.getEquipo());
				}).forEach((e) -> {
					model.addElement(e);
				});
			} catch (SQLException e) {
				notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());

			}
			jComboBox7.setModel(model);
			DefaultComboBoxModel<Posicion> modelp = new DefaultComboBoxModel<Posicion>() {

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
			};

			try {
				con.getPosiciones().stream().forEach((p) -> {
					modelp.addElement(p);
				});
				modelp.setSelectedItem(selectedValue.getPosicion());
			} catch (SQLException e) {
				notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());

			}
			jComboBox6.setModel(modelp);
		}

	}

	@SuppressWarnings(value = "all")
	protected void itemSelectedJComboBox1(Equipo selectedItem) {
		if (selectedItem.equals(equipoNoSeleccionable)) {
			jTextField10.setEnabled(false);
			jButton7.setEnabled(false);
			jButton8.setEnabled(false);
			jButton10.setEnabled(false);
			jButton6.setEnabled(false);
			jList2.setEnabled(false);
			jTextField9.setEnabled(false);
			jTextField11.setEnabled(false);
			jComboBox6.setEnabled(false);
			jComboBox7.setEnabled(false);
		} else {
			jTextField10.setEnabled(true);
			jButton7.setEnabled(true);
			jButton8.setEnabled(true);
			jButton9.setEnabled(true);
			jList2.setEnabled(true);
			jTextField9.setEnabled(false);
			jTextField11.setEnabled(false);
			jComboBox6.setEnabled(false);
			jComboBox7.setEnabled(false);
			jButton10.setEnabled(false);
			jButton6.setEnabled(false);
			jButton5.setEnabled(false);
			jTextField10.setText(selectedItem.getNombre());
			DefaultListModel<Jugador> model = new DefaultListModel<Jugador>();

			selectedItem.getJugadors().stream().forEach((j) -> {
				model.addElement((Jugador) j);
			});
			jList2.setModel(model);
			jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

	}

	protected void guardarCompeticion() throws SQLException {
		String nombre = ((Competicion) jComboBox2.getSelectedItem()).getNombre();
		Date fechaIni = ((Competicion) jComboBox2.getSelectedItem()).getFechaComienzo();
		Date fechaFin = ((Competicion) jComboBox2.getSelectedItem()).getFechaFin();
		if (jTextField1.getText().equals("") || jDateChooser1.getDate() == null || jDateChooser2.getDate() == null) {
			notificaError(this, "ERROR", null, "Datos de la competici髇 inv醠idos");
			return;
		}
		Competicion compe = ((Competicion) jComboBox2.getSelectedItem());
		compe.setNombre(jTextField1.getText());
		compe.setFechaComienzo(jDateChooser1.getDate());
		compe.setFechaFin(jDateChooser2.getDate());
		if (CreacionGUI.validarCompeticion.validate(compe, true)) {
			con.insertOrUpdateCompeticion(compe);
			notifica(this, "Competici贸n Actualizada", null, "Se ha actualizado la Competici贸n",
					JOptionPane.INFORMATION_MESSAGE);

		} else {
			compe.setFechaComienzo(fechaIni);
			compe.setFechaFin(fechaFin);
			compe.setNombre(nombre);
			notificaError(this, "ERROR AL INSERTAR", null, "Datos incorrectos");
		}
	}

	@SuppressWarnings(value = "all")
	protected void competicionJComboBox3Selected(Competicion selectedItem) {
		System.out.println(selectedItem.getNombre());
		if (selectedItem.getNombre().equals("-Elige una Competici贸n-")) {
			System.out.println("NO ELEGIBLE");
			jList1.setEnabled(false);
			comboBox.setEnabled(false);
			comboBox_1.setEnabled(false);
			jTextField4.setEnabled(false);
			jTextField5.setEnabled(false);
			jComboBox4.setEnabled(false);
			jTextField7.setEnabled(false);
			jTextField8.setEnabled(false);
			jComboBox5.setEnabled(false);
			jCheckBox1.setEnabled(false);
			jButton11.setEnabled(false);
			jButton12.setEnabled(false);
			jDateChooser3.setEnabled(false);
			jButton13.setEnabled(false);
		} else {
			System.out.println("SI ELEGIBLE");
			DefaultListModel<Partido> model = new DefaultListModel<Partido>();
			System.out.println("Numero Partidos:" + selectedItem.getPartidos().size());
			selectedItem.getPartidos().stream().forEach((e) -> {
				System.out.println("Partido " + e);
				model.addElement((Partido) e);
			});
			jList1.setModel(model);

			jList1.setEnabled(true);
		}

	}

	protected void restaurarCompeticion(Competicion selectedItem) {
		try {
			refrescarJCombo();
		} catch (SQLException e) {
			MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());

		}

	}

	protected void competicionJComboBox2Selected(Competicion selectedItem) {
		if (selectedItem.equals(competicionNoSeleccionable)) {
			jDateChooser1.setEnabled(false);
			jDateChooser2.setEnabled(false);
			list.setEnabled(false);
			jTextField1.setEnabled(false);
		} else {
			DefaultListModel<Equipo> model = new DefaultListModel<>();
			try {
				con.getListaEquipos().stream().forEach((e) -> {
					model.addElement(e);
				});
				list.setModel(model);
			} catch (SQLException e) {

				e.printStackTrace();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(selectedItem.getFechaComienzo());
			jDateChooser1.setCalendar(c);
			jDateChooser2.setEnabled(true);
			c.setTime(selectedItem.getFechaFin());
			jDateChooser2.setCalendar(c);
			jDateChooser1.setEnabled(true);

			list.setEnabled(true);
			jTextField1.setText(selectedItem.getNombre());
			jTextField1.setEnabled(true);

		}
	}

	protected void generarPartido(Object selectedItem) {
		if (((Competicion) selectedItem).equals(competicionNoSeleccionable))
			return;
		Competicion compe = (Competicion) selectedItem;
		if (compe.getPartidos().size() == 0 && list.getSelectedValues().length > 0) {
			System.out.println(list.getSelectedValuesList().size());
			List<Partido> lp = Utilidades.generarCompeticion(list.getSelectedValuesList(), 24, compe);
			if (notifica(this, "AVISO", null,
					"Es posible que, debido a las restricciones, no se hayan generado todos los partidos.",
					JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION) {
				lp.stream().forEach((p) -> {
					System.out.println(p);
				});
				compe.setPartidos(new HashSet<>(lp));
				System.out.println(compe.getPartidos().size());
				try {
					con.insertOrUpdateCompeticion(compe);
				} catch (SQLException e) {
					notificaError(this, "ERROR", e, "Ha ocurrido un error \n" + e.getMessage());

				}
			} else {
				return;
			}
		}
	}

	private void crearBActionPerformed(java.awt.event.ActionEvent evt) {
		new CreacionGUI<>(this, Competicion.class).setVisible(true);
		this.setEnabled(false);
	}

	private void crearB2ActionPerformed(java.awt.event.ActionEvent evt) {
		new CreacionGUI<>(this, Posicion.class).setVisible(true);
		this.setEnabled(false);
	}

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jTextField9.getText().equals("") || jTextField11.getText().equals("")
				|| jTextField11.getText().matches("[0-9]+")
				|| ((Equipo) jComboBox7.getSelectedItem()).equals(equipoNoSeleccionable)) {
			notificaError(this, "ERROR", null, "Campos Vac韔s");
			return;
		}
		Jugador ori = new Jugador();

		Jugador j = jList2.getSelectedValue();

		ori.setNombre(j.getNombre());
		ori.setDorsal(j.getDorsal());
		ori.setPosicion(j.getPosicion());
		ori.setEquipo(j.getEquipo());

		j.setDorsal(Integer.parseInt(jTextField11.getText()));
		j.setNombre(jTextField9.getText());
		j.setPosicion((Posicion) jComboBox6.getSelectedItem());
		j.setEquipo((Equipo) jComboBox7.getSelectedItem());

		if (CreacionGUI.validarJugador.validate(j, true)) {
			try {
				con.insertOrUpdateJugador(j);
			} catch (SQLException e) {
				MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());

			}
			((Equipo) jComboBox1.getSelectedItem()).getJugadors().remove(j);
			j.getEquipo().getJugadors().add(j);
			try {
				refrescarJCombo();
			} catch (SQLException e) {
				MainGUI.notificaError(this, "ERROR", e, "Ha ocurrido un error\n" + e.getMessage());

			}
		} else {
			j.setDorsal(ori.getDorsal());
			j.setNombre(ori.getNombre());
			j.setEquipo(ori.getEquipo());
			j.setPosicion(ori.getPosicion());
		}

	}

	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jTextField10.getText().equals("")) {
			notificaError(this, "ERROR", null, "Datos inv醠idos");
			return;
		}
		Equipo e = (Equipo) jComboBox1.getSelectedItem();
		e.setNombre(jTextField10.getText());
		try {
			con.insertOrUpdateEquipo(e);
		} catch (SQLException e1) {
			MainGUI.notificaError(this, "ERROR", e1, "Ha ocurrido un error\n" + e1.getMessage());

		}
	}

	private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
		new CreacionGUI<>(this, Partido.class).setVisible(true);
		this.setEnabled(false);
	}

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
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					new MainGUI().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings(value = "all")
	public void refrescarJCombo() throws SQLException {
		// DECLARACI脫N DE COMBOS
		comboBox_2.setModel(new DefaultComboBoxModel<Competicion>() {

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
		comboBox_2.addItem(competicionNoSeleccionable);
		jComboBox2.addItem(competicionNoSeleccionable);
		jComboBox3.addItem(competicionNoSeleccionable);
		con.getCompeticiones().stream().forEach((c) -> {
			jComboBox2.addItem(c);
			jComboBox3.addItem(c);
			comboBox_2.addItem(c);
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
		jComboBox1.addItem(equipoNoSeleccionable);
		con.getListaEquipos().stream().forEach((e) -> {
			jComboBox1.addItem(e);
		});

		jComboBox9.setModel(new DefaultComboBoxModel<Posicion>() {

			boolean selectionAllowed = true;

			@Override
			public void setSelectedItem(Object anObject) {
				if (!anObject.equals(posicionNoSeleccionable)) {
					super.setSelectedItem(anObject);
				} else if (selectionAllowed) {
					selectionAllowed = false;
					super.setSelectedItem(anObject);
				}
			}
		});

		jComboBox9.addItem(posicionNoSeleccionable);
		con.getPosiciones().stream().forEach((p) -> {
			jComboBox9.addItem(p);
		});
		// FIN COMBOS
		crearB2.setEnabled(true);
		jButton5.setEnabled(true);
		jButton9.setEnabled(true);
		jButton13.setEnabled(true);
	}

	private javax.swing.JButton crearB;

	private javax.swing.JButton crearB2;

	private javax.swing.JButton jButton1;

	private javax.swing.JButton jButton10;

	private javax.swing.JButton jButton11;

	private javax.swing.JButton jButton12;

	private javax.swing.JButton jButton13;

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

	private javax.swing.JList<Partido> jList1;

	private javax.swing.JList<Jugador> jList2;

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

	private javax.swing.JTextField jTextField4;

	private javax.swing.JTextField jTextField5;

	private javax.swing.JTextField jTextField6;

	private javax.swing.JTextField jTextField7;

	private javax.swing.JTextField jTextField8;

	private javax.swing.JTextField jTextField9;

	private JLabel lblEquipos;

	private JScrollPane scrollPane;

	private JList<Equipo> list;

	private JButton button;

	private JButton btnSeleccionar;

	private JComboBox<Equipo> comboBox;

	private JComboBox<Equipo> comboBox_1;

	private JButton btnNewButton;

	private JButton button_1;

	private JComboBox comboBox_2;

	private JTable table;

	public JPanel getJPanel4() {
		return jPanel4;
	}

	public JButton getJButton2() {
		return jButton2;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JButton getButton() {
		return button;
	}

	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}

	public JComboBox getComboBox() {
		return comboBox;
	}

	public JComboBox getComboBox_1() {
		return comboBox_1;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public JButton getButton_1() {
		return button_1;
	}

	public JComboBox<Competicion> getComboBox_2() {
		return comboBox_2;
	}

	public JTable getTable() {
		return table;
	}
}
