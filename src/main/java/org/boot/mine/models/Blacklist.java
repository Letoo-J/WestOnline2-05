package org.boot.mine.models;

import java.io.Serializable;

//黑名单
public class Blacklist implements Serializable {
    private Integer bno;     //主键

    private Integer UIDop;	 //拉黑操作者

    private Integer UIDed;   //被拉黑者

    private static final long serialVersionUID = 1L;

    public Blacklist(Integer bno, Integer UIDop, Integer UIDed) {
        this.bno = bno;
        this.UIDop = UIDop;
        this.UIDed = UIDed;
    }

    public Blacklist() {
        super();
    }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public Integer getUIDop() {
        return UIDop;
    }

    public void setUIDop(Integer UIDop) {
        this.UIDop = UIDop;
    }

    public Integer getUIDed() {
        return UIDed;
    }

    public void setUIDed(Integer UIDed) {
        this.UIDed = UIDed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bno=").append(bno);
        sb.append(", UIDop=").append(UIDop);
        sb.append(", UIDed=").append(UIDed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}