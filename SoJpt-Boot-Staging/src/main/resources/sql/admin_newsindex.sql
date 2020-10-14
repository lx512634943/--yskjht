
### 全部
#sql("select")
SELECT newsindex.id,newsindex.pictures,news.title,newsindex.newsid FROM newsindex JOIN news  on news.id=newsindex.newsid
#end

### 全部
#sql("select1")
SELECT news.id,news.title FROM news
#end


### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("newsindex", idList)
#end