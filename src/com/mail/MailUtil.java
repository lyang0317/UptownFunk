package com.mail;

import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class MailUtil {  
	  
    public boolean send() {  
        // 发送email  
        HtmlEmail email = new HtmlEmail();  
        try {  
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
            email.setHostName("smtp.163.com");  
            // 字符编码集的设置  
            email.setCharset("UTF-8");  
            // 收件人的邮箱  
            email.addTo("703956653@qq.com");  
            // 发送人的邮箱  
            email.setFrom("18224094305@163.com");  
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication("18224094305@163.com", "popohao");  
            // 要发送的邮件主题  
            email.setSubject("sapdata报警");  
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg("出现异常"+new Date().toString());  
            // 发送  
            email.send();  
            return true;  
        } catch (EmailException e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
  
    public static void main(String[] args) {  
    	new MailUtil().send();  
    }  
}  

