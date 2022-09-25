package servlets;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;

import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

public class ODataServiceFactory extends ODataJPAServiceFactory {

	private static final String PUNIT_NAME = "odatatest";
	private DataSource ds;
	private EntityManagerFactory emf;

	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		try {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
//
			Map properties = new HashMap();
			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
			emf = Persistence.createEntityManagerFactory(PUNIT_NAME, properties);
//			emf = Persistence.createEntityManagerFactory(PUNIT_NAME);
		} catch (NamingException e) {
			try {
				throw new ServletException(e);
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
		}

		ODataJPAContext oDataJpaCtx = getODataJPAContext();
		oDataJpaCtx.setEntityManagerFactory(emf);
		oDataJpaCtx.setPersistenceUnitName(PUNIT_NAME);
		// TODO Auto-generated method stub
		return oDataJpaCtx;
	}

}
