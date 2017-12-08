package ycj.com.familyledger.bean

import com.alibaba.fastjson.annotation.JSONField
import java.util.*

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2017-12-07 16:16
 */
class UserBean {
    /**
     * 主键
     */
    var id: Long? = null

    /**
     * 用户id
     */
    var userId: Long? = null

    /**
     * 用户名
     */
    @JSONField(name = "user_name")
    var userName: String? = null

    /**
     * 昵称
     */
    @JSONField(name = "nick_name")
    var nickName: String? = null

    /**
     * 手机号
     */
    var mobile: String? = null

    /**
     * 密码
     */
    var password: String? = null

    /**
     * 修改时间
     */
    @JSONField(name = "update_date", format = "yyyy-MM-dd HH:mm:ss")
    var updateDate: Date? = null

    /**
     * 创建时间
     */
    @JSONField(name = "create_date", format = "yyyy-MM-dd HH:mm:ss")
    var createDate: Date? = null


}
