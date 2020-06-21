package org.boot.mine.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.boot.mine.models.Blacklist;
import org.boot.mine.models.Question;
import org.boot.mine.models.Reply;
import org.boot.mine.models.Report;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.BlacklistServiceImpl;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.boot.mine.service.impl.ReplyServiceImpl;
import org.boot.mine.service.impl.ReportServiceImlp;
import org.boot.mine.util.JsoupUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContentController {
	
	@Autowired
	QuestionServiceImpl _quesService;
	
	@Autowired
	BlacklistServiceImpl _blackService;
	
	@Autowired
	ReportServiceImlp _reportService;
	
	@Autowired
	ReplyServiceImpl _replyService;
	
	//@PathVariable(name="QID")Integer QID
	@GetMapping("/home/content")  
	public String content(@RequestParam(value = "QID")Integer QID, Model model,HttpSession ssession) {
		//获得当前用户
		User u = (User) ssession.getAttribute("user");
		Integer UID = u.getUID();
		
		Question ques = _quesService.selectQuestionByQID(QID);  //找到对应问题
		System.out.println("前端问题ID："+QID);
		System.out.println("UIDCO:"+UID+" UIDans:"+ques.getUIDans()
						+" UIDques:"+ques.getUIDques());
		
		//检查此用户是否为此问题的答题者/提问者：
		if(!UID.equals(ques.getUIDans()) && !UID.equals(ques.getUIDques())) {
			System.out.println("不可访问");
			return "home";
		}
	
		Question q = new Question();
		//筛去提问者信息
		q.setQID(ques.getQID());
		q.setUIDans(ques.getUIDans());
		q.setAnsName(ques.getAnsName());
		q.setTitle(ques.getTitle());
		q.setFlagDelete(ques.getFlagDelete());
		q.set_strTime(ques.get_strTime());
		q.setIsReply(ques.getIsReply());
		
		//若此时该问题的 提问者被回答者拉黑，则返回Bno
		Blacklist black = _blackService.selectBlackByU2(ques.getUIDans(), ques.getUIDques());
		if(black!=null) {  //找到拉黑关系
			model.addAttribute("isBlack", black.getBno());
			System.out.println("被拉黑了："+black.getBno());
		}
		else {
			model.addAttribute("isBlack", 0);
		}
		
		//若此时该问题的 提问者被回答者举报，则返回Rno
		Report report = _reportService.selectReportByU2(ques.getUIDans(), ques.getUIDques(),QID);
		if(report!=null) {
			model.addAttribute("isReport", report.getRno());
			System.out.println("被举报了："+report.getRno());
		}
		else {
			model.addAttribute("isReport", 0);
		}
		
		//得到此问题的回复列表
		List<Reply> list = new ArrayList<Reply>();
		list = _replyService.selectReplyByQID(QID);
		//返回数据进行防xss攻击
		for (Reply reply : list) {
			reply.setContent(JsoupUtil.clean(reply.getContent()));
		}
		
		if(list.isEmpty()) {
			model.addAttribute("list", 0);
		}
		else {
			model.addAttribute("list", list);
		}
		
		
		model.addAttribute("question",q);
		return "home/content";
	}

}
