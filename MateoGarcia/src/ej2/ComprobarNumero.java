package ej2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ComprobarNumero extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComprobarNumero frame = new ComprobarNumero();
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
	public ComprobarNumero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(114, 33, 159, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setBounds(12, 35, 70, 15);
		contentPane.add(lblNumero);
		
		JButton btnComprobar = new JButton("Comprobar");
		btnComprobar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comprobar(textField.getText());
			}
		});
		btnComprobar.setBounds(298, 30, 117, 25);
		contentPane.add(btnComprobar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 76, 372, 174);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}
	protected void comprobar(String text) {
		try {
			ConexionBD.conectar();
			ManipuladorXML.inicializar();
			int numero = Integer.parseInt(text);
			if(ConexionBD.existe(numero)) {
				textArea.setText(textArea.getText() + "\n " + ConexionBD.premio(numero));
			} else {
				if(ConexionBD.existeAdyacente(numero)) {
					
				} else
					textArea.setText(textArea.getText() + "\n " + numero + ": No premiado");
					ManipuladorXML.poner(numero);
			}
			ManipuladorXML.guardar();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		textField.grabFocus();
		textField.setText("");
		
	}

	public JTextArea getTextArea() {
		return textArea;
	}
	public JTextField getTextField() {
		return textField;
	}
}
