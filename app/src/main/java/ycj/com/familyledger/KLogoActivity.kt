package ycj.com.familyledger

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.startActivity

class KLogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自动把下面的布局设置为setContentView
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundResource = R.mipmap.logo
        }
        Handler().postDelayed({
            startActivity<KRegisterActivity>()
            finish()
        }, 2000)
    }
}
