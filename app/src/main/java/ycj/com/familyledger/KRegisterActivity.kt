package ycj.com.familyledger

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import org.jetbrains.anko.*


class KRegisterActivity : KBaseActivity() {


    private var edxPhone: EditText? = null

    private var btnGo: Button? = null

    private var edxPassword: EditText? = null

    override fun initialize() {
        val mSP = getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE)
        val phone = mSP.getString(SP_PHONE, "")
        val password = mSP.getString(SP_PASSWORD, "")
        if (phone != "" && password != "") {
            go2Home()
            edxPhone!!.setText(phone)
            edxPassword!!.setText(password)
        }
    }

    override fun initView() {
        linearLayout {
            lparams(height = matchParent, width = matchParent) {
                orientation = LinearLayout.VERTICAL
            }
            linearLayout {
                textView("伐木雷") {
                    textSize = resources.getDimension(R.dimen.title_text_size)
                    gravity = Gravity.CENTER
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.color.color_title_bar

                }.lparams(height = dip(48), width = matchParent)
            }
            linearLayout {
                lparams(height = matchParent, width = matchParent) {
                    gravity = Gravity.CENTER
                    orientation = LinearLayout.VERTICAL
                    topMargin = dip(48)
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
                    text = "注册"
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
            if (edxPhone!!.text.toString().length < 11) {
                toast(getString(R.string.error_phone_number))
            } else if (edxPassword!!.text.toString().length < 6) {
                toast(getString(R.string.password_not_full_six))
            } else {
                saveData()
                toast(getString(R.string.success_register))
                go2Home()
            }
        }
    }

    companion object {
        var SP_APP_NAME = "sp_user"
        var SP_PHONE = "phone"
        var SP_PASSWORD = "password"
    }

    private fun saveData() {
        val edit = getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE).edit()
        edit.putString(SP_PHONE, edxPhone!!.text.toString())
        edit.putString(SP_PASSWORD, edxPassword!!.text.toString())
        edit.apply()
    }

    private fun go2Home() {
        startActivity<KHomeActivity>()
    }
}
