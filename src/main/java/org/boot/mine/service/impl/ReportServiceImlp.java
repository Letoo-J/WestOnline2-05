package org.boot.mine.service.impl;

import java.util.List;

import org.boot.mine.dao.ReportMapper;
import org.boot.mine.models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImlp {
	@Autowired
	ReportMapper _reportMapper;
	
	/**
	 * 查询举报关系
	 * @param UID1
	 * @param UID2
	 * @return 
	 */
	public Report selectReportByU2(Integer UID1,Integer UID2,Integer QID) {
		return _reportMapper.selectReportByU2(UID1,UID2,QID);
	}
	
	/**
	 * 查询所有举报关系
	 * 
	 * @param 
	 * @return 
	 */
	public List<Report> selectAllReport() {
		return _reportMapper.selectAll();
	}
	
	/**
	 * 查询举报关系
	 * @param UID1
	 * @param UID2
	 * @return 
	 */
	public Report selectReportByRno(Integer rno) {
		return _reportMapper.selectByPrimaryKey(rno);
	}
	
	/**添加举报关系
	 * @param report
	 */
	public int insertReport(Report report) {
		return _reportMapper.insertReport(report);
	}

	/**
	 * 删除举报关系
	 * @param rno
	 * @return 
	 */
	public int deleteReport(Integer rno) {
		return _reportMapper.deleteReport(rno);
	}
	
	/**
	 * 修改举报的处理状态
	 * @param rno
	 * @param handle
	 * @return
	 */
	public int updateReportSta(Integer rno,String handle) {
		return _reportMapper.updateReportSta(rno,handle);
	}
	

}
