package org.boot.mine.service.impl;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.boot.mine.common.exception.user.UserPasswordNotMatchException;
import org.boot.mine.models.User;
import org.boot.mine.util.DigestHelper;
import org.springframework.stereotype.Component;

/**
 * 登录密码方法
 * 
 * @author ruoyi
 */
@Component
public class PasswordService
{
    public boolean validate(User user, String password)
    {
        //校验密码
        if (!matches(user, password))  //若密码校验未通过
        {
        	//return false;
            throw new UserPasswordNotMatchException();  //【改！】
        }
        else {
        	return true;
        }
    }

    public boolean matches(User user, String newPassword)
    {
    	//返回的加盐密文与数据库的密文比较
        return user.getPassword().equals(encryptPassword(user.getUsername(), newPassword, user.getSalt()));
    }


    //返回加盐密文
    public String encryptPassword(String username, String password, String salt)
    {
    	String pw = username + password + salt;
        return  md5( sha512(pw)+md5(salt) );  //MD5+盐
    }
    
    private String  md5(String text) {
		return DigestHelper.md5(text);
	}
	private String  sha512(String text) {
		return DigestHelper.sha512(text);
	}


}
