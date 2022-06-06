package is.yarr.queuegen.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

/**
 * The options for Hibernate.
 */
public class HibernateOptions {

    private static final Map<String, String> overrides = Map.of(
            "javax.persistence.jdbc.url", System.getenv("POSTGRES_URL"),
            "javax.persistence.jdbc.user", System.getenv("POSTGRES_USER"),
            "javax.persistence.jdbc.password", System.getenv("POSTGRES_PASS")
    );

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("is.yarr.queuegen.backend.Persistence", overrides);

    /**
     * The {@link EntityManager} provided by the {@link EntityManagerFactory}.
     *
     * @return The created {@link EntityManager}
     */
    public static EntityManager getEntityManager() {
         return emf.createEntityManager();
    }
}
