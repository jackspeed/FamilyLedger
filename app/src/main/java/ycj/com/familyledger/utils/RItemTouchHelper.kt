package ycj.com.familyledger.utils

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.ViewGroup
import ycj.com.familyledger.adapter.KHomeAdapter
import ycj.com.familyledger.impl.ItemTouchCallBack


/**
 * @author: ycj
 * @date: 2017-06-19 13:22
 * @version V1.0 <>
 */
class RItemTouchHelper(adapter: ItemTouchCallBack) : ItemTouchHelper.Callback() {
    private var callBack: ItemTouchCallBack = adapter

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlags = ItemTouchHelper.LEFT
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
//        不需要拖动
//        return ItemTouchHelper.Callback.makeMovementFlags(0, swipeFlags)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        callBack.onSwiped(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {
//        super.clearView(recyclerView, viewHolder)
//        重置改变，防止由于复用而导致的显示问题
        viewHolder.itemView.scrollX = 0
    }


    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        //仅对侧滑状态下的效果做出改变
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
            val withs = getSlideLimitation(viewHolder)
            Log.i("ycj******", "" + dX)
            val dx = Math.abs(dX).toInt()
            if (dx <= withs) {
                viewHolder.itemView.scrollX = dx
                viewHolder as KHomeAdapter.MyViewHolder
                viewHolder.fy.isFocusable = true
                callBack.onScroll(dx)
            }
            //如果dX还未达到能删除的距离，此时慢慢增加“眼睛”的大小，增加的最大值为ICON_MAX_SIZE
        }
//        else {
//            拖拽状态下不做改变，需要调用父类的方法
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//        }
    }

    /**
     * 获取删除方块的宽度
     */
    fun getSlideLimitation(viewHolder: RecyclerView.ViewHolder): Int {
        val viewGroup = viewHolder.itemView as ViewGroup
        return viewGroup.getChildAt(1).layoutParams.width
    }

}