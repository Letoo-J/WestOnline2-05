package org.boot.mine.controller.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.boot.mine.models.Reply;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.IMailServiceImpl;
import org.boot.mine.service.impl.PasswordService;
import org.boot.mine.service.impl.UserServiceImpl;
import org.boot.mine.util.DigestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.visitor.functions.If;

/**
 * 个人中心
 * @author Lenovo
 *
 */
@Controller
public class PersonController {
	@Autowired
	UserServiceImpl _userService;
	
	@Autowired
	PasswordService _passwordService;
	
	@Autowired
	IMailServiceImpl _mailService;
	

	@RequestMapping("/home/person")
	public Object person(HttpSession session) {
		User u = (User) session.getAttribute("user");
		Integer UID = u.getUID();
		
		Map<String, Object> result = new HashMap<String, Object>();
		User user = _userService.selectUserById(UID);
		session.setAttribute("user", user);
		return "home/person";
	}
	
	
	
	@PostMapping("/home/person/modifyUsername")
	@ResponseBody
	public Object myQuiz(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User u = (User) request.getSession().getAttribute("user");
		String newName = request.getParameter("newName");
		System.out.println(newName);
		if(newName.isEmpty()) {
			result.put("code", 3);
			return result;
		}
	
		User user = _userService.selectUserByUserName(newName);
		if(user==null) {  //可修改
			u.setUsername(newName);
			int a = 0;
			a = _userService.updateUser(u);
			System.out.println(a);
			if(a!=0) {
				result.put("code", 200);
			}
			else {
				result.put("code",2);
			}
		}else {  //用户名已存在！
			result.put("code", 1);
		}
		
		return result;
	}
	
	
	@GetMapping("/home/person/modifyMail")
	public Object modifyMail() {
		return "home/modifyMail";
	}
	
	@PostMapping("/home/person/modifyMail")
	@ResponseBody
	public Object modifyMail(HttpServletRequest request,HttpSession session) {
		
		String password1 = request.getParameter("password");
		String mail = request.getParameter("mail");
		System.out.println(mail);
		
		User u = (User) session.getAttribute("user");
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(_passwordService.matches(u, password1)) {  //若密码一致
			System.out.println("密码是对滴！");
			//判断邮箱是否已经存在：
			User user = _userService.selectUserByEmail(mail);
			if(user==null) {  //可修改，需重新激活邮箱
				u.setMailBox(mail);
				u.setAct("no");
				int a = 0;
				a = _userService.updateUser(u);
				System.out.println(a);
				if(a!=0) {  //修改成功
					result.put("code", 200);
				}
				else {
					result.put("code",2);   //修改失败
				}
			}else {  //邮箱已存在！
				result.put("code", 1);
			}
		}else {  //密码不一致
			result.put("code", 3);
		}
		return result;
	}
	
	

	@GetMapping("/home/person/modifyPassword")
	public Object modifyPassword() {
		return "home/modifyPassword";
	}
	
	@PostMapping("/home/person/modifyPassword")
	@ResponseBody
	public Object modifyPassword(HttpServletRequest request,HttpSession session) {
		
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");  //新密码
		
		User u = (User) session.getAttribute("user");
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(_passwordService.matches(u, password)) {  //若原密码一致
			System.out.println("原密码是对滴！");
			//设置盐值:
			u.setSalt(UUID.randomUUID().toString());
			String pw = u.getUsername() + password1 + u.getSalt();
			//获得密文
			String ciphertext = md5( sha512(pw)+md5(u.getSalt()) );
			u.setPassword(ciphertext);
			
			int a = 0;
			a = _userService.updateUser(u);
			if(a!=0) {  //修改成功
				Subject subject = SecurityUtils.getSubject(); //取出当前验证主体
		        if (subject != null) {
		            subject.logout();  //不为空,执行一次logout的操作，将session全部清空
		        }
				result.put("code", 200);
			}
			else {
				result.put("code",2);   //修改失败
			}
		}else {  //密码不一致
			result.put("code", 3);
		}
		
		return result;
	}
	
	//点击“激活邮箱”
	@PostMapping("/home/person/mailVerifi")
	@ResponseBody
	public Object mailVerifi(HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		User u = (User) session.getAttribute("user");
		//获取激活码
        String mailCode = this.getUUID();
        u.setMailCode(mailCode);
        //放入数据库
        _userService.updateUser(u);
        
        System.out.println("mailCode:"+mailCode);
        //主题
        String subject = "来自LETOO提问箱的激活邮件";
        //user/checkCode?code=code(激活码)是我们点击邮件链接之后根据激活码查询用户，如果存在说明一致，将用户状态修改为“1”激活
        //上面的激活码发送到用户注册邮箱
        String _sss = "http://localhost:8080/user/checkCode?mailCode=";
        String context = "<a href=\"http://localhost:8080/user/checkCode?mailCode="+mailCode+"\">激活请访问:"+_sss+mailCode+"</a>";
        //发送激活邮件
        try {
			_mailService.sendHtmlMail (u.getMailBox(),subject,context);
			System.out.println("已发送@@@@");
		} catch (MessagingException e) {
			e.printStackTrace();
			result.put("code", 404);
			return result;
		}
        result.put("code", 200);
        return result;
	}
	
	
	@PostMapping("/home/person/noMailVerifi")  
	@ResponseBody
	public Object question(HttpSession session){
		User u = (User) session.getAttribute("user");
		String act = u.getAct();
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(act.equals("no")) {
			result.put("code", 500);  //邮件还未激活
			
		}else {
			u.setAct("no");
			int a = _userService.updateUser(u);
			if(a!=0) {
				result.put("code", 200); //修改成功
			}else {
				result.put("code", 404); //修改失败
			}
		}
		return result;
	}
	
	//开启OR关闭邮箱
	@PostMapping("/home/person/OCMail")  
	@ResponseBody
	public Object OCMail(HttpSession session){
		User u = (User) session.getAttribute("user");
		String sta = u.getOpenSta();
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(sta.equals("close")) {
			u.setOpenSta("open");
			int a = _userService.updateUser(u);
			if(a!=0) {
				result.put("code", 200); //修改成功
			}else {
				result.put("code", 500); //修改失败
			}
			
		}else {
			u.setOpenSta("close");
			int a = _userService.updateUser(u);
			if(a!=0) {
				result.put("code", 200); //修改成功
			}else {
				result.put("code", 500); //修改失败
			}
		}
		return result;
	}
	
	
	//前端显示图片
	@RequestMapping("/pic/{fileName:.+}")
    @ResponseBody
    public void show(@PathVariable String fileName, HttpServletResponse response)throws IOException{
		System.out.println("xxxxbbbb");
		FileInputStream fileIs=null;
           OutputStream outStream = null;
		try
		{
        	File path = new File(ResourceUtils.getURL("classpath:").getPath());
        	if(!path.exists()) path = new File("");
        	System.out.println("回显path:"+path.getAbsolutePath());
      
        	String pathhead=path.getAbsolutePath();
            String path1=pathhead+"/XXXX/"+fileName;
    		//resourceLoader.getResource("file:" + uploadPicturePath + fileName) 返回指定路径的资源句柄，这里返回的就是URL [file:D:/springboot/upload/test.png]
    		//ResponseEntity.ok(T) 返回指定内容主体
         
            fileIs = new FileInputStream(path1);
            System.out.println("回显path111:"+path1);
            //得到文件大小
            int i=fileIs.available();
            //准备一个字节数组存放二进制图片
            byte data[]=new byte[i];
            //读字节数组的数据
            fileIs.read(data);
            response.setContentType("image/*");
            //得到向客户端输出二进制数据的对象
            outStream=response.getOutputStream();
            //输出数据
            outStream.write(data);
            outStream.flush();
        	
		} catch (Exception e) {
   	 		
		}finally {
			  //关闭输出流
            outStream.close();
            //关闭输入流
            fileIs.close();
		}
    }

	
	private String  md5(String text) {
		return DigestHelper.md5(text);
	}
	private String  sha512(String text) {
		return DigestHelper.sha512(text);
	}
	
	//获得邮件激活码
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
