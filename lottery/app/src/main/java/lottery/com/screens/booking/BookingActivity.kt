package lottery.com.screens.booking


import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_booking.*
import lottery.com.R
import lottery.com.base.list.BaseAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import android.util.Log
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
        val animation = TranslateAnimation(
            500f,
            0f,
            500f,
            0f
        ) // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
        animation.duration = 1000 // animation duration
        animation.fillAfter = true
        mTextViewFrame.startAnimation(animation)

        mTextViewDay.text = convertDay(day!!)
        mTextViewToday.text =
            "$date\tTháng $month, $year"

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


    private fun convertDay(day: String): String {
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

    private fun initData() {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM")
        val value = dateFormat.format(currentDate)
        val currentVal = date?.toInt()
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
        mAdapterFrame = BaseAdapter()
        mRecyclerViewFrame?.layoutManager = LinearLayoutManager(this)
        mRecyclerViewFrame?.adapter = mAdapterFrame
        mAdapterFrame?.addItem(FrameItem(this, "Minh Zâm"))
        mAdapterFrame?.addItem(FrameItem(this, "Minh Zâm"))
        mAdapterFrame?.addItem(FrameItem(this, "Minh Zâm"))

        for (it in daysOfWeek) {
            mAdapterDay?.addHeader(DayItem(this, it))
        }

        when (value) {
            "01", "03", "05", "07", "08", "10", "12" -> {
                for (it in currentVal!!..31) {
                    if (it == date?.toInt()) {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 0))
                    }

                }
            }
            "04", "06", "09", "11" -> {
                for (it in currentVal!!..31) {
                    if (it == date?.toInt()) {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 0))

                    }
                }
            }
            "02" -> {
                for (it in currentVal!!..31) {
                    if (it == date?.toInt()) {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapterDate?.addItem(DateItem(this, it.toString(), 0))

                    }
                }
            }
        }

        mRecyclerViewDate.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("TAG", "Start scrolling.")
//                totalItemCount = gridLayoutManager.getItemCount()
//                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
//                if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
//                    // End has been reached
//                    // Do something
//                    if (onLoadMoreListener != null) {
//                        onLoadMoreListener.onLoadMore(lastVisibleItem)
//                    }
//                    loading = true
//                }
                val a = mAdapterDate?.getItemSize()!! - 1
                print(a)

                val b = mAdapterDate?.itemCount!!
                print(b)
                val itemCounted = 7
                if (itemCounted <= mAdapterDate?.itemCount!!) {
                    print("load more")
                } else {
                    return
                }
//                if (!isLoading && itemCount <= mAdapterDate?.itemCount!!) {
//                    isLoading = true
//                } else {
//                    return
//                }
            }
        })
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
