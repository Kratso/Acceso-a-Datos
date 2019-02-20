
package ejercicio1;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


public class PersistenciaSeguro {
	
	Session session;
	
	public PersistenciaSeguro() {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure("ejercicio1/hibernate.cfg.xml");
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
	}
	
	public void insertarSeguro(Seguro seguro) {
		session.save(seguro);
	}
	
	public Seguro getSeguro(int idSeguro) {
		return (Seguro) session.get(Seguro.class, idSeguro);
	}
	
	public void updateSeguro(Seguro seguro) {
		session.update(seguro);
	}
	
	public void deleteSeguro(int idSeguro) {
		session.delete(getSeguro(idSeguro));
	}
	
}
