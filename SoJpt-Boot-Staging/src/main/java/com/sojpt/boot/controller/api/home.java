package com.sojpt.boot.controller.api;

import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.News;
import com.sojpt.model.Partners;
import com.sojpt.model.Slideshow;
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
        List<News>  list =News.dao.find("select * from news");
        renderSuccess(list);

    }
    /**
     * 合作伙伴
     */
    @RequestMapping("/partners")
    public  void list(){
        List<Partners> partners=Partners.dao.find("select * from  partners");
        renderSuccess(partners);
    }
}
