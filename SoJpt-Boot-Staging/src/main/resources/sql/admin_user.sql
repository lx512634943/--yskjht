
### 全部用户(like) root
#sql("root_selectLike")
    select * from dic_user
    #for(x : cond)
        #(for.index == 0 ? "where" : "and") 
        #if(x.key == 'entry_time_start_time')
            ENTRY_TIME >= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'entry_time_end_time')
            ENTRY_TIME <= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #else
            #(x.key) like #para('%'+x.value+'%')
        #end
    #end
    order by #(sortStr) ENTRY_TIME
#end

### 全部用户(like)
#sql("selectLike")
    select * from dic_user
    where CREATER_ID IS NOT NULL 
    #for(x : cond)
        and 
        #if(x.key == 'entry_time_start_time')
            ENTRY_TIME >= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #elseif(x.key == 'entry_time_end_time')
            ENTRY_TIME <= str_to_date(#para(x.value), '%Y-%m-%d %H:%i:%s')
        #else
            #(x.key) like #para('%'+x.value+'%')
        #end
    #end
    order by #(sortStr) ENTRY_TIME
#end

### 全部用户(=)
#sql("selectEqual")
	select * from dic_user
    #for(x : cond)
        #(for.index == 0 ? "where" : "and") 
            #(x.key) = #para(x.value)
    #end
#end

### 根据USER_ID查询角色列表() root
#sql("root_roleListByUserId")
    select r.ID, r.ID "id", r.TYPE, r.ROLE_NAME "text", r.PARENT_ID, case when (ur.ROLE_ID is null or r.TYPE != 1) then 'false' else 'true' end  "checked" from dic_role r left join (select * from dic_user_role where USER_ID = #para(0)) ur on r.ID = ur.ROLE_ID ORDER BY r.XH
#end

### 根据USER_ID查询角色列表()
#sql("roleListByUserId")
    select r.ID, r.ID "id", r.TYPE, r.ROLE_NAME "text", r.PARENT_ID, case when (ur.ROLE_ID is null or r.TYPE != 1) then 'false' else 'true' end  "checked" from dic_role r left join (select * from dic_user_role where USER_ID = #para(0)) ur on r.ID = ur.ROLE_ID where r.CREATER_ID IS NOT NULL ORDER BY r.XH
#end

### 根据登录名查找用户
#sql("selectByUserCode")
    select * from dic_user where USER_CODE = #para(0)
#end

### 根据IDList删除用户
#sql("deleteByIds")
    #@deleteByIdList("dic_user", idList)
#end

