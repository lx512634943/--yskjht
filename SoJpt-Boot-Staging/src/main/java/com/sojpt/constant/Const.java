package com.sojpt.constant;

import java.math.BigDecimal;

public class Const {
	
	public static BigDecimal user_disabled = new BigDecimal(1);
	
	public static BigDecimal user_not_disabled = new BigDecimal(2);
	
	
	public static BigDecimal user_type_admin = new BigDecimal(2);
	
	public static BigDecimal audit  = new BigDecimal(1);
	
	/**
	 * 权限菜单类型
	 */
	public static BigDecimal popedom_type = new BigDecimal(2);
	
	public static String session_username = "user";
	
	public static String session_duplicate_login = "duplicate_login";

	public static Object rootName = "root";

	public static String session_popedoms = "popedoms";
	
	public static Integer loginCountLimit = 3;
	
	public static Integer loginCountOverDate = 300000; //登录错误次数过期时间ms
	
	public static String login_address = "/admin/login";
	
}
