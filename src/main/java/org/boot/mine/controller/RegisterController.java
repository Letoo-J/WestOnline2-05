package org.boot.mine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.boot.mine.models.User;
import org.boot.mine.service.impl.RegisterServiceImpl;
import org.boot.mine.service.impl.UserServiceImpl;
import org.boot.mine.util.BASE64Util;
import org.boot.mine.util.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RegisterServiceImpl _registerService;
	
	@Autowired
	UserServiceImpl _userService;
	
	@GetMapping("/register")
    public String register()
    {
        return "register";
    }
	
	
	@PostMapping("/register")//注册
    @ResponseBody
    public Object register(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password11 = request.getParameter("encryptPassword1");
		String password22 = request.getParameter("encryptPassword2");
		
		//解密前端密码：
//		String password1 = BASE64Util.decode(password11);
//		String password2 = BASE64Util.decode(password22);

		//后端私钥解密
		String password1=null,password2=null;
		try {
			String privateKey = RSAController.privateKey;
			String modulus = RSAController.modulus; 
			password1 = RSAUtils.Decrypt(password11,modulus,privateKey);
			password2 = RSAUtils.Decrypt(password22,modulus,privateKey);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String mail = request.getParameter("mail");
		String verifyInput = request.getParameter("verifyInput").toUpperCase(); //转换为大写
		
		Map<String, Object> result = _registerService.register(username,password1,password2,mail,verifyInput);
		
        return result;
    }
	
	
	//对应前端的remote中的URL地址 
    //远程地址只能输出 "true" 或 "false"，不能有其他输出!
    @RequestMapping("/register/validateUsername")
    @ResponseBody
    public boolean validateUsername(@Param("username") String username){ //验证用户名是否存在
    	User u = _userService.selectUserByUserName(username);
        if(u == null){  //不存在此用户名
            return true;
        }
        return false;
    }
    
    @RequestMapping("/register/validateEMail")
    @ResponseBody
    public boolean validateEMail(@Param("mail") String mail){ 
    	User u = _userService.selectUserByEmail(mail);
        if(u == null){  //不存在此用户邮箱
            return true;
        }
        return false;
    }

}
