
package com.sojpt.boot.intercepter;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.sojpt.constant.Const;
import com.sojpt.model.DicUser;
import com.sojpt.storge.WebStorage;

/**
 * 登录次数限制拦截器
 */
public class LoginCountLimitInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		
		Controller controller = inv.getController();
		
        DicUser dicUser = controller.getModel(DicUser.class, "user");
        
		//判断是否限制登录
		Integer currLoginCount = WebStorage.getLoginCount(dicUser.getUserCode());
		
		if (currLoginCount != null && currLoginCount >= Const.loginCountLimit) {
			
			controller.renderJson(Ret.fail("msg", "输入错误次数过多,请"+Const.loginCountOverDate/60000+"分钟后再试")); 
		} else {
			inv.invoke();
			
			if (controller.getAttr("updateLoginCount") != null ) {
				Integer loginCount = WebStorage.getLoginCount(dicUser.getUserCode());
				
				WebStorage.putLoginCount(dicUser.getUserCode(), loginCount != null ? ++loginCount : 1);
				controller.removeAttr("updateLoginCount");
			}
			if (controller.getAttr("clearLoginCount") != null ) {
				WebStorage.clearLoginCount(dicUser.getUserCode());
				controller.removeAttr("clearLoginCount");
			}

		}
	}
}

