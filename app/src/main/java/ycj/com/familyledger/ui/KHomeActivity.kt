package ycj.com.familyledger.ui

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.ImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ycj.com.familyledger.Consts
import ycj.com.familyledger.adapter.KHomeAdapter
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.http.HttpUtils
import ycj.com.familyledger.impl.IAddLedger
import ycj.com.familyledger.impl.IGetLedgerList
import ycj.com.familyledger.utils.RItemTouchHelper
import ycj.com.familyledger.utils.RecyclerViewItemDiv
import ycj.com.familyledger.utils.SPUtils
import ycj.com.familyledger.view.EdxDialog


class KHomeActivity : KBaseActivity(),
        KHomeAdapter.ItemClickListener,
        EdxDialog.DataCallBack,
        View.OnClickListener, IGetLedgerList, IAddLedger {
    private val isAdd: Boolean = false
    private var lView: RecyclerView? = null

    private var data: ArrayList<LedgerBean> = ArrayList()

    private var fBtn: FloatingActionButton? = null

    private var rightBtn: ImageView? = null

    private var mAdapter: KHomeAdapter? = null

    override fun initialize() {
        val userId = SPUtils.getInstance().getString(Consts.SP_USER_ID)
        HttpUtils.getInstance().getLedgerList(userId, this)
    }

    override fun initListener() {

        lView?.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                android.util.Log.i("ycj onScrolled", "" + dx + "       " + dy)
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
                android.util.Log.i("ycj ScrollChanged", "" + newState)
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
        val userId = SPUtils.getInstance().getString(Consts.SP_USER_ID)
        HttpUtils.getInstance().addLedger(userId, dates, cashs, this)
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

    override fun onAddSuccess(data: BaseResponse<LedgerBean>) {
        toast("添加成功")
        mAdapter?.addNewItem(0, data.data!!)
    }

    override fun onSuccess(data: BaseResponse<List<LedgerBean>>) {
        if (data.data == null || data.data!!.isEmpty()) {
            mAdapter?.clearData()
            toast("暂无数据")
        } else {
            mAdapter?.setDatas(data.data!!)
        }
    }

    override fun onFail(msg: String) {
        mAdapter?.clearData()
        toast("获取失败")
    }

    override fun onAddFail(msg: String) {
        toast("添加失败")
    }

    private fun go2Detail(position: Int) {
        val intent = android.content.Intent(KHomeActivity@ this, KLedgerDetailActivity::class.java)
        intent.putExtra(Consts.DATA_ID, position)
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

        mAdapter = KHomeAdapter(data, this)
        //分割线
        lView?.addItemDecoration(RecyclerViewItemDiv(this@KHomeActivity, LinearLayoutManager.VERTICAL))
        lView?.adapter = mAdapter
        lView?.layoutManager = LinearLayoutManager(this@KHomeActivity)
        //滑动监听
        lView?.overScrollMode = android.view.View.OVER_SCROLL_NEVER
        lView?.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?//设置Item增加、移除动画

        val callBack = RItemTouchHelper(mAdapter!!)
        val helpers = ItemTouchHelper(callBack)
        helpers.attachToRecyclerView(lView)
    }

}