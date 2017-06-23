package ycj.com.familyledger.impl

import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean

/**
 * @author: ycj
 * @date: 2017-06-23 12:41
 * @version V1.0 <>
 */
interface IDeleteCallBack {
    fun onDeleteSuccess(data: BaseResponse<LedgerBean>)
    fun onDeleteFail(failMsg: String)
}