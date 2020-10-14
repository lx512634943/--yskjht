package com.sojpt.boot.controller.api;

import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主页接口
 */
@RestController("home")
@RequestMapping("/api/home")
@CrossOrigin(origins = "*", maxAge = 3600)
public class home extends IViewController {

    /**
     * 轮播图接口
     */
    @RequestMapping("/slideshow")
    public void slideshow() {
        List<Slideshow>  list =Slideshow.dao.find("select * from slideshow");
        renderSuccess(list);

    }

    /**
     * 新闻接口
     */
    @RequestMapping("/news")
    public void news() {
        List<News>  list =News.dao.find("SELECT newsindex.id,newsindex.pictures,news.title,news.outline,newsindex.newsid FROM newsindex JOIN news on news.id=newsindex.newsid   ");
        renderSuccess(list);

    }
    /**
     * 合作伙伴
     */
    @RequestMapping("/partners")
    public  void list(){
        List<Partners> partners=Partners.dao.find("select * from  partners_index");
        renderSuccess(partners);
    }

    /**
     * 项目案例
     */
    @RequestMapping("/cases")
    public  void cases(){
        List<ProjectCases> projectCases =ProjectCases.dao.find("select  * from project_cases");
        renderSuccess(projectCases);

    }
    /**
     * 公司业务
     */
    @RequestMapping("/business")
    public  void business(){
        List<Business> businesses=Business.dao.find ("select  * from  business");
        renderSuccess(businesses);
    }
    /**
     * 公司业务虚浮
     */
    @RequestMapping("/businessBYid")
    public  void businessBYid(){
        Integer id=getInt("id");
        List<Business> businesses=Business.dao.find("SELECT businessById.picture,business.businessBytitle FROM businessById JOIN business on businessById.businessId=business.id WHERE businessById.businessId=?",id);
        renderSuccess(businesses);
    }

    /**
     * 水环境质量地图展示
     */
    @RequestMapping("/quality")
    public  void quality(){

        List<Quality> businesses=Quality.dao.find("SELECT *FROM quality");
        renderSuccess(businesses);
    }
}
