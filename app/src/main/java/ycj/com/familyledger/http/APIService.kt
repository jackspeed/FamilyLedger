package ycj.com.familyledger.http

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.UserBean

/**
 * @author: ycj
 * @date: 2017-06-22 15:44
 * @version V1.0 <>
 */
interface APIService {
    @POST("loginAndRegister.action")
    fun loginAndRegister(@Query("loginName") userName: String,
                         @Query("phone") phone: String,
                         @Query("pwd") password: String): Observable<BaseResponse<UserBean>>

    @GET("getUserList.action") //userId不传，获取全部
    fun getUserList(): Observable<BaseResponse<List<UserBean>>>

    @GET("getLedgerList.action") //userId不传，获取全部
    fun getLedgerList(@Query("userId") userId: Long): Observable<BaseResponse<List<LedgerBean>>>

    @POST("deleteLedger.action")
    fun deleteLedger(@Query("id") id: Long): Observable<BaseResponse<LedgerBean>>

    @POST("addLedger.action")
    fun addLedger(@Query("userId") userId: Long,
                  @Query("time") time: String,
                  @Query("cash") cash: String): Observable<BaseResponse<LedgerBean>>

    @POST("updateLedger.action")
    fun updateLedger(
            @Query("id") id: Long,
            @Query("userId") userId: Long,
            @Query("time") time: String,
            @Query("cash") cash: String): Observable<BaseResponse<LedgerBean>>
}
