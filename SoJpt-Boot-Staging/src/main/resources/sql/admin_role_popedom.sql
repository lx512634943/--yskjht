### 根据ROLE_ID删除
#sql("deleteByRoleId")
    delete from dic_role_popedom where ROLE_ID = #para(0)
#end

### 根据角色IDList删除角色权限
#sql("deleteByRoleIds")
    #@deleteByFieldList("dic_role_popedom", "ROLE_ID", idList)
#end