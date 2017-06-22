package ycj.com.familyledger.http

import android.content.Context
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.UserBean
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.impl.IAddLedger
import ycj.com.familyledger.impl.IGetLedgerList

/**
 * @author: ycj
 * @date: 2017-06-20 16:27
 * @version V1.0 <>
 */
class HttpUtils {
    private var mContext: Context? = null
    fun init(context: Context) {
        this.mContext = context
    }

    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = HttpUtils()
    }


    fun loginAndRegister(phone: String, password: String, callback: BaseCallBack<UserBean>) {
        RetrofitUtils.getInstance()
                .loginAndRegister(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({
                    result ->
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    print(throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun getLedgerList(userId: String, callback: IGetLedgerList) {
        RetrofitUtils.getInstance()
                .getLedgerList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { println("call…………………………") }
                .subscribe({
                    result ->
                    print(result.toString())
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    println(throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun deleteLedger(id: Int, callback: BaseCallBack<LedgerBean>) {
        RetrofitUtils.getInstance()
                .deleteLedger(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { println("call…………………………") }
                .subscribe({
                    result ->
                    print(result.toString())
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    println(throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun addLedger(userId: String, time: String, cash: String, callback: IAddLedger) {
        RetrofitUtils.getInstance()
                .addLedger(userId, time, cash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { println("call…………………………") }
                .subscribe({
                    result ->
                    print(result.toString())
                    callback.onAddSuccess(result)
                }) {
                    throwable ->
                    println(throwable.toString())
                    callback.onAddFail(throwable.message.toString())
                }
    }

    fun updateLedger(id: Int, userId: String, time: String, cash: String, callback: BaseCallBack<LedgerBean>) {
        RetrofitUtils.getInstance()
                .updateLedger(id, userId, time, cash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .subscribe({
                    result ->
                    print(result.toString())
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    println(throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }
}