package ycj.com.familyledger.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.*
import ycj.com.familyledger.R
import ycj.com.familyledger.utils.RegexUtils

/**
 * @author: ycj
 * @date: 2017-06-15 15:06
 * @version V1.0 <>
 */
class EdxDialog {
    companion object {
        fun create(): EdxDialog = EdxDialog()
    }

    fun showEdxDialog(msg: String, context: Context, callBack: DataCallBack) {
        var dialog = Dialog(context)
        dialog.show()
        var wind = dialog.window
        wind.setBackgroundDrawable(BitmapDrawable())
        var windowMa = context.windowManager

        var attri = wind.attributes
        attri.width = (windowMa.defaultDisplay.width * 0.75).toInt()
        attri.height = (windowMa.defaultDisplay.height * 0.45).toInt()
        wind.attributes = attri

        var edxDate: EditText? = null
        var edxCash: EditText? = null
        var tvOk: TextView? = null

        val dialogView = context.relativeLayout {
            background = context.resources.getDrawable(R.drawable.bg_edx_dialog)
            lparams(width = matchParent, height = matchParent) {
                topPadding = dip(10)
            }
            textView(msg) {
                gravity = Gravity.CENTER
                textSize = sp(10).toFloat()
                textColor = context.resources.getColor(R.color.black_text)
            }.lparams(width = matchParent, height = dip(48))

            linearLayout {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                edxDate = editText {
                    id = R.id.edx_date_add
                    maxLines = 1
                    textSize = sp(8).toFloat()
                    inputType = InputType.TYPE_DATETIME_VARIATION_DATE or InputType.TYPE_CLASS_DATETIME
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
                    background = context.resources.getDrawable(R.drawable.edx_input_bg)
                    hintTextColor = context.resources.getColor(R.color.gray_text)
                    hint = "日期模板 2017-06-23"
                }.lparams(width = matchParent, height = dip(40)) {
                    rightMargin = dip(20)
                    leftMargin = dip(20)
                }

                edxCash = editText {
                    id = R.id.edx_cash_add
                    maxLines = 1
                    textSize = sp(8).toFloat()
                    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(8))
                    background = context.resources.getDrawable(R.drawable.edx_input_bg)
                    hint = "请输入金额"
                    hintTextColor = context.resources.getColor(R.color.gray_text)

                }.lparams(width = matchParent, height = dip(40)) {
                    topMargin = dip(20)
                    rightMargin = dip(20)
                    leftMargin = dip(20)
                }

            }.lparams(width = matchParent, height = wrapContent) {
                centerInParent()
            }

            tvOk = textView("确定") {
                gravity = Gravity.CENTER
                textSize = sp(9).toFloat()
                textColor = context.resources.getColor(R.color.btn_text_color_selector)
                background = context.resources.getDrawable(R.drawable.bg_btn_bottom_dialog)
            }.lparams(width = matchParent, height = dip(40)) {
                topMargin = dip(20)
                alignParentBottom()
            }
        }
        tvOk?.setOnClickListener {
            val dates = edxDate?.editableText.toString()
            val cashs = edxCash?.editableText.toString()
            if (dates.length < 6 || cashs == "") {
                context.toast("数据有误")
            } else if (RegexUtils.create().isDate(dates)) {
                callBack.callBack(dates, cashs)
                dialog.dismiss()
            } else {
                context.toast("日期格式不正确")
            }
        }
        edxCash?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val res = s.toString()

                if (s !is Number && s != ".") return

                if (res.length == 1 && res == ".") {
                    edxCash?.setText("")
                    return
                }
                if (res.length == 2 && res.startsWith("0")) {
                    if (res != "0.") {
                        edxCash?.setText("0")
                        edxCash?.setSelection(res.length - 1)
                        return
                    }
                }
                if (res.length == 4 && res.endsWith(".")) {
                    if (!res.substring(1, res.length - 1).contains(".")) {
                    } else {
                        edxCash?.setText(res.substring(0, res.length - 1))
                        edxCash?.setSelection(res.length - 1)
                    }
                    return
                }
                if (res.length == 4 && res.contains("0.0")) {
                    if (res.endsWith(".") || res.endsWith("0")) {
                        edxCash?.setText("0.0")
                        edxCash?.setSelection(res.length - 1)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        wind.setContentView(dialogView)
    }

    interface DataCallBack {
        fun callBack(dates: String, cashs: String)
    }
}

