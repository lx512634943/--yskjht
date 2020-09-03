package com.sojpt.boot.controller.api;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.ui.iview.IViewController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("test")
@RequestMapping("/api/test")
public class test extends IViewController {

    @RequestMapping("/list")
    public void list() {
        renderSuccess("成功");
    }
}
