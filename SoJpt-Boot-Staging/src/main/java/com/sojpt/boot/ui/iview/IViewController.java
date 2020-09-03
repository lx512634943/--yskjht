package com.sojpt.boot.ui.iview;

import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.sojpt.boot.controller.SoJController;

public class IViewController extends SoJController {



	/**
	 * 访问成功
	 * @param obj 返回的参数
	 */
	public void renderSuccess(Object obj){
		renderJson(Ret.ok("data",obj));
	}

	/**
	 * 访问失败
	 * @param msg 失败消息
	 */
	public void renderFail(String msg){
		renderJson(Ret.fail("msg",msg));
	}

	/**
	 * 获取排序字符串
	 */
	public Kv getSortstr() {
		if (getPara("field") != null) {			
			return Kv.by("sortStr", new StringBuilder(getPara("field")).append(" ").append(getPara("order")).append(","));
		} else {
			return new Kv();
		}
	}
	
	/**
	 * 获取当前页数
	 */
	public Integer pageNumber() {
		return getParaToInt("pageNumber");
	}
	
	/**
	 * 获取页面显示数量
	 */
	public Integer pageSize() {
		return getParaToInt("pageSize");
	}
	
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void  renderJson(Object object){
//		if (object instanceof Page) {
//			Page<?> page = (Page<?>) object;
//			super.renderJson(new IViewPager(page.getList(), page.getPageNumber(), page.getPageSize(), page.getTotalPage(), page.getTotalRow()));
//		} else {			
//			super.renderJson(object);
//		}
//	}
}
