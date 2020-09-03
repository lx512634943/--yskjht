package com.sojpt.boot.controller.admin;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.sojpt.boot.controller.admin.validator.LoginValidator;
import com.sojpt.boot.intercepter.LoginCountLimitInterceptor;
import com.sojpt.boot.intercepter.admin.AdminLoginInterceptor;
import com.sojpt.boot.intercepter.admin.AdminPopedomInterceptor;
import com.sojpt.boot.listener.SoJptSessionListener;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.constant.Const;
import com.sojpt.kit.SoJLogKit;
import com.sojpt.model.DicPopedom;
import com.sojpt.model.DicUser;

@RestController
@Before({AdminLoginInterceptor.class, AdminPopedomInterceptor.class})
public class IndexAdminController extends IViewController {
	
	
	private static final Log LOG = Log.getLog(Class.class);
	
	private static final ConcurrentMap<String, String> CURRENT_ONLINE_USER = new ConcurrentHashMap<String, String>();

	@Clear
	@RequestMapping("/admin")
	public void index() {
		render("index.html");
	}
	
	@Clear
	@Before({LoginValidator.class, LoginCountLimitInterceptor.class})
	@RequestMapping("/admin/signIn")
	public void signIn() {
		DicUser dicUser = getModel(DicUser.class, "user");
		DicUser sUser = new DicUser();
		sUser.setUserCode(dicUser.getUserCode());
		SqlPara sqlPara = Db.getSqlPara("admin_user.selectEqual", getKv(sUser));
		DicUser saltUser  = DicUser.dao.findFirst(sqlPara);
		if (saltUser != null) {			
			String password = HashKit.sha256(saltUser.getSalt() + dicUser.getPasswd());
			dicUser.setPasswd(password);
			dicUser.setIsDisabled(Const.user_not_disabled);
			SqlPara sqlPara2 = Db.getSqlPara("admin_user.selectEqual", getKv(dicUser));
			DicUser user = DicUser.dao.findFirst(sqlPara2);
			if (user != null) {
				//用户信息存放于session中
				setSessionAttr(Const.session_username, user);
				setSessionAttr(Const.session_popedoms, DicPopedom.dao.find(Db.getSqlPara("admin.popedomUriListByUserId", user.getId())));
				//审计
				DicPopedom dicPopedom = new DicPopedom();
				dicPopedom.setPopedomName("登录");
				dicPopedom.setForwardAction("/admin/login/signIn");
				SoJLogKit.writeLog(this, dicPopedom, user);
				
				String sessionId = CURRENT_ONLINE_USER.get(user.getUserCode());
				//清空重复登录session
				setSessionAttr(Const.session_duplicate_login, null);
				//判断是否不是当前客户端登录
				if (CURRENT_ONLINE_USER.containsKey(user.getUserCode()) && !getSession().getId().equals(sessionId)) {
					System.err.println("覆盖: "+sessionId);
					HttpSession httpSession = SoJptSessionListener.getSessionById(sessionId);
					if (httpSession != null) {
						httpSession.setAttribute(Const.session_duplicate_login, getRequest());
						//httpSession.invalidate();
					}
				}
				CURRENT_ONLINE_USER.put(user.getUserCode(), getSession().getId());
				
				LOG.info("登录成功  用户ID: "+user.getId()+" 用户名: "+user.getUserCode());
				
				set("clearLoginCount", true); //在登录次数拦截器里处理,清理登陆次数
				
				renderJson(Ret.ok("userCode", user.getUserCode()).set("userId", user.getId()));
				
			} else {
				
				set("updateLoginCount", true); //在登录次数拦截器里处理,更新登陆次数
				renderJson(Ret.fail("msg", "用户名或密码错误"));
			}
		} else {			
			LOG.info("无效的用户名: "+dicUser.getUserCode());
			renderJson(Ret.fail("msg", "用户名或密码错误"));
		}
		
		
	}
	
	@Clear(AdminPopedomInterceptor.class)
	@RequestMapping("/admin/logOut")
	public void logOut() {
		removeSessionAttr(Const.session_username);
		renderJson();
	}
	
	@Clear(AdminPopedomInterceptor.class)
	@RequestMapping("/admin/popedomList")
	public void popedomList() {
		if (isRoot()) {			
			List<Record> popedomList = Db.find(Db.getSqlPara("admin.selectPopedomAll"));
			setAttr("popedomList", popedomList);
		} else {
			List<Record> popedomList = Db.find(Db.getSqlPara("admin.selectPopedomByUserId", getSessionUser().getId()));
			setAttr("popedomList", popedomList);
		}
		
		setAttr("user", new Record().set("userId", getSessionUser().getId()).set("userCode", getSessionUser().getUserCode()));
		renderJson();
	}
	
}
