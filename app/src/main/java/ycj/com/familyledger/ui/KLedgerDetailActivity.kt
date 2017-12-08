package ycj.com.familyledger.ui

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.*
import ycj.com.familyledger.Consts
import ycj.com.familyledger.R
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.http.HttpUtils
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.utils.RegexUtils

class KLedgerDetailActivity : KBaseActivity(), View.OnClickListener, BaseCallBack<String>, TextWatcher {
    private var isChanged: Boolean = false
    private var saveFlag: Boolean = false
    private var id: Long = 0
    private var backLayout: RelativeLayout? = null
    private var dataBean: LedgerBean? = null
    private var btnEdit: TextView? = null
    private var edxCash: EditText? = null
    private var edxDate: EditText? = null
    private var edxPhone: EditText? = null

    override fun initialize() {
        dataBean = intent.getParcelableExtra<LedgerBean>(Consts.DATA_BEAN) as LedgerBean
        id = dataBean!!.id!!
        edxCash?.setText(dataBean?.consume_money)
        edxDate?.setText(dataBean?.consume_date)
        edxPhone?.setText(dataBean?.user_id.toString())
        setEdxAble(false)
    }

    private fun setEdxAble(enable: Boolean) {
        edxCash?.isEnabled = enable
        edxDate?.isEnabled = enable
        edxPhone?.isEnabled = enable
    }

    override fun initView() {
        verticalLayout {
            orientation = LinearLayout.VERTICAL
            //titleLayout
            relativeLayout {
                textView("详情") {
                    textSize = resources.getDimension(R.dimen.title_text_size)
                    gravity = Gravity.CENTER
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.color.color_title_bar
                }.lparams(height = matchParent, width = matchParent)
                backLayout = relativeLayout {
                    id = R.id.layout_back
                    gravity = Gravity.CENTER
                    backgroundResource = R.drawable.bg_btn
                    imageView {
                        imageResource = R.mipmap.icon_back
                    }.lparams(height = wrapContent, width = wrapContent)
                }.lparams(height = matchParent, width = dip(48))
                btnEdit = textView(R.string.edit) {
                    id = R.id.tv_edit_save
                    gravity = Gravity.CENTER
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.drawable.bg_btn
                }.lparams(width = dip(48), height = matchParent) {
                    centerInParent()
                    alignParentRight()
                }
            }.lparams(height = dip(48), width = matchParent)
            relativeLayout {
                gravity = Gravity.CENTER
                verticalLayout {
                    orientation = LinearLayout.VERTICAL
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            textResource = R.string.cash
                            textSize = resources.getDimension(R.dimen.title_text_size)
                            textColor = resources.getColor(R.color.black_text)
                        }.lparams(height = wrapContent, width = dip(80))
                        edxCash = editText {
                            inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
                            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(8))
                            textColor = resources.getColor(R.color.gray_text)
                            textSize = resources.getDimension(R.dimen.title_text_size)
                        }.lparams(height = wrapContent, width = matchParent)
                    }.lparams(height = wrapContent, width = matchParent) {
                        rightMargin = dip(20)
                        leftMargin = dip(20)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            textResource = R.string.date
                            textSize = resources.getDimension(R.dimen.title_text_size)
                            textColor = resources.getColor(R.color.black_text)
                        }.lparams(height = wrapContent, width = dip(80))
                        edxDate = editText {
                            inputType = InputType.TYPE_DATETIME_VARIATION_DATE or InputType.TYPE_CLASS_DATETIME
                            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
                            textColor = resources.getColor(R.color.gray_text)
                            textSize = resources.getDimension(R.dimen.title_text_size)
                            hint = "日期模板 2017-06-23"
                        }.lparams(height = wrapContent, width = matchParent)
                    }.lparams(height = wrapContent, width = matchParent) { margin = dip(20) }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            textResource = R.string.phone
                            textSize = resources.getDimension(R.dimen.title_text_size)
                            textColor = resources.getColor(R.color.black_text)
                        }.lparams(height = wrapContent, width = dip(80))
                        edxPhone = editText {
                            inputType = InputType.TYPE_CLASS_PHONE
                            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(11))
                            textColor = resources.getColor(R.color.gray_text)
                            textSize = resources.getDimension(R.dimen.title_text_size)
                        }.lparams(height = wrapContent, width = matchParent)
                    }.lparams(height = wrapContent, width = matchParent) { margin = dip(20) }
                }
            }.lparams(width = matchParent, height = matchParent)
        }
    }

    override fun initListener() {
        backLayout?.setOnClickListener(this@KLedgerDetailActivity)
        btnEdit?.setOnClickListener(this@KLedgerDetailActivity)

        edxCash?.addTextChangedListener(this)
        edxDate?.addTextChangedListener(this)
        edxPhone?.addTextChangedListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.layout_back -> finish()
            R.id.tv_edit_save -> {
                if (saveFlag) {
                    if (isChanged) {
                        updateLedger()
                        setEdxAble(false)
                    } else {
                        toast("请修改之后再保存")
                    }
                } else {
                    setEdxAble(true)
                }
                btnEdit?.textResource = if (saveFlag) R.string.edit else R.string.save
                saveFlag = !saveFlag
            }
        }
    }

    private fun updateLedger() {
        if (RegexUtils.create().isDate(edxDate!!.editableText.toString().trim())) {
            showLoading()
            HttpUtils.getInstance().updateLedger(
                    dataBean!!.id!!,
                    edxPhone?.editableText.toString().toLong(),
                    edxDate!!.editableText.toString().trim(),
                    edxCash!!.editableText.toString().trim(),
                    this)
        } else {
            toast(getString(R.string.not_format_to_date))
        }
    }

    override fun onSuccess(data: BaseResponse<String>) {
        hideLoading()
        if (data.result == 1) {
            toast(data.data)
            setResult(Consts.ACTIVITY_RESULT_REFRESH)
            finish()
        } else {
            toast(data.error.message)
        }

    }

    override fun onFail(msg: String) {
        toast(R.string.fail_update)
        hideLoading()
    }

    override fun afterTextChanged(s: Editable?) {
        isChanged = true
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

}
