package ycj.com.familyledger.impl

import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.PageResult

/**
 * @author: ycj
 * @date: 2017-06-22 18:01
 * @version V1.0 <>
 */
interface IGetLedgerList {
    fun onSuccess(data: PageResult<LedgerBean>)
    fun onFail(msg: String)
}