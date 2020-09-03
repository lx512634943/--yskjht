package com.sojpt.boot.controller.admin.validator;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.validate.Validator;
import com.sojpt.constant.Const;
import com.sojpt.model.DicUser;

public class UserDuplicateValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		
		DicUser dicUser = c.getModel(DicUser.class, "user");
		
		if (dicUser.getId() == null) {
			UserNameDuplicate(dicUser);
		} else {
			DicUser oldUser = DicUser.dao.findById(dicUser.getId());
			if (!oldUser.getUserCode().equals(dicUser.getUserCode()) ) {
				UserNameDuplicate(dicUser);
			}
		}
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson();
	}
	
	public void UserNameDuplicate(DicUser dicUser) {
		
		SqlPara sqlPara = Db.getSqlPara("admin_user.selectByUserCode", dicUser.getUserCode());
		Record r = Db.findFirst(sqlPara);
		
		if (r != null || Const.rootName.equals(dicUser.getUserCode())) {			
			addError("msg", "该用户名已存在!");
		}
	}

}
