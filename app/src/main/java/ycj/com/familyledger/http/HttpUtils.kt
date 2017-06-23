package ycj.com.familyledger.http

import android.util.Log
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.UserBean
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.impl.IAddLedger
import ycj.com.familyledger.impl.IDeleteCallBack
import ycj.com.familyledger.impl.IGetLedgerList

/**
 * @author: ycj
 * @date: 2017-06-20 16:27
 * @version V1.0 <>
 */
class HttpUtils {

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
                    Log.i("ycj", result.toString())
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    Log.i("ycj", throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun getLedgerList(userId: String, callback: IGetLedgerList) {
        RetrofitUtils.getInstance()
                .getLedgerList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({
                    result ->
                    Log.i("ycj", result.toString())
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    Log.i("ycj", throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun deleteLedger(id: Int, callback: IDeleteCallBack) {
        RetrofitUtils.getInstance()
                .deleteLedger(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({
                    result ->
                    Log.i("ycj", result.toString())
                    callback.onDeleteSuccess(result)
                }) {
                    throwable ->
                    Log.i("ycj", throwable.toString())
                    callback.onDeleteFail(throwable.message.toString())
                }
    }

    fun addLedger(userId: String, time: String, cash: String, callback: IAddLedger) {
        RetrofitUtils.getInstance()
                .addLedger(userId, time, cash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({
                    result ->
                    Log.i("ycj", result.toString())
                    callback.onAddSuccess(result)
                }) {
                    throwable ->
                    Log.i("ycj", throwable.toString())
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
                    Log.i("ycj", result.toString())
                    callback.onSuccess(result)
                }) {
                    throwable ->
                    Log.i("ycj", throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }
}