

### 全部
#sql("select")

		SELECT partners_index.id,partners_index.cover,partners.pname,partners.id as ids FROM partners_index  JOIN partners on  partners_index.partnersid=partners.id
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("partners_index", idList)
#end