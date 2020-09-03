package com.sojpt.boot.controller.api;

import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.Icons;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 招聘信息接口
 */
@RestController("icons")
@RequestMapping("/api/icons")
@CrossOrigin(origins = "*", maxAge = 3600)
public class incons extends IViewController {

    @RequestMapping("/selectList")
    public  void  icons(){
        List<Icons>  icons =Icons.dao.find("SELECT employment.id,icons.icon,employment.releasedate,employment.pnumber,employment.skill,icons.id as ids ,icons.icname FROM icons JOIN employment on icons.id=employment.iconid");
        renderSuccess(icons);
    }

}
