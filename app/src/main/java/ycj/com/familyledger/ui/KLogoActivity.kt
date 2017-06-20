package ycj.com.familyledger.ui

import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.startActivity

class KLogoActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        //自动把下面的布局设置为setContentView
        linearLayout {
            lparams(width = org.jetbrains.anko.matchParent, height = org.jetbrains.anko.matchParent)
            backgroundResource = ycj.com.familyledger.R.mipmap.logo
        }
        android.os.Handler().postDelayed({
            startActivity<KRegisterActivity>()
            finish()
        }, 1000)
    }
}
