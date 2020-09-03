#include("common_function.sql")

在此统一管理所有 sql，优点有：
1：避免在 JFinalClubConfig 中一个个添加 sql 模板文件
2：免除在实际的模板文件中书写 namespace，以免让 sql 定义往后缩进一层
3：在此文件中还可以通过 define 指令定义一些通用模板函数，供全局共享
   例如定义通用的 CRUD 模板函数

#namespace("admin")
#include("admin.sql")
#end

#namespace("admin_popedom")
#include("admin_popedom.sql")
#end

#namespace("admin_user")
#include("admin_user.sql")
#end
#namespace("admin_user_role")
#include("admin_user_role.sql")
#end
#namespace("admin_role")
#include("admin_role.sql")
#end
#namespace("admin_role_popedom")
#include("admin_role_popedom.sql")
#end
#namespace("admin_log")
#include("admin_log.sql")
#end

#namespace("admin_student")
#include("admin_student.sql")
#end

#namespace("admin_merchandise")
#include("admin_merchandise.sql")
#end

#namespace("admin_partners")
#include("admin_partners.sql")
#end


#namespace("admin_slideshow")
#include("admin_slideshow.sql")
#end


#namespace("admin_partnerkinds")
#include("admin_partnerkinds.sql")
#end

#namespace("admin_newkinds")
#include("admin_newkinds.sql")
#end

#namespace("admin_news")
#include("admin_news.sql")
#end

#namespace("admin_shangping")
#include("admin_shangping.sql")
#end

#namespace("admin_succeed")
#include("admin_succeed.sql")
#end

#namespace("admin_contact")
#include("admin_contact.sql")
#end

#namespace("admin_icons")
#include("admin_icons.sql")
#end

#namespace("admin_employment")
#include("admin_employment.sql")
#end

#namespace("admin_cases")
#include("admin_cases.sql")
#end


#namespace("admin_businessbyid")
#include("admin_businessbyid.sql")
#end

#namespace("admin_business")
#include("admin_business.sql")
#end