package org.boot.mine.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.User;
import org.boot.mine.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@Autowired
	UserServiceImpl _userService;
//	@RequestMapping("/")     //默认访问
//	public String index(){
//		return "index";
//	}
	@RequestMapping("/")
	public String index(Model model){
		List<User> list1 = _userService.selectAllUserYou();
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
		}
		System.out.println("index后端查询到了！");

		model.addAttribute("list",list2);
		return "index";
	}
	
	
	
	@RequestMapping("/avatar")
	public String avatar(){
		return "wuyong/avatar";
	}
	
	@RequestMapping("/password")
	public String password(){
		return "wuyong/password";
	}
	
	@RequestMapping("/profile")
	public String profile(){
		return "wuyong/profile";
	}
	
	@RequestMapping("/detail")
	public String detail(){
		return "wuyong/detail";
	}
	
	
	
}
