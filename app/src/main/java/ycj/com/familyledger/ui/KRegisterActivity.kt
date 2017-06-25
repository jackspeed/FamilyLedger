package ycj.com.familyledger.ui

import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.*
import ycj.com.familyledger.Consts
import ycj.com.familyledger.R
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.UserBean
import ycj.com.familyledger.http.HttpUtils
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.utils.RegexUtils
import ycj.com.familyledger.utils.SPUtils


class KRegisterActivity : KBaseActivity(), BaseCallBack<UserBean> {

    private var edxPhone: android.widget.EditText? = null

    private var btnGo: android.widget.Button? = null

    private var edxName: EditText? = null

    private var edxPassword: android.widget.EditText? = null

    override fun initialize() {
        val phone = SPUtils.getInstance().getString(Consts.SP_PHONE)
        val password = SPUtils.getInstance().getString(Consts.SP_PASSWORD)
        val userName = SPUtils.getInstance().getString(Consts.SP_USER_NAME)
        edxName?.visibility = if (userName.isEmpty()) View.VISIBLE else View.GONE
        edxName!!.setText(userName)
        edxPhone!!.setText(phone)
        edxPassword!!.setText(password)
    }


    override fun initView() {
        linearLayout {
            lparams(height = matchParent, width = matchParent) {
                orientation = android.widget.LinearLayout.VERTICAL
            }
            linearLayout {
                textView("伐木雷") {
                    textSize = resources.getDimension(R.dimen.title_text_size)
                    gravity = android.view.Gravity.CENTER
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.color.color_title_bar

                }.lparams(height = dip(48), width = matchParent)
            }
            linearLayout {
                lparams(height = matchParent, width = matchParent) {
                    gravity = android.view.Gravity.CENTER
                    orientation = android.widget.LinearLayout.VERTICAL
                    topMargin = dip(48)
                }
                edxName = editText {
                    id = R.id.edx_phone_main
                    maxLines = 1
                    maxEms = 11
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(11))
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "留下你的名号"

                }.lparams(width = matchParent, height = dip(48)) {
                    bottomMargin = dip(40)
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }
                edxPhone = editText {
                    id = R.id.edx_phone_main
                    maxLines = 1
                    maxEms = 11
                    inputType = InputType.TYPE_CLASS_PHONE
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(11))
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "请输入手机号"

                }.lparams(width = matchParent, height = dip(48)) {
                    bottomMargin = dip(40)
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }

                edxPassword = editText {
                    id = R.id.edx_password_main
                    maxLines = 1
                    inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "请输密码"
                }.lparams(width = matchParent, height = dip(48)) {
                    bottomMargin = dip(40)
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }

                btnGo = button {
                    id = R.id.btn_go_main
                    text = "进入应用"
                    textSize = sp(10).toFloat()
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.drawable.bg_btn
                }.lparams(width = matchParent, height = dip(48)) {
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }
            }
        }
    }

    override fun initListener() {
        btnGo!!.setOnClickListener {
            val userName = edxName!!.text.toString().trim()
            val phone = edxPhone!!.text.toString().trim()
            val password = edxPassword!!.text.toString().trim()
            if (userName.isEmpty()) {
                toast(getString(R.string.error_user_name))
            } else if (phone.length < 11) {
                toast(getString(R.string.error_phone_number))
            } else if (password.length < 3) {
                toast(getString(R.string.password_not_full_six))
            } else if (!RegexUtils.create().isMobileExact(phone)) {
                toast(getString(R.string.not_phone_number))
            } else {
                showLoading()
                HttpUtils.getInstance().loginAndRegister(userName, phone, password, this@KRegisterActivity)
            }
        }
    }

    override fun onSuccess(data: BaseResponse<UserBean>) {
        hideLoading()
        if (data.code == 200) {
            saveData(data.data!!)
            startActivity<KHomeActivity>()
            finish()
        }
        toast(data.message)
    }

    override fun onFail(msg: String) {
        hideLoading()
        toast(getString(R.string.fail_login_in))
    }

    private fun saveData(user: UserBean) {
        SPUtils.getInstance().putString(Consts.SP_PHONE, edxPhone!!.text.toString())
        SPUtils.getInstance().putString(Consts.SP_PASSWORD, edxPassword!!.text.toString())
        SPUtils.getInstance().putString(Consts.SP_USER_ID, if (user.userId == null) "" else user.userId)
        SPUtils.getInstance().putString(Consts.SP_USER_NAME, if (user.loginName == null) "" else user.loginName)
    }
}
