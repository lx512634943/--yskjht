package com.sojpt.boot.controller.admin;

import java.util.Arrays;
import java.util.Date;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

import com.sojpt.boot.controller.admin.validator.UserDuplicateValidator;
import com.sojpt.boot.controller.admin.validator.UserUpdateValidator;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.kit.IdKit;
import com.sojpt.model.DicUser;
import com.sojpt.model.MerchandiseNew;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/new/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class MerchandiseNewAdminController extends IViewController {

    @RequestMapping("list")
    public void list() {

        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");

        SqlPara sqlPara = Db.getSqlPara("admin_merchandise.select", getKv(merchandiseNew));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }

    @RequestMapping("list1")
    public void list1() {

        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");

        SqlPara sqlPara = Db.getSqlPara("admin_merchandise.list", getKv(merchandiseNew));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }

    @RequestMapping("update")
    public void update() {
        MerchandiseNew  merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");
        if(merchandiseNew.update()) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }


    @RequestMapping("save")
    public void save() {
        MerchandiseNew latestNews = getModel(MerchandiseNew.class, "merchandiseNew");
        if(latestNews.save()) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    @RequestMapping("delete")
    public void delete() {
        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");
        if(MerchandiseNew.dao.deleteById(merchandiseNew.getId())) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_new.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

}