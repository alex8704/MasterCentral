package co.com.binariasystems.mastercentral.business.bean.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.binariasystems.commonsmodel.constants.SystemConstants;
import co.com.binariasystems.commonsmodel.enumerated.Application;
import co.com.binariasystems.mastercentral.shared.business.bean.ConfigParameterBean;
import co.com.binariasystems.mastercentral.shared.business.dto.ConfigParameterDTO;
import co.com.binariasystems.orionclient.ClientCredentialsProvider;
import co.com.binariasystems.orionclient.dto.ClientCredentialsDTO;

@Service
public class OrionClientCredentialsProvider implements ClientCredentialsProvider {
	@Autowired
	private ConfigParameterBean paramBean;
	
	@Override
	public ClientCredentialsDTO getClientCredentials() {
		ConfigParameterDTO loginParam = paramBean.findByCodeAndApp(SystemConstants.ORION_RESTAPI_LOGIN_PARAM, Application.MASTERCENTRAL);
		ConfigParameterDTO passwordParam = paramBean.findByCodeAndApp(SystemConstants.ORION_RESTAPI_PWD_PARAM, Application.MASTERCENTRAL);
		return new ClientCredentialsDTO(loginParam.getStringValue(), passwordParam.getStringValue());
	}
}
