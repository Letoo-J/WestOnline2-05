package org.boot.mine.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.boot.mine.common.constant.ShiroConstants;
import org.boot.mine.common.constant.UserConstants;
import org.boot.mine.common.enums.UserStatus;
import org.boot.mine.common.exception.user.CaptchaException;
import org.boot.mine.common.exception.user.UserBlockedException;
import org.boot.mine.common.exception.user.UserNotExistsException;
import org.boot.mine.common.exception.user.UserPasswordNotMatchException;
import org.boot.mine.common.utils.ServletUtils;
import org.boot.mine.models.User;
import org.boot.mine.service.RegisterService;
import org.boot.mine.util.DigestHelper;
import org.boot.mine.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.boot.mine.common.utils.StringUtils;

@Service
public class RegisterServiceImpl implements RegisterService{
	
	@Autowired 
	private UserServiceImpl _userService;   //用户校验
	
//	@Autowired
//    private PasswordService passwordService;  //密码校验(注册不需要)
	
	public Map<String, Object> register(String username, String password1, String password2, 
			String mail, String verifyInput) {
		
		Map<String, Object> map = new HashMap();

		// 验证码校验
		String verifyCode = (String) ServletUtils.getRequest().getSession().getAttribute("verifyCode");
		System.out.println("后端验证码verifyCode:  "+verifyCode);
        if (!StringUtils.isEmpty(verifyCode)){
        	if(!verifyInput.equals(verifyCode)) {
                return Result.of(01, "验证码不正确");
        		//throw new CaptchaException();
        	}    
        }
        
        
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password1)|| StringUtils.isEmpty(password2)){
        	return Result.of(02, "用户名或密码为空");
        	//throw new UserNotExistsException();
        }
        
        // 密码如果不在指定范围内 错误
        if (password1.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password1.length() > UserConstants.PASSWORD_MAX_LENGTH){
        	return Result.of(03, "密码不在指定范围内");
            //throw new UserPasswordNotMatchException();
        }
        
        //如果密码不相等 错误
        if (!password1.equals(password2)){
        	return Result.of(04, "两次密码不相等");
            //throw new UserPasswordNotMatchException();
        }
        
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH){
        	return Result.of(05, "用户名不在指定范围内");
            //throw new UserPasswordNotMatchException();
        }
        
        // 查询用户信息(也可以直接查询结果条数--更好)
        User user1 = _userService.selectUserByUserName(username);
        User user2 = _userService.selectUserByEmail(mail);
        if (user1 != null) {  //若用户名已经存在;
        	return Result.of(07, "用户名已经存在");
        }
        if (user2 != null) {  //若邮箱已经存在
        	return Result.of(8, "邮箱已经存在");
        }

        //验证邮箱是否符合格式
        if(!maybeEmail(mail)) {
        	return Result.of(06, "邮箱不符合格式");
        } 
        
        //验证密码是否符合格式
        if(!rightPassword(password1)) {
        	return Result.of(9, "密码不符合格式");
        }  
       

		User user = new User();
		//设置盐值:
		user.setSalt(UUID.randomUUID().toString());
		String pw = username + password1 + user.getSalt();
		//获得密文
		String ciphertext = md5( sha512(pw)+md5(user.getSalt()) );
		//各种算法......balabala
		//DigestHelper.md5(text);
		//DigestHelper.sha1(text)
		/*重复加密
		 * for(int i=1;i<=100;i++) {
			pw=DigestHelper.sha1(pw);
		}*/
		// mad5(md5(pw)+md5(salt))
		user.setUsername(username);
		user.setPassword(ciphertext);
		user.setMailBox(mail);
		if(_userService.insertUser(user) == 0) {  //插入失败
			return Result.of(9, "注册失败....有一股神秘力量.....");
		}
    	return Result.of(200, "注册成功");
	}
	
	private boolean maybeEmail(String mail)
    {
        if (!mail.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }
	
	private boolean rightPassword(String password)
    {
        if (!password.matches(UserConstants.PASSWORD_PATTERN))
        {
            return false;
        }
        return true;
    }
	
	private String  md5(String text) {
		return DigestHelper.md5(text);
	}
	private String  sha512(String text) {
		return DigestHelper.sha512(text);
	}
}
