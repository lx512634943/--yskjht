package com.sojpt.boot.controller.api;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
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
     * 根据id查询指定新闻类型
     */
    @RequestMapping("/listNkid")
    public  void  listNkid(){
        Integer nkid=getInt("nkid");
        SqlPara sqlPara = Db.getSqlPara("admin_news.selectKid",Kv.by("nkid",nkid));

        Page<Record> page = Db.paginate(pageNumber(), 9, sqlPara);
      renderSuccess(page);
    }
    /**
     * 查询全部新闻接口
     */
    @RequestMapping("/lsit")
    public void  lsit(){
        List<News> list=News.dao.find("SELECT * FROM  news  ");
        renderSuccess(list);
    }
    /**
     *企业资讯接口
     */
    @RequestMapping("/Corporate_news")
    public void  news(){
     List<News> list=News.dao.find("SELECT * FROM  news   WHERE nkid = 2 ");
     renderSuccess(list);
    }
    /**
     *一带一路新闻接口
     */
    @RequestMapping("/Road_Initiative")
    public void  Road_Initiative(){
        List<News> list=News.dao.find("SELECT * FROM  news   WHERE nkid = 3 ");
        renderSuccess(list);
    }
    /**
     *媒体报道
     */
    @RequestMapping("/Industry_news")
    public void  Industry_news(){
        List<News> list=News.dao.find("SELECT * FROM  news   WHERE nkid = 1");
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
     *根据标题查询全部新闻资讯
     */
    @RequestMapping("/selectTitle")
    public  void SelectTitile(){
        String title=get("title");
        List<News> list  =News.dao.find("SELECT * FROM news WHERE title LIKE  '%"+title+"%' ");
        renderSuccess(list);
    }
    /**
     *根据标题,id查询指定类型新闻资讯
     */
    @RequestMapping("/selectTitleAll")
    public  void SelectTitileAll(){
        String title=get("title");
        Integer id=getInt("id");
        if (id==4){
            List<News> list  =News.dao.find("SELECT * FROM news WHERE title LIKE  '%"+title+"%' ");
            renderSuccess(list);
        }else {
            List<News> list  =News.dao.find("SELECT * FROM news WHERE title LIKE  '%"+title+"%'  && nkid=?",id);
            renderSuccess(list);

        }


    }

}
