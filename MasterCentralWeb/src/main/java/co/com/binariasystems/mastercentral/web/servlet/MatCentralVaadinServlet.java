package co.com.binariasystems.mastercentral.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import co.com.binariasystems.mastercentral.web.MasterCentral;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;


@WebServlet(
		urlPatterns = {"/main/*","/VAADIN/*"}, 
		name = "MatCentral-VServlet", asyncSupported = true, loadOnStartup = 1,
		initParams={@WebInitParam(
						name="org.atmosphere.cpr.AtmosphereInterceptor", 
						value="co.com.binariasystems.fmw.vweb.util.atmosphere.SpringContextPushInterceptor,org.atmosphere.interceptor.ShiroInterceptor")}
)
@VaadinServletConfiguration(ui = MasterCentral.class, productionMode = false)
public class MatCentralVaadinServlet extends VaadinServlet{
	private MatCentralSessionListener sessionListener = new MatCentralSessionListener();
	private MatCentralSystemMessagesProvider systemMessagesProvider;
	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		
		systemMessagesProvider = new MatCentralSystemMessagesProvider(getServletContext().getContextPath());
		getService().addSessionInitListener(sessionListener);
		getService().addSessionDestroyListener(sessionListener);
		getService().setSystemMessagesProvider(systemMessagesProvider);
	}
}
