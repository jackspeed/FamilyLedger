package ycj.com.familyledger.http

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import ycj.com.familyledger.Consts
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.PageResult
import ycj.com.familyledger.bean.UserBean
import ycj.com.familyledger.utils.NetWorkUtils
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author: ycj
 * @date: 2017-06-22 15:40
 * @version V1.0 <>
 */
class RetrofitUtils private constructor() : Interceptor {
    //长缓存有效期为7天
    private val CACHE_STALE_LONG = 0//"60 * 60 * 24 * 7"

    var mOkHttpClient: OkHttpClient? = null

    var service: APIService? = null

    var mContext: Context? = null

    fun init(context: Context) {
        this.mContext = context
        initOkHttpclient()
        val retrofit = Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(APIService::class.java)
    }


    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        @SuppressLint("StaticFieldLeak")
        val instance = RetrofitUtils()
    }

    //配置缓存策略
    private fun initOkHttpclient() {
        val interceptor = HttpLogInterceptor()
        interceptor.level = HttpLogInterceptor.Level.BODY
        if (mOkHttpClient == null) {
            val cache = Cache(File(mContext?.cacheDir, "File_Kotlin"), 14 * 1024 * 100)
            mOkHttpClient = OkHttpClient.Builder()
                    .cache(cache)
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(this)
                    .addInterceptor(this)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build()
        }
    }

    //云端响应头拦截器，用来适配缓存策略
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        if (!NetWorkUtils.isNetWorkConnected(mContext!!)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val response = chain.proceed(request)
        return if (NetWorkUtils.isNetWorkConnected(mContext!!)) {
            val cacheControl: String = request.cacheControl().toString()
            response.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build()
        } else {
            response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                    .removeHeader("Pragma").build()
        }
    }

    fun login(phone: String, password: String): Observable<BaseResponse<UserBean>>
            = service!!.login(phone, password)

    fun register(userBean: UserBean): Observable<BaseResponse<UserBean>>
            = service!!.register(userBean)

    fun getUserList(userId: Long?): Observable<BaseResponse<List<UserBean>>>
            = service!!.getUserList(userId)

    fun getLedgerList(userId: Long, pageNo: Int, pageSize: Int): Observable<PageResult<LedgerBean>>
            = service!!.getLedgerList(userId, pageNo, pageSize)

    fun deleteLedger(id: Long): Observable<BaseResponse<String>>
            = service!!.deleteById(id)

    fun addLedger(userId: Long, time: String, cash: String): Observable<BaseResponse<String>>
            = service!!.addLedger(userId, time, cash)

    fun updateLedger(id: Long, userId: Long, time: String, cash: String): Observable<BaseResponse<String>>
            = service!!.updateLedger(id, userId, time, cash)
}