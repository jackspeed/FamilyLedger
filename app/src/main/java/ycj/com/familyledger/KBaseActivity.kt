package ycj.com.familyledger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*

/**
 * @author: ycj
 * @date: 2017-06-12 18:43
 * @version V1.0 <>
 */
open class KBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getTitleView(): View {
        return linearLayout {
            textView("伐木雷") {
                textSize = sp(8).toFloat()
                gravity = Gravity.CENTER_HORIZONTAL
                textColor = resources.getColor(R.color.white)
                backgroundResource = R.color.color_title_bar
                lparams(height = dip(48), width = matchParent)
            }
            lparams(height = dip(48), width = matchParent)
        }.view()
    }
}