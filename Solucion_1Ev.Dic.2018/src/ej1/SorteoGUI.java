package ej1;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SorteoGUI extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame ventanaPrincipal = this; // Almacenamos una referencia a la
											// ventana de la aplicacion
	private static BaseDatos bd;
	private static Document sorteo;// Arbol DOM con informacion del sorteo
	private static String nombreBD = "RenePozo";
	private static String nombreTabla;
	private JButton btnExportarACsv;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		procesoInicial();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					SorteoGUI frame = new SorteoGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void procesoInicial() {
		// Comprueba libreria BD
		try {
			bd = new BaseDatos();
		} catch (ClassNotFoundException e) {
			notificaError(null, "Base de datos", e, "No se encuentra clase para acceder a BD");
			System.exit(-1);
		}
		// Lee CFG.INI
		String ip = null;
		String usu = null;
		String pass = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File("CFG.INI")));
			ip = br.readLine();
			usu = br.readLine();
			pass = br.readLine();
			br.close();
		} catch (IOException e) {
			notificaError(null, "Parametros Base de Datos", e, "Error al cargar CFG.INI");
			System.exit(-1);
		}
		// Conecta con BD
		try {
			bd.conectar(ip, usu, pass, null);
		} catch (SQLException e) {
			notificaError(null, "Base de Datos", e, "Error al conectar con BD");
			System.exit(-1);
		}

		// Carga informacion sorteo(Arbol DOM)
		try {
			sorteo = XML.cargarXML(new File("sorteo.xml"));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			notificaError(null, "sorteo.xml", e, "Error al cargar informacion del sorteo");
			System.exit(-1);
		}
		// Obtiene el nombre de la tabla
		try {
			nombreTabla = XML.nombreTabla(sorteo);
		} catch (XPathExpressionException e) {
			notificaError(null, "Contenido XML", e, "Error al buscar nobre y año del sorteo");
			System.exit(-1);
		}
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public SorteoGUI() throws SQLException {
		setTitle("Sorteo de loteria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 192);
		setLocationRelativeTo(null); // Para centrar la ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("EXTRAER BOLAS PREMIADAS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extraerBolas();
			}
		});
		btnNewButton.setBounds(49, 30, 243, 25);
		contentPane.add(btnNewButton);

		btnExportarACsv = new JButton("EXPORTAR A CSV");
		btnExportarACsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportarCSV();
			}
		});
		// Se activa solo si existe la tabla
		btnExportarACsv.setEnabled(bd.existeTabla(nombreBD, nombreTabla));
		btnExportarACsv.setBounds(49, 93, 243, 25);
		contentPane.add(btnExportarACsv);
	}

	protected void exportarCSV() {
		// Obtiene lista de premios
		ArrayList<Premio> premios = null;
		try {
			premios = XML.obtenerPremios(sorteo);
		} catch (XPathExpressionException e) {
			notificaError(ventanaPrincipal, "XML", e, "Error al obtener premios");
			System.exit(-1);
		}
		try {
			int n=bd.exportarPremiosCSV(nombreBD, nombreTabla,premios);
			JOptionPane.showMessageDialog(ventanaPrincipal, "Se han exportado "+n+" premios");
		} catch (Exception e) {
			notificaError(ventanaPrincipal, "Base de datos-Sistema ficheros", e, "Error al exportar a CSV");
			System.exit(-1);
		}
	}

	protected void extraerBolas() {
		try {
			bd.crearBD(nombreBD);
			if (bd.existeTabla(nombreBD, nombreTabla)) {
				if (preguntaUsuario(ventanaPrincipal, "La tabla con los premios " + nombreTabla + " ya existe",
						"Quiere borrarla y crearla de nuevo?"))
					bd.borrarTabla(nombreBD, nombreTabla);
				else
					return;
			}
			bd.crearTablaPremios(nombreBD, nombreTabla);
			btnExportarACsv.setEnabled(true);
		} catch (Exception e) {
			notificaError(ventanaPrincipal, "Base de datos", e, "Error al extraer bolas");
			System.exit(-1);
		}
		// Obtiene lista de premios
		ArrayList<Premio> premios = null;
		try {
			premios = XML.obtenerPremios(sorteo);
		} catch (XPathExpressionException e) {
			notificaError(ventanaPrincipal, "XML", e, "Error al obtener premios");
			System.exit(-1);
		}
		// Graba en la tabla los numeros premiados extraidos al azar
		for (Premio pr : premios) {
			if (pr.getBolasExtraidas() > 0) {//Solo se graban en BD aquellos premios que conllevan extraccion de bola al azar
				try {
					bd.grabarNumerosPremio(nombreBD,nombreTabla,pr);
				} catch (SQLException e) {
					notificaError(ventanaPrincipal, "Base de datos", e, "Error al grabar numeros premiados");
					System.exit(-1);
				}
			}
		}
	}

	private static void notificaError(JFrame padre, String titulo, Exception e, String mensaje) {
		String contenido = "";
		if (mensaje != null)
			contenido += mensaje + "\n";
		if (e != null) {
			e.printStackTrace();
			contenido += e.getClass().getName() + "\n" + e.getMessage(); 
		}
		JOptionPane.showMessageDialog(padre, contenido, titulo, JOptionPane.ERROR_MESSAGE);
	}

	private boolean preguntaUsuario(JFrame padre, String titulo, String mensaje) {
		/// Mensaje de confirmacion. Devuelve true si el usuario pulsa SI
		return JOptionPane.showConfirmDialog(padre, mensaje, titulo,
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

}
