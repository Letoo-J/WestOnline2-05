package org.boot.mine;

import java.util.List;

import javax.mail.MessagingException;

import org.boot.mine.models.Question;
import org.boot.mine.models.User;
import org.boot.mine.service.impl.IMailServiceImpl;
import org.boot.mine.service.impl.QuestionServiceImpl;
import org.boot.mine.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WestOnline205ApplicationTests {

	@Autowired
    private UserServiceImpl _userService;
	
	@Autowired
	QuestionServiceImpl _quesService;
	
	@Autowired
	IMailServiceImpl _imailService;
	
	
	@Test
	void contextLoads() {
//		String email = "1171392006@qq.com";
//		User u1 = _userService.selectUserByEmail(email);
		/*
		 * Question ques = _quesService.selectQuestionByQID(1);
		 * System.out.println(ques);
		 */
		List<User> list1 = _userService.selectAllUser(1);
		for (User user : list1) {
			System.out.println(user);
		}
	}
	
	@Test  //smfx1314@163.com
	void sendMail() {
		//_imailService.sendSimpleMail("1171392006@qq.com","主题：你好普通邮件","内容：第一封邮件");
		
		try {
			_imailService.sendHtmlMail("1171392006@qq.com","主题：你好html邮件","<h1>第一封html邮件</h1>");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
