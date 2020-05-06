package org.boot.mine.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.Blacklist;
import org.boot.mine.models.Question;
import org.boot.mine.models.Report;
import org.boot.mine.service.impl.BlacklistServiceImpl;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.boot.mine.service.impl.ReportServiceImlp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {
	@Autowired
	ReportServiceImlp _reportService;
	
	@Autowired
	QuestionServiceImpl _quesService;
	
	@PostMapping("/home/isReport")  
	@ResponseBody
	public Object isBlack(HttpServletRequest request){
		String qID = request.getParameter("QID");
		String uIDans = request.getParameter("UIDans");
		String reason = request.getParameter("reason");
	
		Integer UIDans = Integer.valueOf(uIDans);
		Integer QID = Integer.valueOf(qID);
		
		System.out.println("reason : " + reason);
		
		int b = 0;
		//拉黑,先根据QID找到UIDques,在插入 + 把此问题设置为删除状态
		Question q = _quesService.selectQuestionByQID(QID);
		Integer UIDques = q.getUIDques();
		Date reportTime = new Date();
		Integer QIDreport = q.getQID();
		
		Report report = new Report();
		report.setUIDby(UIDques);  //被操作者
		report.setUIDre(UIDans);  //操作者
		report.setReason(reason);
		report.setReportTime(reportTime);
		report.setQIDreport(QIDreport);
		//插入
		b = _reportService.insertReport(report);
		
		//删除问题
		_quesService.deleteQuesByQID(QID, "yes");
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(b != 0) {   //修改成功
			result.put("code", 200);
			System.out.println("举报成功");
		}
		else {
			result.put("code", 1);
		}
		return result;
	}

}
