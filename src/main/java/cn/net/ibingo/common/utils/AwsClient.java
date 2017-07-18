package cn.net.ibingo.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 数据校验
 * @author yuxiangjie
 */

public class CheckUtil {


    /**
     * 校验邮件地址
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;  
        try {  
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);  
            Matcher matcher = regex.matcher(email);  
            flag = matcher.matches();  
        } catch (Exception e) {  
            flag = false;  
        }
        return flag;  
    }


    /**
     * 校验手机号码
     */
    public static boolean checkMobileNumber(String mobileNumber) {  
        boolean flag = false;  
        try {  
            //Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Pattern regex = Pattern.compile("[0-9]*");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();  
        } catch (Exception e) {  
            flag = false;  
        }  
        return flag;  
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static boolean stringLength(String value,int length) {
        boolean flg = true;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        if(valueLength > length){
            return false;
        }
        return true;
    }

}
