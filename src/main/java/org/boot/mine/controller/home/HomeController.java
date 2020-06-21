package org.boot.mine.controller.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@Autowired 
	UserServiceImpl _userService;
	
	@RequestMapping("/home")
	public String home(Model model, HttpServletRequest request,HttpSession session){
		User u1 = (User) session.getAttribute("user");
		
		Integer UID = u1.getUID();
		List<User> list1 = _userService.selectAllUser(UID);
		List<User> list2 = new ArrayList<User>();
		//UID, Username, MailBox, PicName, Questions, Replie
		for (User user : list1) {
			User u = new User();
			u.setUID(user.getUID());
			System.out.println(user.getUID());
			u.setUsername(user.getUsername());
			u.setMailBox(user.getMailBox());
			u.setPicName(user.getPicName());
			u.setQuestions(user.getQuestions());
			System.out.println("被提问数："+user.getQuestions());
			u.setReplies(user.getReplies());
			list2.add(u);
			System.out.println(u);
		}
		System.out.println("home后端查询到了！");

		model.addAttribute("list",list2);
		return "home";
	}
	
}
