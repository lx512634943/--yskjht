
### 全部
#sql("select")
select * FROM succeed WHERE get_time is not null
    #for(x : cond)

          and  #(x.key) like #para('%'+x.value+'%')

    #end
    GROUP BY get_time DESC

#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("succeed", idList)
#end