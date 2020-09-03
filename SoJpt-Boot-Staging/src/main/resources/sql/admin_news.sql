
### 全部
#sql("select")
SELECT  news.id,news.title,news.outline,news.content,news.newcover,news.createdate,newkinds.kinds,newkinds.id as ids FROM   news  RIGHT JOIN   newkinds on news.nkid=newkinds.id
#end

### 全部
#sql("selectitle")
SELECT id,title FROM news
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("news", idList)
#end