package ycj.com.familyledger.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author: ycj
 * @date: 2017-06-22 15:43
 * @version V1.0 <>
 */
class NetWorkUtils {
    companion object {
        fun isNetWorkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val currentNet = cm.activeNetworkInfo ?: return false
            return currentNet.isAvailable
        }
    }
}