package ej1.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersistenciaBD implements Persistencia<ej1.controlador.Persona> {
	Connection con;

	public void conectarDB(String IP, String usu, String pass, String bd) throws Exception {
		con = DriverManager.getConnection("jdbc:mysql://" + IP + ":3306/" + bd, usu, pass);
	}

	public void desconectarDB() throws Exception {
		con.close();
	}

	public ArrayList<Persona> listado(String tabla, String orderBy) throws SQLException {
		ArrayList<Persona> result = new ArrayList<>();
		String sql = "SELECT * FROM " + tabla;
		if (orderBy != null)
			sql += " ORDER BY " + orderBy;
		System.out.println(sql);
		ResultSet rs = con.createStatement().executeQuery(sql);
		while (rs.next())
			result.add(new Persona(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		return result;
	}

	@Override
	public void guardar(String tabla, Persona p) throws Exception {
		
		String sql = "INSERT INTO " + tabla + " VALUES (?,?,?,?);";

		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, p.getNombre());
		ps.setString(2, p.getCP());
		ps.setString(3, p.getPais());
		ps.setString(4, p.getEmail());
		try {
		ps.execute();
		} catch(SQLException sqle) {
			sql = "UPDATE " + tabla + " SET nombre = ?, cp = ?, pais = ?, email = ?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, p.getNombre());
			ps.setString(2, p.getCP());
			ps.setString(3, p.getPais());
			ps.setString(4, p.getEmail());
			
			ps.executeUpdate();
		}
	}

	@Override
	public void borrarPersona(String tabla, String email) throws Exception {
		String sql = "DELETE FROM " + tabla + " WHERE email = ?;";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, email);
		ps.execute();

	}

	@Override
	public Persona consultar(String tabla, String email) throws Exception {
		String sql = "SELECT * FROM " + tabla + " WHERE email = \"" + email + "\"";


		ResultSet rs = con.createStatement().executeQuery(sql);

		return rs.next() ? new Persona(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)) : null;
	}

}
