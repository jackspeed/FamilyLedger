package ycj.com.familyledger.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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
}