package com.sojpt.boot.controller.api;

import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公司业务接口
 */
@RestController("bussiness")
@RequestMapping("/api/bussiness/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class bussiness  extends IViewController {
    /**
     * 查询大健康业务
     */
    @RequestMapping("bussinessByid")
    public  void bussiness(){

        List<Business>  businesses =Business.dao.find("SELECT  business.`name`,businessById.id,business.byid_pictures,business.businessBytitle,businessById.buid_name,businessById.picture,businessById.byid_title,businessById.byid_titles,businessById.businessId FROM businessById  JOIN business on  businessById.businessId=business.id");
        renderSuccess(businesses);
    }


    /**
     * 查询公司业务  “信息化服务”
     */
    @RequestMapping("promotion")
    public  void promotion (){

        List<Promotion>  promotions =Promotion.dao.find("SELECT  promotion.id,promotion.`name`,promotion.picture,promotion.title,promotion.titles,promotion.business_id,business.`name` as business_name,business.byid_pictures  FROM  promotion  JOIN business ON  promotion.business_id=business.id");
        renderSuccess(promotions);
    }
    /**
     * 查询公司业务  “智能化”
     */
    @RequestMapping("intellectualization")
    public  void  intellectualization(){
        List<Intellectualization> list=Intellectualization.dao.find("SELECT Intellectualization.id,Intellectualization.`name`,Intellectualization.title1,Intellectualization.picture,Intellectualization.title,Intellectualization.business_id,business.`name` as business_name,business.byid_pictures FROM Intellectualization  JOIN  business  on  Intellectualization.business_id=business.id\n");
        renderSuccess(list);
    }
    /**
     * 智能化业务 案例
     */
    @RequestMapping("intellect_case")
    public  void  intellect_case(){
        List<Intellectualization> list=Intellectualization.dao.find("SELECT * FROM intellect_case");
        renderSuccess(list);
    }

    /**
     * 查询公司业务  “大数据”
     */
    @RequestMapping("bigdata")
    public  void  bigdata(){
        List<BigData> list=BigData.dao.find("SELECT big_data.id,big_data.`name`,big_data.picture,big_data.title,big_data.title1,big_data.title2,big_data.business_id,business.`name` as business_name,business.byid_pictures  FROM big_data  JOIN  business on  big_data.business_id=business.id\n");
        renderSuccess(list);
    }
    /**
     * 查询公司业务  “供应链”
     */
    @RequestMapping("chai")
    public  void  chai(){
        List<SupplyChain> list=SupplyChain.dao.find("\n" +
                "SELECT  supply_chain.id,supply_chain.`name`,supply_chain.picture,supply_chain.business_id,supply_chain.title,supply_chain.title1 ,business.`name` as business_name,business.byid_pictures  FROM supply_chain JOIN  business on  supply_chain.business_id=business.id");
        renderSuccess(list);
    }
    /**
     * 供应链业务案例
     */
    @RequestMapping("Supply_chainfinance")
    public  void  Supply_chainfinance(){
        List<SupplyChain> list=SupplyChain.dao.find("SELECT * FROM Supply_chainfinance");
        renderSuccess(list);
    }

    /**
     * 环保服务
     */
    @RequestMapping("EnvironmentProtection")
    public  void  EnvironmentProtection(){
        List<EnvironmentProtection> list=EnvironmentProtection.dao.find("\tSELECT environment_protection.id,environment_protection.`name`,environment_protection.business_id,environment_protection.picture,environment_protection.title,business.byid_pictures,business.`name` as business_name FROM environment_protection  JOIN business on  environment_protection.business_id=business.id");
        renderSuccess(list);
    }

    /**
     * 环保服务
     */
    @RequestMapping("ringcase")
    public  void  ringcase(){
        List<Ringcase> list=Ringcase.dao.find("SELECT * FROM ringcase");
        renderSuccess(list);
    }
}
