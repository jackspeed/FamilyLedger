package ycj.com.familyledger.http

import android.util.Log
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import ycj.com.familyledger.bean.UserBean
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.impl.IGetLedgerList

/**
 * @author: ycj
 * @date: 2017-06-20 16:27
 * @version V1.0 <>
 */
class HttpUtils {
    private val tag = "HttpUtils"


    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = HttpUtils()
    }

    fun login(phone: String, password: String, callback: BaseCallBack<UserBean>) {
        RetrofitUtils.getInstance()
                .login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun register(userBean: UserBean, callback: BaseCallBack<UserBean>) {
        RetrofitUtils.getInstance()
                .register(userBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun getLedgerList(userId: Long, pageNo: Int, pageSize: Int, callback: IGetLedgerList) {
        RetrofitUtils.getInstance()
                .getLedgerList(userId, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun getUserList(callback: BaseCallBack<List<UserBean>>) {
        RetrofitUtils.getInstance()
                .getUserList(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun deleteLedger(id: Long, callback: BaseCallBack<String>) {
        RetrofitUtils.getInstance()
                .deleteLedger(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun addLedger(userId: Long, time: String, cash: String, callback: BaseCallBack<String>) {
        RetrofitUtils.getInstance()
                .addLedger(userId, time, cash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { }
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }

    fun updateLedger(id: Long, userId: Long, time: String, cash: String, callback: BaseCallBack<String>) {
        RetrofitUtils.getInstance()
                .updateLedger(id, userId, time, cash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .subscribe({ result ->
                    Log.i(tag, result.toString())
                    callback.onSuccess(result)
                }) { throwable ->
                    Log.i(tag, throwable.toString())
                    callback.onFail(throwable.message.toString())
                }
    }
}