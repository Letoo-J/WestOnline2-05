package org.boot.mine.service.impl;

import java.util.Map;

import org.boot.mine.common.constant.ShiroConstants;
import org.boot.mine.common.constant.UserConstants;
import org.boot.mine.common.enums.UserStatus;
import org.boot.mine.common.exception.user.CaptchaException;
import org.boot.mine.common.exception.user.UserBlockedException;
import org.boot.mine.common.exception.user.UserNotExistsException;
import org.boot.mine.common.exception.user.UserPasswordNotMatchException;
import org.boot.mine.common.exception.user.UsernameORmailNotMatchException;
import org.boot.mine.common.exception.user.UsernameORpasswordNotExistsException;
import org.boot.mine.common.utils.ServletUtils;
import org.boot.mine.models.User;
import org.boot.mine.service.UserService;
import org.boot.mine.service.impl.UserServiceImpl;
import org.boot.mine.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Service
public class LoginServiceImpl
{
    @Autowired
    private PasswordService passwordService;  //密码校验

    @Autowired
    private UserServiceImpl _userService;

    
    /**
     * @param username
     * @param password
     * @param verifyInput
     * @return  User( 法一：shiro )/   (法二)Map<String, Object>
     */
    public User login(String username, String password ,String verifyInput)
    {
        // 验证码校验
    	String verifyCode = (String) ServletUtils.getRequest().getSession().getAttribute("verifyCode");
		System.out.println("后端验证码verifyCode:  "+verifyCode);
		System.out.println("前端验证码verifyInput:  "+verifyInput);
        if (!StringUtils.isEmpty(verifyCode)){
        	if(!verifyInput.equals(verifyCode)) {
                //return Result.of(01, "验证码不正确");
        		throw new CaptchaException();
        	}    
        }
        
        // 用户名/邮箱或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
        	//return Result.of(02, "用户名或密码为空");
        	throw new UserNotExistsException();
        }
        
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH){
        	//return Result.of(03, "密码不在指定范围内");
            throw new UsernameORpasswordNotExistsException();
        }

        // 用户名/邮箱不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH){
        	//return Result.of(05, "用户名不在指定范围内");
        	throw new UsernameORmailNotMatchException();  //【改！】
        }

        /********************************************************************/
        // 查询用户信息
        User user = null ;
        user = _userService.selectUserByUserName(username);
        if (user == null )   //若用户名找不到，则根据邮箱查找&& maybeEmail(username)
        {
            user = _userService.selectUserByEmail(username);
        }

        //用户不存在
        if (user == null)
        {
        	//return Result.of(06, "用户不存在");
            throw new UserNotExistsException();  //抛出异常
        }
        
        //校验用户是否被禁用：Prohibit
        if (user.getProhibit().equals("yes"))
        {
        	//return Result.of(8, "用户已被封禁！");
        	throw new UserBlockedException();
        }
        
        //验证密码是否正确： 传入从数据库去除的用户对象，进行加盐密文匹配
        passwordService.validate(user, password);   //密码不正确
        //return Result.of(07, "用户密码不正确！");
        
        
        return user;
        
        //return Result.of(200, "登入成功！", user);
    }

    private boolean maybeEmail(String username)   //暂时不用，有误
    {
        if (!username.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }


}
