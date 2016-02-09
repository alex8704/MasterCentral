package co.com.binariasystems.mastercentral.web.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.com.binariasystems.fmw.constants.FMWConstants;
import co.com.binariasystems.fmw.entity.util.FMWEntityConstants;
import co.com.binariasystems.fmw.ioc.IOCHelper;
import co.com.binariasystems.fmw.util.db.DBUtil;
import co.com.binariasystems.fmw.util.di.SpringIOCProvider;
import co.com.binariasystems.fmw.util.messagebundle.PropertiesManager;
import co.com.binariasystems.fmw.vweb.constants.VWebCommonConstants;
import co.com.binariasystems.mastercentral.business.utils.MatCentralBusinessUtils;
import co.com.binariasystems.mastercentral.business.utils.MatCentralConstants;
import co.com.binariasystems.mastercentral.web.utils.MatCentralWebConstants;

/**
 * Application Lifecycle Listener implementation class OrionContextListener
 *
 */

public class MatCentralContextListener implements ServletContextListener, MatCentralWebConstants{
	private static final Logger LOGGER = LoggerFactory.getLogger(MatCentralContextListener.class);
	private static final String PARAM_CREATE_DATAMODEL = MatCentralContextListener.class.getSimpleName()+".createDataModelIfNotExist";

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	PropertiesManager properties = PropertiesManager.forPath("/configuration.properties");
    	System.setProperty(MatCentralConstants.APPLICATION_VERSION_PROPERTY, properties.getString(MatCentralConstants.APPLICATION_VERSION_PROPERTY));
    	System.setProperty(MatCentralConstants.APPLICATION_NAME_PROPERTY, properties.getString(MatCentralConstants.APPLICATION_NAME_PROPERTY));
    	System.setProperty(MatCentralConstants.MAIN_DATASOURCE_PROPERTY, properties.getString(MatCentralConstants.MAIN_DATASOURCE_PROPERTY));
    	
    	IOCHelper.setProvider(SpringIOCProvider.configure(WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())));
    	DBUtil.init(IOCHelper.getBean(MatCentralBusinessUtils.getMainDataSourceName(), DataSource.class));
    	Class resourceLoaderClass = IOCHelper.getBean(FMWConstants.DEFAULT_LOADER_CLASS, Class.class);
    	String entitiesStringsFilePath = IOCHelper.getBean(VWebCommonConstants.APP_ENTITIES_MESSAGES_FILE_IOC_KEY, String.class);
    	String entityOperatiosShowSql = IOCHelper.getBean(FMWEntityConstants.ENTITY_OPERATIONS_SHOWSQL_IOC_KEY, String.class);
    	
    	
    	LOGGER.info(FMWConstants.DEFAULT_LOADER_CLASS + ": " + resourceLoaderClass);
    	LOGGER.info(VWebCommonConstants.APP_ENTITIES_MESSAGES_FILE_IOC_KEY + ": " + entitiesStringsFilePath);
    	LOGGER.info(FMWEntityConstants.ENTITY_OPERATIONS_SHOWSQL_IOC_KEY + ": " + entityOperatiosShowSql);
    	
    	initializeApplication(sce);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	LOGGER.info("Bajando la Aplicacion [{}]", MatCentralBusinessUtils.getApplicationName());
    }
    
    private void initializeApplication(ServletContextEvent sce){
    	
    }
	
}
