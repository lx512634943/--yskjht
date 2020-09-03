package com.sojpt.boot.controller.admin;


import java.util.Arrays;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.Partnerkinds;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/partnerkinds/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class PartnerkindsAdminController extends IViewController {

    @RequestMapping("list")
    public void list() {

        Partnerkinds partnerkinds = getModel(Partnerkinds.class, "partnerkinds");

        SqlPara sqlPara = Db.getSqlPara("admin_partnerkinds.select", getKv(partnerkinds));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }


    public void delete() {
        Partnerkinds partnerkinds = getModel(Partnerkinds.class, "partnerkinds");
        if(Partnerkinds.dao.deleteById(partnerkinds.getId())) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_partnerkinds.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

}