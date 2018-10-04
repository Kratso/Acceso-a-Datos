package raf;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.print.attribute.standard.JobKOctetsProcessed;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.awt.event.ActionEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class RandomAccessFilePruebador extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	private RandomAccessFile raf;
	private String aCambiar;
	private JButton btnCambiar;
	private int cnt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RandomAccessFilePruebador frame = new RandomAccessFilePruebador();
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
	public RandomAccessFilePruebador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSeleccionarTexto = new JButton("Seleccionar Texto");
		btnSeleccionarTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					seleccionarArchivo();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSeleccionarTexto.setBounds(111, 29, 218, 31);
		contentPane.add(btnSeleccionarTexto);

		textField = new JTextField();
		textField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if (textField.getText().length() >= 1) {
					btnCambiar.setEnabled(true);
					aCambiar = textField.getText();
				} else
					btnCambiar.setEnabled(false);
			}
		});
		textField.setBounds(181, 100, 206, 23);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblCadenaAConvertir = new JLabel("Cadena a convertir");
		lblCadenaAConvertir.setBounds(6, 104, 163, 15);
		contentPane.add(lblCadenaAConvertir);

		btnCambiar = new JButton("CONVERTIR");
		btnCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					convertir();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCambiar.setEnabled(false);
		btnCambiar.setBounds(111, 186, 232, 31);
		contentPane.add(btnCambiar);
	}

	protected void convertir() throws IOException {

		String linea;
		long pos = 0;
		while ((linea = raf.readLine()) != null) {
			int tempcnt = cnt;
			while (linea.contains(aCambiar)) {
				linea = linea.replace(aCambiar, aCambiar.toUpperCase());
				cnt++;
			}
			linea = linea + "\n";
			raf.seek(pos);
			pos += linea.length();
			if (cnt != tempcnt)
				raf.writeBytes(linea);
			else
				raf.seek(pos);

		}

		raf.close();

		JOptionPane.showMessageDialog(this, "Se han cambiado " + cnt + " instancias", "Cambio realizado",
				JOptionPane.INFORMATION_MESSAGE);
		cnt = 0;
		btnCambiar.setEnabled(false);
		textField.setText(null);
	}

	protected void seleccionarArchivo() throws FileNotFoundException {
		JFileChooser jfc = new JFileChooser("/home/alumno");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int salida = jfc.showOpenDialog(null);
		if (salida == JFileChooser.APPROVE_OPTION) {
			raf = new RandomAccessFile(jfc.getSelectedFile(), "rw");
		}

	}

	public JTextField getTextField() {
		return textField;
	}

	public JButton getBtnCambiar() {
		return btnCambiar;
	}
}
