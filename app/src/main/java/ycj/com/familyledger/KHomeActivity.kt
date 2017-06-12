package ycj.com.familyledger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sp
import org.jetbrains.anko.textView

class KHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            textView {
                text="KHomeActivity"
                textSize= sp(8).toFloat()
            }
        }
    }
}
