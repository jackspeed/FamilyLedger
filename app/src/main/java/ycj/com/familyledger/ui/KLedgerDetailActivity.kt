package ycj.com.familyledger.ui

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import org.jetbrains.anko.*
import ycj.com.familyledger.Consts
import ycj.com.familyledger.R
import ycj.com.familyledger.bean.LedgerBean

class KLedgerDetailActivity : KBaseActivity(), View.OnClickListener {
    private var id: Int = 0
    private var backLayout: RelativeLayout? = null

    private var dataBean: LedgerBean? = null

    override fun initialize() {
        dataBean = intent.getParcelableExtra<LedgerBean>(Consts.DATA_BEAN) as LedgerBean
        id = dataBean!!.id
        tvContent?.text = dataBean.toString()
    }

    private var tvContent: TextView? = null

    override fun initView() {
        verticalLayout {
            orientation = LinearLayout.VERTICAL
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
            }.lparams(height = dip(48), width = matchParent)
            relativeLayout {
                tvContent = textView {
                    textSize = resources.getDimension(R.dimen.title_text_size)
                    textColor = resources.getColor(R.color.black_text)
                }.lparams(height = wrapContent, width = wrapContent) {
                    centerInParent()
                }
            }.lparams(height = matchParent, width = matchParent)
        }
    }

    override fun initListener() {
        backLayout?.setOnClickListener(this@KLedgerDetailActivity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.layout_back -> finish()
        }
    }
}