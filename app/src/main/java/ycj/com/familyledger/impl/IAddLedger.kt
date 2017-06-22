package ycj.com.familyledger.impl

import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean

/**
 * @author: ycj
 * @date: 2017-06-22 17:54
 * @version V1.0 <>
 */
interface IAddLedger {
    fun onAddSuccess(data: BaseResponse<LedgerBean>)
    fun onAddFail(failMsg: String)
}