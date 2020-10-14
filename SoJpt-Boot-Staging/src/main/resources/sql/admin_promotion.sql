### 全部
#sql("select")
SELECT  promotion.id,promotion.`name`,promotion.picture,promotion.title,promotion.titles,promotion.business_id,business.`name` as business_name,business.byid_pictures  FROM  promotion  JOIN business ON  promotion.business_id=business.id
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("promotion", idList)
#end