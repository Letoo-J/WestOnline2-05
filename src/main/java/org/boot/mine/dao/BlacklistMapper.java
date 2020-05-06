package org.boot.mine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.boot.mine.models.Blacklist;

public interface BlacklistMapper {
	/**
	 * 查找黑名单关系
	 * @param UID1 拉黑者
	 * @param UID2 被拉黑者
	 * @return
	 */
	Blacklist selectBlackByU2(@Param("UID1")Integer UID1, @Param("UID2")Integer UID2);

	/**
	 * 插入黑名单关系(回答者拉黑提问者)
	 * 
	 * @param black
	 * @return
	 */
	int insertBlacklist(Blacklist black);

	/**删除拉黑关系
	 * 
	 * @param Bno
	 * @return
	 */
	int deleteBlacklistByBno(Integer bno);
	//相同：
    int deleteByPrimaryKey(Integer bno);

    int insert(Blacklist record);

    Blacklist selectByPrimaryKey(Integer bno);

    List<Blacklist> selectAll();

    int updateByPrimaryKey(Blacklist record);

	
}