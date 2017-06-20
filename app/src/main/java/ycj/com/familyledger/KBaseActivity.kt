package ycj.com.familyledger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author: ycj
 * @date: 2017-06-12 18:43
 * @version V1.0 <>
 */
abstract class KBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
        initialize()
    }

    abstract fun initialize()

    abstract fun initView()

    abstract fun initListener()
}