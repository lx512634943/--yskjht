
### 全部 root
#sql("root_select")
    select * from dic_role
    #for(x : cond)
        #(for.index == 0 ? "where" : "and")
        #if(x.key == 'entry_time_start_time')
            ENTRY_TIME >= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'entry_time_end_time')
            ENTRY_TIME <= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'TYPE')
            #(x.key) = #para(x.value)
        #elseif(x.key == 'IS_PRIVATE')
            #(x.key) = #para(x.value)
        #elseif(x.key == 'PARENT_ID')
            #(x.key) = #para(x.value)
        #else
            #(x.key) like #para('%'+x.value+'%')
        #end
    #end
    order by #(sortStr) ENTRY_TIME
#end

### 全部
#sql("select")
    select * from dic_role
    where CREATER_ID is not null 
    #for(x : cond)
        and 
        #if(x.key == 'entry_time_start_time')
            ENTRY_TIME >= to_date(#para(x.value), 'yyyy-MM-dd hh24:mi:ss')
        #elseif(x.key == 'entry_time_end_time')
            ENTRY_TIME <= to_date(#para(x.value), 'yyyy-MM-dd hh24:mi:ss')
        #elseif(x.key == 'TYPE')
            #(x.key) = #para(x.value)
        #elseif(x.key == 'IS_PRIVATE')
            #(x.key) = #para(x.value)
        #elseif(x.key == 'PARENT_ID')
            #(x.key) = #para(x.value)
        #else
            #(x.key) like #para('%'+x.value+'%')
        #end
    #end
    order by #(sortStr) ENTRY_TIME
#end

### 根据ROLE_ID查询权限列表() root用户
#sql("rootPopedomListByRoleId")
    select p.ID, p.ID "id", p.POPEDOM_NAME "text", p.TYPE, p.FORWARD_ACTION, p.PARENT_ID, case when (r.POPEDOM_ID is null) then 'false' else 'true' end  "checked" from dic_popedom p left join (select * from dic_role_popedom where ROLE_ID = #para(0)) r on p.ID = r.POPEDOM_ID ORDER BY p.XH
#end

### 根据ROLE_ID查询权限列表()
#sql("popedomListByRoleId")
    select p.ID, p.ID "id", p.POPEDOM_NAME "text", p.TYPE, p.FORWARD_ACTION, p.PARENT_ID, case when (r.POPEDOM_ID is null) then 'false' else 'true' end  "checked" from (select * from dic_popedom where ID in (select POPEDOM_ID FROM dic_role_popedom WHERE ROLE_ID IN (select ROLE_ID FROM dic_user_role where USER_ID = #para(0)))) p left join (select * from dic_role_popedom where ROLE_ID = #para(1)) r on p.ID = r.POPEDOM_ID ORDER BY p.XH
#end

### 根据ID删除角色及子角色
#sql("deleteChildById")
    DELETE FROM dic_role WHERE FIND_IN_SET(ID, dic_role_child(#para(0)))
#end
### 根据IDList删除角色及子角色
#sql("deleteChildByIds")
    DELETE FROM dic_role
    #for(id : idList)
        #(for.index > 0 ? "or" : "where") FIND_IN_SET(ID, dic_role_child(#para(id)))
    #end
#end
