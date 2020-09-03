package com.sojpt.boot.controller.admin;

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
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.MerchandiseNew;
import com.sojpt.model.Partners;
import com.sojpt.model.Shangping;
import com.sojpt.model.Succeed;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/shangping/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class ShangpingAdminController extends IViewController {


    @RequestMapping("list")
    public void list() {
        Shangping shangping = getModel(Shangping.class, "shangping");
        SqlPara sqlPara = Db.getSqlPara("admin_shangping.select", getKv(shangping));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }




    @RequestMapping("save")
    public void save() {
        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");
        Shangping shangping = getModel(Shangping.class, "shangping");
        System.out.println(shangping.getSname());
        System.out.println(shangping.getUname());
        System.out.println(shangping.getNumber());

        shangping.setTime(new Date());
        shangping.setZhuangtai(2);
        String names=shangping.getSname();
        MerchandiseNew first = merchandiseNew.findFirst("SELECT * FROM merchandise_new  WHERE commodity_name = ? ", names);
        if(first.getResidue()>=shangping.getNumber()){
                    if(shangping.save()) {
                    renderJson(Ret.ok());
                 } else {
                renderJson(Ret.fail());
                 }
        }else {
            renderJson(Ret.fail());
        }

    }
    @RequestMapping("update")
    public void update() {
        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");
        Shangping shangping = getModel(Shangping.class, "shangping");
        System.out.println(shangping.getSname());
        System.out.println(shangping.getUname());
        System.out.println(shangping.getNumber());
        shangping.setZhuangtai(3);
            if(shangping.update()) {
                renderJson(Ret.ok());
            } else {
                renderJson(Ret.fail());
            }
    }
    @RequestMapping("list1")
    public void list1() {
        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");

        SqlPara sqlPara = Db.getSqlPara("admin_merchandise.select", getKv(merchandiseNew));
        List<Partners> page = Partners.dao.find(sqlPara);
        renderJson(page);
    }


    @RequestMapping("delete")
    public void delete() {
        Shangping shangping = getModel(Shangping.class, "shangping");
        MerchandiseNew merchandiseNew = getModel(MerchandiseNew.class, "merchandiseNew");
        System.out.println(shangping.getNumber());
        System.out.println(shangping.getUname());
        System.out.println(shangping.getSname());
        System.out.println(shangping.getTime());

        String names=shangping.getSname();
        MerchandiseNew first = merchandiseNew.findFirst("SELECT * FROM merchandise_new  WHERE commodity_name = ? ", names);
        shangping.setZhuangtai(1);

        if(shangping.update()) {
            System.out.println("1");
            merchandiseNew.setId(first.getId());
             merchandiseNew.setResidue(first.getResidue()-shangping.getNumber());
            if(merchandiseNew.update()) {
                System.out.println("2");
                Succeed succeed = getModel(Succeed.class, "succeed");
                succeed.setCommodityName(shangping.getSname());
                succeed.setGetPeopel(shangping.getUname());
                succeed.setGetTime(shangping.getTime());
                succeed.setGet(shangping.getNumber());
                succeed.setResidue( first.getResidue()-shangping.getNumber());

                if(succeed.save()) {
                    renderJson(Ret.ok());
                } else {
                    renderJson(Ret.fail());
                }
            } else {
                renderJson(Ret.fail());
            }

        } else {
            renderJson(Ret.fail());
        }
    }

    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_shangping.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }

}