package ej2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ej1.BaseDatos;
import ej1.Premio;
import ej1.XML;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ComprobarNumerosGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNumero;
	private JTextArea textAreaResultados;
	private JFrame ventanaPrincipal = this; // Almacenamos una referencia a la
	// ventana de la aplicacion
	private static BaseDatos bd;
	private static String nombreBD = "RenePozo";
	private static String nombreTabla;
	private static String monedaPremios;
	static ArrayList<Premio> premios = null;
	private static Document comprobados;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		procesoInicial();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComprobarNumerosGUI frame = new ComprobarNumerosGUI();
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
		
		Document sorteo=null;// Arbol DOM con informacion del sorteo
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
		// Obtiene lista de premios
		try {
			premios = XML.obtenerPremios(sorteo);
		} catch (XPathExpressionException e) {
			notificaError(null, "XML", e, "Error al obtener premios");
			System.exit(-1);
		}
		// Obtiene moneda premios
		try {
			monedaPremios = XML.obtenerMonedaPremios(sorteo);
		} catch (XPathExpressionException e) {
			notificaError(null, "XML", e, "Error al obtener moneda premios");
			System.exit(-1);
		}
		File rutaComp=new File("comprobados.xml");
		try {
			if (!rutaComp.exists())
				comprobados=XML.creaDOMComprobados(nombreTabla);
			else
				comprobados = XML.cargarXML(rutaComp);
		}catch (SAXException | ParserConfigurationException | IOException e) {
				notificaError(null, rutaComp.getName(), e, "Error al cargar numeros ya comprobados");
				System.exit(-1);
		}
	}


	/**
	 * Create the frame.
	 */
	public ComprobarNumerosGUI() {
		setTitle("Comprobar Numeros Premiados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 388);
		setLocationRelativeTo(null); // Para centrar la ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Numero");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(100, 25, 59, 14);
		contentPane.add(lblNewLabel);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldNumero.setBounds(169, 22, 113, 20);
		contentPane.add(textFieldNumero);
		textFieldNumero.setColumns(10);
		
		JButton btnComprobar = new JButton("COMPROBAR");
		btnComprobar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comprobarNumero();
			}
		});
		btnComprobar.setBounds(292, 21, 134, 23);
		contentPane.add(btnComprobar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 67, 560, 266);
		contentPane.add(scrollPane);
		
		textAreaResultados = new JTextArea();
		textAreaResultados.setEditable(false);
		scrollPane.setViewportView(textAreaResultados);
	}

	protected void comprobarNumero() {
		String num=textFieldNumero.getText().trim();
		if (num.length()==0)
			return;
		try {
			int n=Integer.valueOf(num);
			if (n<0 || n >99999) throw new Exception("Numero fuera de rango");
			Premio pr=bd.consultarPremioNumero(nombreBD, nombreTabla, n);
			if (pr==null)//Si no est� en BD(numero extraido) del bombo se buscan premios por aproximaci�n
				pr=buscarPermioPorAproximacion(n);
			else{
				//Busca descripcion premio (no est� en la BD)
				for (Premio p : premios) {
					if (pr.getTipo()==p.getTipo()){
						pr.setDescripcion(p.getDescripcion());
						break;
					}
				}
			}
			registrarPremio(n,pr);
		} catch (Exception e) {
			notificaError(ventanaPrincipal, "Numero incorrecto", e, "Debe introducir un numero entre 0 y 99999");
		}
		
	}

	private void registrarPremio(int n, Premio pr) {
		//Muestra mensaje en textArea
		String mensaje;
		if (pr==null)
			mensaje=String.format("%d:No premiado\n", n);
		else
			mensaje=String.format("%d:%s %d%s (%d al d�cimo)\n",n,pr.getDescripcion(),pr.getCantidad(),monedaPremios,pr.getCantidad()/10);
		textAreaResultados.setText(textAreaResultados.getText()+mensaje);
		//Graba numero en comprobados.xml
		try {
			XML.grabarNumeroComprobado(comprobados,n,pr);
		} catch (XPathExpressionException | TransformerException e) {
			notificaError(ventanaPrincipal, "XML", e, "Error al grabar numero en comprobados.xml");
		}
	}

	private Premio buscarPermioPorAproximacion(int n) {
		Premio primerPremio=null, segundoPremio=null;
		try {
			primerPremio=bd.consultarPremioTipo(nombreBD, nombreTabla, 1);
			segundoPremio=bd.consultarPremioTipo(nombreBD, nombreTabla, 2);
			if (primerPremio==null||segundoPremio==null)
				throw new SQLException("Primer o segundo premio no existe");
		} catch (SQLException e) {
			notificaError(null, "Base de datos", e, "Error al obtener primer y segundo premio");
			System.exit(-1);
		}
		for (Premio pr : premios) {
			switch (pr.getTipo()){
			case 7: //Numero anterior o posterior al primer premio
				if (n==primerPremio.getNumero()+1 || n==primerPremio.getNumero()-1)
					return pr;
				break;
			case 8: //Numero anterior o posterior al segundo premio
				if (n==segundoPremio.getNumero()+1 || n==segundoPremio.getNumero()-1)
					return pr;
				break;
			case 9: //Dos ultimas cifras del primer premio
				if (n%100==primerPremio.getNumero()%100)
					return pr;
				break;
			case 10: //Dos ultimas cifras del segundo premio
				if (n%100==segundoPremio.getNumero()%100)
					return pr;
				break;
			case 11: //Reintegro:ultima cifra del primer premio<
				if (n%10==primerPremio.getNumero()%10)
					return pr;
				break;
			}
		}
		return null;
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
}
