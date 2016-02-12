package co.com.binariasystems.mastercentral.web.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.binariasystems.fmw.security.FMWSecurityException;
import co.com.binariasystems.fmw.security.authc.SecurityPrincipalConverter;
import co.com.binariasystems.fmw.security.util.SecConstants;
import co.com.binariasystems.fmw.util.messagebundle.MessageBundleManager;
import co.com.binariasystems.mastercentral.business.bean.security.RealmBusinessBean;
import co.com.binariasystems.orion.model.dto.UserDTO;

@Component
public class MatCentralSecurityPrincipalConverter implements SecurityPrincipalConverter<String, UserDTO> {
	private MessageBundleManager messageManager;
	@Autowired
	private RealmBusinessBean businessBean;
	
	@PostConstruct
	protected void init(){
		messageManager = MessageBundleManager.forPath(SecConstants.DEFAULT_AUTH_MESSAGES_PATH, SecurityPrincipalConverter.class);
	}
	
	@Override
	public UserDTO toPrincipalModel(String representation) throws FMWSecurityException {
		return businessBean.findUserByLoginAlias(representation);
	}

	@Override
	public String toPrincipalRepresentation(UserDTO model) throws FMWSecurityException {
		return model.getLoginAlias();
	}

}
