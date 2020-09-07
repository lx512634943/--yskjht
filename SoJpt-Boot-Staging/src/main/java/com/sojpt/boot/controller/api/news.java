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
 *新闻接口
 */
@RestController("news")
@RequestMapping("/api/news")
@CrossOrigin(origins = "*", maxAge = 3600)
public class news extends IViewController {
    /**
     *查询全部新闻
     */
     @RequestMapping("listnews")
     public  void  listnews(){
         List<News> list =News.dao.find("select * from news");
         renderSuccess(list);
     }

    /**
     *企业资讯接口
     */
    @RequestMapping("/Corporate_news")
    public void  news(){
     List<News> list=News.dao.find("SELECT * FROM  news   WHERE nkid = 1 ");
     renderSuccess(list);
    }
    /**
     *一带一路新闻接口
     */
    @RequestMapping("/Road_Initiative")
    public void  Road_Initiative(){
        List<News> list=News.dao.find("SELECT * FROM  news   WHERE nkid = 2 ");
        renderSuccess(list);
    }
    /**
     *行业新闻
     */
    @RequestMapping("/Industry_news")
    public void  Industry_news(){
        List<News> list=News.dao.find("SELECT * FROM  news   WHERE nkid = 3");
        renderSuccess(list);
    }
    /**
     * 根据id查询指定新闻
     */
    @RequestMapping("/selectId")
    public  void  SelectId(){
        Integer id=getInt("id");
        List<News> list  =News.dao.find("select * from news  where id=?",id);
        renderSuccess(list);
    }
    /**
     *根据标题查询新闻资讯
     */
    @RequestMapping("/selectTitle")
    public  void SelectTitile(){
        String title=get("title");
        List<News> list  =News.dao.find("SELECT * FROM news WHERE title LIKE  '%"+title+"% ' ");
        renderSuccess(list);
    }
}
