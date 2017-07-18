package cn.net.ibingo.core.model;

import java.util.Date;

/**
 * Created by yuxiangjie on 2017-07-06.
 */
public class LoginLog {

    private int id;

    private String userName;

    private Date createDate;

    private Integer loginType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }
}
