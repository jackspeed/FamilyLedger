package ycj.com.familyledger.ui

import org.jetbrains.anko.*
import ycj.com.familyledger.bean.Users
import ycj.com.familyledger.http.HttpClientUtils
import ycj.com.familyledger.impl.BaseCallBack


class KRegisterActivity : KBaseActivity(), BaseCallBack {
    private var edxPhone: android.widget.EditText? = null

    private var btnGo: android.widget.Button? = null

    private var edxPassword: android.widget.EditText? = null

    override fun initialize() {
        val mSP = getSharedPreferences(KRegisterActivity.Companion.SP_APP_NAME, android.content.Context.MODE_PRIVATE)
        val phone = mSP.getString(KRegisterActivity.Companion.SP_PHONE, "")
        val password = mSP.getString(KRegisterActivity.Companion.SP_PASSWORD, "")
        if (phone != "" && password != "") {
            edxPhone!!.setText(phone)
            edxPassword!!.setText(password)
//            HttpClientUtils.getInstance(0).login(phone, password, this@KRegisterActivity)
        }
    }

    override fun initView() {
        linearLayout {
            lparams(height = org.jetbrains.anko.matchParent, width = org.jetbrains.anko.matchParent) {
                orientation = android.widget.LinearLayout.VERTICAL
            }
            linearLayout {
                textView("伐木雷") {
                    textSize = resources.getDimension(ycj.com.familyledger.R.dimen.title_text_size)
                    gravity = android.view.Gravity.CENTER
                    textColor = resources.getColor(ycj.com.familyledger.R.color.white)
                    backgroundResource = ycj.com.familyledger.R.color.color_title_bar

                }.lparams(height = dip(48), width = org.jetbrains.anko.matchParent)
            }
            linearLayout {
                lparams(height = org.jetbrains.anko.matchParent, width = org.jetbrains.anko.matchParent) {
                    gravity = android.view.Gravity.CENTER
                    orientation = android.widget.LinearLayout.VERTICAL
                    topMargin = dip(48)
                }

                edxPhone = editText {
                    id = ycj.com.familyledger.R.id.edx_phone_main
                    maxLines = 1
                    maxEms = 11
                    inputType = android.text.InputType.TYPE_CLASS_PHONE
                    filters = arrayOf<android.text.InputFilter>(android.text.InputFilter.LengthFilter(11))
                    backgroundResource = ycj.com.familyledger.R.drawable.edx_input_bg
                    hint = "请输入手机号"

                }.lparams(width = org.jetbrains.anko.matchParent, height = dip(48)) {
                    bottomMargin = dip(40)
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }

                edxPassword = editText {
                    id = ycj.com.familyledger.R.id.edx_password_main
                    maxLines = 1
                    inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                    filters = arrayOf<android.text.InputFilter>(android.text.InputFilter.LengthFilter(20))
                    backgroundResource = ycj.com.familyledger.R.drawable.edx_input_bg
                    hint = "请输密码"
                }.lparams(width = org.jetbrains.anko.matchParent, height = dip(48)) {
                    bottomMargin = dip(40)
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }

                btnGo = button {
                    id = ycj.com.familyledger.R.id.btn_go_main
                    text = "注册"
                    textSize = sp(10).toFloat()
                    textColor = resources.getColor(ycj.com.familyledger.R.color.white)
                    backgroundResource = ycj.com.familyledger.R.drawable.bg_btn
                }.lparams(width = org.jetbrains.anko.matchParent, height = dip(48)) {
                    leftMargin = dip(40)
                    rightMargin = dip(40)
                }
            }
        }
    }

    override fun initListener() {
        btnGo!!.setOnClickListener {
            var phone = edxPhone!!.text.toString()
            var password = edxPassword!!.text.toString()
            if (phone.length < 11) {
                toast(getString(ycj.com.familyledger.R.string.error_phone_number))
            } else if (password.length < 3) {
                toast(getString(ycj.com.familyledger.R.string.password_not_full_six))
            } else {
                HttpClientUtils.getInstance(0).login(phone, password, this@KRegisterActivity)
            }
        }
    }

    override fun onSuccess(data: Any) {
        data as Users
        if (data.code == 200) {
            if (data.data?.loginFlag!!) {
                toast(getString(ycj.com.familyledger.R.string.success_login))
            } else {
                toast(getString(ycj.com.familyledger.R.string.success_register))
            }
            saveData()
            go2Home()
        } else {
            toast(data.message)
        }
    }

    override fun onFail(msg: String) {
        toast(getString(ycj.com.familyledger.R.string.fail_login_in))
    }

    companion object {
        var SP_APP_NAME = "sp_user"
        var SP_PHONE = "phone"
        var SP_PASSWORD = "password"
    }

    private fun saveData() {
        val edit = getSharedPreferences(KRegisterActivity.Companion.SP_APP_NAME, android.content.Context.MODE_PRIVATE).edit()
        edit.putString(KRegisterActivity.Companion.SP_PHONE, edxPhone!!.text.toString())
        edit.putString(KRegisterActivity.Companion.SP_PASSWORD, edxPassword!!.text.toString())
        edit.apply()
    }

    private fun go2Home() {
        startActivity<KHomeActivity>()
        finish()
    }
}
