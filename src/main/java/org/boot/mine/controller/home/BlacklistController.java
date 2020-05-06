package org.boot.mine.controller.home;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.boot.mine.models.Blacklist;
import org.boot.mine.models.Question;
import org.boot.mine.service.impl.BlacklistServiceImpl;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlacklistController {
	
	@Autowired
	BlacklistServiceImpl _blackService;
	
	@Autowired
	QuestionServiceImpl _quesService;
	
	@PostMapping("/home/isBlack")  
	@ResponseBody
	public Object isBlack(HttpServletRequest request){
		String qID = request.getParameter("QID");
		String uIDans = request.getParameter("UIDans");
		String IsBlack = request.getParameter("isBlack");
	
		Integer UIDans = Integer.valueOf(uIDans);
		Integer isBlack = Integer.valueOf(IsBlack);
		Integer QID = Integer.valueOf(qID);
		
		System.out.println("isBlack : " + UIDans);
		
		int b = 0;
		if(isBlack == 0) {  //拉黑,先根据QID找到UIDques,在插入 + 把此问题设置为删除状态
			Question q = _quesService.selectQuestionByQID(QID);
			Integer UIDques = q.getUIDques();
			Blacklist black = new Blacklist();
			black.setUIDed(UIDques);  //被操作者
			black.setUIDop(UIDans);   //操作者
			//插入
			b = _blackService.insertBlacklist(black);
			
			//删除问题
			_quesService.deleteQuesByQID(QID, "yes");
		}
		else {  //取消拉黑
			b = _blackService.deleteBlacklistByBno(isBlack);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(b != 0) {   //修改成功
			result.put("code", 200);
			System.out.println("修改成功");
		}
		else {
			result.put("code", 1);
		}
		return result;
	}
}
