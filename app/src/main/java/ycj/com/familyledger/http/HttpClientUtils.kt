package ycj.com.familyledger.http

import android.content.Context
import android.util.Log
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.methods.PostMethod
import ycj.com.familyledger.bean.LedgerBean

/**
 * @author: ycj
 * @date: 2017-06-20 16:27
 * @version V1.0 <>
 */
class HttpClientUtils {
    private val BASE_URL = "http://192.168.1.126:8080/webpro1/"
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

    fun test() {
        Thread(Runnable {
            try {
                val ss="?phone=1330227123456"
                val post = PostMethod(BASE_URL + "test.do"+ss)
                post.setRequestHeader("Content-Type", CONTENT_TYPE)
                doPostRequest(post)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    fun login(phoneNumber: String, password: String) {
        val post = PostMethod(BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("phoneNumber", phoneNumber)
        post.setParameter("password", password)
        client?.executeMethod(post)
        doPostRequest(post)
    }

    private fun doPostRequest(method: PostMethod) {
        client?.executeMethod(method)
        val result = method.responseBodyAsString
        Log.i("******", "" + method.uri)
        Log.i("******", method.responseCharSet + "   \n  " + result)
    }

    private fun doGetRequest(method: GetMethod) {
        client?.executeMethod(method)
        val result = method.responseBodyAsString
    }

    fun getLedgerList(userId: String) {
        val post = PostMethod(BASE_URL)
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
        val post = PostMethod(BASE_URL)
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
        val post = PostMethod(BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("ledgerId", id.toString())
//        val client = HttpClient()
//        client.httpConnectionManager.params.connectionTimeout = 1000 * 30
//        client.httpConnectionManager.params.soTimeout = 1000 * 30
        client?.executeMethod(post)
        val result = post.responseBodyAsString
    }

    fun queryLedger(startDate: String, endDate: String) {
        val post = PostMethod(BASE_URL)
        post.setRequestHeader("Content-Type", CONTENT_TYPE)
        post.setParameter("startDate", startDate)
        post.setParameter("endDate", endDate)
//        val client = HttpClient()
//        client.httpConnectionManager.params.connectionTimeout = 1000 * 30
//        client.httpConnectionManager.params.soTimeout = 1000 * 30
        client?.executeMethod(post)
        val result = post.responseBodyAsString
    }
}