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
import co.com.binariasystems.fmw.vweb.constants.VWebCommonConstants;
import co.com.binariasystems.mastercentral.business.utils.MatCentralBusinessUtils;
import co.com.binariasystems.mastercentral.web.utils.MatCentralWebConstants;

/**
 * Application Lifecycle Listener implementation class OrionContextListener
 *
 */

public class MatCentralContextListener implements ServletContextListener, MatCentralWebConstants{
	private static final Logger LOGGER = LoggerFactory.getLogger(MatCentralContextListener.class);
	private boolean successfulInitilized;
	//private static final String PARAM_CREATE_DATAMODEL = MatCentralContextListener.class.getSimpleName()+".createDataModelIfNotExist";

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	IOCHelper.setProvider(SpringIOCProvider.configure(WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())));
    	DBUtil.init(IOCHelper.getBean(MatCentralBusinessUtils.getMainDataSourceName(), DataSource.class));
    	Class resourceLoaderClass = IOCHelper.getBean(FMWConstants.DEFAULT_LOADER_CLASS, Class.class);
    	String entitiesStringsFilePath = IOCHelper.getBean(VWebCommonConstants.APP_ENTITIES_MESSAGES_FILE_IOC_KEY, String.class);
    	String entityOperatiosShowSql = IOCHelper.getBean(FMWEntityConstants.ENTITY_OPERATIONS_SHOWSQL_IOC_KEY, String.class);
    	
    	
    	LOGGER.info(FMWConstants.DEFAULT_LOADER_CLASS + ": " + resourceLoaderClass);
    	LOGGER.info(VWebCommonConstants.APP_ENTITIES_MESSAGES_FILE_IOC_KEY + ": " + entitiesStringsFilePath);
    	LOGGER.info(FMWEntityConstants.ENTITY_OPERATIONS_SHOWSQL_IOC_KEY + ": " + entityOperatiosShowSql);
    	
    	successfulInitilized = initializeApplication(sce);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  {
    	if(successfulInitilized)
    		LOGGER.info("Bajando la aplicaci\u00f3n [{} Ver. {}]", MatCentralBusinessUtils.getApplicationName(), MatCentralBusinessUtils.getApplicationVersion());
    }
    
    private boolean initializeApplication(ServletContextEvent sce){
    	LOGGER.info("Inicializando la aplicaci\u00f3n [{} Ver. {}]", MatCentralBusinessUtils.getApplicationName(), MatCentralBusinessUtils.getApplicationVersion());
    	return true;
    }
	
}
