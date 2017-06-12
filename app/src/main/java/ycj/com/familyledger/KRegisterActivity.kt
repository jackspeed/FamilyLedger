package ycj.com.familyledger

import android.content.Context
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        linearLayout {
            lparams(height = matchParent, width = matchParent) {
                orientation = LinearLayout.VERTICAL
            }
//                title栏
            linearLayout {
                textView("伐木雷") {
                    textSize = sp(8).toFloat()
                    gravity = Gravity.CENTER_HORIZONTAL
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.color.color_title_bar
                    lparams(height = dip(48), width = matchParent)
                }
                lparams(height = dip(48), width = matchParent)
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
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "请输入手机号"
                    lparams(width = matchParent, height = dip(48)) {
                        bottomMargin = dip(40)
                        leftMargin = dip(40)
                        rightMargin = dip(40)
                        padding = dip(5)
                    }
                }

                edxPassword = editText {
                    id = R.id.edx_password_main
                    maxLines = 1
                    inputType = inputType
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "请输密码"
                    lparams(width = matchParent, height = dip(48)) {
                        bottomMargin = dip(40)
                        leftMargin = dip(40)
                        rightMargin = dip(40)
                        padding = dip(5)

                    }
                }

                btnGo = button {
                    id = R.id.btn_go_main
                    text = "注册"
                    textSize = sp(6).toFloat()
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.drawable.bg_btn
                    lparams(width = matchParent, height = dip(48)) {
                        leftMargin = dip(40)
                        rightMargin = dip(40)
                    }
                    onClick {
                        if (edxPhone!!.text.toString().length < 11) {
                            toast("手机号不正确")
                        } else if (edxPassword!!.text.toString().length < 6) {
                            toast("密码不能少于六位")
                        } else {
                            saveData()
                            toast("注册成功")
                            go2Home()
                        }
                    }
                }
            }
        }
    }

    private fun saveData() {
        val edit = getSharedPreferences("sp_user", Context.MODE_PRIVATE).edit()
        edit.putString("phone", edxPhone!!.text.toString())
        edit.putString("password", edxPassword!!.text.toString())
        edit.apply()
    }

    private fun go2Home() {
        startActivity<KHomeActivity>()
    }
}
