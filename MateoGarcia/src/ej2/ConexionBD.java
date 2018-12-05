package ej2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ConexionBD {

	private static Connection con;
	
	public static void conectar() throws IOException, SQLException {
		BufferedReader bfr = new BufferedReader(new FileReader(new File("CFG.INI")));
		String url = "jdbc:mysql://" + bfr.readLine() + ":3306/MateoGarcia";
		String usu = bfr.readLine();
		String pass = bfr.readLine();
		
		con = DriverManager.getConnection(url, usu, pass);
		
	}

	public static boolean existe(int numero) throws SQLException {
		String sql = "SELECT * FROM Sorteo WHERE numero = " + numero;
		ResultSet rs = con.createStatement().executeQuery(sql);
		return rs.next();
	}

	public static boolean existeAdyacente(int numero) throws SQLException {
		boolean exists = false;
		String sql = "SELECT * FROM Sorteo WHERE numero = " + (numero + 1);
		ResultSet rs = con.createStatement().executeQuery(sql);
		exists = exists || rs.next();
		sql = "SELECT * FROM Sorteo WHERE numero = " + (numero - 1);
		rs = con.createStatement().executeQuery(sql);
		exists = exists || rs.next();
		return exists;
	}

	public static String premio(int numero) throws SQLException, ParserConfigurationException, SAXException, IOException, DOMException, XPathExpressionException {
		String result = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File("sorteo.xml"));
		if(existe(numero)) {
			String sql = "SELECT * FROM Sorteo WHERE numero = " + numero;
			ResultSet rs = con.createStatement().executeQuery(sql);
			
			
			rs.first();
			int num = rs.getInt(1);
			int premio = rs.getInt(2);
			int tipo = rs.getInt(3);
			
			String descripcion = ((Node)(XPathFactory.newInstance().newXPath()
							.evaluate("//premio[cantidad = " + premio + "]/descripcion", doc, XPathConstants.NODE)))
					.getTextContent();
			
			
			result += num +
					":" +
					descripcion +
					" " +
					premio +
					"euros (" +
					(premio/10) + 
					"euros al decimo)";
			ManipuladorXML.poner(num, descripcion, premio);
		}  else {
			String sql = "SELECT * FROM Sorteo WHERE numero = " + (numero+1);
			ResultSet rs = con.createStatement().executeQuery(sql);
			if(rs.next()) {
				int num = rs.getInt(1);
				int premio = rs.getInt(2);
				int tipo = rs.getInt(3);
				
				String descripcion = ((Node)(XPathFactory.newInstance().newXPath()
								.evaluate("//premio[cantidad = " + premio + "]/descripcion", doc, XPathConstants.NODE)))
						.getTextContent();
				
				
				result += num +
						":" +
						descripcion +
						" " +
						premio +
						"euros (" +
						(premio/10) + 
						"euros al decimo)";
			} else {
				sql = "SELECT * FROM Sorteo WHERE numero = " + (numero-1);
				rs = con.createStatement().executeQuery(sql);
				rs.first();
				int num = rs.getInt(1);
				int premio = rs.getInt(2);
				int tipo = rs.getInt(3);
				
				String descripcion = ((Node)(XPathFactory.newInstance().newXPath()
								.evaluate("//premio[cantidad = " + premio + "]/descripcion", doc, XPathConstants.NODE)))
						.getTextContent();
				
				
				result += num +
						":" +
						descripcion +
						" " +
						premio +
						"euros (" +
						(premio/10) + 
						"euros al decimo)";
			}
		}
		return result;
	}
	
	
	
}
