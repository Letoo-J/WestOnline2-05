package org.boot.mine.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.boot.mine.models.Report;

public interface ReportMapper {
	/**
	 * 查询举报关系
	 * @param UID1 举报者
	 * @param UID2 被举报者
	 * @return 
	 */
	Report selectReportByU2(@Param("UID1")Integer UID1, @Param("UID2")Integer UID2,@Param("QID")Integer QID);

	/**添加举报关系
	 * @param report
	 */
	int insertReport(Report report);

	/**
	 * 删除举报关系
	 * @param rno
	 * @return 
	 */
	int deleteReport(Integer rno);

	/**
	 * 修改举报的处理状态
	 * @param rno
	 * @param handle
	 * @return
	 */
	int updateReportSta(@Param("rno")Integer rno, @Param("handle")String handle);
	
	/**
	 * 查询所有举报关系
	 * 
	 * @param 
	 * @return 
	 */
	List<Report> selectAll();
	
	
    int deleteByPrimaryKey(Integer rno);

    int insert(Report record);

    Report selectByPrimaryKey(Integer rno);

    

    int updateByPrimaryKey(Report record);

	

}