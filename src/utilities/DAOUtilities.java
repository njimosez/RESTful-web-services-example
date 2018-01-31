package utilities;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages
 * a single instance of the database connection.
 */
public class DAOUtilities {

		public static SessionFactory getSession() {

		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		SessionFactory sessionFactory = null;

		try {

			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

		} catch (Exception ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			ex.printStackTrace();
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}
		return sessionFactory;
	}

	/*------------------------------------------------------------------------------------------------*/
		
		

}