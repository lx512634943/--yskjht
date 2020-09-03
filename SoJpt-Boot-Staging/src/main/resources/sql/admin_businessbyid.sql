
### 全部
#sql("select")
SELECT businessById.id,businessById.picture,business.`name`,businessById.businessId  FROM businessById  JOIN  business  on  businessById.businessId=business.id


#end

### 全部
#sql("select1")

SELECT * FROM business
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("businessById", idList)
#end