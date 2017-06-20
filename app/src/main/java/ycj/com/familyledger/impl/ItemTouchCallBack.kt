package ycj.com.familyledger.impl

/**
 * @author: ycj
 * @date: 2017-06-19 13:24
 * @version V1.0 <>
 */
interface ItemTouchCallBack {
    fun onMove(fromPosition: Int, toPosition: Int)
    fun onSwiped(position: Int)
    fun onScroll(dx:Int)
}