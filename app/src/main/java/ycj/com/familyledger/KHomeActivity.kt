package ycj.com.familyledger

import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.ImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ycj.com.familyledger.adapter.KHomeAdapter
import ycj.com.familyledger.utils.RItemTouchHelper
import ycj.com.familyledger.view.EdxDialog
import ycj.com.familyledger.view.RecyclerViewItemDiv
import java.util.*


class KHomeActivity : KBaseActivity(), KHomeAdapter.ItemClickListener, EdxDialog.DataCallBack, View.OnClickListener {
    private var lView: RecyclerView? = null

    private var data: ArrayList<String> = ArrayList()

    private var fBtn: FloatingActionButton? = null

    private var rightBtn: ImageView? = null

    companion object {

        val DATA_ID = "id"
    }

    private var mAdapter: KHomeAdapter? = null

    override fun initialize() {
        for (i in 0..200) {
            data.add("WHILE 循环" + i)
        }
        mAdapter?.notifyDataSetChanged()
    }

    private fun setData() {
        mAdapter = KHomeAdapter(data, this)
        //分割线
        lView?.addItemDecoration(RecyclerViewItemDiv(this@KHomeActivity, LinearLayoutManager.VERTICAL))
        lView?.adapter = mAdapter
        lView?.layoutManager = LinearLayoutManager(this@KHomeActivity)
        //滑动监听
        lView?.overScrollMode = View.OVER_SCROLL_NEVER
        lView?.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?//设置Item增加、移除动画

        val callBack = RItemTouchHelper(mAdapter!!)
        val helpers = ItemTouchHelper(callBack)
        helpers.attachToRecyclerView(lView)
    }

    override fun initListener() {
        setData()
        lView?.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i("ycj onScrolled", "" + dx + "       " + dy)
                if (lView?.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    if (dy > 0) {
                        fBtn?.hide()
                    } else {
                        fBtn?.show()
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i("ycj ScrollChanged", "" + newState)
                //==1,显示
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
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
        Log.i("ycj", "" + dates + "---------" + cashs)
    }

    override fun onItemClickListener(position: Int) {
        go2Detail(position)
    }

    override fun onItemDelClickListener(position: Int) {
        Log.i("ycj", "position: " + position)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_right_home -> toast("搜索")
            R.id.btn_home_add -> EdxDialog.create()
                    .showEdxDialog("请输入信息", this@KHomeActivity, this@KHomeActivity)
        }
    }

    private fun go2Detail(position: Int) {
        val intent = Intent(KHomeActivity@ this, KLedgerDetailActivity::class.java)
        intent.putExtra(DATA_ID, position)
        startActivity(intent)
    }


    override fun initView() {
        relativeLayout {
            lparams(height = matchParent, width = matchParent)
            relativeLayout {
                id = R.id.layout_title
                backgroundResource = R.color.color_title_bar
                textView("伐木雷") {
                    textSize = resources.getDimension(R.dimen.title_text_size)
                    textColor = resources.getColor(R.color.white)
                }.lparams(height = wrapContent, width = wrapContent) {
                    centerInParent()
                }
                rightBtn = imageView {
                    id = R.id.img_right_home
                    imageResource = android.R.drawable.ic_menu_search
                }.lparams(width = dip(24), height = dip(24)) {
                    alignParentRight()
                    centerVertically()
                    rightMargin = dip(16)
                }
            }.lparams(height = dip(48), width = matchParent)
            lView = recyclerView {
                id = R.id.list_view_home
            }.lparams(width = matchParent, height = matchParent) { below(R.id.layout_title) }
            fBtn = floatingActionButton {
                id = R.id.btn_home_add
                imageResource = android.R.drawable.ic_menu_add
            }.lparams(width = wrapContent, height = wrapContent) {
                alignParentRight()
                alignParentBottom()
                bottomMargin = dip(20)
                rightMargin = dip(20)
            }
        }
    }

}