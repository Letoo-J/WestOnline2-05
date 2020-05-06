package org.boot.mine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.boot.mine.models.Question;

public interface QuestionMapper {
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
	 * 删除问题
	 * 
	 * @param QID
	 * @return int
	 */
	public int deleteQuesByQID(Integer QID);
	

	/**
	 * 已回复问题（提问者）
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
	 * 未回复问题
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
	 * 已删除的问题
	 * 
	 * @param UID
	 * @return
	 */
	public List<Question> hasDeleteQuestions(Integer uID);
	/**
	 * 已删除的问题（被提问者）
	 * 
	 * @param QID
	 * @return
	 */
	public List<Question> hasDeleteQuestions2(Integer UID);
	
	/**
	 * 修改问题状态问题
	 * 
	 * @param QID
	 * @return int
	 */
    public int deleteByPrimaryKey(@Param("QID")Integer QID, @Param("flagDelete")String flagDelete);
    
    /**
	 * 修改问题为回复状态
	 * 
	 * @param QID
	 * @return int
	 */
	public int updateQuesByQID(Integer QID);
    
    /**
	 * 主键查找具体问题对象
	 * 
	 * @param QID
	 * @return
	 */
    public Question selectQuestionByQID(Integer qID);
    //同样的
    Question selectByPrimaryKey(Integer QID);
    
    
    int insert(Question record);

    

    List<Question> selectAll();

    int updateByPrimaryKey(Question record);

}