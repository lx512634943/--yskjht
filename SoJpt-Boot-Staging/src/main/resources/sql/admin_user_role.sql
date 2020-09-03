### 根据ROLE_ID删除
#sql("deleteByUserId")
    delete from dic_user_role where USER_ID = #para(0)
#end

### 根据用户IDList删除用户角色
#sql("deleteByUserIds")
    #@deleteByFieldList("dic_user_role", "USER_ID", idList)
#end