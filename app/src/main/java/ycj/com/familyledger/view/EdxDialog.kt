package ycj.com.familyledger.view

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AlertDialog
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
        var dialog = AlertDialog.Builder(context).create()
        dialog.show()
        var wind = dialog.window
        wind.setBackgroundDrawable(BitmapDrawable())
        var windowMa = context.windowManager

        var attri = wind.attributes
        attri.width = (windowMa.defaultDisplay.width * 0.6).toInt()
        attri.height = dip2px(context, 220F)

        wind.attributes = attri
        var edxDate: EditText? = null
        var edxCash: EditText? = null
        var tvCancel: TextView? = null
        var tvOk: TextView? = null

        var dialogView = context.relativeLayout {
            lparams(width = matchParent, height = matchParent) {
                topPadding = dip(10)
            }
            background = context.resources.getDrawable(R.drawable.bg_edx_dialog)
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
                    maxEms = 11
                    textSize = sp(6).toFloat()
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "请输入日期"
                    hintTextColor = context.resources.getColor(R.color.gray_text)
                }.lparams(width = matchParent, height = wrapContent) {
                    rightMargin = dip(20)
                    leftMargin = dip(20)
                    padding = dip(5)
                }

                edxCash = editText {
                    id = R.id.edx_cash_add
                    maxLines = 1
                    textSize = sp(6).toFloat()
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
                    backgroundResource = R.drawable.edx_input_bg
                    hint = "请输入金额"
                    hintTextColor = context.resources.getColor(R.color.gray_text)

                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(20)
                    rightMargin = dip(20)
                    leftMargin = dip(20)
                    padding = dip(5)
                }

            }.lparams(width = matchParent, height = wrapContent) {
                centerInParent()
            }

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                tvCancel = textView("取消") {
                    gravity = Gravity.CENTER
                    textSize = sp(8).toFloat()
                    textColor = context.resources.getColor(R.color.btn_text_color_selector)
                    background = context.resources.getDrawable(R.drawable.bg_btn_l_dialog)
                }.lparams(width = 0, height = dip(48), weight = 1F)
                textView {}.lparams(width = 1, height = matchParent)
                tvOk = textView("确定") {
                    gravity = Gravity.CENTER
                    textSize = sp(8).toFloat()
                    textColor = context.resources.getColor(R.color.btn_text_color_selector)
                    background = context.resources.getDrawable(R.drawable.bg_btn_r_dialog)
                }.lparams(width = 0, height = dip(48), weight = 1F)

            }.lparams(width = matchParent, height = dip(40)) {
                topMargin = dip(10)
                alignParentBottom()
            }
        }
        tvOk?.setOnClickListener {
            var dates = edxDate?.editableText.toString()
            var cashs = edxCash?.editableText.toString()
            //TODO:数据规则匹配
            callBack.callBack(dates, cashs)
            dialog.dismiss()
            dialog = null
        }
        tvCancel?.setOnClickListener {
            dialog.dismiss()
            dialog = null
        }
        edxCash?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val res = s.toString()
                if (res.startsWith("0") && res.length > 1) {
                    edxCash?.setText("0")
                    edxCash?.setSelection(0)
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

    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}

