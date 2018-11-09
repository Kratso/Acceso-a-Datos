package jdbctest;

import java.sql.Connection;
import java.util.ArrayList;

public class PersistenciaBD {
	Connection con;
	
	public PersistenciaBD(String string) {
		
	}

	void conectarDB(String IP, String usu, String pass, String bd) throws Exception{
		
	}
	
	void desconectarDB() throws Exception{
		
	}
	
	ArrayList <Persona> listadoPersonas(String tabla, String orderBy) {
		return null;
	}
	
	void guardarPersona(Persona p) throws Exception {
		
	}
	
	void borrarPersona(Persona p) throws Exception {
		
	}
	
	Persona consultarPersona(String email) throws Exception {
		return null;
	}
}
