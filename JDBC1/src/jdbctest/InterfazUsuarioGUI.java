package jdbctest;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazUsuarioGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnConectar;
	private JButton btnMostrarPersonas;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnNewButton;
	private JButton btnEliminarPersona;
	private JButton btnAadirPersona;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnOrdenarPorPas;
	private JRadioButton rdbtnOrdenarPorMail;
	private JRadioButton rdbtnOrdenarPorNombre;
	private JRadioButton rdbtnSinOrden;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazUsuarioGUI frame = new InterfazUsuarioGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazUsuarioGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pbd = new PersistenciaBD("data/cfg.ini");
			}
		});
		btnConectar.setBounds(148, 203, 117, 25);
		contentPane.add(btnConectar);
		
		btnMostrarPersonas = new JButton("Mostrar Personas");
		btnMostrarPersonas.setEnabled(false);
		btnMostrarPersonas.setBounds(37, 26, 173, 25);
		contentPane.add(btnMostrarPersonas);
		
		rdbtnNewRadioButton = new JRadioButton("Ordenar por CP");
		rdbtnNewRadioButton.setEnabled(false);
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setActionCommand("Ordenar por CP");
		rdbtnNewRadioButton.setBounds(238, 27, 149, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		rdbtnOrdenarPorMail = new JRadioButton("Ordenar por Mail");
		rdbtnOrdenarPorMail.setEnabled(false);
		buttonGroup.add(rdbtnOrdenarPorMail);
		rdbtnOrdenarPorMail.setBounds(238, 56, 149, 23);
		contentPane.add(rdbtnOrdenarPorMail);
		
		rdbtnOrdenarPorNombre = new JRadioButton("Ordenar por Nombre");
		rdbtnOrdenarPorNombre.setEnabled(false);
		buttonGroup.add(rdbtnOrdenarPorNombre);
		rdbtnOrdenarPorNombre.setBounds(238, 83, 202, 23);
		contentPane.add(rdbtnOrdenarPorNombre);
		
		rdbtnOrdenarPorPas = new JRadioButton("Ordenar por País");
		rdbtnOrdenarPorPas.setEnabled(false);
		buttonGroup.add(rdbtnOrdenarPorPas);
		rdbtnOrdenarPorPas.setBounds(238, 110, 149, 23);
		contentPane.add(rdbtnOrdenarPorPas);
		
		rdbtnSinOrden = new JRadioButton("Sin orden");
		rdbtnSinOrden.setEnabled(false);
		buttonGroup.add(rdbtnSinOrden);
		rdbtnSinOrden.setBounds(238, 137, 149, 23);
		contentPane.add(rdbtnSinOrden);
		
		btnAadirPersona = new JButton("Añadir Persona");
		btnAadirPersona.setEnabled(false);
		btnAadirPersona.setBounds(37, 63, 179, 25);
		contentPane.add(btnAadirPersona);
		
		btnNewButton = new JButton("Modificar Persona");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(37, 109, 179, 25);
		contentPane.add(btnNewButton);
		
		btnEliminarPersona = new JButton("Eliminar Persona");
		btnEliminarPersona.setEnabled(false);
		btnEliminarPersona.setBounds(37, 146, 179, 25);
		contentPane.add(btnEliminarPersona);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setVisible(false);
		lblNewLabel.setBounds(0, 31, 70, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setVisible(false);
		textField.setBounds(146, 29, 294, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPais = new JLabel("Pais");
		lblPais.setVisible(false);
		lblPais.setBounds(0, 63, 70, 15);
		contentPane.add(lblPais);
		
		textField_1 = new JTextField();
		textField_1.setVisible(false);
		textField_1.setBounds(116, 63, 324, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setVisible(false);
		lblEmail.setBounds(0, 100, 70, 15);
		contentPane.add(lblEmail);
		
		textField_2 = new JTextField();
		textField_2.setVisible(false);
		textField_2.setBounds(96, 98, 344, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblCp = new JLabel("CP");
		lblCp.setVisible(false);
		lblCp.setBounds(0, 141, 70, 15);
		contentPane.add(lblCp);
		
		textField_3 = new JTextField();
		textField_3.setVisible(false);
		textField_3.setBounds(96, 146, 344, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setVisible(false);
		btnModificar.setBounds(12, 203, 117, 25);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setVisible(false);
		btnEliminar.setBounds(277, 203, 117, 25);
		contentPane.add(btnEliminar);
		
		JButton btnAadir = new JButton("Añadir");
		btnAadir.setVisible(false);
		btnAadir.setBounds(12, 240, 117, 25);
		contentPane.add(btnAadir);
		
		JButton button = new JButton("<<");
		button.setVisible(false);
		button.setBounds(148, 240, 62, 25);
		contentPane.add(button);
		
		JButton button_1 = new JButton("<");
		button_1.setVisible(false);
		button_1.setBounds(215, 240, 50, 25);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton(">");
		button_2.setVisible(false);
		button_2.setBounds(277, 240, 50, 25);
		contentPane.add(button_2);
		
		JButton btnNewButton_1 = new JButton(">>");
		btnNewButton_1.setVisible(false);
		btnNewButton_1.setBounds(334, 240, 70, 25);
		contentPane.add(btnNewButton_1);
	}
	
	public PersistenciaBD pbd;
	
	public JButton getBtnConectar() {
		return btnConectar;
	}
	public JButton getBtnMostrarPersonas() {
		return btnMostrarPersonas;
	}
	public JTextField getTextField_1() {
		return textField_1;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}
	public JButton getBtnEliminarPersona() {
		return btnEliminarPersona;
	}
	public JButton getBtnAadirPersona() {
		return btnAadirPersona;
	}
	public JRadioButton getRdbtnNewRadioButton() {
		return rdbtnNewRadioButton;
	}
	public JRadioButton getRdbtnOrdenarPorPas() {
		return rdbtnOrdenarPorPas;
	}
	public JRadioButton getRdbtnOrdenarPorMail() {
		return rdbtnOrdenarPorMail;
	}
	public JRadioButton getRdbtnOrdenarPorNombre() {
		return rdbtnOrdenarPorNombre;
	}
	public JRadioButton getRdbtnSinOrden() {
		return rdbtnSinOrden;
	}
}
