package org.boot.mine.service.impl;

import org.boot.mine.dao.BlacklistMapper;
import org.boot.mine.models.Blacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 黑名单
 * 
 * @author Lenovo
 *
 */
@Service
public class BlacklistServiceImpl {
	
	@Autowired 
	BlacklistMapper _blackMapper;

	/**
	 * 查找黑名单关系
	 * @param UID1
	 * @param UID2
	 * @return
	 */
	public Blacklist selectBlackByU2(Integer UID1,Integer UID2) {
		return  _blackMapper.selectBlackByU2(UID1,UID2);
	}
	
	/**
	 * 插入黑名单关系(回答者拉黑提问者)
	 * 
	 * @param black
	 * @return
	 */
	public int insertBlacklist(Blacklist black) {
		return _blackMapper.insertBlacklist(black);
	}
	
	/**删除拉黑关系
	 * 
	 * @param Bno
	 * @return
	 */
	public int deleteBlacklistByBno(Integer Bno) {
		return _blackMapper.deleteBlacklistByBno(Bno);
	}
	
	
}
