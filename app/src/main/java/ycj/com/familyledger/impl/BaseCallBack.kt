package ycj.com.familyledger.impl

/**
 * @author: ycj
 * @date: 2017-06-21 17:21
 * @version V1.0 <>
 */
interface BaseCallBack {
    fun onSuccess(data: Any)
    fun onFail(msg: String)
}