package com.douban.book.common;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMesage  {
    public void sendInformationError(String Accept)throws MessagingException {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");
                props.put("mail.smtp.starttls.enable", "true");
        // 发件人的账号
        props.put("mail.user", "723652352@qq.com");
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "szruysyzzxhjbdbc");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(Accept);
        message.setRecipient(Message.RecipientType.TO, to);

        // 设置抄送，抄送和密送如果不写正确的地址也要报错。最好注释不用。
//        InternetAddress cc = new InternetAddress("");
//        message.setRecipient(RecipientType.CC, cc);
//
//        // 设置密送，其他的收件人不能看到密送的邮件地址
//        InternetAddress bcc = new InternetAddress("");
//        message.setRecipient(RecipientType.CC, bcc);
        // 设置邮件标题
        message.setSubject("BookInformation爬虫信息反馈");
        // 设置邮件的内容体
        message.setContent("<a href='http://www.XXX.org'>BookInformation爬虫出现了问题，请立即解决</a>", "text/html;charset=UTF-8");
        // 发送邮件
        Transport.send(message);
    }
    public void sendCommentError(String Accept)throws MessagingException {
        final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // 发件人的账号
        props.put("mail.user", "723652352@qq.com");
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "szruysyzzxhjbdbc");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(Accept);
        message.setRecipient(Message.RecipientType.TO, to);

        // 设置抄送，抄送和密送如果不写正确的地址也要报错。最好注释不用。
//        InternetAddress cc = new InternetAddress("");
//        message.setRecipient(RecipientType.CC, cc);
//
//        // 设置密送，其他的收件人不能看到密送的邮件地址
//        InternetAddress bcc = new InternetAddress("");
//        message.setRecipient(RecipientType.CC, bcc);
        // 设置邮件标题
        message.setSubject("CommentError爬虫信息反馈");
        // 设置邮件的内容体
        message.setContent("<a href='http://www.XXX.org'>CommentError爬虫出现了问题，请立即解决</a>", "text/html;charset=UTF-8");
        // 发送邮件
        Transport.send(message);
    }
}

