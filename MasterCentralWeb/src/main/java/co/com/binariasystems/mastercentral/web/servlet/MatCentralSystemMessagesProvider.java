package co.com.binariasystems.mastercentral.web.servlet;

import org.apache.commons.lang3.StringUtils;

import co.com.binariasystems.fmw.util.messagebundle.MessageBundleManager;
import co.com.binariasystems.mastercentral.web.resources.resources;
import co.com.binariasystems.mastercentral.web.utils.MatCentralWebConstants;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;

public class MatCentralSystemMessagesProvider implements SystemMessagesProvider, MatCentralWebConstants {

	private MessageBundleManager messages;
	private String contextPath;
	
	
	private MatCentralSystemMessagesProvider(){
		messages = MessageBundleManager.forPath(resources.getMessageFilePath(MAIN_MESSAGES_FILE), false);
	}
	
	public MatCentralSystemMessagesProvider(String contextPath){
		this();
		this.contextPath = contextPath;
	}
	
	@Override
	public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
		CustomizedSystemMessages customizedMessages = new CustomizedSystemMessages();
		customizedMessages.setSessionExpiredURL(contextPath);
		customizedMessages.setSessionExpiredNotificationEnabled(Boolean.valueOf(StringUtils.defaultIfBlank(messages.getString("MasterCentral.sessionExpiredNotificationEnabled", systemMessagesInfo.getLocale()), "true")));
		customizedMessages.setSessionExpiredCaption(messages.getString("MasterCentral.sessionExpiredCaption", systemMessagesInfo.getLocale()));
		customizedMessages.setSessionExpiredMessage(messages.getString("MasterCentral.sessionExpiredMessage", systemMessagesInfo.getLocale()));
		
		customizedMessages.setCommunicationErrorURL(contextPath);
		customizedMessages.setCommunicationErrorNotificationEnabled(Boolean.valueOf(StringUtils.defaultIfBlank(messages.getString("MasterCentral.communicationErrorNotificationEnabled", systemMessagesInfo.getLocale()), "true")));
		customizedMessages.setCommunicationErrorCaption(messages.getString("MasterCentral.communicationErrorCaption", systemMessagesInfo.getLocale()));
		customizedMessages.setCommunicationErrorMessage(messages.getString("MasterCentral.communicationErrorMessage", systemMessagesInfo.getLocale()));
		
		customizedMessages.setAuthenticationErrorURL(contextPath);
		customizedMessages.setAuthenticationErrorNotificationEnabled(Boolean.valueOf(StringUtils.defaultIfBlank(messages.getString("MasterCentral.authenticationErrorNotificationEnabled", systemMessagesInfo.getLocale()), "true")));
		customizedMessages.setAuthenticationErrorCaption(messages.getString("MasterCentral.authenticationErrorCaption", systemMessagesInfo.getLocale()));
		customizedMessages.setAuthenticationErrorMessage(messages.getString("MasterCentral.authenticationErrorMessage", systemMessagesInfo.getLocale()));
		
		customizedMessages.setInternalErrorURL(contextPath);
		customizedMessages.setInternalErrorNotificationEnabled(Boolean.valueOf(StringUtils.defaultIfBlank(messages.getString("MasterCentral.internalErrorNotificationEnabled", systemMessagesInfo.getLocale()), "true")));
		customizedMessages.setInternalErrorCaption(messages.getString("MasterCentral.internalErrorCaption", systemMessagesInfo.getLocale()));
		customizedMessages.setInternalErrorMessage(messages.getString("MasterCentral.internalErrorMessage", systemMessagesInfo.getLocale()));
		
		customizedMessages.setCookiesDisabledURL(contextPath);
		customizedMessages.setCookiesDisabledNotificationEnabled(Boolean.valueOf(StringUtils.defaultIfBlank(messages.getString("MasterCentral.cookiesDisabledNotificationEnabled", systemMessagesInfo.getLocale()), "true")));
		customizedMessages.setCookiesDisabledCaption(messages.getString("MasterCentral.cookiesDisabledCaption", systemMessagesInfo.getLocale()));
		customizedMessages.setCookiesDisabledMessage(messages.getString("MasterCentral.cookiesDisabledMessage", systemMessagesInfo.getLocale()));
		
		return customizedMessages;
	}

}
