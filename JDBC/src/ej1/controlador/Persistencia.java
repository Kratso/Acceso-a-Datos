package ej1.controlador;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Persistencia<T> {
	void conectarDB(String IP, String usu, String pass, String bd) throws Exception;

	void desconectarDB() throws Exception;

	ArrayList<T> listado(String tabla, String orderBy) throws SQLException;

	void guardar(String tabla, T p) throws Exception; // inserta o actualiza

	void borrarPersona(String tabla, String email) throws Exception;

	T consultar(String tabla, String email) throws Exception;
}
