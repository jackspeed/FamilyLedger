package ycj.com.familyledger.ui

import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.startActivity
import ycj.com.familyledger.Consts
import ycj.com.familyledger.R
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.UserBean
import ycj.com.familyledger.http.HttpUtils
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.utils.RegexUtils
import ycj.com.familyledger.utils.SPUtils

class KLogoActivity : KBaseActivity(), BaseCallBack<UserBean> {
    override fun initialize() {
        showLoading()
        android.os.Handler().postDelayed({
            val userName = SPUtils.getInstance().getString(Consts.SP_USER_NAME)
            val phone = SPUtils.getInstance().getString(Consts.SP_PHONE)
            val password = SPUtils.getInstance().getString(Consts.SP_PASSWORD)
            if (userName == "" || phone == "" || password == "" || !RegexUtils.create().isMobileExact(phone)) {
                loginFail()
                hideLoading()
            } else {
                HttpUtils.getInstance().loginAndRegister(userName, phone, password, this)
            }
        }, 1000)
    }

    override fun initView() {
        //自动把下面的布局设置为setContentView
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundResource = R.mipmap.logo
        }
    }

    override fun initListener() {}


    override fun onSuccess(data: BaseResponse<UserBean>) {
        hideLoading()
        if (data.code == 200) {
            SPUtils.getInstance().putLong(Consts.SP_PHONE, data.data!!.userId!!)
            SPUtils.getInstance().putString(Consts.SP_PASSWORD, data.data!!.password!!)
            SPUtils.getInstance().putString(Consts.SP_USER_ID, data.data!!.userId.toString())
            SPUtils.getInstance().putString(Consts.SP_USER_NAME, data.data!!.userName!!)
            startActivity<KHomeActivity>()
            finish()
        } else {
            loginFail()
        }
    }

    override fun onFail(msg: String) {
        hideLoading()
        loginFail()
    }

    private fun loginFail() {
        startActivity<KRegisterActivity>()
        finish()
    }
}
