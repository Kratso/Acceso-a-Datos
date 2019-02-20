package ejercicio1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;


public class GUI extends JFrame {

	private JPanel contentPane;
	
	static PersistenciaSeguro persistencia;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					persistencia = new PersistenciaSeguro();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(40, 34, 114, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(202, 34, 114, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(40, 77, 114, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(202, 77, 114, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(40, 123, 114, 19);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(202, 123, 114, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(40, 174, 114, 19);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(202, 174, 114, 19);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		JButton btnNewButton = new JButton("HARDER");
		btnNewButton.setBounds(50, 333, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("BETTER");
		btnNewButton_1.setBounds(209, 333, 117, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("STRONGER");
		btnNewButton_2.setBounds(209, 368, 117, 25);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("FASTER");
		btnNewButton_3.setBounds(50, 370, 117, 25);
		contentPane.add(btnNewButton_3);
		
		JLabel lblHit = new JLabel("HIT");
		lblHit.setBounds(40, 7, 70, 15);
		contentPane.add(lblHit);
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setBounds(211, 7, 70, 15);
		contentPane.add(lblOr);
		
		JLabel lblMiss = new JLabel("MISS");
		lblMiss.setBounds(40, 61, 70, 15);
		contentPane.add(lblMiss);
		
		JLabel lblIGuess = new JLabel("I GUESS");
		lblIGuess.setBounds(202, 61, 70, 15);
		contentPane.add(lblIGuess);
		
		JLabel lblThey = new JLabel("THEY");
		lblThey.setBounds(40, 108, 70, 15);
		contentPane.add(lblThey);
		
		JLabel lblNever = new JLabel("NEVER");
		lblNever.setBounds(202, 108, 70, 15);
		contentPane.add(lblNever);
		
		JLabel lblMiss_1 = new JLabel("MISS");
		lblMiss_1.setBounds(40, 154, 70, 15);
		contentPane.add(lblMiss_1);
		
		JLabel lblHuh = new JLabel("HUH");
		lblHuh.setBounds(212, 154, 70, 15);
		contentPane.add(lblHuh);
	}
}
