package lottery.com.screens.booking


import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_booking.*
import lottery.com.R
import lottery.com.base.list.BaseAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import android.view.animation.TranslateAnimation

class BookingActivity : AppCompatActivity(), View.OnClickListener {


    private var mAdapterDay: BaseAdapter<Any>? = null
    private var mAdapterDate: BaseAdapter<Any>? = null
    private var mAdapterFrame: BaseAdapter<Any>? = null

    private var isLoading: Boolean = false

    private var day: String? = null
    private var date: String? = null
    private var month: String? = null
    private var year: String? = null

    private var daysOfWeek: MutableList<String> = mutableListOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        day = getCurrentDay()
        date = getCurrentDate()
        month = getCurrentMoth()
        year = getCurrentYear()
        initData()
        initAnimation()
        mTextViewDay.text = convertLongDay(day!!)
        mTextViewToday.text =
            "$date\tTháng $month, $year"
    }

    private fun initAnimation() {
        val animation = TranslateAnimation(
            500f,
            0f,
            500f,
            0f
        )
        animation.duration = 1000 // animation duration
        animation.fillAfter = true
        mTextViewFrame.startAnimation(animation)
        mTextViewStart.startAnimation(animation)
    }

    private fun getCurrentMoth(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM")
        return dateFormat.format(date)
    }

    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd")
        return dateFormat.format(date)
    }

    private fun getCurrentYear(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy")
        return dateFormat.format(date)
    }

    private fun getCurrentDay(): String {
        val date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEE")
        return dateFormat.format(date)
    }


    private fun convertLongDay(day: String): String {
        return when (day) {
            "Mon" -> "Thứ 2"
            "Tue" -> "Thứ 3"
            "Wed" -> "Thứ 4"
            "Thu" -> "Thứ 5"
            "Fri" -> "Thứ 6"
            "Sat" -> "Thứ 7"
            "Sun" -> "Chủ nhật"
            else -> day
        }
    }

    private fun convertShortDay(day: String): String {
        return when (day) {
            "Mon" -> "T2"
            "Tue" -> "T3"
            "Wed" -> "T4"
            "Thu" -> "T5"
            "Fri" -> "T6"
            "Sat" -> "T7"
            "Sun" -> "CN"
            else -> day
        }
    }

    private fun convertDayNumber(value: Int): String {
        return when (value) {
            2 -> "T2"
            3 -> "T3"
            4 -> "T4"
            5 -> "T5"
            6 -> "T6"
            1 -> "T7"
            0 -> "CN"
            else -> value.toString()
        }
    }

    private fun initData() {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM")
        val value = dateFormat.format(currentDate)
        /**
         * Init adapter for days
         */
        mAdapterDay = BaseAdapter()
        mRecyclerViewDay?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerViewDay?.adapter = mAdapterDay
        /**
         * Init adapter for dates
         */
        mAdapterDate = BaseAdapter()
        mRecyclerViewDate?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerViewDate?.adapter = mAdapterDate


        /**
         * Init adapter for times frame
         */
//        mAdapterFrame = BaseAdapter()
//        mRecyclerViewFrame?.layoutManager = LinearLayoutManager(this)
//        mRecyclerViewFrame?.adapter = mAdapterFrame
//
//        val data = DBHelper().getTimesFrame()
//        data?.forEachIndexed { index, timeFrame ->
//            mAdapterFrame?.addItem(FrameItem(this, timeFrame.detail))
//        }

        val currentVal = convertShortDay(day!!)
        val lengthDays = daysOfWeek.size
        val temp = currentVal.split("T")
        val result = temp[1].toInt()

        for (index in 0..lengthDays) {
            val value = (index + result) % 7
            val data = convertDayNumber(value)
            mAdapterDay?.addItem(DayItem(this, data))
        }

        when (value) {
            "01", "03", "05", "07", "08", "10", "12" -> {
                for (it in date?.toInt()!!..31) {
                    if (it == date?.toInt()) {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 0))
                    }

                }
            }
            "04", "06", "09", "11" -> {
                for (it in date?.toInt()!!..30) {
                    if (it == date?.toInt()) {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 0))

                    }
                }
            }
            "02" -> {
                for (it in date?.toInt()!!..28) {
                    if (it == date?.toInt()) {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 0))

                    }
                }
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {

//            R.id.mButtonSubmit -> {
////                if (mAdapter?.itemCount!! > 1) {
////                    Toast.makeText(
////                        this,
////                        "Quý khách không thể đặt nhiều hơn 1 ngày. Vui lòng chỉ chọn 1 ngày hẹn.",
////                        Toast.LENGTH_SHORT
////                    ).show()
////                }
//            }
        }
    }

}
