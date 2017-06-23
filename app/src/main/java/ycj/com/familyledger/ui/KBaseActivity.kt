package ycj.com.familyledger.ui

import android.app.Dialog
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.widget.TextView
import org.jetbrains.anko.find
import ycj.com.familyledger.R

/**
 * @author: ycj
 * @date: 2017-06-12 18:43
 * @version V1.0 <>
 */
abstract class KBaseActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
        initialize()
    }

    abstract fun initialize()

    abstract fun initView()

    abstract fun initListener()

    private var dialog: Dialog? = null

    fun showLoading() {
        hideLoading()
        dialog = Dialog(this)
        dialog?.show()
        val wind = dialog?.window
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(true)
        wind?.setBackgroundDrawable(BitmapDrawable())
        val attri = wind?.attributes
        attri?.alpha = 1.0F//alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明
        attri?.dimAmount = 0.4f//dimAmount在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        wind?.attributes = attri
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_loading_dialog, null)
        wind?.setContentView(dialogView)
    }

    fun hideLoading() {
        if (dialog != null) {
            dialog?.dismiss()
            dialog = null
        }
    }

    fun showTips(msg: String, callback: OnTipsCallBack) {
        val dialog = Dialog(this)
        dialog.show()
        val wind = dialog.window
        wind.setBackgroundDrawable(BitmapDrawable())
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_tips, null)
        val tvTips = dialogView.find<TextView>(R.id.tv_tips_dialog)
        tvTips.text = msg
        dialogView.find<TextView>(R.id.btn_ok_dialog).setOnClickListener {
            callback.positiveClick()
            dialog.dismiss()
        }
        dialogView.find<TextView>(R.id.btn_cancel_dialog).setOnClickListener {
            dialog.dismiss()
        }
        wind.setContentView(dialogView)
    }

    interface OnTipsCallBack {
        fun positiveClick()
    }
}