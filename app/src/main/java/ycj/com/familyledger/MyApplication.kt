package ycj.com.familyledger

import android.app.Application
import ycj.com.familyledger.http.RetrofitUtils
import ycj.com.familyledger.utils.SPUtils

/**
 * @author: ycj
 * @date: 2017-06-12 17:09
 * @version V1.0 <>
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //缓存Context
        SPUtils.getInstance().init(applicationContext)
        RetrofitUtils.getInstance().init(applicationContext)
    }
}