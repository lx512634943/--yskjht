
### 全部
#sql("select")
SELECT  business.`name`,businessById.id,business.byid_pictures,business.businessBytitle,businessById.buid_name,businessById.picture,businessById.byid_title,businessById.byid_titles,businessById.businessId FROM businessById  JOIN business on  businessById.businessId=business.id
#end

### 全部
#sql("select1")

SELECT * FROM business
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("businessById", idList)
#end