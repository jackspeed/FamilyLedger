package ycj.com.familyledger.bean

/**
 * @author: ycj
 * @date: 2017-06-20 16:06
 * @version V1.0 <>
 */
class Users : BaseResponse() {

    /**
     * code : 200
     * data : {"loginName":"17710580646","pwd":"123456"}
     * message : 注册成功
     */

    internal var data: DataBean? = null

    class DataBean {
        /**
         * loginName : 17710580646
         * pwd : 123456
         */
        var loginName: String? = null
        var pwd: String? = null
        var loginFlag: Boolean = false

    }
}