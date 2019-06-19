package lottery.com.screens.confirm

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import lottery.com.R
import lottery.com.model.MainTimeFrame
import lottery.com.model.User
import lottery.com.utils.Constants
import lottery.com.utils.PreferenceHelper
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.activity_confirm.mRecyclerView
import lottery.com.base.list.BaseAdapter
import lottery.com.database.DBHelper
import lottery.com.model.Service
import lottery.com.room.respository.ServiceRepos
import lottery.com.screens.fail.FailActivity
import lottery.com.screens.succeed.SucceedActivity
import lottery.com.utils.DialogUtils
import java.util.*

class ConfirmActivity : AppCompatActivity() {

    private var services: MutableList<Service>? = mutableListOf()

    private var user: User? = null

    private var strDate: String? = null

    private var day: String? = null

    private var mainTimeFrame: MainTimeFrame? = null

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    private var serviceRepos: ServiceRepos? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        serviceRepos = ServiceRepos(this)
        user = PreferenceHelper.getUser(this)
        if (intent != null) {
            services = intent.getSerializableExtra(Constants.Data.DATA) as MutableList<Service>?
            print(services)
            mainTimeFrame = intent.getParcelableExtra(Constants.Data.MODEL) as? MainTimeFrame?
            strDate = intent.getStringExtra(Constants.Data.DATE)
            day = intent.getStringExtra(Constants.Data.DAY)
        }
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRecyclerView?.adapter = mAdapter
        val content = SpannableString("Dịch vụ đã chọn")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        mTextViewService.text = content
        mTextViewName.text = user?.name
        mTextViewFrame.text = mainTimeFrame?.detail
        mTextViewDate.text = "$day,$strDate"



        if (services.isNullOrEmpty()) {
            mAdapter.addItem(ConfirmSelectedEmptyItem(this))
        } else {
            for (it in services!!) {
                mAdapter.addItem(ConfirmSelectedItem(this, it))
            }
        }

        mButtonConfirm?.setOnClickListener {
            if (services.isNullOrEmpty() || TextUtils.isEmpty(mTextViewName.text) ||
                TextUtils.isEmpty(mTextViewFrame.text) || TextUtils.isEmpty(mTextViewDate.text)
            ) {
                DialogUtils.showToastMessage(this, "Thông tin chưa đầy đủ, vui lòng thử lại !")
            } else {
                var userId: Int = 0
                if (DBHelper().getUserIdByphone(user?.phoneNumber!!) != 0) {
                    userId = DBHelper().getUserIdByphone(user?.phoneNumber!!)
                }

                when (DBHelper().createDate(
                    userId,
                    mainTimeFrame?.id!!,
                    day!!,
                    strDate!!,
                    UUID.randomUUID().toString()
                )) {
                    true -> {
                        val result = DBHelper().getScheduleIdByUserId(userId, mainTimeFrame?.id!!, strDate!!)
                        if (result != null) {
                            for (item in services!!) {
                                DBHelper().createSchedule(result, item.id)
                            }
                            startActivity(Intent(this, SucceedActivity::class.java))
                            services?.clear()
                            serviceRepos?.deleteAll()
                        } else {
                            startActivity(Intent(this, FailActivity::class.java))
                        }
                    }

                }
            }
        }

    }
}
