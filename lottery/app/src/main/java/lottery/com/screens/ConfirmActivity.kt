package lottery.com.screens

import android.app.Service
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import lottery.com.R
import lottery.com.model.MainTimeFrame
import lottery.com.model.User
import lottery.com.utils.Constants
import lottery.com.utils.PreferenceHelper

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
    }
}
