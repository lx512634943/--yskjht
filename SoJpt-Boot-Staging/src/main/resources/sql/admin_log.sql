### 全部
#sql("select")
    select 
    dic_log.SESSION_ID,
	dic_log.USER_CODE,
	dic_log.POPEDOM_NAME,
	dic_log.FORWARD_ACTION,
	dic_log.IP,
	dic_log.BROWSER,
	dic_log.OS,
	dic_log.ID,
	dic_log.PARAS,
	dic_log.ENTRY_TIME
    from dic_log
    #for(x : cond)
        #(for.index == 0 ? "where" : "and")
        #if(x.key == 'entry_time_start_time')
            ENTRY_TIME >= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'entry_time_end_time')
            ENTRY_TIME <= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #else
            #(x.key) = #para(x.value)
        #end
    #end
    order by #(sortStr) ENTRY_TIME DESC
#end

### 浏览器占比
#sql("browser")
    select BROWSER as "name", count(BROWSER) as "y" from dic_log where date_format(ENTRY_TIME, '%Y') = #para(0) GROUP BY BROWSER 
#end
### 系统占比
#sql("os")
    select OS as "name", count(OS) as "y" from dic_log where date_format(ENTRY_TIME, '%Y')  = #para(0) GROUP BY OS 
#end
