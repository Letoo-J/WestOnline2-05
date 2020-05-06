package org.boot.mine.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report implements Serializable {
    private Integer rno;		//主键

    private Integer UIDre;		//举报者

    private Integer UIDby;		//被举报者

    private Date reportTime;	//举报时间
    private String _strTime;

	private Integer QIDreport;	//被举报的问题ID

    private String reason;		//举报理由（可为空）

    private String handle;		//处理状态（no、yes、ing）

    private static final long serialVersionUID = 1L;

    public Report(Integer rno, Integer UIDre, Integer UIDby, Date reportTime, Integer QIDreport, String reason, String handle) {
        this.rno = rno;
        this.UIDre = UIDre;
        this.UIDby = UIDby;
        this.reportTime = reportTime;
        this.QIDreport = QIDreport;
        this.reason = reason;
        this.handle = handle;
        this._strTime = this.DateToString(this.reportTime);
    }

    public Report() {
        super();
    }

    public String get_strTime() {
		return _strTime;
	}

	public void set_strTime(String _strTime) {
		this._strTime = _strTime;
	}

    
    public Integer getRno() {
        return rno;
    }

    public void setRno(Integer rno) {
        this.rno = rno;
    }

    public Integer getUIDre() {
        return UIDre;
    }

    public void setUIDre(Integer UIDre) {
        this.UIDre = UIDre;
    }

    public Integer getUIDby() {
        return UIDby;
    }

    public void setUIDby(Integer UIDby) {
        this.UIDby = UIDby;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getQIDreport() {
        return QIDreport;
    }

    public void setQIDreport(Integer QIDreport) {
        this.QIDreport = QIDreport;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle == null ? null : handle.trim();
    }
    
    public String DateToString(Date date) {
    	//以下填写的是想要进行转换的时间格式
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");    
		String date_str = format.format(date);
		//System.out.println(date_str);
		return date_str;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rno=").append(rno);
        sb.append(", UIDre=").append(UIDre);
        sb.append(", UIDby=").append(UIDby);
        sb.append(", reportTime=").append(reportTime);
        sb.append(", QIDreport=").append(QIDreport);
        sb.append(", reason=").append(reason);
        sb.append(", handle=").append(handle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}