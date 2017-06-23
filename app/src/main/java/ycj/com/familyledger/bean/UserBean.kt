package ycj.com.familyledger.bean

/**
 * @author: ycj
 * @date: 2017-06-22 18:04
 * @version V1.0 <>
 */
data class UserBean(var loginFlag: Boolean, var loginName: String, var pwd: String, var userId: String){
    override fun toString(): String {
        return "UserBean(isLoginFlag=$loginFlag, loginName='$loginName', pwd='$pwd', userId='$userId')"
    }
}