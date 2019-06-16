package lottery.com.screens.confirm

import android.app.Service
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import lottery.com.R
import lottery.com.model.MainTimeFrame
import lottery.com.model.User
import lottery.com.utils.Constants
import lottery.com.utils.PreferenceHelper
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : AppCompatActivity() {

    private var services: MutableList<Service>? = mutableListOf()

    private var user: User? = null

    private var strDate: String? = null

    private var day: String? = null

    private var mainTimeFrame: MainTimeFrame? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        user = PreferenceHelper.getUser(this)

        services = intent.getSerializableExtra(Constants.Data.DATA) as MutableList<Service>?
        print(services)
        mainTimeFrame = intent.getParcelableExtra(Constants.Data.MODEL) as? MainTimeFrame?
        strDate = intent.getStringExtra(Constants.Data.DATE)
        day = intent.getStringExtra(Constants.Data.DAY)

        val content = SpannableString("Dịch vụ đã chọn")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        mTextViewService.text = content
    }
}
