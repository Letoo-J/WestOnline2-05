package org.boot.mine.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.Question;
import org.boot.mine.models.Report;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.ReportServiceImlp;
import org.boot.mine.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {
	
	@Autowired
	UserServiceImpl _userService;
	
	@Autowired
	ReportServiceImlp _reportService;
	
	@RequestMapping("/admin/allUser")
	public String home(Model model){
		
		List<User> list = _userService.selectAllUserNo();
		System.out.println("admin后端查询到了！");
		
		List<Report> list2 = _reportService.selectAllReport();

		model.addAttribute("list",list);
		model.addAttribute("list2",list2);
		return "admin/adminCenter";
	}
	
	@PostMapping("/admin/isProhibit")  
	@ResponseBody
	public Object question(HttpServletRequest request){
		String prohibit = request.getParameter("prohibit");
		String uID = request.getParameter("UID");
		Integer UID = Integer.valueOf(uID);
		
		User user = _userService.selectUserById(UID);
		
		int a = 0;
		if(prohibit.equals("no")) {  //封禁用户
			user.setProhibit("yes");
			a = _userService.updateUser(user);
		}
		else if(prohibit.equals("yes")){  //解封用户
			user.setProhibit("no");
			a = _userService.updateUser(user);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(a != 0) {   //修改成功
			result.put("code", 200);
			System.out.println("修改成功");
		}
		else {
			result.put("code", 500);
		}
		return result;
	}
	
	
	@GetMapping("/admin/searchUser")
	public String searchUser(@RequestParam(value = "name")String name,Model model){
		
		List<User> list = _userService.selectUserLikeUserName(name);
		System.out.println("admin-searchUser后端查询到了！" + name);
		for (User user : list) {
			System.out.println(user);
		}
		model.addAttribute("list",list);
		return "admin/adminCenter";
	}
	
	
//	@RequestMapping("/admin/report")
//	public String myQuiz(Model model) {
//		//获得登入的UID
//		List<User> list = _reportService.se
//		System.out.println("admin后端查询到了！");
//
//		model.addAttribute("list",list);
//		return "admin/adminCenter";
//	}
	
	
	@PostMapping("/admin/isHandle")
	@ResponseBody
	public Object searchUser(@RequestParam(value = "sta")String sta,
							 @RequestParam(value = "rno")String rno ,Model model){
		Integer Rno = Integer.valueOf(rno);
		Report r = _reportService.selectReportByRno(Rno);

		int a = 0;
		if(sta.equals("1")) {  // 处理 + 封禁被举报用户
			a = _reportService.updateReportSta(Rno, "yes");
			
			Integer UID = r.getUIDby();
			User user = _userService.selectUserById(UID);
			user.setProhibit("yes");
			_userService.updateUser(user);
		}
		else if(sta.equals("2")){  // 忽略
			a = _reportService.updateReportSta(Rno, "no");
		}

		Map<String, Object> result = new HashMap<String, Object>();
		if(a != 0) {   //操作成功
			result.put("code", 200);
			System.out.println("操作成功");
		}
		else {
			result.put("code", 500);
		}
		return result;
	}


}
