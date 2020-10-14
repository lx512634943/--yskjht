
### 全部
#sql("select")

SELECT  supply_chain.id,supply_chain.`name`,supply_chain.picture,supply_chain.business_id,supply_chain.title,supply_chain.title1 ,business.`name` as business_name,business.byid_pictures  FROM supply_chain JOIN  business on  supply_chain.business_id=business.id

#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("supply_chain", idList)
#end