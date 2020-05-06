package org.boot.mine.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.bytebuddy.asm.Advice.This;

public class Question implements Serializable {
    private Integer QID;      	//主键

    private Integer UIDques;	//提问者

    private Integer UIDans;		//回答者
    private String ansName;

	private String title;		//问题题目

    private String flagDelete;		//存在状态（no、yes）

    private Date proTime;		//问题的提出时间
    
    private String isReply;     //是否回复
    
    private String _strTime;    //字符串类型的时间

    private static final long serialVersionUID = 1L;

    public Question(Integer QID, Integer UIDques, Integer UIDans, String title, String flagDelete, Date proTime,String isReply,String ansName) {
        this.QID = QID;
        this.UIDques = UIDques;
        this.UIDans = UIDans;
        this.title = title;
        this.flagDelete = flagDelete;
        this.proTime = proTime;
        this.isReply = isReply;
        this._strTime = this.DateToString(this.proTime);
        this.ansName = ansName;
    }

    public String get_strTime() {
		return _strTime;
	}
    

    public String getAnsName() {
		return ansName;
	}

	public void setAnsName(String ansName) {
		this.ansName = ansName;
	}


	public void set_strTime(String _strTime) {
		this._strTime = _strTime;
	}

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public Question() {
        super();
    }

    public Integer getQID() {
        return QID;
    }

    public void setQID(Integer QID) {
        this.QID = QID;
    }

    public Integer getUIDques() {
        return UIDques;
    }

    public void setUIDques(Integer UIDques) {
        this.UIDques = UIDques;
    }

    public Integer getUIDans() {
        return UIDans;
    }

    public void setUIDans(Integer UIDans) {
        this.UIDans = UIDans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFlagDelete() {
		return flagDelete;
	}

	public void setFlagDelete(String flagDelete) {
		this.flagDelete = flagDelete;
	}

	public Date getProTime() {
        return proTime;
    }

    public void setProTime(Date proTime) {
        this.proTime = proTime;
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
        sb.append(", QID=").append(QID);
        sb.append(", UIDques=").append(UIDques);
        sb.append(", UIDans=").append(UIDans);
        sb.append(", title=").append(title);
        sb.append(", flagDelete=").append(flagDelete);
        sb.append(", proTime=").append(proTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}