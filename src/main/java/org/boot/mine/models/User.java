package org.boot.mine.models;

import java.io.Serializable;

public class User implements Serializable {
    private Integer UID;  //主键

    private String username;  //用户名

    private String password;  //密文密码

    private String mailBox;   //邮箱

    private String picName;	  //头像名称
 
    private String openSta;   //提问箱开启状态（open、close）

    private String act;		  //邮箱激活状态（no、yes）

    private String prohibit;  //封禁状态（no、yes）

    private Integer quiz;	  //向别人的提问数

    private Integer questions;  //被提问的问题数

    private Integer replies;    //回答的问题数

    private String salt;       //盐值
    
    private String isAdmin;    //是否为管理员
    
    private String mailCode;   //邮件激活码（临时）

    public String getMailCode() {
		return mailCode;
	}

	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}

	private static final long serialVersionUID = 1L;

    public User(Integer UID, String username, String password, String mailBox, String picName, String openSta, String act, String prohibit, Integer quiz, Integer questions, Integer replies, String salt, String isAdmin,String mailCode) {
        if(UID != null) {
        	this.UID = UID;
        }
        if(username != null) {
        	this.username = username;
        }
        if(password != null) {
        	this.password = password;
        }
        if(mailBox != null) {
        	this.mailBox = mailBox;
        }
        if(picName != null) {
        	this.picName = picName;
        }
        if(openSta != null) {
        	this.openSta = openSta;
        }
        if(act != null) {
        	this.act = act;
        }
        if(prohibit != null) {
        	this.prohibit = prohibit;
        }
        if(quiz != null) {
        	this.quiz = quiz;
        }
        if(questions != null) {
        	this.questions = questions;
        }
        if(replies != null) {
        	this.replies = replies;
        }
        if(salt != null) this.salt = salt;
        if(isAdmin != null) {
        	this.isAdmin = isAdmin;
        }
        this.mailCode = mailCode;
    }

    public User() {
        super();
    }
    
    public User(Integer UID, String Username, String MailBox,
    			String PicName,Integer Questions, Integer Replies) {
        this.UID = UID;
        this.username = Username;
        this.mailBox = MailBox;
        this.picName = PicName;
        this.questions = questions;
        this.replies = Replies;       
    }

    public User(Integer userId) {
		// TODO Auto-generated constructor stub
    	this.UID = userId;
	}

	public Integer getUID() {
        return UID;
    }

    public void setUID(Integer UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMailBox() {
        return mailBox;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox == null ? null : mailBox.trim();
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getOpenSta() {
        return openSta;
    }

    public void setOpenSta(String openSta) {
        this.openSta = openSta == null ? null : openSta.trim();
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act == null ? null : act.trim();
    }

    public String getProhibit() {
        return prohibit;
    }

    public void setProhibit(String prohibit) {
        this.prohibit = prohibit == null ? null : prohibit.trim();
    }

    public Integer getQuiz() {
        return quiz;
    }

    public void setQuiz(Integer quiz) {
        this.quiz = quiz;
    }

    public Integer getQuestions() {
        return questions;
    }

    public void setQuestions(Integer questions) {
        this.questions = questions;
    }

    public Integer getReplies() {
        return replies;
    }

    public void setReplies(Integer replies) {
        this.replies = replies;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", UID=").append(UID);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", mailBox=").append(mailBox);
        sb.append(", picName=").append(picName);
        sb.append(", openSta=").append(openSta);
        sb.append(", act=").append(act);
        sb.append(", prohibit=").append(prohibit);
        sb.append(", quiz=").append(quiz);
        sb.append(", questions=").append(questions);
        sb.append(", replies=").append(replies);
        sb.append(", salt=").append(salt);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public boolean isAdmin() {
		// TODO Auto-generated method stub
		if(isAdmin.equals("yes")) {
			return true;
		}
		return false;
	}

}