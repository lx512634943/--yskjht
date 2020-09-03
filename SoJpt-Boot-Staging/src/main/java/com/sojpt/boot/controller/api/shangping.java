package com.sojpt.boot.controller.api;

import com.jfinal.kit.Ret;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.Partners;
import com.sojpt.model.Shangping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("shangping")
@RequestMapping("/api/shangping")
public class shangping extends IViewController {

    @PostMapping("/save")
    public void save() {
//
//        Shangping shangping = getModel(Shangping.class, "shangping");
//        shangping.setTime(new Date());
//        if(shangping.save()) {
//            renderJson(Ret.ok());
//        } else {
//            renderJson(Ret.fail());
//        }
        String sname=get("sname");
        String uname=get("uname");
        
        Integer number=getInt("number");
        String beizhu=get("beizhu");
        Shangping shangping=new Shangping();
        shangping.setSname(sname);
        shangping.setUname(uname);
        shangping.setTime(new Date());
        shangping.setZhuangtai(2);
        shangping.setNumber(number);
        shangping.setBeizhu(beizhu);
        Boolean save=shangping.save();
        renderSuccess(save);
    }
}
