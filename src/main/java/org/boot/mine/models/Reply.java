package org.boot.mine.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.type.StringNVarcharType;

public class Reply implements Serializable {
    private Integer RID;		//主键

    private Integer QID;		//关联问题号（外键）

    private String content;		//回复内容

    private Date reTime;		//回复时间
    private String _strTime;
    
    private  String role;       //=1:回答者,  =2:提问者
    
    private String ansName;     //回答者名字

    
    public String getAnsName() {
		return ansName;
	}

	public void setAnsName(String ansName) {
		this.ansName = ansName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private static final long serialVersionUID = 1L;

    public Reply(Integer RID, Integer QID, String content, Date reTime, String role, String ansName) {
        this.RID = RID;
        this.QID = QID;
        this.content = content;
        this.reTime = reTime;
        this.role = role;
        this._strTime = this.DateToString(this.reTime);
        this.ansName = ansName;
    }

    public String get_strTime() {
		return _strTime;
	}

	public void set_strTime(String _strTime) {
		this._strTime = _strTime;
	}

	public Reply() {
        super();
    }

    public Integer getRID() {
        return RID;
    }

    public void setRID(Integer RID) {
        this.RID = RID;
    }

    public Integer getQID() {
        return QID;
    }

    public void setQID(Integer QID) {
        this.QID = QID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getReTime() {
        return reTime;
    }

    public void setReTime(Date reTime) {
        this.reTime = reTime;
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
        sb.append(", RID=").append(RID);
        sb.append(", QID=").append(QID);
        sb.append(", content=").append(content);
        sb.append(", reTime=").append(reTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}