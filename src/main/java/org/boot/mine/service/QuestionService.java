package org.boot.mine.service;

import java.util.List;

import org.boot.mine.models.Question;

/**
 * 问题处理接口
 * 
 * @author Letoo
 *
 */
public interface QuestionService {
	
	/**
	 * 提出问题
	 * 
	 * @param  question
	 * @return int
	 */
	public int insertQuestion(Question question);

	/**
	 * "我"的提问
	 * 
	 * @param UID
	 * @return 问题列表
	 */
	public List<Question> selectQuesByUID1(Integer UID); 
	
	/**
	 * 收到的问题
	 * 
	 * @param UID
	 * @return 问题列表
	 */
	public List<Question> selectQuesByUID2(Integer UID); 
	
	/**
	 * 修改问题状态问题
	 * 
	 * @param QID
	 * @return int
	 */
	public int deleteQuesByQID(Integer QID, String flagDelete);
	
	/**
	 * 修改问题为回复状态
	 * 
	 * @param QID
	 * @return int
	 */
	public int updateQuesByQID(Integer QID);
	
	
	/**
	 * 已回复提问（提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> replyQuestions(Integer UID);
	/**
	 * 已回复问题（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> replyQuestions2(Integer UID);
	
	/**
	 * 未回复提问（提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> noReplyQuestions(Integer UID);
	/**
	 * 未回复提问（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> noReplyQuestions2(Integer UID);
	
	
	/**
	 * 已删除的问题（提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> hasDeleteQuestions(Integer UID);
	/**
	 * 已删除的问题（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> hasDeleteQuestions2(Integer UID);
	
	/**
	 * 主键查找具体问题对象
	 * 
	 * @param QID
	 * @return
	 */
	public Question selectQuestionByQID(Integer QID);
 
 }
