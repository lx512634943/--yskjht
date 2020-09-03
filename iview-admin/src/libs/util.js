import Cookies from 'js-cookie'
// cookie保存的天数
import config from '@/config'
import { forEach, hasOneOf, objEqual } from '@/libs/tools'
import moment from 'moment'
import router from '@/router'
import store from '@/store'
import { SoTree } from '@/libs/tools'

export const TOKEN_KEY = 'token'

export const setToken = (token) => {
  Cookies.set(TOKEN_KEY, token, {expires: config.cookieExpires || 1})
}

export const getToken = () => {
  const token = Cookies.get(TOKEN_KEY)
  if (token) return token
  else return false
}

export const hasChild = (item) => {
  return item.children && item.children.length !== 0
}

const showThisMenuEle = (item, access) => {
  if (item.meta && item.meta.access && item.meta.access.length) {
    if (hasOneOf(item.meta.access, access)) return true
    else return false
  } else return true
}
/**
 * @param {Array} list 通过路由列表得到菜单列表
 * @returns {Array}
 */
export const getMenuByRouter = (list, access) => {
  let res = []
  forEach(list, item => {
    if (!item.meta || (item.meta && !item.meta.hideInMenu)) {
      let obj = {
        icon: (item.meta && item.meta.icon) || '',
        name: item.name,
        meta: item.meta
      }
      if ((hasChild(item) || (item.meta && item.meta.showAlways)) && showThisMenuEle(item, access)) {
        obj.children = getMenuByRouter(item.children, access)
      }
      if (item.meta && item.meta.href) obj.href = item.meta.href
      if (showThisMenuEle(item, access)) res.push(obj)
    }
  })  
  return res
}

/**
 * @param {Array} routeMetched 当前路由metched
 * @returns {Array}
 */
export const getBreadCrumbList = (routeMetched, homeRoute) => {
  let res = routeMetched.filter(item => {
    return item.meta === undefined || !item.meta.hide
  }).map(item => {
    let obj = {
      icon: (item.meta && item.meta.icon) || '',
      name: item.name,
      meta: item.meta
    }
    return obj
  })
  res = res.filter(item => {
    return !item.meta.hideInMenu
  })
  return [Object.assign(homeRoute, { to: homeRoute.path }), ...res]
}

export const showTitle = (item, vm) => vm.$config.useI18n ? vm.$t(item.name) : ((item.meta && item.meta.title) || item.name)

/**
 * @description 本地存储和获取标签导航列表
 */
export const setTagNavListInLocalstorage = list => {
  localStorage.tagNaveList = JSON.stringify(list)
}
/**
 * @returns {Array} 其中的每个元素只包含路由原信息中的name, path, meta三项
 */
export const getTagNavListFromLocalstorage = () => {
  const list = localStorage.tagNaveList
  return list ? JSON.parse(list) : []
}

/**
 * @param {Array} routers 路由列表数组
 * @description 用于找到路由列表中name为home的对象
 */
export const getHomeRoute = routers => {
  let i = -1
  let len = routers.length
  let homeRoute = {}
  while (++i < len) {
    let item = routers[i]
    if (item.children && item.children.length) {
      let res = getHomeRoute(item.children)
      if (res.name) return res
    } else {
      if (item.name === 'home') homeRoute = item
    }
  }
  return homeRoute
}

/**
 * @param {*} list 现有标签导航列表
 * @param {*} newRoute 新添加的路由原信息对象
 * @description 如果该newRoute已经存在则不再添加
 */
export const getNewTagList = (list, newRoute) => {
  const { name, path, meta } = newRoute
  let newList = [...list]
  if (newList.findIndex(item => item.name === name) >= 0) return newList
  else newList.push({ name, path, meta })
  return newList
}

/**
 * @param {*} access 用户权限数组，如 ['super_admin', 'admin']
 * @param {*} route 路由列表
 */
const hasAccess = (access, route) => {
  if (route.meta && route.meta.access) return hasOneOf(access, route.meta.access)
  else return true
}

/**
 * 权鉴
 * @param {*} name 即将跳转的路由name
 * @param {*} access 用户权限数组
 * @param {*} routes 路由列表
 * @description 用户是否可跳转到该页
 */
export const canTurnTo = (name, access, routes) => {
  const routePermissionJudge = (list) => {
    return list.some(item => {
      if (item.children && item.children.length) {
        return routePermissionJudge(item.children)
      } else if (item.name === name) {
        return hasAccess(access, item)
      }
    })
  }

  return routePermissionJudge(routes)
}

/**
 * @param {String} url
 * @description 从URL中解析参数
 */
export const getParams = url => {
  const keyValueArr = url.split('?')[1].split('&')
  let paramObj = {}
  keyValueArr.forEach(item => {
    const keyValue = item.split('=')
    paramObj[keyValue[0]] = keyValue[1]
  })
  return paramObj
}

/**
 * @param {Array} list 标签列表
 * @param {String} name 当前关闭的标签的name
 */
export const getNextRoute = (list, route) => {
  let res = {}
  if (list.length === 2) {
    res = getHomeRoute(list)
  } else {
    const index = list.findIndex(item => routeEqual(item, route))
    if (index === list.length - 1) res = list[list.length - 2]
    else res = list[index + 1]
  }
  return res
}

/**
 * @param {Number} times 回调函数需要执行的次数
 * @param {Function} callback 回调函数
 */
export const doCustomTimes = (times, callback) => {
  let i = -1
  while (++i < times) {
    callback(i)
  }
}

/**
 * @param {Object} file 从上传组件得到的文件对象
 * @returns {Promise} resolve参数是解析后的二维数组
 * @description 从Csv文件中解析出表格，解析成二维数组
 */
export const getArrayFromFile = (file) => {
  let nameSplit = file.name.split('.')
  let format = nameSplit[nameSplit.length - 1]
  return new Promise((resolve, reject) => {
    let reader = new FileReader()
    reader.readAsText(file) // 以文本格式读取
    let arr = []
    reader.onload = function (evt) {
      let data = evt.target.result // 读到的数据
      let pasteData = data.trim()
      arr = pasteData.split((/[\n\u0085\u2028\u2029]|\r\n?/g)).map(row => {
        return row.split('\t')
      }).map(item => {
        return item[0].split(',')
      })
      if (format === 'csv') resolve(arr)
      else reject(new Error('[Format Error]:你上传的不是Csv文件'))
    }
  })
}

/**
 * @param {Array} array 表格数据二维数组
 * @returns {Object} { columns, tableData }
 * @description 从二维数组中获取表头和表格数据，将第一行作为表头，用于在iView的表格中展示数据
 */
export const getTableDataFromArray = (array) => {
  let columns = []
  let tableData = []
  if (array.length > 1) {
    let titles = array.shift()
    columns = titles.map(item => {
      return {
        title: item,
        key: item
      }
    })
    tableData = array.map(item => {
      let res = {}
      item.forEach((col, i) => {
        res[titles[i]] = col
      })
      return res
    })
  }
  return {
    columns,
    tableData
  }
}

export const findNodeUpper = (ele, tag) => {
  if (ele.parentNode) {
    if (ele.parentNode.tagName === tag.toUpperCase()) {
      return ele.parentNode
    } else {
      return findNodeUpper(ele.parentNode, tag)
    }
  }
}

export const findNodeDownward = (ele, tag) => {
  const tagName = tag.toUpperCase()
  if (ele.childNodes.length) {
    let i = -1
    let len = ele.childNodes.length
    while (++i < len) {
      let child = ele.childNodes[i]
      if (child.tagName === tagName) return child
      else return findNodeDownward(child, tag)
    }
  }
}

export const showByAccess = (access, canViewAccess) => {
  return hasOneOf(canViewAccess, access)
}

/**
 * @description 根据name/params/query判断两个路由对象是否相等
 * @param {*} route1 路由对象
 * @param {*} route2 路由对象
 */
export const routeEqual = (route1, route2) => {
  const params1 = route1.params || {}
  const params2 = route2.params || {}
  const query1 = route1.query || {}
  const query2 = route2.query || {}
  return (route1.name === route2.name) && objEqual(params1, params2) && objEqual(query1, query2)
}

/**
 * 判断打开的标签列表里是否已存在这个新添加的路由对象
 */
export const routeHasExist = (tagNavList, routeItem) => {
  let len = tagNavList.length
  let res = false
  doCustomTimes(len, (index) => {
    if (routeEqual(tagNavList[index], routeItem)) res = true
  })
  return res
}

/**
 * 用于form表单值添加model前缀,如: user.username
 */
export const createFormObj = (row, modelName) => {
  let newRow = {}
  for (let p in row) {
    if (row[p] != null && row[p] != '' && row[p] != undefined) {  //过滤掉空值

      if (row[p] instanceof Array) { //判断是否为iview时间数组
        
        if ( row[p][0] != null && row[p][0] != '' && row[p][0] != undefined) {
          newRow[ p + '_start_time'] = moment(row[p][0]).format('YYYY-MM-DD HH:mm:ss')
          newRow[ p + '_end_time'] = moment(row[p][1]).format('YYYY-MM-DD HH:mm:ss')
        }

      } else {

        newRow[modelName + '.' + p] = row[p]
      }

    }
  }
  return newRow
}

/**
 * 用于添加model前缀,如: user.username
 */
export const createModelObj = (row, modelName) => {
  let newRow = {}
  for (let p in row) {
    if (row[p] != null && row[p] != undefined) {  //过滤掉空值

      if (row[p] instanceof Array) { //判断是否为iview时间数组
        
        if ( row[p][0] != null && row[p][0] != '' && row[p][0] != undefined) {
          newRow[ p + '_start_time'] = moment(row[p][0]).format('YYYY-MM-DD HH:mm:ss')
          newRow[ p + '_end_time'] = moment(row[p][1]).format('YYYY-MM-DD HH:mm:ss')
        }
      } else if (row[p] instanceof Date) { 
        newRow[modelName + '.' + p] = moment(row[p]).format('YYYY-MM-DD HH:mm:ss')
      } else {

        newRow[modelName + '.' + p] = row[p]
      }

    }
  }
  return newRow
}

/**
* 用第二个对象的属性覆盖第一个对象的属性, 交集覆盖
*/
export const coverProps = (obj1, obj2) => {
  for (let p in obj1) {
    if (obj2[p] != undefined && obj2 != null) {
      obj1[p] = obj2[p]
    }
  }
  return obj1;
}

/**
* 用第二个对象的属性覆盖第一个对象的属性, 交集覆盖(适用于覆盖form表单对象)
*/
export const coverFormProps = (obj1, obj2) => {
  for (let p in obj1) {
    if (obj2[p] != undefined && obj2 != null) {
      if (typeof obj1[p] === 'number') {
        obj1[p] = obj2[p]
      } else {
        obj1[p] = obj2[p]+''
      }
    }
  }
  return obj1;
}

export const initRouter = (userId) => {

  return new Promise((resolve, reject) => {

    router.onReady(() => {
      store.dispatch('handleGetRouter', {userId}).then(res => {
        let menus = res.popedomList.filter(item => item.TYPE === 1)
        let popedomList = res.popedomList.filter(item => item.TYPE === 2)

        const routers = SoTree().toTreeData(menus, (popedom) => {

          popedom.meta = {}
          if (popedom.FORWARD_ACTION) {
            popedom.path = popedom.FORWARD_ACTION
          }
          if (popedom.NAME) {
            popedom.name = popedom.NAME
          }
          if (popedom.COMPONENT) {

            popedom.component = () => import('@/'+popedom.COMPONENT)

            if (popedom.COMPONENT === 'components/parent-view') {
              popedom.meta.showAlways = true
            }
          }
          if (popedom.POPEDOM_NAME) {
            popedom.meta.title = popedom.POPEDOM_NAME
          }
          if (popedom.NAV_ICON) {
            popedom.meta.icon = popedom.NAV_ICON
          }
          if (popedom.HREF) {
            popedom.meta.href = popedom.HREF
          }
          if (popedom.REDIRECT) {
            popedom.redirect = popedom.REDIRECT
          }
          if (popedom.IS_HIDE) {
              popedom.meta.hideInMenu = popedom.IS_HIDE == 1 ? true : false
          }          
          if (popedom.IS_NOT_CACHE) {
            popedom.meta.notCache = popedom.IS_NOT_CACHE == 1 ? true : false
          }
        });
        router.addRoutes(routers);
        store.commit('setRouters', routers.concat(router.options.routes))
        store.commit('setPopedomList', popedomList)

        resolve(router)
      }).catch(function(reason){
        reject(reason);
      })
    })
  })
}

export const MyTreeTools = function () {
	
	this.attributes = { // 属性配置信息
      id: 'ID',
      parentId: 'PARENT_ID',
      rootId: "0"
	}
	
	this.toTreeData = function  (data, attributes, loadChild) {
		
		this.tree = [];
		
        if(typeof attributes === "function") { //是函数    其中 FunName 为函数名称
        	loadChild = attributes;
        	attributes = undefined;
        }
		
        if(!attributes) attributes = this.attributes;
		
		var resData = data;
		
		for (var i = 0; i < resData.length; i++) {
			if (resData[i][attributes.parentId] === attributes.rootId) {
        if(loadChild) {
          loadChild(resData[i]);
				}
        this.tree.push(resData[i]);
				resData.splice(i, 1);
				i--;
			}
		}
		run(this.tree);
		function run(chiArr) {
			if (resData.length !== 0) {
				for (var i = 0; i < chiArr.length; i++) {
					if(!chiArr[i].children) chiArr[i].children = [];
					for (var j = 0; j < resData.length; j++) {
            if(!resData[j].children) resData[j].children = [];
						if (chiArr[i][attributes.id] === resData[j][attributes.parentId]) {
							if(loadChild) {
								loadChild(resData[j]);
							}
							chiArr[i].children.push(resData[j]);
							resData.splice(j, 1);
							j--;
						}
					}
					if(chiArr[i].children.length > 0) run(chiArr[i].children);
				}
			}
		}
		
		return this.tree;
		
	}

}