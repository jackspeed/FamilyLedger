package ycj.com.familyledger

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import org.jetbrains.anko.*

class KRegisterActivity : AppCompatActivity() {

    private var mView: View? = null

    private var edxPhone: EditText? = null

    private var btnGo: Button? = null

    private var edxPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = initView()
        setContentView(mView)
    }

    private fun initView(): View {
        return UI {
            linearLayout {
                lparams(height = matchParent, width = matchParent) {
                    orientation = LinearLayout.VERTICAL
                }
//                title栏
                linearLayout {
                    textView("We Are  Family") {
                        textSize = sp(12).toFloat()
                        gravity = Gravity.CENTER
                        textColor = Color.WHITE
                        backgroundResource = R.color.colorPrimary
                        lparams(height = dip(48), width = matchParent)
                    }
                    lparams(height = dip(48), width = matchParent)
                }

                linearLayout {
                    lparams(height = matchParent, width = matchParent) {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                    }

                    edxPhone = editText {
                        id = R.id.edx_phone_main
                        maxLines = 1
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
                        lparams(width = matchParent, height = dip(48)) {
                            leftMargin = dip(40)
                            rightMargin = dip(40)
                        }
                        onClick {
                            saveData()
                            toast("注册成功")
                        }
                    }
                }
            }
        }.view
    }

    private fun saveData() {
        val edit = getSharedPreferences("sp_user", Context.MODE_PRIVATE).edit()
        edit.putString("phone", edxPhone!!.text.toString())
        edit.putString("password", edxPassword!!.text.toString())
        edit.apply()
    }

    private fun go2Home() {
        var intent: Intent? = null
        intent = Intent()
        startActivity(intent)
    }
}
