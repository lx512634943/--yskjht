package com.sojpt.boot.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.aop.Before;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.sojpt.boot.controller.admin.validator.UserAddValidator;
import com.sojpt.boot.controller.admin.validator.UserDuplicateValidator;
import com.sojpt.boot.controller.admin.validator.UserUpdateValidator;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.constant.Const;
import com.sojpt.kit.IdKit;
import com.sojpt.model.DicUser;

/**
 * 
 * @author Sohnny
 *
 */
@RestController
@RequestMapping("/admin/user/")
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class 	UserAdminController extends IViewController {

	@RequestMapping("list")
	public void list() {
		DicUser dicUser = getModel(DicUser.class, "user");
		dicUser.put("entry_time_start_time", getPara("ENTRY_TIME_start_time"));
		dicUser.put("entry_time_end_time", getPara("ENTRY_TIME_end_time"));
		
		SqlPara sqlPara = null;
		if (isRoot()) {			
			sqlPara = Db.getSqlPara("admin_user.root_selectLike", getKv(dicUser));
		} else if (isAdmin()){
			sqlPara = Db.getSqlPara("admin_user.selectLike", getKv(dicUser));
		} else {
			//普通用户
		}
		
		Page<Record> page = Db.paginate(pageNumber(), pageSize(), sqlPara);
		renderJson(page);
	}
	
	@RequestMapping("save")
	@Before({UserDuplicateValidator.class, UserAddValidator.class})
	public void save() {
		DicUser dicUser = getModel(DicUser.class, "user");

		String password = dicUser.getPasswd();

		// 密码加盐 hash
		String salt = HashKit.generateSaltForSha256();
		password = HashKit.sha256(salt + password);
		dicUser.setSalt(salt);
		dicUser.setPasswd(password);
		dicUser.setCreaterId(getSessionUser().getId());
		dicUser.setId(IdKit.generatorID());
		dicUser.setEntryTime(new Date());

		if(dicUser.save()) {
			renderJson(Ret.ok("row", dicUser));
		} else {
			renderJson(Ret.fail());
		}
	}
	
	@RequestMapping("update")
	@Before({UserDuplicateValidator.class, UserUpdateValidator.class})
	public void update() {
		DicUser dicUser = getModel(DicUser.class, "user");
		if(dicUser.update()) {
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail());
		}
	}
	
	@RequestMapping("delete")
	public void delete() {
		DicUser dicUser = getModel(DicUser.class, "user");
		if(DicUser.dao.deleteById(dicUser.getId())) {
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail());
		}
	}
	
	@RequestMapping("batchDelete")
	public void batchDelete() {
		String[] ids = getPara("ids").split(",");
		SqlPara sqlPara = Db.getSqlPara("admin_user.deleteByIds", Kv.by("idList", Arrays.asList(ids)));
		if(Db.update(sqlPara) > 0) {
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail());
		}
	}
	
	@RequestMapping("resetPwd")
	public void resetPwd() {
		DicUser dicUser = getModel(DicUser.class, "user");
		String password = dicUser.getPasswd();
		String pwd = password;
		// 密码加盐 hash
		String salt = HashKit.generateSaltForSha256();
		password = HashKit.sha256(salt + password);
		dicUser.setSalt(salt);
		dicUser.setPasswd(password);
		
		if(dicUser.update()) {
			renderJson(Ret.ok("msg", "密码重置为: "+pwd));
		} else {
			renderJson(Ret.fail());
		}
	}
	
	@RequestMapping("roleListByUserId")
    public void roleListByUserId() {
    	SqlPara sqlPara = null;
    	if (isRoot()) {    		
    		sqlPara = Db.getSqlPara("admin_user.root_roleListByUserId", getPara("id"));
    	} else if (isAdmin()) {
    		sqlPara = Db.getSqlPara("admin_user.roleListByUserId", getPara("id"));
    	} else {
    		//普通用户
    	}
    	renderJson(Db.find(sqlPara));
    }
    
    @Before(Tx.class)
    @RequestMapping("saveRole")
    public void saveRole() {
    	String userId = getPara("userId");
    	String[] roleIds = getPara("roleIds").split(",");
    	
    	List<Record> recordList = new ArrayList<>();
    	
    	for (int i = 0; i < roleIds.length; i++) {
    		if (StrKit.notBlank(roleIds[i]))
    			recordList.add(new Record().set("ID", IdKit.generatorID()).set("USER_ID", userId).set("ROLE_ID", roleIds[i]));
		}
    	
    	SqlPara sqlPara = Db.getSqlPara("admin_user_role.deleteByUserId", userId);
    	boolean b = Db.update(sqlPara) >= 0;
    	
    	if (b && Db.batchSave("dic_user_role", recordList, recordList.size()).length >= 0) {
    		renderJson(Ret.ok());
    	} else {
    		renderJson(Ret.fail());    		
    	}
    }
    
    @Before(Tx.class)
    @RequestMapping("batchSaveRole")
    public void batchSaveRole() {
    	String[] userIds = getPara("userIds").split(",");
    	String[] roleIds = getPara("roleIds").split(",");
    	
    	List<Record> recordList = new ArrayList<>();
    	
    	for (int i = 0; i < userIds.length; i++) {
    		if (StrKit.notBlank(userIds[i]))
    		for (int j = 0; j < roleIds.length; j++) {
    			if (StrKit.notBlank(roleIds[j]))
    				recordList.add(new Record().set("ID", IdKit.generatorID()).set("USER_ID", userIds[i]).set("ROLE_ID", roleIds[j]));
    		}
		}
    	
    	SqlPara sqlPara = Db.getSqlPara("admin_user_role.deleteByUserIds", Kv.by("idList", Arrays.asList(userIds)));
    	boolean b = Db.update(sqlPara) >= 0;
    	
    	if (b && Db.batchSave("dic_user_role", recordList, recordList.size()).length >= 0) {
    		renderJson(Ret.ok());
    	} else {
    		renderJson(Ret.fail());    		
    	}
    }
    
    @RequestMapping("isDisable")
	public void isDisable() {
		DicUser dicUser = new DicUser();
		dicUser.setId(getPara("id"));
		dicUser.setIsDisabled(new BigDecimal(getPara("is_disabled")));
		if(dicUser.update()) {
			renderJson(Ret.ok());
		} else {
			renderJson(Ret.fail());
		}
	}
	
    @RequestMapping("updatePwd")
	public void updatePwd() {
		
		String oldPwd = getPara("oldPassword");
		DicUser sessionUser = getSessionUser();
		String oldSalt = sessionUser.getSalt();
		
		if (HashKit.sha256(oldSalt + oldPwd).equals(sessionUser.getPasswd())) {
			sessionUser.setPasswd(HashKit.sha256(oldSalt + getPara("newPassword")));
			if (sessionUser.update()) {
				setSessionAttr(Const.session_username, sessionUser);
				renderJson(Ret.ok());
			} else {
				renderJson(Ret.fail("msg", "修改失败"));
			}
		} else {
			renderJson(Ret.fail("msg", "旧密码输入错误"));
		}
	}
    
}
