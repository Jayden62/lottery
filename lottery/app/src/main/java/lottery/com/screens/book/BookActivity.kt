package lottery.com.screens.book

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_booking.*
import lottery.com.base.list.BaseAdapter
import java.text.SimpleDateFormat
import android.view.animation.TranslateAnimation
import android.widget.AdapterView
import lottery.com.R
import lottery.com.common.RecyclerViewDisable
import lottery.com.database.DBHelper
import lottery.com.model.MainTimeFrame
import lottery.com.model.Service
import lottery.com.screens.confirm.ConfirmActivity
import lottery.com.utils.Constants
import lottery.com.utils.DialogUtils
import lottery.com.utils.PreferenceHelper
import java.io.Serializable
import java.time.LocalDate
import java.util.*

class BookActivity : AppCompatActivity(), View.OnClickListener, DateItem.Callback, DayItem.Callback,
    TimeFrameActiveItem.Callback {

    private var mAdapterDay: BaseAdapter<Any>? = null
    private var mAdapterDate: BaseAdapter<Any>? = null
    private var mAdapterFrame: BaseAdapter<Any>? = null

    private var day: String? = null
    private var date: String? = null
    private var month: String? = null
    private var year: String? = null

    private var isPicked: Boolean = false

    private var strDate: String? = null

    private var mData: MainTimeFrame? = null

    private var mList: MutableList<MainTimeFrame>? = mutableListOf()

    private var item: MainTimeFrame? = null

    private var dataPassed: MutableList<Service>? = mutableListOf()

    private val TAG = this.javaClass.simpleName

    private var count = 0

    private var daysOfWeek: MutableList<String> = mutableListOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val mProgressDialog = DialogUtils.showLoadingDialog(this, this.getString(R.string.loading_data))
        day = getCurrentDay()
        date = getCurrentDate()
        month = getCurrentMoth()
        year = getCurrentYear()
        dataPassed = intent.getParcelableArrayListExtra(Constants.Data.DATA)
        mButtonSubmit?.setOnClickListener(this)
        initData()
        initAnimation()
        mTextViewDay.text = convertLongDay(day!!)
        mTextViewBook.text = date + "-" + getCurrentMoth() + "-" + getCurrentYear()

        /**
         * Hide loading dialog
         */
        Handler().postDelayed({ mProgressDialog.dismiss() }, 1500)

    }

    /**
     * Make animation for step 1 and 2
     */
    private fun initAnimation() {
        val animation = TranslateAnimation(
            500f,
            0f,
            500f,
            0f
        )
        animation.duration = 1000
        animation.fillAfter = true
        mTextViewFrame.startAnimation(animation)
        mTextViewStart.startAnimation(animation)
    }

    /**
     * Get current month
     */
    private fun getCurrentMoth(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM")
        return dateFormat.format(date)
    }

    private fun getCurrentDateText(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd-MMM-YY")
        return dateFormat.format(date)
    }

    private fun getCurrentText(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MMM-YY")
        return dateFormat.format(date)
    }

    private fun getDayPicked(strDay: String): String {
        val dateFormat = SimpleDateFormat("MM-dd-yyyy")
        val cvDate = dateFormat.parse(strDay)
        val fmt = SimpleDateFormat("EEEE")
        return fmt.format(cvDate)
    }

    /**
     * Get current date
     */
    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd")
        return dateFormat.format(date)
    }

    /**
     * Get current year
     */
    private fun getCurrentYear(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy")
        return dateFormat.format(date)
    }

    private fun getCurrentYearTwo(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yy")
        return dateFormat.format(date)
    }

    /**
     * Get current day {T2, T3, T4, T5, T6, T7, CN}
     */
    private fun getCurrentDay(): String {
        val date = Calendar.getInstance().time
//        val date = Date()
        print(date)
        val dateFormat = SimpleDateFormat("E")
        return dateFormat.format(date)
    }


    /**
     * Convert long day from eng to vn
     */
    private fun convertLongDay(day: String): String {
        return when (day) {
            "Mon" -> "MonDay"
            "Tue" -> "TuesDay"
            "Wed" -> "Wednesday"
            "Thu" -> "Thursday"
            "Fri" -> "Friday"
            "Sat" -> "Saturday"
            "Sun" -> "Sunday"
            else -> day
        }
    }

    /**
     * Convert short day from eng to vn
     */
    private fun convertShortDay(day: String): String {
        return when (day) {
            "Mon" -> "T2"
            "Tue" -> "T3"
            "Wed" -> "T4"
            "Thu" -> "T5"
            "Fri" -> "T6"
            "Sat" -> "T7"
            "Sun" -> "T8"
            else -> day
        }
    }

    private fun convertShortDayPicked(day: String): String {
        return when (day) {
            "T2" -> "Thứ 2"
            "T3" -> "Thứ 3"
            "T4" -> "Thứ 4"
            "T5" -> "Thứ 5"
            "T6" -> "Thứ 6"
            "T7" -> "Thứ 7"
            "CN" -> "Chủ nhật"
            else -> day
        }
    }

    /**
     * Convert day number
     */
    private fun convertDayNumber(value: Int): String {
        return when (value) {
            2 -> "T2"
            3 -> "T3"
            4 -> "T4"
            5 -> "T5"
            6 -> "T6"
            0 -> "T7"
            1 -> "CN"
            else -> value.toString()
        }
    }

    private fun initData() {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM")
        val value = dateFormat.format(currentDate)
        isPicked = true
        /**
         * Init adapter for days
         */
        mAdapterDay = BaseAdapter()
        mRecyclerViewDay?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerViewDay?.adapter = mAdapterDay

        /**
         * Prevent recycler view scroll horizontal
         */
        val disable = RecyclerViewDisable()
//        mRecyclerViewDay.addOnItemTouchListener(disable)

        /**
         * Init adapter for dates
         */
        mAdapterDate = BaseAdapter()
        mRecyclerViewDate?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerViewDate?.adapter = mAdapterDate
//        mRecyclerViewDate.addOnItemTouchListener(disable)

        val currentVal = convertShortDay(day!!)
        val lengthDays = daysOfWeek.size
        val temp = currentVal.split("T")
//        var strList = temp
//        strList = currentVal.split(".")
        val result = temp[1].toInt()
        for (index in 0 until lengthDays) {
            val value = (index + result) % 7
            val data = convertDayNumber(value)
            mAdapterDay?.addItem(DayItem(this, data, this))
        }

        val calCurrent = Calendar.getInstance()
        mAdapterDate?.addItem(DateItem(this, calCurrent.get(Calendar.DAY_OF_MONTH).toString(), true, this))

        for (index in 1..6) {
            calCurrent.add(Calendar.DATE, 1)
            mAdapterDate?.addItem(DateItem(this, calCurrent.get(Calendar.DAY_OF_MONTH).toString(), false, this))
        }
        /**
         * Loading main time frame
         */
        val data = DBHelper().getMainTimesFrame() ?: return
        val mSpinnerAdapter = data.let { MainFrameAdapter(this, it) }
        mSpinnerFrame.adapter = mSpinnerAdapter
        mSpinnerFrame?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                item = if (position == 0) {
                    mSpinnerFrame?.selectedItem as MainTimeFrame?
                } else {
                    mSpinnerFrame?.selectedItem as MainTimeFrame?
                }

                initTimesActive(strDate)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        strDate = getCurrentDateText()
        initTimesActive(strDate)
    }

    private fun initTimesActive(date: String?) {
        /**
         * Init adapter for times frame active
         */
        mAdapterFrame?.removeAll()
        mAdapterFrame = BaseAdapter()
        mRecyclerViewFrame?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerViewFrame?.adapter = mAdapterFrame

        if (item?.id == null) {
            mList = DBHelper().getTimesFrameActive(14, date)
            for (it in mList!!) {
                mAdapterFrame?.addItem(TimeFrameActiveItem(this, it, true, this))
            }
        } else {
            mList = DBHelper().getTimesFrameActive(item?.id!!, date)
            for (it in mList!!) {
                mAdapterFrame?.addItem(TimeFrameActiveItem(this, it, true, this))
            }
        }

        item?.id?.let { PreferenceHelper.saveMainFrame(this, it) }
    }

    /**
     * Query date to load times frame.
     * @param date
     */
    override fun onTapDateItem(date: String) {
        Log.d(TAG, date)
        val result = date + "-" + getCurrentMoth() + "-" + getCurrentYear()
        mTextViewBook.text = result

        val localDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.of(getCurrentYear().toInt(), getCurrentMoth().toInt(), date.toInt()).dayOfWeek
        } else {
            Log.d(TAG, "")
        }
        mTextViewDay.text = localDate.toString()
        val newDate = date + "-" + getCurrentText()
        initTimesActive(newDate)
        DialogUtils.showToastMessage(this, "Bạn đã chọn ngày $date")
    }

    override fun onTapDayItem(day: String) {
        Log.d(TAG, day)
    }

    override fun onTapActive(data: MainTimeFrame?) {
        if (isPicked) {
            mData = data
            isPicked = false
        } else {
            isPicked = true
            mData = null
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mButtonSubmit -> {
                if (mData == null) {
                    DialogUtils.showToastMessage(
                        this,
                        "Khung giờ hoạt động chỉ được chọn duy nhất và không được trống !"
                    )
                } else {
                    val intent = Intent(this, ConfirmActivity::class.java)
                    intent.putExtra(Constants.Data.DATA, dataPassed as Serializable)
                    intent.putExtra(Constants.Data.MODEL, mData)
                    intent.putExtra(Constants.Data.DATE, strDate)
                    intent.putExtra(Constants.Data.DAY, mTextViewDay.text.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
