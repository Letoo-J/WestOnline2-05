package org.boot.mine.dao;

import java.util.List;
import org.boot.mine.models.Reply;

public interface ReplyMapper {
	/**
	 * 查询此问题下的回复
	 * @param QID
	 * @return
	 */
	List<Reply> selectReplyByQID(Integer QID);

	/**
	 * 进行回复
	 * @param reply
	 * @return
	 */
	int insertReply(Reply reply);

	int deleteReplyByRID(Integer RID);
	
    int deleteByPrimaryKey(Integer RID);

    int insert(Reply record);

    Reply selectByPrimaryKey(Integer RID);

    List<Reply> selectAll();

    int updateByPrimaryKey(Reply record);

	
}