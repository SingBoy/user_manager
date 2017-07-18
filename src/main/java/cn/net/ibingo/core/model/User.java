package cn.net.ibingo.core.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by yuxiangjie on 2017-07-05.
 */
public class User {

    private int id;

    private String userName;//用户账号名

    private String passWord;//密码

    private String userId;//用户id

    private String nickName;//昵称

    private Integer sex;//性别

    private Date birthDate;//出生日期

    private String country;//国家

    private String imageUrl;//头像Url

    private String callerType;//调用者类别（因为接口为公共接口，此字段为区别不同软件调用，游戏中心固定值：gameCenter。菲律宾固定值：philip）

    private String softName;//跳转前软件名称，（游戏中心直接注册时为空，菲律宾方面通过其他应用软件跳转到游戏中心注册时需带上该参数）

    private String imsi;//imsi

    private String deviceNo;//设备编号

    private String imei;//imei 设备序列号

    private Integer userNameType;//账号类型0:手机账号，1:邮箱账号，2:第三方登录账号

    private Date createDate;

    private String verificCode;

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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCallerType() {
        return callerType;
    }

    public void setCallerType(String callerType) {
        this.callerType = callerType;
    }

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getUserNameType() {
        return userNameType;
    }

    public void setUserNameType(Integer userNameType) {
        this.userNameType = userNameType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getVerificCode() {
        return verificCode;
    }

    public void setVerificCode(String verificCode) {
        this.verificCode = verificCode;
    }
}
