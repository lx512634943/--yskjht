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
import com.sojpt.model.MerchandiseNew;
import com.sojpt.model.Partners;
import com.sojpt.model.Succeed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/succeed/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class SucceedAdminController extends IViewController {

    @RequestMapping("list")
    public void list() {

        Succeed succeed = getModel(Succeed.class, "succeed");

        SqlPara sqlPara = Db.getSqlPara("admin_succeed.select", getKv(succeed));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }


    @RequestMapping("update")
    public void update() {
        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");
        Succeed succeed = getModel(Succeed.class, "succeed");
        String names=succeed.getCommodityName();
        MerchandiseNew first = merchandiseNew.findFirst("SELECT * FROM merchandise_new  WHERE commodity_name = ? ", names);
        Integer residue=first.getResidue()+succeed.getGet();

        System.out.println(succeed.getId());
        if(succeed.dao.deleteById(succeed.getId())) {
            merchandiseNew.setResidue(residue);
            merchandiseNew.setId(first.getId());    
            if(merchandiseNew.update()) {
                renderJson(Ret.ok());
            } else {
                renderJson(Ret.fail());
            }
        } else {
            renderJson(Ret.fail());
        }
    }
    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_succeed.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
}