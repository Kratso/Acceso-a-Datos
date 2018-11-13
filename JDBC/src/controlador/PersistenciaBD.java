package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersistenciaBD {
	Connection con;
	public void conectarDB(String IP, String usu, String pass, String bd) throws Exception{
		con = DriverManager.getConnection("jdbc:mysql://"+ IP +":3306/" + bd, usu, pass);
	}
	public void desconectarDB() throws Exception{
		con.close();
	}
	public ArrayList <Persona> listadoPersonas(String tabla, String orderBy) throws SQLException{
		ArrayList<Persona> result = new ArrayList<>();
		String sql = "SELECT * FROM " + tabla;
		if(orderBy != null)
			sql += " ORDER BY " + orderBy;
		System.out.println(sql);
		ResultSet rs = con.createStatement().executeQuery(sql);
		while(rs.next())
			result.add(new Persona(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		return result;
	}
	public void guardarPersona(Persona p) throws Exception{
		
	}
	public void borrarPersona(Persona p) throws Exception {
		
	}
	public Persona consultarPersona(String email) throws Exception{
		return null;
	}

}
