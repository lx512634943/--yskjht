package com.sojpt.boot.controller.api;


import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.Contact;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 留言添加
 */
@RestController("contat")
@RequestMapping("/api/contat/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class contat extends IViewController {


    @PostMapping("save")
    public  void save(){
        String sendnam=get("sendname");
        String telphone=get("telphone");
        String message=get("message");
        Contact contact=new Contact();
        contact.setSendname(sendnam);
        contact.setTelphone(telphone);
        contact.setMessage(message);
        contact.setSendtime(new Date());
        Boolean save=contact.save();
        renderSuccess(save);
    }
}
