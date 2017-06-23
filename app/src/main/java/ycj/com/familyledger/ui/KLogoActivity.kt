package ycj.com.familyledger.ui

import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.startActivity
import ycj.com.familyledger.Consts
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
            val phone = SPUtils.getInstance().getString(Consts.SP_PHONE)
            val password = SPUtils.getInstance().getString(Consts.SP_PASSWORD)
            if (phone == null || phone == "" || password == null || password == "" || !RegexUtils.create().isMobileExact(phone)) {
                loginFail()
                hideLoading()
            } else {
                HttpUtils.getInstance().loginAndRegister(phone, password, this)
            }
        }, 1000)
    }

    override fun initView() {
        //自动把下面的布局设置为setContentView
        linearLayout {
            lparams(width = org.jetbrains.anko.matchParent, height = org.jetbrains.anko.matchParent)
            backgroundResource = ycj.com.familyledger.R.mipmap.logo
        }
    }

    override fun initListener() {}


    override fun onSuccess(data: BaseResponse<UserBean>) {
        hideLoading()
        if (data.code == 200) {
            SPUtils.getInstance().putString(Consts.SP_PHONE, data.data!!.loginName)
            SPUtils.getInstance().putString(Consts.SP_PASSWORD, data.data!!.pwd)
            SPUtils.getInstance().putString(Consts.SP_USER_ID, data.data!!.userId)
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
