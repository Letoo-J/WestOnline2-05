package org.boot.mine.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.boot.mine.common.exception.user.CaptchaException;
import org.boot.mine.common.exception.user.RoleBlockedException;
import org.boot.mine.common.exception.user.UserBlockedException;
import org.boot.mine.common.exception.user.UserNotExistsException;
import org.boot.mine.common.exception.user.UserPasswordNotMatchException;
import org.boot.mine.common.exception.user.UserPasswordRetryLimitExceedException;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * realm实现类,用于实现具体的验证和授权方法
 * @author Bean
 *
 */
public class MyShiroRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(MyShiroRealm.class);
	
	@Autowired
	LoginServiceImpl _loginService;
	
	//自定义token：
	@Override
	public boolean supports(AuthenticationToken token){
		return token != null && token instanceof MyToken;
    }

 
	/**
	 * 方面用于加密 参数：AuthenticationToken是从表单穿过来封装好的对象
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo:" + token);
 
		// 将AuthenticationToken强转为AuthenticationToken对象
		//这边是界面的登陆数据，将数据封装成token (从loginController传来的)
		MyToken upToken = (MyToken) token;
 
		// 获得从表单传过来的用户名
		String username = upToken.getUsername();
		String password = "";
        if (upToken.getPassword() != null)
        {
            password = new String(upToken.getPassword()); //拿到表单的密码
        }
        String verifyInput = upToken.getVerifyInput(); //获得验证码
 
        User user = null;
        try
        {
        	//尝试登入！（里面进行 验证码、密码加盐校验）
            user = _loginService.login(username, password,verifyInput);
            
        }
        catch (CaptchaException e)
        {
            throw new AuthenticationException(e.getMessage(), e);
        }
        catch (UserNotExistsException e)
        {
            throw new UnknownAccountException(e.getMessage(), e);
        }
        catch (UserPasswordNotMatchException e)
        {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }
        catch (UserPasswordRetryLimitExceedException e)
        {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        }
        catch (UserBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (RoleBlockedException e)
        {
            throw new LockedAccountException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        
        // 创建SimpleAuthenticationInfo对象，并且把user和password等信息封装到里面
     	// 用户密码的比对是Shiro帮我们完成的
        // getName()：当前realm对象的名称，调用分类的getName()
        //传入User信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
	}


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
 
	
	// 用于授权
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
// 
//		System.out.println("MyShiroRealm的doGetAuthorizationInfo授权方法执行");
// 
//		// User user=(User)
//		// principals.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
//		// System.out.println("在MyShiroRealm中AuthorizationInfo（授权）方法中从session中获取的user对象:"+user);
// 
//		// 从PrincipalCollection中获得用户信息
//		Object principal = principals.getPrimaryPrincipal();
//		System.out.println("ShiroRealm  AuthorizationInfo:" + principal.toString());
// 
//		// 根据用户名来查询数据库赋予用户角色,权限（查数据库）
//		Set<String> roles = new HashSet<>();
//		Set<String> permissions = new HashSet<>();
//		//给用户添加user权限 (没有进行判断、对所有的用户给user权限)
//		if("user".equals(principal)){
//			roles.add("user");
//			permissions.add("user:query");
//		}
////		当用户名为admin时 为用户添加权限admin  两个admin可以理解为连个字段
//		if ("admin".equals(principal)) {
//			roles.add("admin");
//			permissions.add("admin:query");
//		}
////		为用户添加visit游客权限，在url中没有为visit权限，所以，所有的操作都没权限
//		if("visit".equals(principal)){
//			roles.add("visit");
//			permissions.add("visit:query");
//		}
////              更新以上代码
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
//		//添加权限
//		info.setStringPermissions(permissions);
//		return info;
//		// return null;
//	}
// 
}