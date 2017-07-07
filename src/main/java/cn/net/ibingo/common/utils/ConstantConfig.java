package cn.net.ibingo.common.utils;

import cn.net.ibingo.common.utils.PropertiesUtil;

/**
 * @author YuanLian
 */
public class ConstantConfig {

    /**
     * 接口返回参数状态码
     */
    public static final int HTTP_CODE_1000 = 1000;//成功

    public static final int HTTP_CODE_1001 = 1001;//格式不正确(手机号码校验数字，邮箱校验邮箱格式)

    public static final int HTTP_CODE_1002 = 1002;//参数为空

    public static final int HTTP_CODE_1003 = 1003;//失败

    public static final int HTTP_CODE_1004 = 1004;//参数长度不符合

    public static final int HTTP_CODE_1005 = 1005;//该用户已注册

    public static final int HTTP_CODE_1006 = 1006;//用户名或密码错误

    public static final int HTTP_CODE_1007 = 1007;//验证码错误

    public static final int HTTP_CODE_1008 = 1008;//图片大于2M

    public static final int HTTP_CODE_1009 = 1009;//手机号修改密码请联系客服

    public static final int HTTP_CODE_1010 = 1010;//文件不存在

    /**
     * 调用者类别
     */
    public static final String CALLTYPE_CENTER = "gameCenter";//游戏中心

    public static final String CALLTYPE_PHILIP = "philip";//菲律宾


    public static final String UID = "uid";

    public static final String USER = "current_user";

    /**
     * 邮件发送信息
     */
    public static String EMAIL_HOST = "";//邮件服务器

    public static String EMAIL_USERNAME = "";//邮件发送人账号

    public static String EMAIL_PASSWORD = "";//邮件发送人密码

    /**
     * 邮件发送内容
     */
    public static String EMAIL_SUBJECT = "";
    public static String EMAIL_MESSAGE = "";

    /**
     * 游戏中心游戏获取地址
     */
    public static String GAME_CENTER_URL = "";

    /**
     * 用户头像的大小
     */
    public static int IMAGE_SIZE = 0;

    static {
        EMAIL_HOST = PropertiesUtil.getInstance().getvalue("email_host");
        EMAIL_USERNAME = PropertiesUtil.getInstance().getvalue("email_username");
        EMAIL_PASSWORD = PropertiesUtil.getInstance().getvalue("email_password");
        EMAIL_SUBJECT = PropertiesUtil.getInstance().getvalue("email_subject");
        EMAIL_MESSAGE = PropertiesUtil.getInstance().getvalue("email_message");
        GAME_CENTER_URL = PropertiesUtil.getInstance().getvalue("game_center_url");
        IMAGE_SIZE = Integer.parseInt(PropertiesUtil.getInstance().getvalue("image_size"));
    }

    public static int VERIFIC_CODE_EXPIRE = 300;//验证码失效时间（秒）


    /**
     * 登录方式
     */
    public static int LOGIN_COMMON = 0;//普通登录方式
    public static int LOGIN_THIRD = 1;//第三方登录


    /**
     * 账号类型
     */
    public static int USERNAME_MOBILE = 0;//手机账号
    public static int USENAME_EMAIL = 1;//邮箱账号
    public static int USENAME_THIRD = 2;//第三方登录账号


    public final static String IMAGESPATH ="/home/ibingo/docker/advert-service";
}
