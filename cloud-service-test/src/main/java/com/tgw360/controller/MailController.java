package com.tgw360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")

public class MailController {

    @Autowired
    private JavaMailSender jms;

    @Value("${spring.mail.receivers}")
    private String receivers;

    @GetMapping("/send")
    public String send(){
        //建立邮件消息  
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者  
        mainMessage.setFrom("13428269649@163.com");

        String[] split = receivers.split(",");


        //接收者
        mainMessage.setTo(split);
        //发送的标题
        mainMessage.setSubject("嗨喽");
        //发送的内容  
        mainMessage.setText("hello world");

        jms.send(mainMessage);
        return "success";
    }
}  