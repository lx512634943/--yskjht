package com.sojpt.boot.controller.api;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.Partners;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 合作伙伴详情接口
 */
@RestController("partners")
@RequestMapping("/api/partners")
@CrossOrigin(origins = "*", maxAge = 3600)
public class partners extends IViewController {

    /**
     * 根据id查询
     */
    @RequestMapping("/partnersByid")
    public  void  list(){
        Integer id =getInt("id");
        List<Partners> partners=Partners.dao.find("select * from partners  where id =?",id);
        renderSuccess(partners);
    }

    /**
     * 政府部分
     */
    @RequestMapping("/Governments")
    public  void  Governments(){
        List<Partners> partners=Partners.dao.find("select * from partners  where pkid =1 ");
        renderSuccess(partners);
    }
    /**
     * 智库部门
     */
    @RequestMapping("/Think_tanks")
    public  void  Think_tanks(){
        List<Partners> partners=Partners.dao.find("select * from partners  where pkid =2 ");
        renderSuccess(partners);

    }
    /**
     * 企业部门
     */
    @RequestMapping("/Enterprises")
    public  void  Enterprises(){
        List<Partners> partners=Partners.dao.find("select * from partners  where pkid =3 ");
        renderSuccess(partners);
    }
    /**
     *金融部门
     */
    @RequestMapping("/Financial")
    public  void  Financial (){
        List<Partners> partners=Partners.dao.find("select * from partners  where pkid =4 ");
        renderSuccess(partners);
    }
    /**
     *媒体部门
     */
    @RequestMapping("/Medias")
    public  void  Medias (){
        List<Partners> partners=Partners.dao.find("select * from partners  where pkid =5 ");
        renderSuccess(partners);
    }
}
