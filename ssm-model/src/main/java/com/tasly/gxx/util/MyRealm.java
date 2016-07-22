package com.tasly.gxx.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.tasly.gxx.domain.Permission;
import com.tasly.gxx.domain.Role;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.service.IUserService;

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String currentUserName = (String) super
				.getAvailablePrincipal(principals);
		List<String> roleList = new ArrayList<String>();
		List<String> permissionList = new ArrayList<String>();
		// �����ݿ��л�ȡ��ǰ��¼�û�����ϸ��Ϣ
		User user = this.userService.findUserByLoginName(currentUserName);
		if (null != user) {
			// ʵ����User�а������û���ɫ��ʵ������Ϣ
			if (!CollectionUtils.isEmpty(user.getRoleList())) {
				// ��ȡ��ǰ��¼�û��Ľ�ɫ
				for (Role role : user.getRoleList()) {
					roleList.add(role.getRolename());
					// ʵ����Role�а����н�ɫȨ�޵�ʵ������Ϣ
					if (!CollectionUtils.isEmpty(role.getPermissionList())) {
						// ��ȡȨ��
						for (Permission pmss : role.getPermissionList()) {
							if (!StringUtils.isEmpty(pmss.getPermissionname())) {
								permissionList.add(pmss.getPermissionname());
							}
						}
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		// Ϊ��ǰ�û����ý�ɫ��Ȩ��
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);

		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		User user = userService.findUserByLoginName(token.getUsername());
		if (null != user) {
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
					user.getUsername(), user.getPassword(), this.getName());
			this.setSession("currentUser", user);
			doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
			return authcInfo;
		} else {
			return null;
		}

	}

	/**
	 * ��һЩ���ݷŵ�ShiroSession��,�Ա��������ط�ʹ��
	 * 
	 * @see ����Controller,ʹ��ʱֱ����HttpSession.getAttribute(key)�Ϳ���ȡ��
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			System.out
					.println("SessionĬ�ϳ�ʱʱ��Ϊ[" + session.getTimeout() + "]����");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
