package ycj.com.familyledger.impl

import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean

/**
 * @author: ycj
 * @date: 2017-06-22 18:01
 * @version V1.0 <>
 */
interface IGetLedgerList {
    fun onSuccess(data: BaseResponse<List<LedgerBean>>)
    fun onFail(msg: String)
}