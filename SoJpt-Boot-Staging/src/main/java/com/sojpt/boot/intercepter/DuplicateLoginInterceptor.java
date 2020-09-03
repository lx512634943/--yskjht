
package com.sojpt.boot.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.sojpt.constant.Const;

/**
 * 后台权限管理拦截器
 * 基于用户、角色、权限的权限管理系统，并且实现角色、权限完全动态化配置
 */
public class DuplicateLoginInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		HttpServletRequest reqObj = controller.getSessionAttr(Const.session_duplicate_login);
		if (reqObj == null) {
			
			inv.invoke();
		} else {
			
			HttpServletRequest req = controller.getRequest();
	        //如果判断是 AJAX 请求,直接设置为session重复登录
            if( req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")  ) {
              
            	HttpServletResponse rep = controller.getResponse();
            	rep.setHeader("sessionstatus", "relogin"); 
            	controller.setHttpServletRequest(req);
            	controller.setHttpServletResponse(rep);
            	controller.renderJson();
            } else {
            	String loginAddress = JFinal.me().getContextPath()+Const.login_address;
            	inv.getController().renderHtml("<h2>被迫下线,该账户已在其它设备登录</h2><hr><p><a href='"+loginAddress+"'>点击这里重新登录!</a></p>");
            }
			
		}
	}
}

