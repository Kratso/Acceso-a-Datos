package ej1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import java.sql.PreparedStatement;

import java.sql.DatabaseMetaData;

import java.sql.Connection;

public class ConexionBD {
	Connection con;
	ManejadorXML mxml;

	String conexion;
	String usu;
	String pass;
	Random r = new Random();
	String tabla;

	public void conectar() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(new File("CFG.INI")));
			conexion = "jdbc:mysql://" + bfr.readLine() + ":3306/";
			usu = bfr.readLine();
			pass = bfr.readLine();
			bfr.close();

			mxml = new ManejadorXML("sorteo.xml");

			con = DriverManager.getConnection(conexion, usu, pass);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void crear() throws ParserConfigurationException, SAXException, IOException, SQLException {
		String sql = "CREATE DATABASE MateoGarcia";
		con.createStatement().execute(sql);

		sql = "CREATE TABLE MateoGarcia.";
		tabla = mxml.getNombre();
		sql += tabla;
		sql += "(numero INT(6) PRIMARY KEY," + "premio INT(10)," + "tipo INT(2));";

		conexion += "MateoGarcia";

		con = DriverManager.getConnection(conexion, usu, pass);

	}

	public boolean existe() throws SQLException {
		DatabaseMetaData metadata = con.getMetaData();

		ResultSet rs = null;
		boolean exists = false;
		rs = metadata.getCatalogs();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			exists = exists || rs.getString(1).equals("MateoGarcia");
		}

		return exists;

	}

	public void extraer() throws SQLException, NumberFormatException, DOMException, XPathExpressionException,
			ParserConfigurationException, SAXException, IOException {
		int maxPull = mxml.maxPulls();
		int[][] tiposPremios = mxml.getBolasPremios();

		Set<Integer> pulls = new TreeSet<>();
		for (int i = 0; i < tiposPremios.length; i++) {
			for (int j = 0; j < tiposPremios[i].length; j++) {
				tiposPremios[i][j] = nuevoPull(pulls);
				String sql = "INSERT INTO " + tabla + " VALUES (?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, tiposPremios[i][j]);
				ps.setInt(2, mxml.getPremio(i));
				ps.setInt(3, i);
				ps.execute();
			}
		}

	}

	private int nuevoPull(Set<Integer> pulls) {
		int tam = pulls.size();
		int val = r.nextInt(100000);
		pulls.add(val);
		if ((pulls.size() - tam) == 0)
			val = nuevoPull(pulls);
		return val;
	}

	public void borrar() throws SQLException {
		String sql = "DROP DATABASE MateoGarcia";
		con.createStatement().execute(sql);

	}

	public void pasarACsv() {
		try {
			File csv = new File("tabla.csv");
			String sql = "SELECT * FROM " + tabla + "ORDER BY 1";
			ResultSet rs = con.createStatement().executeQuery(sql);
			PrintWriter pw = new PrintWriter(new FileWriter(csv, true));
			while (rs.next()) {
				int num = rs.getInt(1);
				int premio = rs.getInt(2);
				int tipo = rs.getInt(3);
				String linea = num + ";" + premio + ";" + tipo + ";" + ManejadorXML.getDesc(tipo);
				pw.println(linea);
			}
		} catch (Exception e) {

		}

	}
}
