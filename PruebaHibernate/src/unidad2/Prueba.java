package unidad2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Prueba {
	
	public static void main(String[] args) {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		Session session = sessionFactory.openSession();
		
		Profesor profesor=new Profesor(101, "Juan", "Perez", "García");  //Creamos el objeto
		session.beginTransaction();
		session.delete(profesor);
		session.save(profesor); //<|--- Aqui guardamos el objeto en la base de datos.
		profesor.setApe1("González");
		session.update(profesor);
		Profesor profesor2=(Profesor)session.get(Profesor.class,101);
		session.delete(profesor);
		session.saveOrUpdate(profesor2);
		session.getTransaction().commit();
		session.close();
		
		
	}

}
