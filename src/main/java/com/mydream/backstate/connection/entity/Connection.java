package com.mydream.backstate.connection.entity;

/**
 * Created by  lpw'ASUS on 2018/7/27.
 *
 * 数据库连接信息实体类
 */
public class Connection {
    // 主键
    private int conid;
    // 连接名称
    private String conname;
    // 连接类型 1 MySql,
    private String contype;
    // IP 地址
    private String conip;
    // 端口
    private String conport;
    // 用户名
    private String conusername;
    // 密码
    private String conpassword;
    // 数据库名称
    private String condbname;

    public Connection() {
        super();
    }

    public Connection(String conname, String contype, String conip, String conport, String conusername, String conpassword, String condbname) {
        this.conname = conname;
        this.contype = contype;
        this.conip = conip;
        this.conport = conport;
        this.conusername = conusername;
        this.conpassword = conpassword;
        this.condbname = condbname;
    }

    public Connection(int conid, String conname, String contype, String conip, String conport, String conusername, String conpassword, String condbname) {
        this.conid = conid;
        this.conname = conname;
        this.contype = contype;
        this.conip = conip;
        this.conport = conport;
        this.conusername = conusername;
        this.conpassword = conpassword;
        this.condbname = condbname;
    }

    public int getConid() {
        return conid;
    }

    public void setConid(int conid) {
        this.conid = conid;
    }

    public String getConname() {
        return conname;
    }

    public void setConname(String conname) {
        this.conname = conname;
    }

    public String getContype() {
        return contype;
    }

    public void setContype(String contype) {
        this.contype = contype;
    }

    public String getConip() {
        return conip;
    }

    public void setConip(String conip) {
        this.conip = conip;
    }

    public String getConport() {
        return conport;
    }

    public void setConport(String conpost) {
        this.conport = conpost;
    }

    public String getConusername() {
        return conusername;
    }

    public void setConusername(String conusername) {
        this.conusername = conusername;
    }

    public String getConpassword() {
        return conpassword;
    }

    public void setConpassword(String conpassword) {
        this.conpassword = conpassword;
    }

    public String getCondbname() {
        return condbname;
    }

    public void setCondbname(String condbname) {
        this.condbname = condbname;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "conid=" + conid +
                ", conname='" + conname + '\'' +
                ", contype='" + contype + '\'' +
                ", conip='" + conip + '\'' +
                ", conpost='" + conport + '\'' +
                ", conusername='" + conusername + '\'' +
                ", conpassword='" + conpassword + '\'' +
                ", condbname='" + condbname + '\'' +
                '}';
    }
}
