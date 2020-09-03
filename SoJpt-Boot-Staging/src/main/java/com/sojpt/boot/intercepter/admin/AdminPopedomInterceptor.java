
package com.sojpt.boot.intercepter.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Ret;
import com.jfinal.log.Log;
import com.sojpt.constant.Const;
import com.sojpt.kit.SoJLogKit;
import com.sojpt.model.DicPopedom;
import com.sojpt.model.DicUser;

/**
 * 后台权限管理拦截器
 */
public class AdminPopedomInterceptor implements Interceptor {
	
	private static final Log LOG = Log.getLog(Class.class);
	
	private HttpServletRequest req;
	
	private DicUser user;

	public void intercept(Invocation inv) {
		
		Controller controller = inv.getController();
		List<DicPopedom> dicPopedoms = controller.getSessionAttr(Const.session_popedoms);
		String uri = controller.getRequest().getRequestURI();
		
		if (uri.endsWith("/")) uri = uri.substring(0, uri.length()-1);
		
		req = controller.getRequest();
		user = controller.getSessionAttr(Const.session_username);
		
		if (isHasPopedom(controller, dicPopedoms, uri) || isRoot(controller.getSessionAttr(Const.session_username))) {
  
            inv.invoke();
            
		} else {

        	HttpServletResponse rep = controller.getResponse();
        	rep.setHeader("sessionstatus", "nopermission"); 
        	rep.setHeader("nourl", uri); 
        	controller.setHttpServletRequest(req);
        	controller.setHttpServletResponse(rep);
        	
        	controller.renderJson(Ret.fail());
            
        	LOG.info("用户ID: "+user.getId()+" 用户名: "+user.getUserCode()+" 无权限: "+uri);
            	
         }
	}
	
	/**
	 * 判断是否是超级用户
	 * @return
	 */
	private boolean isRoot(DicUser user) {
		
		if(user.getUserCode().equals(Const.rootName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isHasPopedom(Controller controller, List<DicPopedom> dicPopedoms, String uri) {
		
		String projectBaseUri = JFinal.me().getContextPath();
		
		if (dicPopedoms != null && uri != null) {
			
			for (DicPopedom dicPopedom : dicPopedoms) {
				
				String severUri = projectBaseUri+dicPopedom.getForwardAction();
				//equal
				if (severUri.equals(uri)) {
					
					//审计

					if (Const.audit.equals(dicPopedom.getIsLog())) {
						SoJLogKit.writeLog(controller, dicPopedom, user);
					}
					return true;
				}
				//like
				if(severUri.indexOf("*") >= 0 && uri.startsWith(severUri.substring(0, severUri.indexOf("*")))) {
					
					//审计
					if (Const.audit.equals(dicPopedom.getIsLog())) {
						SoJLogKit.writeLog(controller, dicPopedom, user);
					}
					return true;
				}
			}
		}
		
		return false;
	}
	
}

