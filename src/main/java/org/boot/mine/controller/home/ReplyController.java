package org.boot.mine.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.boot.mine.models.Question;
import org.boot.mine.models.Reply;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.boot.mine.service.impl.ReplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReplyController {
	@Autowired
	ReplyServiceImpl _replyServce;
	
	@Autowired
	QuestionServiceImpl _quesService;
	
	@PostMapping("/home/reply")
	@ResponseBody
	public Object myQuiz(HttpServletRequest request) {
		String qID = request.getParameter("QID");
		String uIDco = request.getParameter("UIDco");    //当前回复操作者ID
		String uIDans = request.getParameter("UIDans");  //当前问题的回答者
		String usernamrCo = request.getParameter("usernamrCo"); //当前回复操作者
		String content = request.getParameter("content");
		content = StringEscapeUtils.escapeHtml4(content);  //转义
		
		Integer UIDco = Integer.valueOf(uIDco);  
		Integer UIDans = Integer.valueOf(uIDans);  
		Integer QID = Integer.valueOf(qID);
		Date reTime = new Date();
		
		Reply r = new Reply();
		r.setQID(QID);
		r.setContent(content);
		r.setReTime(reTime);
		r.setAnsName(usernamrCo);
		if(UIDco == UIDans) {
			r.setRole("1");
		}
		else {
			r.setRole("2");
		}
		
		int a = 0;
		a=_replyServce.insertReply(r);
		Map<String, Object> result = new HashMap<String, Object>();
		if(a==0) {
			result.put("code", 1);
		}else {
			result.put("code", 200);

			//此问题设置为“已回复”：
			_quesService.updateQuesByQID(QID);
		}
		
		return result;
	}
	
}
