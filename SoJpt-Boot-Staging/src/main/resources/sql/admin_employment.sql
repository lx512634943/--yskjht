
### 全部
#sql("select")

SELECT employment.id,icons.icon,employment.releasedate,employment.pnumber,employment.skill,icons.id as ids ,icons.icname	 FROM icons JOIN employment on icons.id=employment.iconid
       #for(x : cond)
          and  #(x.key) like #para('%'+x.value+'%')
    #end
#end



### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("dic_student", idList)
#end


