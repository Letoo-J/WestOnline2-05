package org.boot.mine.models;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Admin implements Serializable {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer AID;      //主键

    private String adminName; //管理员名

    private String password;  //密文密码

    private String salt;	  //盐值

    private static final long serialVersionUID = 1L;

    public Admin(Integer AID, String adminName, String password, String salt) {
        this.AID = AID;
        this.adminName = adminName;
        this.password = password;
        this.salt = salt;
    }

    public Admin() {
        super();
    }

    public Integer getAID() {
        return AID;
    }

    public void setAID(Integer AID) {
        this.AID = AID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", AID=").append(AID);
        sb.append(", adminName=").append(adminName);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}