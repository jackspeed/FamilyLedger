package ycj.com.familyledger.http

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.PageResult
import ycj.com.familyledger.bean.UserBean

/**
 * @author: ycj
 * @date: 2017-06-22 15:44
 * @version V1.0 <>
 */
interface APIService {
    @POST("user/login")
    fun login(@Query("mobile") mobile: String,
              @Query("password") password: String): Observable<BaseResponse<UserBean>>

    @POST("user/register")
    fun register(userBean: UserBean): Observable<BaseResponse<UserBean>>

    @GET("user/findUserList") //userId不传，获取全部
    fun getUserList(@Query("userId") userId: Long?): Observable<BaseResponse<List<UserBean>>>

    @GET("consume/record/findByCondition") //userId不传，获取全部
    fun getLedgerList(@Query("userId") userId: Long,
                      @Query("pageNo") pageNo: Int,
                      @Query("pageSize") pageSize: Int): Observable<PageResult<LedgerBean>>

    @POST("consume/record/deleteById")
    fun deleteById(@Query("id") id: Long): Observable<BaseResponse<String>>

    @POST("consume/record/add")
    fun addLedger(@Query("userId") userId: Long,
                  @Query("consumeDate") consume_date: String,
                  @Query("consumeMoney") consume_money: String): Observable<BaseResponse<String>>

    @POST("consume/record/update")
    fun updateLedger(
            @Query("id") id: Long,
            @Query("userId") userId: Long,
            @Query("consumeDate") consumeDate: String,
            @Query("consumeMoney") consumeMoney: String): Observable<BaseResponse<String>>
}
