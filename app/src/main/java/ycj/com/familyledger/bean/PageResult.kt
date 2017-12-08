package ycj.com.familyledger.bean

/**
 * @version V1.0 <>
 * @author: ycj
 * @date: 2017-12-08 17:25
 */

class PageResult<T>(var firstPage: Boolean,
                    var lastPage: Boolean,
                    var pageNo: Int,
                    var pageSize: Int,
                    var pages: Int,
                    var total: Int,
                    var list: List<T>
)
