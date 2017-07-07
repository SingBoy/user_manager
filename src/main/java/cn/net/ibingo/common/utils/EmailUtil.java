package cn.net.ibingo.common.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import java.util.Properties;

/**
 * Created by yuxiangjie on 2017-07-06.
 */
public class EmailUtil {

    public static void sendMessage(String to, String subject,
                                   String messageText) throws MessagingException {
        // 第一步：配置javax.mail.Session对象
        Properties props = new Properties();
        props.put("mail.smtp.host", ConstantConfig.EMAIL_HOST);
        props.put("mail.smtp.starttls.enable","true");//使用 STARTTLS安全连接
        //props.put("mail.smtp.port", "25");             //google使用465或587端口
        props.put("mail.smtp.auth", "true");        // 使用验证
        //props.put("mail.debug", "true");
        Session mailSession = Session.getInstance(props,new MyAuthenticator(ConstantConfig.EMAIL_USERNAME,ConstantConfig.EMAIL_PASSWORD));

        // 第二步：编写消息
        InternetAddress fromAddress = new InternetAddress(ConstantConfig.EMAIL_USERNAME);
        InternetAddress toAddress = new InternetAddress(to);

        MimeMessage message = new MimeMessage(mailSession);

        message.setFrom(fromAddress);
        message.addRecipient(Message.RecipientType.TO, toAddress);

        message.setSentDate(Calendar.getInstance().getTime());
        message.setSubject(subject);
        message.setContent(messageText, "text/html;charset=UTF-8");

        // 第三步：发送消息
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(ConstantConfig.EMAIL_HOST,ConstantConfig.EMAIL_USERNAME, ConstantConfig.EMAIL_PASSWORD);
        transport.send(message, message.getRecipients(Message.RecipientType.TO));
    }

    public static void main(String[] args) {
        try {
            sendMessage("i_xiangj@163.com", "nihao",
                    "---------------wrwe-----------");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    static class MyAuthenticator extends Authenticator {
        String userName="";
        String password="";
        public MyAuthenticator(){
        }
        public MyAuthenticator(String userName,String password){
            this.userName=userName;
            this.password=password;
        }
        protected PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(userName, password);
        }
    }
}
