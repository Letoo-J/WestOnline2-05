package org.boot.mine.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.boot.mine.models.User;
import org.boot.mine.service.impl.IMailServiceImpl;
import org.boot.mine.service.impl.UserServiceImpl;
import org.boot.mine.util.DigestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {
	@Autowired
	UserServiceImpl _userService;
	
	@Autowired
	IMailServiceImpl _mailService;
	
	//@RequestMapping({"/","login.html"})
//	@RequestMapping({"/login"})
//	public String Login(){
//		return "login";  //此处返回值，对应templates的文件名，SpringBoot根据它找到对应Html
//	}
	
//	@RequestMapping({"/home/question"})
//    public String question(HttpServletRequest request, HttpServletResponse response) {
//		
//		
//		return "question";
//	}
	
	
	@GetMapping("/user/checkCode")
	@ResponseBody
    public String checkCode(@RequestParam(value="mailCode")String mailCode) {
		User user = _userService.selectUserByMaliCode(mailCode);
		System.out.println(user);
        //如果用户不等于null，把用户状态修改status=1
        if (user !=null){
           user.setAct("yes");
           //把code验证码清空，已经不需要了
           user.setMailCode("");
           _userService.updateUser(user);
           System.out.println("激活邮箱后的user："+user);
           
           return "激活成功！";
        }else {
        	return "激活失败！请重试";
        }
       
    }
	
	//1.点击“忘记密码？”：访问页面resetPassword.html
	@GetMapping("/resetPassword")
	public Object modifyPassword() {
		return "resetPassword";
	}
	
	@Value("${Store.root.localhost}")
	String localhost;
	
	//2.在resetPassword.html 点击“发送密码重置链接”
	@PostMapping("/resetPassword")
	@ResponseBody
	public Object resetPassword(@RequestParam("mail") String mail,
            HttpServletRequest request) {
		System.out.println("mail:"+mail);
		Map<String, Object> result = new HashMap<String, Object>();
		
		User u = _userService.selectUserByEmail(mail);
		if(u == null) { 
			result.put("code", 404);  //不存在此用户
		}else {
			//获取重置码
	        String mailCode = this.getUUID();
	        u.setMailCode(mailCode);
	        //放入数据库
	        _userService.updateUser(u);
	        System.out.println("mailCode:"+mailCode);
	        
	        //邮件主题
	        String subject = "来自LETOO提问箱的密码重置邮件";
	        //user/checkCode?code=code(激活码)是我们点击邮件链接之后根据激活码查询用户，如果存在说明一致，将用户状态修改为激活
	        //上面的码发送到用户注册邮箱
	        String _sss = "http://"+localhost+":8080/user/resetPassword?mailCode=";
	        String context = "<a href=\"http://"+localhost+":8080/user/resetPassword?mailCode="+mailCode+"\">重置密码请访问: "+_sss+mailCode+"</a>";
	        //发送激活邮件
	        try {
				_mailService.sendHtmlMail (mail,subject,context);
				System.out.println("已发送####");
			} catch (MessagingException e) {
				e.printStackTrace();
				result.put("code", 500);  //发送失败
				return result;
			}
	        result.put("code", 200);  //发送成功
		}
        return result;
	}
	
	
	//@RequestParam("password") String password,
	//@RequestParam("confirmPassword") String confirmPassword
	//3.点击邮箱链接来到resetPasswordForm.html
	@GetMapping("/user/resetPassword")
    public Object resetPassword(@RequestParam("mailCode") String mailCode) {
		
		//根据mailCode查找用户
		User user = _userService.selectUserByMaliCode(mailCode);
		System.out.println(user);
		
        if (user != null){   //可以重置密码了
           	System.out.println("重置密码的user："+user);
           	return "redirect:/user/resetPasswordForm?mailCode="+mailCode;  //转到重置密码具体页面 + 并且传mailCode
        }else {
        	return "redirect:login";   //回到登录页面
        }
    }

	@GetMapping("/user/resetPasswordForm")
	public String resetPasswordForm() {
		return "resetPasswordForm";
	}
	
	@PostMapping("/user/resetPasswordForm")
	@ResponseBody
    public Object resetPasswordForm(@RequestParam("mailCode")String mailCode,
    								@RequestParam("password1") String password1) {
		System.out.println("mailCode:"+mailCode);
		//得到当前用户：
		User u = _userService.selectUserByMaliCode(mailCode);
		System.out.println(u);

		Map<String, Object> result = new HashMap<String, Object>();
        if (u !=null){
        	//设置盐值:
			u.setSalt(UUID.randomUUID().toString());
			String pw = u.getUsername() + password1 + u.getSalt();
			//获得密文
			String ciphertext = md5( sha512(pw)+md5(u.getSalt()) );
			u.setPassword(ciphertext);
			//把code验证码清空，已经不需要了
			u.setMailCode("");
			
			//修改密码：
			_userService.updateUser(u);
          	System.out.println("修改密码后的user："+u);
          	
          	result.put("code", 200);
        }else {
        	result.put("code", 500);
        }
        return result;
    }
	
	
	//无权限页面
	@RequestMapping("/unauth")
	public String unauthorized() {
	    return "unauth";
	}
		
		
	@RequestMapping("/admin")
    @ResponseBody//注解之后只是返回json数据,不返回界面
    public String admin() {
        return "admin success";
    }
 
    @RequestMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    
    private String  md5(String text) {
		return DigestHelper.md5(text);
	}
	private String  sha512(String text) {
		return DigestHelper.sha512(text);
	}
    //获得密码重置
  	public static String getUUID(){
  		return UUID.randomUUID().toString().replace("-","");
  	}
	
}
