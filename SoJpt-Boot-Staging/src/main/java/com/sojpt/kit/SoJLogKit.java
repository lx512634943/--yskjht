package com.sojpt.kit;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.sojpt.model.DicLog;
import com.sojpt.model.DicPopedom;
import com.sojpt.model.DicUser;

public class SoJLogKit {
	
	private static int maxOutputLengthOfParaValue = 512;
	
    //审计
	public static void writeLog(Controller controller, DicPopedom dicPopedom, DicUser user) {
		
		HttpServletRequest req = controller.getRequest();
		DicLog dicLog = new DicLog();
		dicLog.setId(IdKit.generatorID());
		dicLog.setSessionId(req.getSession().getId());
		dicLog.setUserCode(user.getUserCode());
		dicLog.setUserId(user.getId());
		dicLog.setPopedomName(dicPopedom.getPopedomName());
		dicLog.setForwardAction(dicPopedom.getForwardAction());
		dicLog.setEntryTime(new Date());
		dicLog.setIp(IpKit.getIpAddr(req));
		dicLog.setOs(UserAgentParserKit.getOS(req.getHeader("User-Agent")));
		dicLog.setBrowser(UserAgentParserKit.getBrowser(req.getHeader("User-Agent")));
		if (!dicPopedom.getPopedomName().equals("登录")) { // 防止登录密码写入数据库 
			
			StringBuilder sb = new StringBuilder();
			
			String urlParas = controller.getPara();
			if (urlParas != null) {
				sb.append("UrlPara     : ").append(urlParas).append("\n");
			}
			
			Enumeration<String> e = req.getParameterNames();
			if (e.hasMoreElements()) {
				sb.append("Parameter   : ");
				while (e.hasMoreElements()) {
					String name = e.nextElement();
					String[] values = req.getParameterValues(name);
					if (values.length == 1) {
						sb.append(name).append("=");
						if (values[0] != null && values[0].length() > maxOutputLengthOfParaValue) {
							sb.append(values[0].substring(0, maxOutputLengthOfParaValue)).append("...");
						} else {
							sb.append(values[0]);
						}
					}
					else {
						sb.append(name).append("[]={");
						for (int i=0; i<values.length; i++) {
							if (i > 0)
								sb.append(",");
							sb.append(values[i]);
						}
						sb.append("}");
					}
					sb.append("  ");
				}
				sb.append("\n");
			}
			
			dicLog.setParas(sb.toString());
		}
			
		dicLog.save();
		
	}
}
