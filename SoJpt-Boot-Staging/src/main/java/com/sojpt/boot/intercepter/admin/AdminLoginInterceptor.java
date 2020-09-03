
package com.sojpt.boot.intercepter.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.sojpt.constant.Const;
import com.sojpt.model.DicUser;

/**
 * 后台登陆拦截器
 * 
 */
public class AdminLoginInterceptor implements Interceptor {

	public static boolean isAdmin(DicUser dicUser) {
		if (null != dicUser) {			
			return dicUser.getType().equals(Const.user_type_admin);
		} else {
			return false;
		}
	}

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		DicUser dicUser = controller.getSessionAttr(Const.session_username);
		if (isAdmin(dicUser)) {
			
			inv.invoke();
		} else {
			
			HttpServletRequest req = controller.getRequest();

        	HttpServletResponse rep = controller.getResponse();
        	
        	rep.setHeader("sessionstatus", "timeout"); 
        	
        	controller.setHttpServletRequest(req);
        	controller.setHttpServletResponse(rep);
        	controller.renderJson(Ret.fail());
			
		}
	}
}

