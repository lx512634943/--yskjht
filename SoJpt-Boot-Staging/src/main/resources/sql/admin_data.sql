
### 全部
#sql("select")
SELECT big_data.id,big_data.`name`,big_data.picture,big_data.title,big_data.title1,big_data.title2,big_data.business_id,business.`name` as business_name,business.byid_pictures  FROM big_data  JOIN  business on  big_data.business_id=business.id
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("big_data", idList)
#end