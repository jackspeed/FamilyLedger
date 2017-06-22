package ycj.com.familyledger.http

import android.content.Context
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import ycj.com.familyledger.Consts
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
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
    val CACHE_STALE_LONG = 0//"60 * 60 * 24 * 7"

    var mOkHttpClient: OkHttpClient? = null

    var service: APIService? = null

    var mContext: Context? = null

    fun init(context: Context) {
        this.mContext = context
        initOkHttpclient()
        var retrofit = Retrofit.Builder()
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
        var response = chain.proceed(request)
        if (NetWorkUtils.isNetWorkConnected(mContext!!)) {
            var cacheControl: String = request.cacheControl().toString()
            return response.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build()
        } else {
            return response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                    .removeHeader("Pragma").build()
        }
    }

    fun loginAndRegister(phone: String, password: String): Observable<BaseResponse<UserBean>>
            = service!!.loginAndRegister(phone, password)

    fun getLedgerList(userId: String): Observable<BaseResponse<List<LedgerBean>>>
            = service!!.getLedgerList(userId)

    fun deleteLedger(id: Int): Observable<BaseResponse<LedgerBean>>
            = service!!.deleteLedger(id)

    fun addLedger(userId: String, time: String, cash: String): Observable<BaseResponse<LedgerBean>>
            = service!!.addLedger(userId, time, cash)

    fun updateLedger(id: Int, userId: String, time: String, cash: String): Observable<BaseResponse<LedgerBean>>
            = service!!.updateLedger(id, userId, time, cash)
}