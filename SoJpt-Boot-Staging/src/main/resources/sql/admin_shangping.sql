
### 全部
#sql("select")
	select * from shangping  where  zhuangtai =2

#end

### 全部
#sql("selectlist")
	SELECT * FROM merchandise_new  WHERE commodity_name=#para{commodity}
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("shangping", idList)
#end