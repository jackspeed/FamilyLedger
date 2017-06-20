package ycj.com.familyledger

import android.app.Application
import ycj.com.familyledger.http.HttpClientUtils

/**
 * @author: ycj
 * @date: 2017-06-12 17:09
 * @version V1.0 <>
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO:初始化程序
        //缓存Context
        HttpClientUtils.getInstance(0).init(this)
    }
}