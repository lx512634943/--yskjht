package com.sojpt.boot.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sojpt.boot.controller.admin.validator.DicRoleAddValidator;
import com.sojpt.boot.controller.admin.validator.DicRoleUpdateValidator;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.kit.IdKit;
import com.sojpt.model.DicRole;

/**
 * 
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/role/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class DicRoleAdminController extends IViewController {
    
	@RequestMapping("list")
    public void list() {
        
        DicRole dicRole = getModel(DicRole.class, "dicRole");
        dicRole.setParentId("0");
        
        if (isFindChild()) dicRole.setParentId(getPara("id"));
        dicRole.put("entry_time_start_time", getPara("ENTRY_TIME_start_time"));
        dicRole.put("entry_time_end_time", getPara("ENTRY_TIME_end_time"));
        
        SqlPara sqlPara = null;
        if (isRoot()) {        	
        	sqlPara = Db.getSqlPara("admin_role.root_select", getKv(dicRole));
        } else if (isAdmin()) {
        	sqlPara = Db.getSqlPara("admin_role.select", getKv(dicRole));        	
        } else {
			//普通用户
		}
        
        if (isFindChild()) {
        	renderJson(Db.find(sqlPara));
        } else {        	
        	Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        	renderJson(page);
        }
    }
    
    @Before(DicRoleAddValidator.class)
    @RequestMapping("save")
    public void save() {
        DicRole dicRole = getModel(DicRole.class, "dicRole");
        dicRole.setId(IdKit.generatorID());
        dicRole.setCreaterId(getSessionUser().getId());
        dicRole.setEntryTime(new Date());
        if(dicRole.save()) {        	
            renderJson(Ret.ok().set("row", dicRole));
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @Before(DicRoleUpdateValidator.class)
    @RequestMapping("update")
    public void update() {
        DicRole dicRole = getModel(DicRole.class, "dicRole");
        if(dicRole.update()) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @RequestMapping("delete")
    public void delete() {
        DicRole dicRole = getModel(DicRole.class, "dicRole");
        SqlPara sqlPara = Db.getSqlPara("admin_role.deleteChildById", dicRole.getId());
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @RequestMapping("batchDelete")
    public void batchDelete() {
        String[] ids = getPara("ids").split(",");
        SqlPara sqlPara = Db.getSqlPara("admin_role.deleteChildByIds", Kv.by("idList", Arrays.asList(ids)));
        if(Db.update(sqlPara) > 0) {
            renderJson(Ret.ok());
        } else {
            renderJson(Ret.fail());
        }
    }
    
    @RequestMapping("popedomListByRoleId")
    public void popedomListByRoleId() {
    	SqlPara sqlPara;
    	
    	if (isRoot()) {    		
    		sqlPara = Db.getSqlPara("admin_role.rootPopedomListByRoleId", getPara("id"));
    	} else {    		
    		sqlPara = Db.getSqlPara("admin_role.popedomListByRoleId", getSessionUser().getId(), getPara("id"));
    	}
    	renderJson(Db.find(sqlPara));
    }
    
    @Before(Tx.class)
    @RequestMapping("savePopedom")
    public void savePopedom() {
    	String roleId = getPara("roleId");
    	String[] ids = getPara("popedomIds").split(",");
    	
    	List<Record> recordList = new ArrayList<>();
    	
    	for (int i = 0; i < ids.length; i++) {
    		if (StrKit.notBlank(ids[i]))
    			recordList.add(new Record().set("ID", IdKit.generatorID()).set("ROLE_ID", roleId).set("POPEDOM_ID", ids[i]));
		}
    	
    	SqlPara sqlPara = Db.getSqlPara("admin_role_popedom.deleteByRoleId", roleId);
    	boolean b = Db.update(sqlPara) >= 0;
    	
    	if (b && Db.batchSave("dic_role_popedom", recordList, recordList.size()).length >= 0) {
    		renderJson(Ret.ok());
    	} else {
    		renderJson(Ret.fail());    		
    	}
    }
    
    @Before(Tx.class)
    @RequestMapping("batchSavePopedom")
    public void batchSavePopedom() {
    	String[] roleIds = getPara("roleIds").split(",");
    	String[] popedomIds = getPara("popedomIds").split(",");
    	
    	List<Record> recordList = new ArrayList<>();
    	
    	for (int i = 0; i < roleIds.length; i++) {
    		if (StrKit.notBlank(roleIds[i]))
	    		for (int j = 0; j < popedomIds.length; j++) {
					if (StrKit.notBlank(popedomIds[j]))
	    				recordList.add(new Record().set("ID", IdKit.generatorID()).set("ROLE_ID", roleIds[i]).set("POPEDOM_ID", popedomIds[j]));
	    		}
		}
    	
    	SqlPara sqlPara = Db.getSqlPara("admin_role_popedom.deleteByRoleIds", Kv.by("idList", Arrays.asList(roleIds)));
    	boolean b = Db.update(sqlPara) >= 0;
    	
    	if (b && Db.batchSave("dic_role_popedom", recordList, recordList.size()).length >= 0) {
    		renderJson(Ret.ok());
    	} else {
    		renderJson(Ret.fail());    		
    	}
    }
    
}