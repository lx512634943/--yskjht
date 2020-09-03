package com.sojpt.boot;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.template.Engine;
import com.sojpt.boot.intercepter.DuplicateLoginInterceptor;
import com.sojpt.handler.JavaMelodyHandler;
import com.sojpt.kit.DruidKit;
import com.sojpt.model._MappingKit;

@Configuration(value = "soJptBootConfig")
public class SoJptBootConfig extends SoJptConfig {

	@Autowired
	private ActiveRecordPluginProperties arpProperties;
	
	@Autowired
	private DataSource dataSource;

	/**
	 * 配置JFinal常量
	 */
	@Override
	public void configConstant(Constants me) {

		// 设置当前是否为开发模式
		me.setDevMode(arpProperties.getIsDevMode());
		me.setError404View("/index.html");
	}

	/**
	 * 配置JFinal插件 数据库连接池 ORM 缓存等插件 自定义插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(()-> dataSource);
		arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
		arp.setShowSql(arpProperties.getIsDevMode());
		arp.setDialect(new MysqlDialect());

		// ******** 在此添加dao层sql文件 *********//*
		arp.addSqlTemplate("sql/all_sqls.sql");

		_MappingKit.mapping(arp);

		// 初始化任务调度插件,参数为配置文件名
		// me.add(new Cron4jPlugin(sysProp));

		me.add(arp);
	}

	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new DuplicateLoginInterceptor());
	}

	/**
	 * 配置全局处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		// druid 统计页面功能
		me.add(DruidKit.getDruidStatViewHandler());
		me.add(new JavaMelodyHandler("/monitoring.*", true));
	}

	@Override
	public void beforeJFinalStop() {
	}

	/**
	 * 
	 * 配置模板引擎
	 */
	@Override
	public void configEngine(Engine me) {
		// 这里只有选择JFinal TPL的时候才用
		me.addSharedObject("RESOURCE_HOST", JFinal.me().getContextPath());
		me.addSharedObject("WEB_HOST", JFinal.me().getContextPath());
		
		//me.setBaseTemplatePath(PathKit.getRootClassPath());
		// 配置共享函数模板
		// me.addSharedFunction("/view/common/layout.html")
	}

	@Override
	public void afterJFinalStart() {
		
		System.err.println("SoJpt Boot 启动成功!");
	}

}
