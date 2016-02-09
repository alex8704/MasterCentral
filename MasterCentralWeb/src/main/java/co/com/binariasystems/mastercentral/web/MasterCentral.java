package co.com.binariasystems.mastercentral.web;

import java.text.MessageFormat;

import co.com.binariasystems.fmw.ioc.IOCHelper;
import co.com.binariasystems.fmw.security.mgt.SecurityManager;
import co.com.binariasystems.fmw.security.model.AuthorizationRequest;
import co.com.binariasystems.fmw.util.messagebundle.MessageBundleManager;
import co.com.binariasystems.fmw.vweb.constants.UIConstants;
import co.com.binariasystems.fmw.vweb.mvp.dispatcher.MVPUtils;
import co.com.binariasystems.fmw.vweb.util.LocaleMessagesUtil;
import co.com.binariasystems.mastercentral.business.utils.MatCentralBusinessUtils;
import co.com.binariasystems.mastercentral.web.resources.resources;
import co.com.binariasystems.mastercentral.web.utils.MatCentralWebConstants;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.UI;

import elemental.json.JsonArray;

@Theme(UIConstants.BINARIA_THEME)
@Widgetset("co.com.binariasystems.mastercentral.web.gwt.widgetset.MatCentralWidgetset")
@Push(PushMode.AUTOMATIC)
public class MasterCentral extends UI implements MatCentralWebConstants {
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		SecurityManager securityManager = IOCHelper.getBean(SecurityManager.class);
		getPage().setTitle(getApplicationTitle());
		UriFragmentChangedListener uriFragmentListener = IOCHelper.getBean(UriFragmentChangedListener.class);
		getPage().addUriFragmentChangedListener(uriFragmentListener);
		Page.getCurrent().getJavaScript().addFunction(OPEN_POPUP_VIEW_FUNCTION, new PopupViewDispachFunction(uriFragmentListener));
		
		MVPUtils.navigateTo(securityManager.isAuthenticated(getAuthorizationRequest(vaadinRequest)) ? securityManager.getDashBoardViewUrl() : "/");
	}
	
	private String getApplicationTitle(){
		MessageBundleManager messages = MessageBundleManager.forPath(resources.getMessageFilePath(MAIN_MESSAGES_FILE));
		MessageFormat titleFmt = new MessageFormat(LocaleMessagesUtil.getLocalizedMessage(messages, "Orion.applicationTitle"));
		return titleFmt.format(new String[]{MatCentralBusinessUtils.getApplicationName(), MatCentralBusinessUtils.getApplicationVersion()});
	}
	
	private AuthorizationRequest getAuthorizationRequest(VaadinRequest vaadinRequest){
		VaadinServletRequest request =  (VaadinServletRequest) VaadinService.getCurrentRequest();
		return new AuthorizationRequest(null, request.getHttpServletRequest(), request.getSession());
	}
	
	private class PopupViewDispachFunction implements JavaScriptFunction{
		private UriFragmentChangedListener uriFragmentListener;
		public PopupViewDispachFunction(UriFragmentChangedListener uriFragmentListener) {
			this.uriFragmentListener = uriFragmentListener;
		}
		@Override
		public void call(JsonArray arguments) {
			UriFragmentChangedEvent event = new  UriFragmentChangedEvent(Page.getCurrent(), arguments.getString(0));
			uriFragmentListener.uriFragmentChanged(event);
		}
	}
}
