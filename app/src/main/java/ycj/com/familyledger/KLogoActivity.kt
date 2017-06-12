package ycj.com.familyledger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.linearLayout

class KLogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            backgroundResource=R.mipmap.ic_launcher_round
        }
    }
}
