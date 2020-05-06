package org.boot.mine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.boot.mine.service.impl.RegisterServiceImpl;
import org.boot.mine.util.BASE64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RegisterServiceImpl _registerService;
	
	@GetMapping("/register")
    public String register()
    {
        return "register";
    }
	
	
	@PostMapping("/register")//注册
    @ResponseBody
    public Object register(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("username");
		String password11 = request.getParameter("password1");
		String password22 = request.getParameter("password2");
		
		//解密前端密码：
		String password1 = BASE64Util.decode(password11);
		String password2 = BASE64Util.decode(password22);
		
		String mail = request.getParameter("mail");
		String verifyInput = request.getParameter("verifyInput");
//		String vvvvString = (String) request.getSession().getAttribute("verifyCode");
//		System.out.println(vvvvString);
//		System.out.println(mail);
		
		Map<String, Object> result = _registerService.register(username,password1,password2,mail,verifyInput);
		
        return result;
    }

}
