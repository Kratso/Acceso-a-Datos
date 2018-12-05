package ej1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SimuladorSorteo extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	ConexionBD con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimuladorSorteo frame = new SimuladorSorteo();
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
	public SimuladorSorteo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnExtraerBolasPremiadas = new JButton("EXTRAER BOLAS PREMIADAS");
		btnExtraerBolasPremiadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportar();
			}
		});
		btnExtraerBolasPremiadas.setBounds(69, 60, 296, 25);
		contentPane.add(btnExtraerBolasPremiadas);

		btnNewButton = new JButton("EXPORTAR A CSV");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.pasarACsv();
			}
		});
		btnNewButton.setBounds(69, 135, 296, 25);
		contentPane.add(btnNewButton);
	}

	protected void exportar() {
		try {
			System.out.println("Hola");
			con = new ConexionBD();
			con.conectar();
			if (con.existe()) {
				int opcion = JOptionPane.showConfirmDialog(this, "La tabla ya existe, deseas borrarla?");
				if (opcion == JOptionPane.OK_OPTION) {
					con.borrar();
					con.crear();
				} else {
					return;
				}
			} else {
				con.crear();
			}
			con.extraer();
			btnNewButton.setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	public JButton getBtnNewButton() {
		return btnNewButton;
	}
}
