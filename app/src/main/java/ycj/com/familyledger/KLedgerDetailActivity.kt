package ycj.com.familyledger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.textView

class KLedgerDetailActivity : AppCompatActivity() {
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getIntExtra(KHomeActivity.DATA_ID, 0)
        relativeLayout {
            textView {

            }
        }
    }
}
