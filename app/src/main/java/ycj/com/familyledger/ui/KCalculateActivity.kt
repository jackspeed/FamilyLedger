package ycj.com.familyledger.ui

import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ycj.com.familyledger.Consts
import ycj.com.familyledger.R
import ycj.com.familyledger.adapter.KCalculateAdapter
import ycj.com.familyledger.bean.CalculateBean
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.utils.RecyclerViewItemDiv
import java.math.BigDecimal
import java.math.RoundingMode

class KCalculateActivity : KBaseActivity(), View.OnClickListener {

    override fun initialize() {
        val dataList = intent.getParcelableArrayListExtra<Parcelable>(Consts.LIST_DATA)
        var totalCash: Double = 0.0
        val map = HashMap<String, Double>()//个人userId对应个人所有花费金额
        val userIdSet = HashSet<String>()
        for (bean in dataList) {
            bean as LedgerBean
            totalCash = BigDecimal(bean.cash).add(BigDecimal(totalCash.toString())).toDouble()
            userIdSet.add(bean.userId)
        }
        for (userId in userIdSet) {
            for (bean in dataList) {
                bean as LedgerBean
                if (userId == bean.userId) {
                    if (map.containsKey(userId)) {
                        val cash = map[userId]?.plus(bean.cash.toDouble())
                        map.put(userId, cash!!)
                    } else {
                        map.put(userId, bean.cash.toDouble())
                    }
                }
            }
        }
        val data = ArrayList<CalculateBean>()
        //2--小数点后面保留两位  RoundingMode.UP--四舍五入
        val averageMoney = BigDecimal(totalCash).divide(BigDecimal(userIdSet.size), 2, RoundingMode.UP).toString()
        for (s in map.keys) {
            //人均金额-个人花费金额
            val value = BigDecimal(averageMoney).subtract(BigDecimal(map[s]?.toString()))
            data.add(CalculateBean("", averageMoney, totalCash.toString(), s, map[s].toString(), value.toString()))
        }
        rv?.adapter = KCalculateAdapter(data, this)
    }

    private var backLayout: RelativeLayout? = null

    private var rv: RecyclerView? = null

    override fun initView() {
        relativeLayout {
            //titleLayout
            relativeLayout {
                id = R.id.layout_title
                textView("结账") {
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
            rv = recyclerView {
                //分割线
                addItemDecoration(RecyclerViewItemDiv(this@KCalculateActivity, LinearLayoutManager.VERTICAL))
                layoutManager = LinearLayoutManager(this@KCalculateActivity)
                id = R.id.list_view_cal
            }.lparams(width = matchParent, height = matchParent) { below(R.id.layout_title) }
        }

    }

    override fun initListener() {
        backLayout?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.layout_back -> finish()
        }
    }
}