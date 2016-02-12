package co.com.binariasystems.mastercentral.business.bean.impl.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import retrofit2.Call;
import retrofit2.Response;
import co.com.binariasystems.commonsmodel.constants.SystemConstants;
import co.com.binariasystems.commonsmodel.enumerated.SN2Boolean;
import co.com.binariasystems.fmw.security.FMWSecurityException;
import co.com.binariasystems.fmw.util.exception.FMWExceptionUtils;
import co.com.binariasystems.mastercentral.business.bean.security.RealmBusinessBean;
import co.com.binariasystems.mastercentral.shared.business.bean.ConfigParameterBean;
import co.com.binariasystems.orion.model.dto.AccessTokenDTO;
import co.com.binariasystems.orion.model.dto.AuthenticationDTO;
import co.com.binariasystems.orion.model.dto.ResourceDTO;
import co.com.binariasystems.orion.model.dto.RoleDTO;
import co.com.binariasystems.orion.model.dto.UserCredentialsDTO;
import co.com.binariasystems.orion.model.dto.UserDTO;
import co.com.binariasystems.orionclient.ClientBuilder;
import co.com.binariasystems.orionclient.ClientCredentialsProvider;
import co.com.binariasystems.orionclient.security.SecurityClient;

@Service
@Transactional
public class SecurityBeanImpl implements RealmBusinessBean {
	@Autowired
	private ConfigParameterBean parameterBean;
	@Autowired
	private ClientCredentialsProvider credentialsProvider;
	private ClientBuilder clientBuilder;
	
	private void initClientBuilder(){
		clientBuilder = ClientBuilder.getInstance(parameterBean.findShared(SystemConstants.ORION_RESTAPI_URL_PARAM).getStringValue(), credentialsProvider);
	}
	
	private SecurityClient getClient(){
		if(clientBuilder == null)
			initClientBuilder();
		return clientBuilder.createService(SecurityClient.class);
	}

	public UserDTO findUserByLoginAlias(String loginAlias) throws FMWSecurityException {
		Call<UserDTO> serviceCall = getClient().findUserByLoginAlias(loginAlias);
		try{
			Response<UserDTO> serviceResponse = serviceCall.execute();
			return serviceResponse.body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public UserCredentialsDTO findUserCredentials(String loginAlias) throws FMWSecurityException {
		Call<UserCredentialsDTO> serviceCall = getClient().findUserCredentials(loginAlias);
		try{
			Response<UserCredentialsDTO> serviceResponse = serviceCall.execute();
			return serviceResponse.body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public AccessTokenDTO saveAuthentication(AuthenticationDTO authentication) throws FMWSecurityException {
		Call<AccessTokenDTO> serviceCall = getClient().saveAuthentication(authentication);
		try{
			Response<AccessTokenDTO> serviceResponse = serviceCall.execute();
			return serviceResponse.body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public List<RoleDTO> findUserRoles(AccessTokenDTO accessToken) throws FMWSecurityException {
		Call<List<RoleDTO>> serviceCall = getClient().findUserRoles(accessToken);
		try{
			Response<List<RoleDTO>> serviceResponse = serviceCall.execute();
			return serviceResponse.body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public List<ResourceDTO> findRoleResources(RoleDTO role) throws FMWSecurityException {
		Call<List<ResourceDTO>> serviceCall = getClient().findRoleResources(role);
		try{
			Response<List<ResourceDTO>> serviceResponse = serviceCall.execute();
			return serviceResponse.body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public boolean validateAccessTokenValidity(AccessTokenDTO accessToken) throws FMWSecurityException {
		Call<SN2Boolean> serviceCall = getClient().validateAccessTokenValidity(accessToken);
		try{
			Response<SN2Boolean> serviceResponse = serviceCall.execute();
			return serviceResponse.body().booleanValue();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public void invalidateUserSession(AccessTokenDTO accessToken) throws FMWSecurityException {
		Call<Void> serviceCall = getClient().invalidateUserSession(accessToken);
		try{
			serviceCall.execute().body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}

	public List<ResourceDTO> findUserResources(AccessTokenDTO accessToken) throws FMWSecurityException {
		Call<List<ResourceDTO>> serviceCall = getClient().findUserResources(accessToken);
		try{
			Response<List<ResourceDTO>> serviceResponse = serviceCall.execute();
			return serviceResponse.body();
		}catch(IOException ex){
			throw new FMWSecurityException(FMWExceptionUtils.prettyMessageException(ex));
		}
	}
}
