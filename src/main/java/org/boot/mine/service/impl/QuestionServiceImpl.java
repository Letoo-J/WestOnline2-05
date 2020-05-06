package org.boot.mine.service.impl; 

import java.util.List;

import org.boot.mine.dao.QuestionMapper;
import org.boot.mine.models.Question;
import org.boot.mine.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 问题处理
 * 
 * @author Letoo
 *
 */
@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
    private QuestionMapper questionMapper;

	/**
	 * 提出问题
	 * 
	 * @param  question
	 * @return int 执行row
	 */
	@Override
	public int insertQuestion(Question question) {
		return questionMapper.insertQuestion(question);
	}

	/**
	 * "我"的提问
	 * 
	 * @param UID
	 * @return 问题列表
	 */
	@Override
	public List<Question> selectQuesByUID1(Integer UID) {
		
		return questionMapper.selectQuesByUID1(UID);
	}

	
	/**
	 * "我"收到的问题
	 * 
	 * @param UID
	 * @return 问题列表
	 */
	@Override
	public List<Question> selectQuesByUID2(Integer UID) {
	
		return questionMapper.selectQuesByUID2(UID);
	}

	
	/**
	 * 修改问题状态问题
	 * 
	 * @param QID
	 * @return int
	 */
	@Override
	public int deleteQuesByQID(Integer QID, String flagDelete) {
		
		return questionMapper.deleteByPrimaryKey(QID,flagDelete);
	}
	
	/**
	 * 修改问题为回复状态
	 * 
	 * @param QID
	 * @return int
	 */
	public int updateQuesByQID(Integer QID) {
		return questionMapper.updateQuesByQID(QID);
	}
	
	/**
	 * 已回复问题（提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> replyQuestions(Integer UID){
		return questionMapper.replyQuestions(UID);
	}
	/**
	 * 已回复问题（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> replyQuestions2(Integer UID){
		return questionMapper.replyQuestions2(UID);
	}
	
	/**
	 * 未回复问题
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> noReplyQuestions(Integer UID){
		return questionMapper.noReplyQuestions(UID);
	}
	/**
	 * 未回复提问（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> noReplyQuestions2(Integer UID){
		return questionMapper.noReplyQuestions2(UID);
	}
	
	/**
	 * 已删除的问题
	 * 
	 * @param UID
	 * @return
	 */
	public List<Question> hasDeleteQuestions(Integer UID){
		return questionMapper.hasDeleteQuestions(UID);
	}
	/**
	 * 已删除的问题（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> hasDeleteQuestions2(Integer UID){
		return questionMapper.hasDeleteQuestions2(UID);
	}
	
	/**
	 * 主键查找具体问题对象
	 * 
	 * @param QID
	 * @return
	 */
	public Question selectQuestionByQID(Integer QID) {
		return questionMapper.selectByPrimaryKey(QID);
	}
	
}
