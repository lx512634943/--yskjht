package com.sojpt.boot.controller.admin.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sojpt.model.DicPopedom;

public class DicPopedomUpdateValidator extends Validator {

    @Override
    protected void validate(Controller c) {
        
        DicPopedom dicPopedom = c.getModel(DicPopedom.class, "dicPopedom");
        
        if(dicPopedom.get("POPEDOM_NAME") != null)
            validateString("dicPopedom.POPEDOM_NAME", 0, 50, "msg", "名称 最多50个字符");
          validateRequired("dicPopedom.POPEDOM_NAME", "msg", "请输入名称");
        if(dicPopedom.get("FORWARD_ACTION") != null)
            validateString("dicPopedom.FORWARD_ACTION", 0, 100, "msg", "URL 最多100个字符");
        if(dicPopedom.get("REMARK") != null)
            validateString("dicPopedom.REMARK", 0, 50, "msg", "备注 最多50个字符");
          validateRequired("dicPopedom.TYPE", "msg", "请输入类型");
        
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }

}