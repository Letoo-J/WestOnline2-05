package org.boot.mine.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.Question;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyQuesController {
	@Autowired
	QuestionServiceImpl _quesService;
	
	@RequestMapping("/home/myQues")
	public String myQues(Model model,HttpServletRequest request) {
		//获得登入的UID
		User user = (User)request.getSession().getAttribute("user");
		Integer UID = user.getUID();
		System.out.println("session的UID："+UID);
		
		List<Question> list = _quesService.selectQuesByUID2(UID);
		List<Question> list2 = partOfList(list);
		System.out.println("myQues：后端查到我的收到问题了");
		
		for (Question question : list) {
			System.out.println(question.getFlagDelete());
		}
		
		model.addAttribute("sectionName", "收到的问题");
		model.addAttribute("section", "myQues");
		model.addAttribute("list",list2);
		return "home/myQues";
	}
	
	@RequestMapping("/home/myQues/{action}")
	public String myQues( @PathVariable(name="action") String action,
			Model model,HttpServletRequest request) {
		//获得登入的UID
		User user = (User)request.getSession().getAttribute("user");
		Integer UID = user.getUID();
		System.out.println("session的UID："+UID);
		
		if ("isReply".equals(action)) { //返回已经回复的提问
			List<Question> list = _quesService.replyQuestions2(UID);
			List<Question> list2 = partOfList(list);
			
			model.addAttribute("sectionName", "已回复的问题");  //我已回复的问题
			model.addAttribute("section", "isReply");
			model.addAttribute("list",list2);
		}
		else if("noReply".equals(action)) {  //返回未收到回复的提问
			List<Question> list = _quesService.noReplyQuestions2(UID);
			List<Question> list2 = partOfList(list);
			
			model.addAttribute("sectionName", "未回复的问题");  //未回复的问题
			model.addAttribute("section", "noReply");
			model.addAttribute("list",list2);
		}
		else if("hasDelete".equals(action)) {  //返回被删除的提问、可以恢复
			List<Question> list = _quesService.hasDeleteQuestions2(UID);
			List<Question> list2 = partOfList(list);
			
			model.addAttribute("sectionName", "已删除的问题");
			model.addAttribute("section", "hasDelete");
			model.addAttribute("list",list2);
		}
		return "home/myQues";
	}
	
	private List<Question> partOfList(List<Question> list1) {
		List<Question> list2 = new ArrayList<Question>();
		for (Question ques : list1) {
			Question q = new Question();
			q.setQID(ques.getQID());
			q.setTitle(ques.getTitle());
			q.setFlagDelete(ques.getFlagDelete());
			q.set_strTime(ques.get_strTime());
			list2.add(q);
		}
		return list2;
	}
	
	
}
