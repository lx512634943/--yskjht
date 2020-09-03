package com.sojpt.boot.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.DicLog;

/**
 * 
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/log")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class DicLogAdminController extends IViewController {
    
	@RequestMapping("/list")
    public void list() {
        
        DicLog dicLog = getModel(DicLog.class, "dicLog");
        dicLog.put("entry_time_start_time", getPara("ENTRY_TIME_start_time"));
        dicLog.put("entry_time_end_time", getPara("ENTRY_TIME_end_time"));
        
        SqlPara sqlPara = Db.getSqlPara("admin_log.select", getKv(dicLog));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }
    
}