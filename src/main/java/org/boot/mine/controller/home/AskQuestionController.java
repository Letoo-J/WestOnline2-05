package org.boot.mine.controller.home;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.buf.C2BConverter;
import org.boot.mine.common.core.text.Convert;
import org.boot.mine.models.Question;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AskQuestionController {

	@Autowired
	QuestionServiceImpl _questionService;
	
	@GetMapping("/home/question")     //默认访问
	public String question(){
		return "home/question";
	}
	
	
	
	@PostMapping("/home/question")  
	@ResponseBody
	public Object question(HttpServletRequest request, Model model){
		String uidans = request.getParameter("uidans");
		User u = (User)request.getSession().getAttribute("user");
		Integer UIDques = u.getUID();
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		System.out.println("被提问者name：" + username);
		
		System.out.println("uidans: " + uidans);
		
		//Integer UIDques = Convert.toInt(uidques);
		Integer UIDans = Integer.valueOf(uidans);
		Date proTime = new Date();
		
		
		Question question = new Question();
		question.setUIDques(UIDques);
		question.setUIDans(UIDans);
		question.setTitle(title);
		question.setProTime(proTime);
		question.setAnsName(username);
		int ques = _questionService.insertQuestion(question);
		Map<String, Object> result = new HashMap<String, Object>();
		if(ques != 0) {   //插入成功
			result.put("code", 200);
		}
		else {
			result.put("code", 1);
		}
		return result;
	}
	
	
}
