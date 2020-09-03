### 定义批量删除函数
#define deleteByIdList(table, idList)
	delete from #(table) where id in(
		#for(id : idList)
			#(for.index > 0 ? "," : "") #para(id)
		#end
	)
#end

### 定义批量删除函数根据字段
#define deleteByFieldList(table, field, idList)
	delete from #(table) where #(field) in(
		#for(id : idList)
			#(for.index > 0 ? "," : "") #para(id)
		#end
	)
#end


