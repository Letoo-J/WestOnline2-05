package org.boot.mine.controller;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import org.boot.mine.common.core.controller.BaseController;
import org.boot.mine.common.core.domain.AjaxResult;
import org.boot.mine.common.utils.ServletUtils;
import org.boot.mine.common.utils.StringUtils;
import org.boot.mine.config.shiro.MyToken;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.LoginServiceImpl;
import org.boot.mine.util.BASE64Util;
import org.boot.mine.util.DESUtil;
import org.boot.mine.util.RSAUtils;
import org.boot.mine.vo.Result;
import org.hibernate.id.UUIDGenerator;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller   //注入容器
public class LoginController 
{
	@Autowired
	LoginServiceImpl _loginService;
	
	//get方式的请求
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    //只接受post方式的请求
    @PostMapping("/login")
    @ResponseBody  //注解之后只是返回json数据,不返回界面
    public Object login(HttpServletRequest request,HttpSession session, @Param("rememberMe")String rememberMe){	
    	String username = request.getParameter("username");
		String encryptPassword = request.getParameter("encryptPassword");
		String verifyInput = request.getParameter("verifyInput").toUpperCase();  //转为大写
		boolean isRememberMe = false;
		
		if(rememberMe != null ) {
			isRememberMe = true;
		}
		
		//RSA私钥解密
		String password = null;
		try {
			String privateKey = RSAController.privateKey;
			String modulus = RSAController.modulus; 
			password = RSAUtils.Decrypt(encryptPassword,modulus,privateKey);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//DES解密
//		String key = session.getAttribute("uuid").toString();
//		String password = null;
//		try {	//密码-解密
//			password = DESUtil.decryption(passwordDES, key);
//			System.out.println("密码："+password);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//简单base64解密
//		System.out.println("加密后："+password1);
//		//对前端密码进行解密：
//		String password = BASE64Util.decode(password1);
//		System.out.println("解密后："+password);
    	
    	//用shiro
    	//初始化自定义token
		MyToken token = new MyToken(username, password,verifyInput,isRememberMe);
		//UsernamePasswordToken  aPasswordToken = new UsernamePasswordToken (username,password,rememberMe);
        Subject subject = SecurityUtils.getSubject();
        
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
        	System.out.println("获取到信息，开始验证！！");
        	//进行登录验证
            subject.login(token);
            //User user = JSON.parseObject((String)subject.getPrincipal(), User.class);
            //System.out.println("user:"+user);

            User user = (User) subject.getPrincipal();
            //System.out.println("login-useer:"+user);
            
            //登陆成功的话，用户信息放到session中
            //session.setAttribute("user1", (String)subject.getPrincipal());
            session.setAttribute("user", user);
            //放入csrf-token:
            //String uuidToken = (String) session.getAttribute("uuidToken");
            String uuidToken = UUID.randomUUID().toString(); 
            session.setAttribute("uuidToken",uuidToken );
            
            //System.out.println("login-uuidToken:"+ uuidToken);
            
            resultMap.put("code", 200);
            resultMap.put("msg", "登录成功！");     
        } catch (AuthenticationException e) {
        	resultMap.put("code", 500);
            resultMap.put("msg", e.getMessage());
            System.out.println("验证信息："+e.getMessage());
        }
        return resultMap;
        
//		法一：直接验证
//    	Map<String, Object> result = _loginService.login(username, password,verifyInput);
//    	int code = (int)result.get("code");
//		String msg = (String)result.get("msg");
//		User u1= (User) result.get("data");
//		
//    	if(code==200) {   //若登入成功，写cookie和session
//    		request.getSession().setAttribute("user", u1);
//    		return Result.of(code,msg).put("username", u1.getUsername());
//    	}else {  //登入失败。重新登入
//    		return Result.of(code,msg);
//    	}
        
    }
    
    
    //退出系统
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();//取出当前验证主体
        if (subject != null) {
            subject.logout();//不为空，执行一次logout的操作，将session全部清空
        }
        return "login";   //重定向到“/login”
    }
    


}
