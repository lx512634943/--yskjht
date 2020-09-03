a### 全部
#sql("select")
    select p.* from dic_popedom p
    #for(x : cond)
        #(for.index == 0 ? "where" : "and")
        #if(x.key == 'entry_time_start_time')
            ENTRY_TIME >= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'entry_time_end_time')
            ENTRY_TIME <= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'TYPE')
            #(x.key) = #para(x.value)
        #elseif(x.key == 'PARENT_ID')
            #(x.key) = #para(x.value)
        #else
            #(x.key) like #para('%'+x.value+'%')
        #end
    #end
    order by #(sortStr) TYPE, XH, ENTRY_TIME
#end

### 根据IDList删除权限及子权限
#sql("deleteChildById")
    DELETE FROM dic_popedom WHERE FIND_IN_SET(ID, dic_popedom_child(#para(0)))
#end
### 根据IDList删除权限及子权限
#sql("deleteChildByIds")
    DELETE FROM dic_popedom
    #for(id : idList)
        #(for.index > 0 ? "or" : "where") FIND_IN_SET(ID, dic_role_child(#para(id)))
    #end
#end
