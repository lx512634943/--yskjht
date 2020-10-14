package com.sojpt.boot.controller.api;

import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.model.Business;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目案例接口
 */
@RestController("cases")
@RequestMapping("/api/cases/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class cases extends IViewController  {

    /**
     * 查询全部项目案例
     */
    @RequestMapping("list")
    public  void bussiness(){
        List<Business> businesses =Business.dao.find("SELECT  id,`name`,title,casepicture FROM project_cases");
        renderSuccess(businesses);
    }
}
