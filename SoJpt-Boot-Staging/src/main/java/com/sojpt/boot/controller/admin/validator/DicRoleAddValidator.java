package com.sojpt.boot.controller.admin.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.sojpt.model.DicRole;

public class DicRoleAddValidator extends Validator {

    @Override
    protected void validate(Controller c) {
        
        DicRole dicRole = c.getModel(DicRole.class, "dicRole");
        
        if(dicRole.get("ROLE_NAME") != null)
            validateString("dicRole.ROLE_NAME", 0, 50, "msg", "角色名称 最多50个字符");
          validateRequired("dicRole.ROLE_NAME", "msg", "请输入角色名称");
        if(dicRole.get("REMARK") != null)
            validateString("dicRole.REMARK", 0, 100, "msg", "注释 最多100个字符");
          validateRequired("dicRole.TYPE", "msg", "请输入角色类型");
          validateRequired("dicRole.IS_PRIVATE", "msg", "请输入是否私有");
        
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }

}