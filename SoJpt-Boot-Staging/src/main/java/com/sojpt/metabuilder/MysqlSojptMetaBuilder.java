package com.sojpt.metabuilder;

import javax.sql.DataSource;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;

public class MysqlSojptMetaBuilder extends MetaBuilder {

	public MysqlSojptMetaBuilder(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造 colName 所对应的 attrName，mysql 数据库建议使用小写字段名或者驼峰字段名
	 * Oralce 反射将得到大写字段名，所以不建议使用驼峰命名，建议使用下划线分隔单词命名法
	 */
	protected String buildAttrName(String colName) {
		if (dialect instanceof OracleDialect) {
			colName = colName.toLowerCase();
		} else if (dialect instanceof MysqlDialect) {
			colName = colName.toLowerCase();
		}
		return StrKit.toCamelCase(colName);
	}
}
