package org.boot.mine.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.Question;
import org.boot.mine.models.Report;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/home/myQuiz")
public class MyQuizController {
	@Autowired
	QuestionServiceImpl _quesService;
	
	
	@RequestMapping("/home/myQuiz")
	public String myQuiz(Model model,HttpServletRequest request) {
		//获得登入的UID
		User user = (User)request.getSession().getAttribute("user");
		Integer UID = user.getUID();
		System.out.println("session的UID："+UID);
		
		List<Question> list = _quesService.selectQuesByUID1(UID);
		System.out.println("myquiz：后端查到我的问题了");
		
		for (Question question : list) {
			System.out.println(question.getFlagDelete());
		}
		
		model.addAttribute("sectionName", "我的提问");
		model.addAttribute("section", "myQuiz");
		model.addAttribute("list",list);
		return "home/myQuiz";
	}
	

	@RequestMapping("/home/myQuiz/{action}")
	public String myQuiz( @PathVariable(name="action") String action,
			Model model,HttpServletRequest request) {
		//获得登入的UID
		User user = (User)request.getSession().getAttribute("user");
		Integer UID = user.getUID();
		System.out.println("session的UID："+UID);
		
		if ("isReply".equals(action)) { //返回已经回复的提问
			List<Question> list = _quesService.replyQuestions(UID);
			model.addAttribute("sectionName", "已收到回复");
			model.addAttribute("section", "isReply");
			model.addAttribute("list",list);
		}
		else if("noReply".equals(action)) {  //返回未收到回复的提问
			List<Question> list = _quesService.noReplyQuestions(UID);
			model.addAttribute("sectionName", "未收到回复");
			model.addAttribute("section", "noReply");
			model.addAttribute("list",list);
		}
		else if("hasDelete".equals(action)) {  //返回被删除的提问、可以恢复
			List<Question> list = _quesService.hasDeleteQuestions(UID);
			model.addAttribute("sectionName", "已删除");
			model.addAttribute("section", "hasDelete");
			model.addAttribute("list",list);
		}
		return "home/myQuiz";
	}
	
	@PostMapping("/home/myQuiz/delete")  
	@ResponseBody
	public Object question(HttpServletRequest request){
		String flagDelete = request.getParameter("flagDelete");
		String qID = request.getParameter("QID");
		Integer QID = Integer.valueOf(qID);
		System.out.println(QID+": " + flagDelete);
		
		int ques = 0;
		if(flagDelete.equals("no")) {  //删除问题
			ques = _quesService.deleteQuesByQID(QID, "yes");
		}
		else if(flagDelete.equals("yes")){  //恢复问题
			ques = _quesService.deleteQuesByQID(QID, "no");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(ques != 0) {   //修改成功
			result.put("code", 200);
			System.out.println("修改成功");
		}
		else {
			result.put("code", 1);
		}
		return result;
	}
	
	
	
	
	
}
