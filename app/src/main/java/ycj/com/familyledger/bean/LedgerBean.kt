package ycj.com.familyledger.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author ycj
 * @version V1.0 <消费记录>
 * @date 2017-12-07 17:45
</消费记录> */
class LedgerBean() :Parcelable {


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
    var user_id: Long? = null


    /**
     * 用户名
     */
    var userName: String? = null


    /**
     * 手机号
     */
    var mobile: String? = null

    /**
     * 消费时间
     */
    var consume_date: String? = null

    /**
     * 消费金额
     */
    var consume_money: String? = null

    /**
     * 修改时间
     */
    var update_date: String? = null

    /**
     * 创建时间
     */
    var create_date: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        status = parcel.readString()
        user_id = parcel.readValue(Long::class.java.classLoader) as? Long
        userName = parcel.readString()
        mobile = parcel.readString()
        consume_date = parcel.readString()
        consume_money = parcel.readString()
        update_date = parcel.readString()
        create_date = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(status)
        parcel.writeValue(user_id)
        parcel.writeString(userName)
        parcel.writeString(mobile)
        parcel.writeString(consume_date)
        parcel.writeString(consume_money)
        parcel.writeString(update_date)
        parcel.writeString(create_date)
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
