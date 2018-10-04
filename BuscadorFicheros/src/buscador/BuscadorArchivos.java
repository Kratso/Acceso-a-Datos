package buscador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utilidades.Utilidades;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class BuscadorArchivos extends JFrame {

	private JPanel contentPane;
	private JTextField pathText;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextArea textArea;
	private JRadioButton rdbtnMayor;
	private JCheckBox chckbxOcultos;
	private JCheckBox chckbxIncluirSubcarpetas;
	private File file;
	private JButton btnBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuscadorArchivos frame = new BuscadorArchivos();
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
	public BuscadorArchivos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton seleccionCarpeta = new JButton("Seleccionar Carpeta");
		seleccionCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser("/home/alumno");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int opcion = jfc.showOpenDialog(null);
				if (opcion == JFileChooser.APPROVE_OPTION) {
					file = jfc.getSelectedFile();
					if (file.isDirectory()) {
						pathText.setText(file.getAbsolutePath());
						btnBuscar.setEnabled(true);
					} else {
						Utilidades.MostrarMensajeError(null, "Error al elegir archivo", null,
								"No ha elegido un directorio válido");
					}
				}

			}
		});
		seleccionCarpeta.setBounds(0, 6, 187, 27);
		contentPane.add(seleccionCarpeta);

		pathText = new JTextField();
		pathText.setEditable(false);
		pathText.setBounds(206, 6, 378, 27);
		contentPane.add(pathText);
		pathText.setColumns(10);

		JLabel lblTamao = new JLabel("Tamaño en bytes");
		lblTamao.setBounds(6, 59, 123, 15);
		contentPane.add(lblTamao);

		textField = new JTextField();
		textField.setBounds(130, 53, 100, 27);
		contentPane.add(textField);
		textField.setColumns(10);

		JRadioButton rdbtnMenor = new JRadioButton("Menor");
		buttonGroup.add(rdbtnMenor);
		rdbtnMenor.setBounds(242, 87, 77, 18);
		contentPane.add(rdbtnMenor);

		rdbtnMayor = new JRadioButton("Mayor");
		buttonGroup.add(rdbtnMayor);
		rdbtnMayor.setBounds(242, 57, 77, 18);
		contentPane.add(rdbtnMayor);
		rdbtnMayor.setSelected(true);

		chckbxOcultos = new JCheckBox("Mostrar Ocultos");
		chckbxOcultos.setBounds(331, 57, 150, 18);
		contentPane.add(chckbxOcultos);

		chckbxIncluirSubcarpetas = new JCheckBox("Incluir Subcarpetas");
		chckbxIncluirSubcarpetas.setBounds(331, 87, 248, 18);
		contentPane.add(chckbxIncluirSubcarpetas);

		btnBuscar = new JButton("BUSCAR");
		btnBuscar.setEnabled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().matches("[0-9]+")) {
					char mayorOMenor;
					if (rdbtnMayor.isSelected())
						mayorOMenor = '+';
					else
						mayorOMenor = '-';
					try {
						int tamanio = Integer.parseInt(textField.getText());
						ArrayList<File> encuentros = Utilidades.buscarArchivosPorTamanio(file, tamanio, mayorOMenor,
								chckbxOcultos.isSelected(), chckbxIncluirSubcarpetas.isSelected());
						String texto = "";
						for (File f : encuentros) {
							texto += f.getPath() + "(" + f.length() + " bytes)\n";
						}
						textArea.setText(texto);
					} catch (NumberFormatException nbe) {
						JOptionPane.showMessageDialog(null,
								"Tamaño no válido\n\n\n" + nbe.getClass() + " " + nbe.getMessage());
					}
				}
			}
		});
		btnBuscar.setBounds(484, 59, 100, 27);
		contentPane.add(btnBuscar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 135, 589, 159);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JRadioButton getRdbtnMayor() {
		return rdbtnMayor;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JTextField getPathText() {
		return pathText;
	}

	public JCheckBox getChckbxOcultos() {
		return chckbxOcultos;
	}

	public JCheckBox getChckbxIncluirSubcarpetas() {
		return chckbxIncluirSubcarpetas;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}
}
