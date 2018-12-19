package ej1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class BaseDatos {
	private Connection con;

	//Conexion con MySQL
	public BaseDatos() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		con=null;
	}

	public void conectar(String IP, String usuario, String passwd, String base) throws SQLException {
		String cadena="jdbc:mysql://" + IP;
		if (base!=null)
			cadena+="/"+base;
		if (con!=null)
			desconectar();
		con = DriverManager.getConnection(cadena,usuario,passwd);
	}
	
	public void desconectar() throws SQLException {
		con.close();
		con=null;
	}

	public ArrayList<String> dameBases() throws SQLException {

		ArrayList<String> l = new ArrayList<String>();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res=null;
		res = meta.getCatalogs();
		while (res.next()) {
			l.add(res.getString("TABLE_CAT"));
		}
		res.close();
		return l;
	}

	public ArrayList<String> dameTablas(String base) throws SQLException {
		ArrayList<String> l = new ArrayList<String>();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = null;
		res = meta.getTables(base, null, "%",null);  

		while (res.next()) {
			if (res.getString("TABLE_TYPE").equals("TABLE"))
				l.add(res.getString("TABLE_NAME"));
		}
		res.close();
		return l;
	}


	public void crearTablaPremios(String base,String tabla) throws SQLException {
		Statement stm=con.createStatement();
		String sql="CREATE TABLE "+base+"."+tabla+" (numero INTEGER PRIMARY KEY, premio INTEGER, tipo INTEGER)";
		stm.executeUpdate(sql);	
	}

	public void borrarTabla(String base,String tabla) throws SQLException {
		Statement stm=con.createStatement();
		String sql="DROP TABLE "+base+"."+tabla;
		stm.executeUpdate(sql);	
	}
	
	public boolean existeTabla(String base, String tabla) throws SQLException {
		ArrayList<String> lt= dameTablas(base);
		for (String t:lt)
			if (t.toLowerCase().equals(tabla.toLowerCase()))
				return true;
		return false;
	}

	public void crearBD(String base) throws SQLException {
		Statement st=con.createStatement();
		String sql="CREATE DATABASE IF NOT EXISTS "+base;
		st.executeUpdate(sql);	
	}
	
	

	public boolean existeBD(String base) throws SQLException {
		ArrayList<String> lbd= dameBases();
		for (String b:lbd)
			if (b.toLowerCase().equals(base.toLowerCase()))
				return true;
		return false;
	}


	public void grabarNumerosPremio(String base, String tabla, Premio pr) throws SQLException {
		Random r=new Random();
		String sql = "INSERT INTO "+base+"."+tabla+" VALUES (?,?,?)";
		PreparedStatement pst = con.prepareStatement(sql);
		int cnt=0;
		//Se extraen tantos numeros al azar(unicos) como bolas indique el premio 
		for (int i = 1; i <= pr.getBolasExtraidas(); i++) {
			int n;
			do {
				//Extrae numero aleatorio 0..99999 hasta que salga uno que no se haya extraido previamente
				n=r.nextInt(100000);
				cnt++;
			} while (consultarPremioNumero(base,tabla,n)!=null);		
		    pst.setInt(1,n);
		    pst.setInt(2,pr.getCantidad());
		    pst.setInt(3,pr.getTipo());
		    pst.executeUpdate();
		    System.out.println(i+" Rep:"+(cnt-i));
		}
	}

	public Premio consultarPremioNumero(String base, String tabla, int n) throws SQLException {
		String sql = "SELECT * FROM "+base+"."+tabla+" WHERE numero=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, n);
		ResultSet rs=pst.executeQuery();
		if (rs.next()){
			Premio p=new Premio();
			p.setNumero(rs.getInt("numero"));
			p.setTipo(rs.getInt("tipo"));
			p.setCantidad(rs.getInt("premio"));
			return p;
		}
		else
			return null;
	}
	
	public Premio consultarPremioTipo(String base, String tabla, int tipo) throws SQLException {
		String sql = "SELECT * FROM "+base+"."+tabla+" WHERE tipo=?";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, tipo);
		ResultSet rs=pst.executeQuery();
		if (rs.next()){
			Premio p=new Premio();
			p.setNumero(rs.getInt("numero"));
			p.setTipo(rs.getInt("tipo"));
			p.setCantidad(rs.getInt("premio"));
			return p;
		}
		else
			return null;
	}

	public int exportarPremiosCSV(String base, String tabla, ArrayList<Premio> premios) throws SQLException, IOException {
		String nombreCSV=tabla+".csv";
		PrintWriter pw=new PrintWriter(new FileWriter(new File(nombreCSV)));
		pw.println("numero;premio;tipo;descripcion"); //Cabecera CSV
		int cnt=0;
		String sql = "SELECT * FROM "+base+"."+tabla+" ORDER BY tipo,numero";
		Statement st = con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		while (rs.next()){
			int tipo=Integer.valueOf(rs.getString("tipo"));
			pw.print(rs.getString("numero")+";"+rs.getString("premio")+";"+tipo+";");
			//Busca descripcion premio
			String descripcion="";
			for (Premio pr : premios) {
				if (pr.getTipo()==tipo){
					descripcion=pr.getDescripcion();
					break;
				}
			}
			pw.println(descripcion);
			cnt++;
		}
		pw.close();
		return cnt;
	}

}