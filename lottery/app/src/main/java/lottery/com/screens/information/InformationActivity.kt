package lottery.com.screens.information

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import lottery.com.R
import lottery.com.model.User
import lottery.com.utils.Constants

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation)

        val value = intent.getParcelableExtra(Constants.Data.DATA) as? User
        print(value)
    }
}
