
### 全部
#sql("select")
	SELECT environment_protection.id,environment_protection.`name`,environment_protection.business_id,environment_protection.picture,environment_protection.title,business.byid_pictures,business.`name` as business_name FROM environment_protection  JOIN business on  environment_protection.business_id=business.id
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("environment_protection", idList)
#end