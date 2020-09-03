package com.sojpt.boot.controller.admin;

import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.DisOrder;
import com.sojpt.model.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/admin/order1/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class SelectOrderController extends IViewController {

    @RequestMapping("list")
    public void list() {

        Order Order = getModel(Order.class, "Order");

        SqlPara sqlPara = Db.getSqlPara("selectOrder.select", getKv(Order));
        Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
        renderJson(page);
    }

}
