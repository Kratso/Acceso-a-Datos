package menusito;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menusito extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menusito frame = new Menusito();
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
	public Menusito() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBorrar = new JButton("BORRAR");
		btnBorrar.setBounds(69, 48, 282, 35);
		contentPane.add(btnBorrar);
		
		JButton btnRenombrar = new JButton("RENOMBRAR");
		btnRenombrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRenombrar.setBounds(69, 95, 282, 35);
		contentPane.add(btnRenombrar);
		
		JButton btnCrear = new JButton("CREAR");
		btnCrear.setBounds(69, 147, 282, 35);
		contentPane.add(btnCrear);
	}
}
