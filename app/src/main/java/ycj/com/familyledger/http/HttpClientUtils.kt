package ycj.com.familyledger.http

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import com.alibaba.fastjson.JSON
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.methods.PostMethod
import ycj.com.familyledger.Consts
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.Users
import ycj.com.familyledger.impl.BaseCallBack

/**
 * @author: ycj
 * @date: 2017-06-20 16:27
 * @version V1.0 <>
 */
class HttpClientUtils {
    private val CONTENT_TYPE = "text/plain; charset:UTF-8"
    private var mContext: Context? = null
    private var client: HttpClient? = null

    fun init(context: Context) {
        this.mContext = context
        client = HttpClient()
        client?.httpConnectionManager?.params?.connectionTimeout = 1000 * 30
        client?.httpConnectionManager?.params?.soTimeout = 1000 * 30
    }

    companion object {
        @Volatile
        var instance: HttpClientUtils? = null

        fun getInstance(i: Int): HttpClientUtils {
            if (instance == null) {
                synchronized(HttpClientUtils::class.java) {
                    if (instance == null) {
                        instance = HttpClientUtils()
                    }
                }
            }
            return instance as HttpClientUtils
        }
    }

    private var callBack: BaseCallBack? = null

    fun login(phone: String, password: String, loginCallBack: BaseCallBack) {
        callBack = loginCallBack
        Thread(Runnable {
            try {
                val post = PostMethod(Consts.BASE_URL
                        + "Login.action?phone=" + phone
                        + "&password=" + password)
                post.setRequestHeader("Content-Type", CONTENT_TYPE)
                client?.executeMethod(post)
                val result = post.responseBodyAsString
                val msg = Message.obtain()
                msg.what = 10000
                msg.obj = result
                mHandler.sendMessage(msg)
            } catch (e: Exception) {
                e.printStackTrace()
                val msg = Message.obtain()
                msg.what = 10000
                msg.obj = ""
                mHandler.sendMessage(msg)
            }
        }).start()
    }

    private fun doPostRequest(method: PostMethod) {
        client?.executeMethod(method)
        val result = method.responseBodyAsString
        Log.i("******", "" + result)
    }

    private fun doGetRequest(method: GetMethod) {
        client?.executeMethod(method)
        val result = method.responseBodyAsString
        Log.i("******", "" + result)
    }

    fun getLedgerList(userId: String) {
        val post = PostMethod(Consts.BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("userId", userId)
//        val client = HttpClient()
//        client.httpConnectionManager.params.connectionTimeout = 1000 * 30
//        client.httpConnectionManager.params.soTimeout = 1000 * 30
        client?.executeMethod(post)
        val result = post.responseBodyAsString
    }

    //增加记录
    fun addLedger(bean: LedgerBean) {
        val post = PostMethod(Consts.BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("cash", bean.cash)
        post.setParameter("time", bean.time)
//        val client = HttpClient()
//        client.httpConnectionManager.params.connectionTimeout = 1000 * 30
//        client.httpConnectionManager.params.soTimeout = 1000 * 30
        client?.executeMethod(post)
        val result = post.responseBodyAsString
    }

    //增加记录
    fun deleteLedger(id: Long) {
        val post = PostMethod(Consts.BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("ledgerId", id.toString())
//        val client = HttpClient()
//        client.httpConnectionManager.params.connectionTimeout = 1000 * 30
//        client.httpConnectionManager.params.soTimeout = 1000 * 30
        client?.executeMethod(post)
        val result = post.responseBodyAsString
    }

    fun queryLedger(startDate: String, endDate: String) {
        val post = PostMethod(Consts.BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("startDate", startDate)
        post.setParameter("endDate", endDate)
//        val client = HttpClient()
//        client.httpConnectionManager.params.connectionTimeout = 1000 * 30
//        client.httpConnectionManager.params.soTimeout = 1000 * 30
        client?.executeMethod(post)
        val result = post.responseBodyAsString
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val jsonResult = msg.obj as String
            Log.i("**********", "" + jsonResult)
            when (msg.what) {
                10000 -> {//登录注册
                    try {
                        if (jsonResult.isEmpty()) callBack?.onFail("网络异常")
                        val data = JSON.parseObject(jsonResult, Users::class.java)
                        callBack?.onSuccess(data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        callBack?.onFail("解析异常")
                    }
                }
                20000 -> {//列表
                    if (jsonResult.isEmpty()) callBack?.onFail("网络异常")
                    val data = JSON.parseArray(jsonResult, LedgerBean::class.java)
                    callBack?.onSuccess(data)
                }
                30000 -> {//详情页
                    if (jsonResult.isEmpty()) callBack?.onFail("网络异常")
                    val data = JSON.parseObject(jsonResult, LedgerBean::class.java)
                    callBack?.onSuccess(data)
                }
                40000 -> {//添加
                    if (jsonResult.isEmpty()) callBack?.onFail("网络异常")
                    val data = JSON.parseObject(jsonResult, BaseResponse::class.java)
                    callBack?.onSuccess(data)
                }
                else -> {
                    callBack?.onFail("网络异常")
                }
            }
        }
    }
}