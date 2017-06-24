package ycj.com.familyledger.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author: ycj
 * @date: 2017-06-20 17:13
 * @version V1.0 <>
 */
data class LedgerBean(val id: Int, val time: String, val cash: String, val userId: String) : Parcelable {
    override fun toString(): String {
        return "LedgerBean(id=$id, time='$time', cash='$cash', userId='$userId')"
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<LedgerBean> = object : Parcelable.Creator<LedgerBean> {
            override fun createFromParcel(source: Parcel): LedgerBean = LedgerBean(source)
            override fun newArray(size: Int): Array<LedgerBean?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(time)
        dest.writeString(cash)
        dest.writeString(userId)
    }
}