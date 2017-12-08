package ycj.com.familyledger.bean

/**
 * @author: ycj
 * @date: 2017-06-21 17:45
 * @version V1.0 <>
 */
open class BaseResponse<T>(var result: Int,
                           var data: T,
                           var error: Error) {
    class Error(var code: String, var message: String)
}