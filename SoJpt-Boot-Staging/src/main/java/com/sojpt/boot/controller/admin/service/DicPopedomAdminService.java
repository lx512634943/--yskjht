package com.sojpt.boot.controller.admin.service;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfinal.aop.Clear;
import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.sojpt.boot.controller.SoJController;
import com.sojpt.boot.ui.iview.IViewController;
import com.sojpt.constant.Const;
import com.sojpt.kit.IdKit;
import com.sojpt.model.DicPopedom;

public class DicPopedomAdminService {
	
	public static final DicPopedomAdminService me = new DicPopedomAdminService();
	
	// 用于排除掉 BaseController 中的几个成为了 action 的方法
	private Set<String> excludedMethodName = buildExcludedMethodName(IViewController.class);

	private Set<String> buildExcludedMethodName(Class<?>... clazz) {
		Set<String> excludedMethodName = new HashSet<String>();
		for (int i = 0; i < clazz.length; i++) {			
			Method[] methods = clazz[i].getMethods();
			for (Method m : methods) {
				if (m.getParameterTypes().length == 0)
					excludedMethodName.add(m.getName());
			}
		}
		return excludedMethodName;
	}
	
	/**
	 * 同步 权限
	 * 获取后台管理所有 actionKey 以及 controller，将数据自动写入 dic_popedom 表
	 * 随着开发过程的前行，可以动态进行同步添加新的 权限 数据
	 * @param id 要更新权限的父节点id
	 * @param url 要更新权限的父节点的url
	 */
	public Ret sync(SoJController soJController, String id, String url) {
		int counter = 0;
		List<String> allActionKeys = JFinal.me().getAllActionKeys();
		
		for (String actionKey : allActionKeys) {
			// 只处理父节点 action，其它跳过
			if (actionKey.startsWith(url)) {
				String[] urlPara = new String[1];
				Action action = JFinal.me().getAction(actionKey, urlPara);
				if (action == null || excludedMethodName.contains(action.getMethodName())) {
					continue ;
				}
				if (null != action.getMethod().getAnnotation(Clear.class)) {
					continue ;
				}
				if (null != action.getControllerClass().getAnnotation(Clear.class)) {
					continue ;
				}
				if (null != action.getMethod().getAnnotation(NotAction.class)) {
					continue ;
				}

				DicPopedom pd = new DicPopedom();
				pd.setForwardAction(actionKey);
				DicPopedom popedom = DicPopedom.dao.findFirst(Db.getSqlPara("admin_popedom.select", soJController.getKv(pd)));

				if (popedom == null) {
					
					popedom = new DicPopedom();
					
					popedom.setId(IdKit.generatorID());
					popedom.setType(Const.popedom_type); //设置类型为权限
					popedom.setCreaterId(soJController.getSessionUser().getId());
					popedom.setForwardAction(actionKey);
					//设置父ID
					popedom.setParentId(id);
					
					popedom.save();
					counter++;
				}
			}
		}

		if (counter == 0) {
			return Ret.ok("msg", "权限已经是最新状态，无需更新");
		} else {
			return Ret.ok("msg", "权限更新成功，共更新权限数 : " + counter);
		}
	}
}
