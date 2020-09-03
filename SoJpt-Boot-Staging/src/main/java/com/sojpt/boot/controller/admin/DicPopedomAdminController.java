package com.sojpt.boot.controller.admin;

import java.util.Arrays;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.controller.admin.service.DicPopedomAdminService;
import com.sojpt.boot.controller.admin.validator.DicPopedomAddValidator;
import com.sojpt.boot.controller.admin.validator.DicPopedomUpdateValidator;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.kit.IdKit;
import com.sojpt.model.DicPopedom;

/**
 * 
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/popedom/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class DicPopedomAdminController extends IViewController {
	
	DicPopedomAdminService srv = DicPopedomAdminService.me;

	@RequestMapping("list")
    public void list() {
        
        DicPopedom dicPopedom = getModel(DicPopedom.class, "dicPopedom");
        dicPopedom.setParentId("0");
        
        if (isFindChild()) dicPopedom.setParentId(getPara("id"));
        dicPopedom.put("entry_time_start_time", getPara("ENTRY_TIME_start_time"));
        dicPopedom.put("entry_time_end_time", getPara("ENTRY_TIME_end_time"));
        
        SqlPara sqlPara = Db.getSqlPara("admin_popedom.select", getKv(dicPopedom));
        
        if (isFindChild()) {
        	renderJson(Db.find(sqlPara));
        } else {        	
        	Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        	renderJson(page);
        }
    }
    
	@Before(DicPopedomAddValidator.class)
	@RequestMapping("save")
    public void save() {
        DicPopedom dicPopedom = getModel(DicPopedom.class, "dicPopedom");
        dicPopedom.setId(IdKit.generatorID());
        dicPopedom.setCreaterId(getSessionUser().getId());
        dicPopedom.setEntryTime(new Date());
        if(dicPopedom.save()) {
            renderJson(Ret.ok().set("row", dicPopedom));
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @Before(DicPopedomUpdateValidator.class)
    @RequestMapping("update")
    public void update() {
        DicPopedom dicPopedom = getModel(DicPopedom.class, "dicPopedom");
        if(dicPopedom.update()) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @Before(DicPopedomUpdateValidator.class)
    @RequestMapping("delete")
    public void delete() {
        DicPopedom dicPopedom = getModel(DicPopedom.class, "dicPopedom");
        SqlPara sqlPara = Db.getSqlPara("admin_popedom.deleteChildById", dicPopedom.getId());
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @RequestMapping("batchDelete")
    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_popedom.deleteChildByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    @RequestMapping("syncPopedom")
    public void syncPopedom() {
    	String id = getPara("id");
    	String url = getPara("url");
    	Ret ret = srv.sync(this, id, url);
		renderJson(ret);
    }
    
}