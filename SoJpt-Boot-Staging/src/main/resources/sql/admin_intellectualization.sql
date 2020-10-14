
### 全部
#sql("select")
SELECT Intellectualization.id,Intellectualization.`name`,Intellectualization.picture,Intellectualization.title,Intellectualization.business_id,business.`name` as business_name,business.byid_pictures FROM Intellectualization  JOIN  business  on  Intellectualization.business_id=business.id
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("Intellectualization", idList)
#end