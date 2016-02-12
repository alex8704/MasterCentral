package co.com.binariasystems.mastercentral.business.bean.security;

import java.util.List;

import co.com.binariasystems.fmw.security.FMWSecurityException;
import co.com.binariasystems.orion.model.dto.AccessTokenDTO;
import co.com.binariasystems.orion.model.dto.AuthenticationDTO;
import co.com.binariasystems.orion.model.dto.ResourceDTO;
import co.com.binariasystems.orion.model.dto.RoleDTO;
import co.com.binariasystems.orion.model.dto.UserCredentialsDTO;
import co.com.binariasystems.orion.model.dto.UserDTO;

public interface RealmBusinessBean {
	public UserDTO findUserByLoginAlias(String loginAlias) throws FMWSecurityException;
	public UserCredentialsDTO findUserCredentials(String loginAlias) throws FMWSecurityException;
	public AccessTokenDTO saveAuthentication(AuthenticationDTO authentication) throws FMWSecurityException;
	public List<RoleDTO> findUserRoles(AccessTokenDTO accessToken) throws FMWSecurityException;
	public List<ResourceDTO> findRoleResources(RoleDTO role) throws FMWSecurityException;
	public boolean validateAccessTokenValidity(AccessTokenDTO accessToken) throws FMWSecurityException;
	public void invalidateUserSession(AccessTokenDTO accessToken) throws FMWSecurityException;
	public List<ResourceDTO> findUserResources(AccessTokenDTO accessToken) throws FMWSecurityException;
}
