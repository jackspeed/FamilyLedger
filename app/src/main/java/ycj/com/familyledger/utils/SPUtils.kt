package ycj.com.familyledger.utils

import android.content.Context
import android.content.SharedPreferences
import ycj.com.familyledger.Consts

/**
 * @author: ycj
 * @date: 2017-06-22 16:54
 * @version V1.0 <>
 */
class SPUtils {
    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = SPUtils()
    }

    private var mContext: Context? = null

    private var sp: SharedPreferences? = null

    fun init(context: Context) {
        this.mContext = context
        sp = mContext?.getSharedPreferences(Consts.SP_APP_NAME, android.content.Context.MODE_PRIVATE)
    }

    fun getString(key: String): String {
        if (key == null) return ""

        return sp?.getString(key, "").toString()
    }

    fun getLong(key: String): Long {
        if (key == null) return 0
        return sp?.getLong(key, 0) as Long
    }


    fun getInt(key: String): Int {
        if (key == null) return 0
        return sp?.getInt(key, 0) as Int
    }

    fun putInt(key: String, value: Int) {
        if (key == null) return
        sp?.edit()?.putInt(key, value)?.apply()
    }


    fun putLong(key: String, value: Long) {
        if (key == null) return
        sp?.edit()?.putLong(key, value)?.apply()
    }


    fun putString(key: String, value: String) {
        if (key == null || value == null) return
        sp?.edit()?.putString(key, value)?.apply()
    }
}