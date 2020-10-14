
### 全部
#sql("select")
SELECT partners.id,partners.banner,partners.cover,partners.createdate,partners.pkid,partners.introduce,partners.pname,partners.pictures,partnerkinds.id as ids,partnerkinds.kinds FROM  partners     JOIN  partnerkinds  ON  partners.pkid =partnerkinds.id  WHERE createdate is not  NULL
    #for(x : cond)

          and  #(x.key) like #para('%'+x.value+'%')

    #end
#end


### 根据IDList删除
#sql("selectList")
select * from partners  where pkid =2
#end


### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("dic_student", idList)
#end


