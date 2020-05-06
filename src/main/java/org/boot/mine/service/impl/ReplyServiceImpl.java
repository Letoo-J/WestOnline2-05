package org.boot.mine.service.impl;

import java.util.List;

import org.boot.mine.dao.ReplyMapper;
import org.boot.mine.models.Reply;
import org.boot.mine.models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 回复处理
 * 
 * @author Lenovo
 *
 */
@Service
public class ReplyServiceImpl {
	@Autowired
	ReplyMapper _repilMapper;
	
	/**
	 * 查询此问题下的回复
	 * @param QID
	 * @return
	 */
	public List<Reply> selectReplyByQID(Integer QID){
		return _repilMapper.selectReplyByQID(QID);
	}
	
	/**
	 * 进行回复
	 * @param reply
	 * @return
	 */
	public int insertReply(Reply reply) {
		return _repilMapper.insertReply(reply);
	}
	
	public int deleteReplyByRID(Integer RID) {
		return _repilMapper.deleteReplyByRID(RID);
	}
}
