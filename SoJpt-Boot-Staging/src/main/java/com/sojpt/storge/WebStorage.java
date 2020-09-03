package com.sojpt.storge;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.sojpt.constant.Const;

public class WebStorage {
	
	public static Integer success = 0;
	public static Integer error = 1;

	private static ConcurrentMap<String, Object> qrCodeMap = new ConcurrentHashMap<String, Object>();
	
	private static ConcurrentMap<String, Integer> successMap = new ConcurrentHashMap<String, Integer>();
	
	private static ConcurrentMap<String, Integer> loginCountMap = new ConcurrentHashMap<String, Integer>();
	
	/**
	 * 存入临时二维码授权信息
	 * @param key
	 * @param value
	 */
	public static void putQrCode(String key, Object value) {
		
		qrCodeMap.put(key, value);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(90000);
					WebStorage.getQrCode(key);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * 取出临时二维码授权信息, 并删除
	 * @param key
	 * @param value
	 */
	public static Object getQrCode(String key) {
		Object obj = qrCodeMap.get(key);
		if (obj != null) qrCodeMap.remove(key);
		return obj;
	}
	
	
	/**
	 * 存入错误代码
	 * @param key
	 * @param errcode
	 */
	public static void putSuccessCode(String key, int errcode) {
		
		successMap.put(key, errcode);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(60000);
					WebStorage.getErrorCode(key);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
					
			}
		}).start();
	}
	
	/**
	 * 取出错误代码, 并删除
	 * @param key
	 */
	public static Integer getErrorCode(String key) {
		Integer errcode = successMap.get(key);
		if (errcode != null) successMap.remove(key);
		return errcode;
	}
	
	
	/**
	 * 存入登录错误次数
	 * @param userCode
	 * @param loginCount
	 */
	public static void putLoginCount(String userCode, int loginCount) {
		
		loginCountMap.put(userCode, loginCount);
		clearLoginCountThread(userCode);
		
	}
	
	public static void clearLoginCount(String userCode) {
		loginCountMap.remove(userCode);
	}
	
	private static void clearLoginCountThread(String userCode) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(Const.loginCountOverDate); //15分钟
					loginCountMap.remove(userCode);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	/**
	 * 取出登录错误次数
	 * @param userCode
	 */
	public static Integer getLoginCount(String userCode) {
		Integer loginCount = loginCountMap.get(userCode);
		return loginCount;
	}

	
}
