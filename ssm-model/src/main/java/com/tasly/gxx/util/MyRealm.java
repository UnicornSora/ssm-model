package com.tasly.gxx.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

public class MyRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String currentUserName=(String) super.getAvailablePrincipal(principals);
//      List<String> roleList = new ArrayList<String>();  
//      List<String> permissionList = new ArrayList<String>();  
//      //�����ݿ��л�ȡ��ǰ��¼�û�����ϸ��Ϣ  
//      User user = userService.getByUsername(currentUsername);  
//      if(null != user){  
//          //ʵ����User�а������û���ɫ��ʵ������Ϣ  
//          if(null!=user.getRoles() && user.getRoles().size()>0){  
//              //��ȡ��ǰ��¼�û��Ľ�ɫ  
//              for(Role role : user.getRoles()){  
//                  roleList.add(role.getName());  
//                  //ʵ����Role�а����н�ɫȨ�޵�ʵ������Ϣ  
//                  if(null!=role.getPermissions() && role.getPermissions().size()>0){  
//                      //��ȡȨ��  
//                      for(Permission pmss : role.getPermissions()){  
//                          if(!StringUtils.isEmpty(pmss.getPermission())){  
//                              permissionList.add(pmss.getPermission());  
//                          }  
//                      }  
//                  }  
//              }  
//          }  
//      }else{  
//          throw new AuthorizationException();  
//      }  
//      //Ϊ��ǰ�û����ý�ɫ��Ȩ��  
//      SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();  
//      simpleAuthorInfo.addRoles(roleList);  
//      simpleAuthorInfo.addStringPermissions(permissionList);
		
		SimpleAuthorizationInfo authorInfo=new SimpleAuthorizationInfo();
		
		if(StringUtils.hasText(currentUserName)&&"gaoxiexin".equals(currentUserName)){
			//���һ����ɫ,�������������ϵ����,����֤�����û�ӵ��admin��ɫ 
			authorInfo.addRole("admin");
			//���Ȩ��  
			authorInfo.addStringPermission("admin:manage");  
            System.out.println("��Ϊ�û�[mike]������[admin]��ɫ��[admin:manage]Ȩ��");  
            return authorInfo;  
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token=(UsernamePasswordToken)authcToken;
		
//      User user = userService.getByUsername(token.getUsername());  
//      if(null != user){  
//          AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), user.getNickname());  
//          this.setSession("currentUser", user);  
//          return authcInfo;  
//      }else{  
//          return null;  
//      }  
        //�˴�����ȶ�,�ȶԵ��߼�Shiro����,����ֻ�践��һ����������ص���ȷ����֤��Ϣ  
        //˵���˾��ǵ�һ���������¼�û���,�ڶ���������Ϸ��ĵ�¼����(�����Ǵ����ݿ���ȡ����,������Ϊ����ʾ��Ӳ������)  
        //����һ��,�����ĵ�¼ҳ���Ͼ�ֻ������ָ�����û����������ͨ����֤ 
		if("gaoxiexin".equals(token.getUsername())){  
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("gaoxiexin", "111111", this.getName());  
            this.setSession("currentUser",token.getUsername());  
            return authcInfo;  
        }
		return null;
	}
	
	/** 
     * ��һЩ���ݷŵ�ShiroSession��,�Ա��������ط�ʹ�� 
     * @see  ����Controller,ʹ��ʱֱ����HttpSession.getAttribute(key)�Ϳ���ȡ�� 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("SessionĬ�ϳ�ʱʱ��Ϊ[" + session.getTimeout() + "]����");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }  

}
