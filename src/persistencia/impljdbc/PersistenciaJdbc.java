package persistencia.impljdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Competicion;
import dao.Equipo;
import dao.Posicion;
import persistencia.abs.PersistenciaGeneral;
import persistencia.impljdbc.*;

public class PersistenciaJdbc implements PersistenciaGeneral {

    private Connection con;

    public PersistenciaJdbc(String connectionString, String usuario, String pass) 
    		throws SQLException, ClassNotFoundException {
    	Class.forName("com.mysql.jdbc.Driver");
		con=null;
		con = DriverManager.getConnection("jdbc:mysql://" + connectionString,usuario,pass);
    }

	@Override
	public Equipo getEquipoById(int id) throws SQLException {
		String sql = "SELECT * FROM equipo WHERE id = " + id;
		ResultSet rs = con.createStatement().executeQuery(sql);
		Equipo res = null;
		if(rs.next()) {
			res = new Equipo(rs.getInt(1), rs.getString(2));
		}
		return res;
	}

	@Override
	public List<Equipo> getEquiposByNombre(String nombre) throws SQLException {
		String sql = "SELECT * FROM equipo WHERE nombre = '" + nombre + "'";
		ResultSet rs = con.createStatement().executeQuery(sql);
		List<Equipo> res = new ArrayList<>();
		while(rs.next()) {
			res.add( new Equipo(rs.getInt(1), rs.getString(2)));
		}
		return res;
	}

	@Override
	public void insertOrUpdateEquipo(Equipo equipo) throws SQLException {
		String sql = "INSERT INTO equipo VALUES (?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, equipo.getId());
		ps.setString(2, equipo.getNombre());
		String sql2 = "INSERT INTO ";
	}

	@Override
	public void deleteEquipoById(int id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Equipo> getListaEquipos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Competicion getCompeticionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Competicion> getCompeticionesByNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertOrUpdateCompeticion(Competicion competicion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Competicion> getCompeticiones() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCompeticionById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Posicion getPosicionById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertOrUpdatePosicion(Posicion posicion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePosicionById(int id) {
		// TODO Auto-generated method stub
		
	}
}