
### 全部
#sql("select")
	SELECT slideshow.id,slideshow.photos,news.title,news.createdate from slideshow JOIN news on slideshow.nid=news.id
#end

### 根据IDList删除
#sql("deleteByIds")
    #@deleteByIdList("slideshow", idList)
#end