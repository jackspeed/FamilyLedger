package ycj.com.familyledger.bean

/**
 * @author: ycj
 * @date: 2017-06-21 17:45
 * @version V1.0 <>
 */
open class BaseResponse<T> {
    //    {"code":202,"message":"账号或密码不正确"}
    var code: Int = 0
    var message: String = ""
    var data: T? = null
    override fun toString(): String {
        return "BaseResponse(code=$code, message='$message', data=$data)"
    }


}