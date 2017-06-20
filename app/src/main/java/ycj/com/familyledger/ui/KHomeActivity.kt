package ycj.com.familyledger.ui

import android.support.v7.widget.LinearLayoutManager
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ycj.com.familyledger.utils.RecyclerViewItemDiv


class KHomeActivity : KBaseActivity(), ycj.com.familyledger.adapter.KHomeAdapter.ItemClickListener, ycj.com.familyledger.view.EdxDialog.DataCallBack, android.view.View.OnClickListener {
    private var lView: android.support.v7.widget.RecyclerView? = null

    private var data: java.util.ArrayList<String> = java.util.ArrayList()

    private var fBtn: android.support.design.widget.FloatingActionButton? = null

    private var rightBtn: android.widget.ImageView? = null

    companion object {

        val DATA_ID = "id"
    }

    private var mAdapter: ycj.com.familyledger.adapter.KHomeAdapter? = null

    override fun initialize() {
        for (i in 0..200) {
            data.add("WHILE 循环" + i)
        }
        mAdapter?.notifyDataSetChanged()
    }

    private fun setData() {
        mAdapter = ycj.com.familyledger.adapter.KHomeAdapter(data, this)
        //分割线
        lView?.addItemDecoration(RecyclerViewItemDiv(this@KHomeActivity, LinearLayoutManager.VERTICAL))
        lView?.adapter = mAdapter
        lView?.layoutManager = android.support.v7.widget.LinearLayoutManager(this@KHomeActivity)
        //滑动监听
        lView?.overScrollMode = android.view.View.OVER_SCROLL_NEVER
        lView?.itemAnimator = android.support.v7.widget.DefaultItemAnimator() as android.support.v7.widget.RecyclerView.ItemAnimator?//设置Item增加、移除动画

        val callBack = ycj.com.familyledger.utils.RItemTouchHelper(mAdapter!!)
        val helpers = android.support.v7.widget.helper.ItemTouchHelper(callBack)
        helpers.attachToRecyclerView(lView)
    }

    override fun initListener() {
        setData()
        lView?.setOnScrollListener(object : android.support.v7.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: android.support.v7.widget.RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                android.util.Log.i("ycj onScrolled", "" + dx + "       " + dy)
                if (lView?.scrollState == android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (dy > 0) {
                        fBtn?.hide()
                    } else {
                        fBtn?.show()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: android.support.v7.widget.RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                android.util.Log.i("ycj ScrollChanged", "" + newState)
                //==1,显示
                if (newState == android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE) {
                    fBtn?.show()
                }
            }
        })
        mAdapter?.setOnItemClickListener(this)
        fBtn?.setOnClickListener(this)
        rightBtn?.setOnClickListener(this)
    }

    //输入框返回
    override fun callBack(dates: String, cashs: String) {
        android.util.Log.i("ycj", "" + dates + "---------" + cashs)
    }

    override fun onItemClickListener(position: Int) {
        go2Detail(position)
    }

    override fun onItemDelClickListener(position: Int) {
        android.util.Log.i("ycj", "position: " + position)

    }

    override fun onClick(v: android.view.View?) {
        when (v?.id) {
            ycj.com.familyledger.R.id.img_right_home -> toast("搜索")
            ycj.com.familyledger.R.id.btn_home_add -> ycj.com.familyledger.view.EdxDialog.Companion.create()
                    .showEdxDialog("请输入信息", this@KHomeActivity, this@KHomeActivity)
        }
    }

    private fun go2Detail(position: Int) {
        val intent = android.content.Intent(KHomeActivity@ this, KLedgerDetailActivity::class.java)
        intent.putExtra(ycj.com.familyledger.ui.KHomeActivity.Companion.DATA_ID, position)
        startActivity(intent)
    }


    override fun initView() {
        relativeLayout {
            lparams(height = org.jetbrains.anko.matchParent, width = org.jetbrains.anko.matchParent)
            relativeLayout {
                id = ycj.com.familyledger.R.id.layout_title
                backgroundResource = ycj.com.familyledger.R.color.color_title_bar
                textView("伐木雷") {
                    textSize = resources.getDimension(ycj.com.familyledger.R.dimen.title_text_size)
                    textColor = resources.getColor(ycj.com.familyledger.R.color.white)
                }.lparams(height = org.jetbrains.anko.wrapContent, width = org.jetbrains.anko.wrapContent) {
                    centerInParent()
                }
                rightBtn = imageView {
                    id = ycj.com.familyledger.R.id.img_right_home
                    imageResource = android.R.drawable.ic_menu_search
                }.lparams(width = dip(24), height = dip(24)) {
                    alignParentRight()
                    centerVertically()
                    rightMargin = dip(16)
                }
            }.lparams(height = dip(48), width = org.jetbrains.anko.matchParent)
            lView = recyclerView {
                id = ycj.com.familyledger.R.id.list_view_home
            }.lparams(width = org.jetbrains.anko.matchParent, height = org.jetbrains.anko.matchParent) { below(ycj.com.familyledger.R.id.layout_title) }
            fBtn = floatingActionButton {
                id = ycj.com.familyledger.R.id.btn_home_add
                imageResource = android.R.drawable.ic_menu_add
            }.lparams(width = org.jetbrains.anko.wrapContent, height = org.jetbrains.anko.wrapContent) {
                alignParentRight()
                alignParentBottom()
                bottomMargin = dip(20)
                rightMargin = dip(20)
            }
        }
    }

}