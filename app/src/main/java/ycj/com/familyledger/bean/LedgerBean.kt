package ycj.com.familyledger.bean

import android.os.Parcel
import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONField
import java.util.*

/**
 * @author ycj
 * @version V1.0 <消费记录>
 * @date 2017-12-07 17:45
</消费记录> */
class LedgerBean() : Parcelable {

    /**
     * 主键
     */
    var id: Long? = null

    /**
     * 状态 0正常 1删除
     */
    var status: String? = null
    /**
     * 用户id
     */
    @JSONField(name = "user_id")
    var userId: Long? = null

    /**
     * 消费时间
     */
    @JSONField(name = "consume_date")
    var consumeDate: String? = null

    /**
     * 消费金额
     */
    @JSONField(name = "consume_money")
    lateinit var consumeMoney: String

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

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        status = parcel.readString()
        userId = parcel.readValue(Long::class.java.classLoader) as? Long
        consumeDate = parcel.readString()
        consumeMoney = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(status)
        parcel.writeValue(userId)
        parcel.writeString(consumeDate)
        parcel.writeString(consumeMoney)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LedgerBean> {
        override fun createFromParcel(parcel: Parcel): LedgerBean {
            return LedgerBean(parcel)
        }

        override fun newArray(size: Int): Array<LedgerBean?> {
            return arrayOfNulls(size)
        }
    }
}
