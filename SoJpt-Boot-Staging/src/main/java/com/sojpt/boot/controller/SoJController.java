package com.sojpt.boot.controller;

import java.util.Map.Entry;
import java.util.Set;

import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Model;
import com.sojpt.constant.Const;
import com.sojpt.model.DicUser;

public abstract class SoJController extends Controller {

	/**
	 * 获取当前登录用户(从session里获取)
	 */
	public DicUser getSessionUser() {
		return getSessionAttr(Const.session_username);
	}
	
	/**
	 * 判断是否是超级用户
	 * @return
	 */
	public boolean isRoot() {
		if(getSessionUser().getUserCode().equals(Const.rootName)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否是treegrid的子查询
	 * @return
	 */
	public Boolean isFindChild() {
		if (getPara("id") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否是管理员
	 * @return
	 */
	public boolean isAdmin() {
		if(getSessionUser().getType().equals(Const.user_type_admin)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Kv getKv(Model<?> model) {
		Set<Entry<String, Object>> entrySet = model._getAttrsEntrySet();
		Kv kv = new Kv();
		  for (Entry<String, Object> entry : entrySet) {  
			  if (entry.getValue() != null && !entry.getValue().toString().equals("")) {				  
				  kv.set(entry.getKey(), entry.getValue());  
			  }
		  }
		return Kv.by("cond", kv).set(getSortstr());

	}
	
	
	public Kv getKv(Model<?> model, String alias) {
		Set<Entry<String, Object>> entrySet = model._getAttrsEntrySet();
		Kv kv = new Kv();
		  for (Entry<String, Object> entry : entrySet) {  
			  if (entry.getValue() != null && !entry.getValue().toString().equals("")) {				  
				  kv.set(alias+"."+entry.getKey(), entry.getValue());  
			  }
		  }
		return Kv.by("cond", kv).set(getSortstr());

	}
	
	@NotAction
	public Kv getKvByJoin(Kv... kv) {
		Kv kvTemp = new Kv();
		for (int i = 0; i < kv.length; i++) {
			if (kv[i].get("cond") != null)
				kvTemp.set((Kv) kv[i].get("cond"));
		}
		Kv kvs = Kv.by("cond", kvTemp);
		return kvs;
	}
	
	/**
	 * 获取排序字符串
	 */
	public abstract Kv getSortstr();
	
}

