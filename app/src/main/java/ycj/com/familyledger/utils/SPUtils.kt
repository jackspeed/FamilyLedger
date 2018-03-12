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

    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        this.mContext = context
        sp = mContext?.getSharedPreferences(Consts.SP_APP_NAME, android.content.Context.MODE_PRIVATE)!!
    }

    fun getString(key: String): String {
        return sp.getString(key, "")
    }

    fun getLong(key: String): Long {
        return sp.getLong(key, 0)
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    fun putInt(key: String, value: Int) {
        sp.edit().putInt(key, value)?.apply()
    }


    fun putLong(key: String, value: Long) {
        sp.edit().putLong(key, value)?.apply()
    }


    fun putString(key: String, value: String) {
        sp.edit().putString(key, value)?.apply()
    }
}