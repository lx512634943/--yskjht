package com.sojpt.boot.ui.iview;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;

/**
 * 适用qui返回分页查询结果
 * @author lenovo
 *
 */
public class IViewPager<T> extends Page<T>{

	private static final long serialVersionUID = -7483988915775905015L;
	private Integer code;

	public IViewPager() {
		super();
	}

	public IViewPager(List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow) {
		super(list, pageNumber, pageSize, totalPage, totalRow);
		this.code = 200;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	
}
