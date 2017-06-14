package ycj.com.familyledger.utols

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup


/**
 * @author: ycj
 * @date: 2017-06-14 15:25
 * @version V1.0 <>
 */
class ScrollBehavior : FloatingActionButton.Behavior {
    private var context: Context?
    private var attrs: AttributeSet?

    constructor(context: Context?, attrs: AttributeSet?) : super() {
        this.context = context
        this.attrs = attrs
    }

    private val folistener = FastOutLinearInInterpolator()


    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, nestedScrollAxes: Int): Boolean {
        //开始滑监听---当观察recyclerview开始发生滑动的时候回调
        //nestedScrollAxes滑动关联的方向
        return nestedScrollAxes == ViewGroup.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
    }

    //正在滑出来
    var isAnimatingOut = false

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        //不断的调用
        //判断滑动的方向 dyConsumed 某个方向的增量
        if (dyConsumed > 0 && !isAnimatingOut && child.visibility == View.VISIBLE) {
            //fab划出去
            animateOut(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            //fab划进来
            animateIn(child)
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    //滑进来
    private fun animateIn(child: FloatingActionButton) {
        child.visibility = View.VISIBLE
        //属性动画
        ViewCompat.animate(child).translationX(0f).setInterpolator(folistener).setListener(null).start()
    }

    //滑出去
    private fun animateOut(child: FloatingActionButton) {
        //属性动画
        //设置监听判断状态
        ViewCompat.animate(child).translationX(child.height.toFloat()).setInterpolator(folistener).setListener(object : ViewPropertyAnimatorListenerAdapter() {
            override fun onAnimationStart(view: View) {
                isAnimatingOut = true
                super.onAnimationStart(view)
            }

            override fun onAnimationCancel(view: View) {
                isAnimatingOut = false
                super.onAnimationCancel(view)
            }

            override fun onAnimationEnd(view: View) {
                view.setVisibility(View.GONE)
                isAnimatingOut = false
                super.onAnimationEnd(view)
            }
        }).start()
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View) {
        super.onStopNestedScroll(coordinatorLayout, child, target)
    }
}