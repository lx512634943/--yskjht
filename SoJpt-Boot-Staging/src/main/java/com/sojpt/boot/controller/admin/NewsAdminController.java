package com.sojpt.boot.controller.admin;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.upload.UploadFile;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.kit.FileCopyKit;
import com.sojpt.kit.IdKit;
import com.sojpt.model.Newkinds;
import com.sojpt.model.News;
import com.sojpt.model.Partners;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/news/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class NewsAdminController extends IViewController {


    @RequestMapping("list")
    public void list() {

        News news = getModel(News.class, "news");

        SqlPara sqlPara = Db.getSqlPara("admin_news.select", getKv(news));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }
    @RequestMapping("save")
    public void save() {
        News news =  getModel(News.class, "news");
        news.setCreatedate(new Date());
        if(news.save()) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    @RequestMapping("update")
    public void update() {
        News news =  getModel(News.class, "news");

        if(news.update()) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    @RequestMapping("list1")
    public void list1() {

        Newkinds newkinds = getModel(Newkinds .class, "newkinds");

        SqlPara sqlPara = Db.getSqlPara("admin_newkinds.select", getKv(newkinds));
        List<Partners> page = Partners.dao.find(sqlPara);
        renderJson(page);
    }

    @Value("${arp.base-upload-path}")
    private String path;
    /**
     * 上传图片
     * @throws IOException
     */
    @RequestMapping("upload")
    public void upload() throws IOException {
        UploadFile file = getFile();
        //存储路径
        String filePath = path + "/images/all";
        String fileName = file.getFileName();
        System.out.println(fileName+"+++++++++++++++++++++++++++-+++++++++++++++++++++++++++");
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }
        //更换图片名称
        File file1 = new File(filePath + "/" + IdKit.generatorID() + fileName.substring(fileName.indexOf(".")));
        FileCopyKit.fileCopy(file.getFile(), file1);
        //删除临时文件
        file.getFile().delete();
        renderJson(Ret.ok("filePath", "all/" + file1.getName()));
        return;
    }

    //删除图片
    @RequestMapping("delImage")
    public void delMatchImage() throws IOException {
        String url = getPara("url");
        String filepath = path + "/images/" + url;
        File file = new File(filepath);
        file.delete();
        renderJson(Ret.ok());
        return;
    }
    @RequestMapping("delete")
    public void delete() {
        News news = getModel(News.class, "news");
        if(News.dao.deleteById(news.getId())) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_news.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

}