
### 全部菜单及权限
#sql("selectPopedomAll")
	select * from dic_popedom order by TYPE, XH, ENTRY_TIME
#end
### 根据用户ID获取菜单及权限
#sql("selectPopedomByUserId")
	select * from dic_popedom WHERE ID IN (SELECT POPEDOM_ID FROM dic_role_popedom WHERE ROLE_ID IN (SELECT ROLE_ID FROM dic_user_role WHERE USER_ID = #para(0)))  order by TYPE, XH, ENTRY_TIME
#end

### 根据用户ID查询权限uri列表(登录时保存于session中,作为后台权限验证的基础数据)
#sql("popedomUriListByUserId")
    SELECT POPEDOM_NAME, FORWARD_ACTION, IS_LOG FROM dic_popedom WHERE FORWARD_ACTION IS NOT NULL AND ID IN (SELECT POPEDOM_ID FROM dic_role_popedom WHERE ROLE_ID IN (SELECT ROLE_ID FROM dic_user_role WHERE USER_ID = #para(0)))
#end
