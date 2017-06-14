package ycj.com.familyledger

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ycj.com.familyledger.adapter.KHomeAdapter
import ycj.com.familyledger.view.RecyclerViewItemDiv

class KHomeActivity : KBaseActivity(), KHomeAdapter.ItemClickListener {


    private var titleView: LinearLayout? = null
    private var lView: RecyclerView? = null

    private var data: ArrayList<String> = ArrayList()

    companion object {
        val DATA_ID = "id"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        val mAdapter = KHomeAdapter(data, this)
        lView?.addItemDecoration(RecyclerViewItemDiv(this@KHomeActivity, LinearLayoutManager.VERTICAL))
        lView?.adapter = mAdapter
        lView?.layoutManager = LinearLayoutManager(this@KHomeActivity)
        mAdapter.setOnItemClickListener(this)
        fBtn?.setOnClickListener { toast("6666666666666666") }
        lView?.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (lView?.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (dy > 0) {
                        fBtn?.hide()
                    } else {
                        fBtn?.show()
                    }
                }
            }
        })
    }

    override fun onItemClickListener(position: Int) {
        go2Detail(position)
    }

    private fun go2Detail(position: Int) {
        val intent = Intent(KHomeActivity@ this, KLedgerDetailActivity::class.java)
        intent.putExtra(DATA_ID, position)
        startActivity(intent)
    }

    private fun initData() {
        for (i in 0..20) {
            data.add("WHILE 循环" + i)
        }
    }

    private var fBtn: FloatingActionButton? = null

    private fun initView() {
        relativeLayout {
            lparams(height = matchParent, width = matchParent)
            titleView = linearLayout {
                id = R.id.layout_title
                textView("伐木雷") {
                    textSize = sp(8).toFloat()
                    gravity = Gravity.CENTER_HORIZONTAL
                    textColor = resources.getColor(R.color.white, null)
                    backgroundResource = R.color.color_title_bar
                }.lparams(height = matchParent, width = matchParent)
            }.lparams(height = dip(48), width = matchParent)
            lView = recyclerView {
                id = R.id.list_view_home
            }.lparams(width = matchParent, height = matchParent) { below(R.id.layout_title) }
            fBtn = floatingActionButton {
                imageResource = R.mipmap.cha
                compatElevation = 100F//阴影效果
                backgroundTintList = ColorStateList.valueOf(R.color.colorAccent)
            }.lparams(width = dip(60), height = dip(60)) {
                alignParentRight()
                alignParentBottom()
                bottomMargin = dip(20)
                rightMargin = dip(20)
            }
        }

    }
}


