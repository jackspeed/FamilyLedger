package ycj.com.familyledger.ui

import android.content.Intent
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import ycj.com.familyledger.Consts
import ycj.com.familyledger.R
import ycj.com.familyledger.adapter.KHomeAdapter
import ycj.com.familyledger.bean.BaseResponse
import ycj.com.familyledger.bean.LedgerBean
import ycj.com.familyledger.bean.PageResult
import ycj.com.familyledger.http.HttpUtils
import ycj.com.familyledger.impl.BaseCallBack
import ycj.com.familyledger.impl.IGetLedgerList
import ycj.com.familyledger.utils.RecyclerViewItemDiv
import ycj.com.familyledger.utils.SPUtils
import ycj.com.familyledger.view.EdxDialog
import java.util.*


class KHomeActivity : KBaseActivity(),
        KHomeAdapter.ItemClickListener,
        EdxDialog.DataCallBack,
        View.OnClickListener, IGetLedgerList {

    private var trigleCancel: Long = 0
    private var selfData: Boolean = true
    private var lView: RecyclerView? = null

    private var data: ArrayList<LedgerBean> = ArrayList()

    private var fBtn: FloatingActionButton? = null

    private var mAdapter: KHomeAdapter? = null

    override fun initialize() {
        showLoading()
        HttpUtils.getInstance().getLedgerList(0, 1, 1000, this);
    }

    override fun initListener() {
        leftLayout?.setOnClickListener(this)
        splitLayout?.setOnClickListener(this)
        mAdapter?.setOnItemClickListener(this)
        fBtn?.setOnClickListener(this)
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

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                android.util.Log.i("ycj ScrollChanged", "" + newState)
                //==1,显示
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fBtn?.show()
                }
            }
        })
    }

    //输入框返回
    override fun callBack(dates: String, cashs: String) {
        val userId = SPUtils.getInstance().getLong(Consts.SP_USER_ID)
        HttpUtils.getInstance().addLedger(userId, dates, cashs, object : BaseCallBack<String> {
            override fun onSuccess(data: BaseResponse<String>) {
                hideLoading()
                if (data.result == 1) {
                    toast(data.data)
                    initialize()
                } else {
                    toast(data.error.message)
                }
            }

            override fun onFail(msg: String) {
                toast(msg)
            }
        })
    }

    override fun onItemClickListener(position: Int) {
        go2Detail(position)
    }


    override fun onItemLongClickListener(position: Int) {
        showTips(getString(R.string.sure_delete), object : OnTipsCallBack {
            override fun positiveClick() {
                HttpUtils.getInstance().deleteLedger(data[position].id!!, object : BaseCallBack<String> {
                    override fun onSuccess(data: BaseResponse<String>) {
                        if (data.result == 1) {

                            toast(getString(R.string.success_delete))
                            this@KHomeActivity.data.removeAt(position)
                            mAdapter?.notifyDataSetChanged()
                        } else {
                            toast(data.error.message)
                        }
                    }

                    override fun onFail(msg: String) {
                        toast(getString(R.string.fail_delete))
                    }

                })
            }
        })
    }

    override fun onClick(v: android.view.View?) {
        when (v?.id) {
            R.id.layout_left_title_home -> {
                go2Calculate()
            }
            R.id.layout_split -> {
                showLoading()
                val userId = SPUtils.getInstance().getLong(Consts.SP_USER_ID)
                HttpUtils.getInstance().getLedgerList(if (selfData) userId else 0,
                        1, 1000, this)
                selfData = !selfData
            }
            R.id.btn_home_add -> EdxDialog.Companion.create()
                    .showEdxDialog("请输入信息", this@KHomeActivity,
                            this@KHomeActivity)
        }
    }


    override fun onSuccess(pages: PageResult<LedgerBean>) {
        hideLoading()
        if (pages.list == null) {
            mAdapter?.clearData()
            toast("暂无数据")
        } else {
            mAdapter?.setDatas(pages.list)
        }

    }

    override fun onFail(msg: String) {
        hideLoading()
        mAdapter?.clearData()
        toast("获取失败")
    }


    private fun go2Detail(position: Int) {
        val intent = android.content.Intent(KHomeActivity@ this, KLedgerDetailActivity::class.java)
        intent.putExtra(Consts.DATA_ID, position)
        intent.putExtra(Consts.DATA_BEAN, data[position])
        startActivityForResult(intent, 100)
    }

    private fun go2Calculate() {
        val intent = android.content.Intent(KHomeActivity@ this, KCalculateActivity::class.java)
        intent.putExtra(Consts.LIST_DATA, data)
        startActivity(intent)
    }

    private var splitLayout: TextView? = null

    private var leftLayout: TextView? = null

    override fun initView() {
        relativeLayout {
            lparams(height = matchParent, width = matchParent)
            relativeLayout {
                id = R.id.layout_title
                backgroundResource = R.color.color_title_bar
                textView("Ledger") {
                    textSize = resources.getDimension(R.dimen.title_size)
                    textColor = resources.getColor(R.color.white)
                }.lparams(height = wrapContent, width = wrapContent) {
                    centerInParent()
                }
                splitLayout = textView("筛选") {
                    id = R.id.layout_split
                    gravity = Gravity.CENTER
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.drawable.bg_btn
                }.lparams(width = dip(48), height = matchParent) {
                    centerInParent()
                    alignParentRight()
                }
                leftLayout = textView("结账") {
                    id = R.id.layout_left_title_home
                    gravity = Gravity.CENTER
                    textColor = resources.getColor(R.color.white)
                    backgroundResource = R.drawable.bg_btn
                }.lparams(width = dip(48), height = matchParent)
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
        mAdapter = KHomeAdapter(data, this)
        //分割线
        lView?.addItemDecoration(RecyclerViewItemDiv(this@KHomeActivity, LinearLayoutManager.VERTICAL))
        lView?.adapter = mAdapter
        lView?.layoutManager = LinearLayoutManager(this@KHomeActivity)
        //滑动监听
        lView?.overScrollMode = android.view.View.OVER_SCROLL_NEVER
        lView?.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?//设置Item增加、移除动画

//        val callBack = RItemTouchHelper(mAdapter!!)
//        val helpers = ItemTouchHelper(callBack)
//        helpers.attachToRecyclerView(lView)
    }

    //按后退键时
    override fun onBackPressed() {
        toastExtrance() // “提示再按一次可退出..”
    }

    //双击退出提醒
    fun toastExtrance() {
        val uptimeMillis = SystemClock.uptimeMillis()
        if (uptimeMillis - trigleCancel > 2000) {
            trigleCancel = uptimeMillis
            toast(getString(R.string.note_exit))
        } else {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Consts.ACTIVITY_RESULT_REFRESH) {
            initialize()
        }
    }
}