
### 全部
#sql("select")
SELECT merchandise_new.id,merchandise_new.commodity_name,merchandise_new.container_number,merchandise_new.unit,merchandise_new.inventory,merchandise_new.purchase,merchandise_new.money,merchandise_new.residue,merchandise_type.merchandise ,merchandise_new.remark FROM merchandise_new RIGHT   JOIN  merchandise_type on merchandise_new.merchandisetypeId=merchandise_type.id
    where  residue  != 0
    #for(x : cond)

          and  #(x.key) like #para('%'+x.value+'%')

    #end
#end



### 全部
#sql("list")
SELECT  * FROM merchandise_new WHERE get_time is not null GROUP BY get_time DESC

#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("dic_student", idList)
#end


