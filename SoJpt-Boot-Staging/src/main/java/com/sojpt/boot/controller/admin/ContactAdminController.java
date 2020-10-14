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
import com.sojpt.model.Contact;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/contact/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class ContactAdminController extends IViewController {




    @RequestMapping("list")
    public void list() {

        Contact contact = getModel(Contact.class, "contact");

        SqlPara sqlPara = Db.getSqlPara("admin_contact.select", getKv(contact));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }

    @RequestMapping("delete")
    public void delete() {
        Contact contact = getModel(Contact.class, "contact");
        if(Contact.dao.deleteById(contact.getId())) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_contact.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

}