package co.com.binariasystems.mastercentral.web.security;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import co.com.binariasystems.commonsmodel.enumerated.Application;
import co.com.binariasystems.fmw.ioc.IOCHelper;
import co.com.binariasystems.fmw.security.FMWSecurityException;
import co.com.binariasystems.mastercentral.business.bean.security.RealmBusinessBean;
import co.com.binariasystems.orion.model.dto.AccessTokenDTO;
import co.com.binariasystems.orion.model.dto.AuthenticationDTO;
import co.com.binariasystems.orion.model.dto.ResourceDTO;
import co.com.binariasystems.orion.model.dto.RoleDTO;
import co.com.binariasystems.orion.model.enumerated.SecurityExceptionType;
import co.com.binariasystems.orionclient.OrionClientException;

public class MatCentralSecurityRealm extends AuthorizingRealm {
	private RealmBusinessBean businessBean;
	protected boolean permissionsLookupEnabled = false;
	private String applicationCode;
	private Application application;

	public MatCentralSecurityRealm() {
		businessBean = IOCHelper.getBean(RealmBusinessBean.class);
		setCredentialsMatcher(new CredentialsMatcher() {
			/**
			 * Dummy credentials matcher because, Orion perform all validations
			 */
			@Override public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
				return true;
			}
		});
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
		
		AccessTokenDTO accessToken = (AccessTokenDTO)getAvailablePrincipal(principals);
		Set<String> roleNames = new LinkedHashSet<String>();
        Set<String> permissions = new LinkedHashSet<String>();
        List<ResourceDTO>  roleResources = null;
        
        List<RoleDTO> userRoles = getUserRoles(accessToken);
        for(RoleDTO role : userRoles)
        	roleNames.add(role.getName());
        
        if(permissionsLookupEnabled){
        	roleResources = getUserResources(accessToken);
        	for(ResourceDTO resource : roleResources)
        		permissions.add(resource.getResourcePath());
        }
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
		SimpleAuthenticationInfo info = null;
		AccessTokenDTO accessToken = null;
		try{
			accessToken = businessBean.saveAuthentication(new AuthenticationDTO(username, new String(upToken.getPassword()), 
					(application != null ? application.name() : null), upToken.getHost()));
		}catch(OrionClientException ex){
			throw traduceOrionClientException(ex);
		} catch (FMWSecurityException ex) {
			throw new AuthenticationException(ex.getMessage());
		}
		
		info = new SimpleAuthenticationInfo(accessToken, accessToken.getUser().getCredentials().getPassword().toCharArray(), getName());
		
		if (accessToken.getUser().getCredentials().getPasswordSalt() != null) {
            info.setCredentialsSalt(ByteSource.Util.bytes(accessToken.getUser().getCredentials().getPasswordSalt()));
        }
		return info;
	}
	
	protected List<RoleDTO> getUserRoles(AccessTokenDTO accessToken){
		try {
			return businessBean.findUserRoles(accessToken);
		} catch(OrionClientException ex){
			throw traduceOrionClientException(ex);
		}catch (FMWSecurityException ex) {
			throw new AuthorizationException(ex.getMessage());
		}
    }
	
	protected List<ResourceDTO> getUserResources(AccessTokenDTO accessToken){
    	try { 
    		return businessBean.findUserResources(accessToken);
		} catch(OrionClientException ex){
			throw traduceOrionClientException(ex);
		}catch (FMWSecurityException ex) {
			throw new AuthorizationException(ex.getMessage());
		}
    }
	
	private AuthenticationException traduceOrionClientException(OrionClientException ex){
		if(ex.getExceptionType() != null){
			if(SecurityExceptionType.INVALID_ACCOUNT.equals(ex.getExceptionType()))
				return new UnknownAccountException();
			if(SecurityExceptionType.CREDENTIALS_NOT_MATCH.equals(ex.getExceptionType()))
				return new IncorrectCredentialsException();
			if(SecurityExceptionType.ACCOUNT_BLOCKED.equals(ex.getExceptionType()))
				return new LockedAccountException();
			if(SecurityExceptionType.MAX_AUTHENTICATION_ATTEMPTS.equals(ex.getExceptionType()))
				return new ExcessiveAttemptsException();
		}
		return new AuthenticationException(ex.getMessage(), ex);
	}
	
	/**
	 * @return the businessBean
	 */
	public RealmBusinessBean getBusinessBean() {
		return businessBean;
	}

	/**
	 * @param businessBean
	 *            the businessBean to set
	 */
	public void setBusinessBean(RealmBusinessBean businessBean) {
		this.businessBean = businessBean;
	}

	/**
	 * @return the permissionsLookupEnabled
	 */
	public boolean isPermissionsLookupEnabled() {
		return permissionsLookupEnabled;
	}

	/**
	 * @param permissionsLookupEnabled the permissionsLookupEnabled to set
	 */
	public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
		this.permissionsLookupEnabled = permissionsLookupEnabled;
	}

	/**
	 * @return the applicationCode
	 */
	public String getApplicationCode() {
		return applicationCode;
	}

	/**
	 * @param applicationCode the applicationCode to set
	 */
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
		setAppication();
	}
	
	private void setAppication(){
		if(StringUtils.isNotEmpty(applicationCode))
			application = Application.valueOf(applicationCode.toUpperCase());
	}
	
}
