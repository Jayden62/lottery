package lottery.com.screens.booking


import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_booking.*
import lottery.com.R
import lottery.com.base.list.BaseAdapter
import java.text.SimpleDateFormat
import java.util.Calendar


class BookingActivity : AppCompatActivity(), View.OnClickListener {


    private var mAdapter: BaseAdapter<Any>? = null

    private var day: String? = null
    private var date: String? = null
    private var month: String? = null
    private var year: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        mButtonSubmit.setOnClickListener(this)

        day = getCurrentDay()
        date = getCurrentDate()
        month = getCurrentMoth()
        year = getCurrentYear()
        mAdapter?.removeAll()
        initDatesOfMonth()
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
            else -> day
        }
    }

    private fun initDatesOfMonth() {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MM")
        val value = dateFormat.format(currentDate)
        val currentVal = date?.toInt()
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView?.adapter = mAdapter
        when (value) {
            "01", "03", "05", "07", "08", "10", "12" -> {
                for (it in currentVal!!..31) {
                    if (it == date?.toInt()) {
                        mAdapter?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapter?.addItem(DateItem(this, it.toString(), 0))
                    }

                }
            }
            "04", "06", "09", "11" -> {
                for (it in currentVal!!..31) {
                    if (it == date?.toInt()) {
                        mAdapter?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapter?.addItem(DateItem(this, it.toString(), 0))

                    }
                }
            }
            "02" -> {
                for (it in currentVal!!..31) {
                    if (it == date?.toInt()) {
                        mAdapter?.addItem(DateItem(this, it.toString(), 1))
                    } else {
                        mAdapter?.addItem(DateItem(this, it.toString(), 0))

                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.mButtonSubmit -> {
                if (mAdapter?.itemCount!! > 1) {
                    Toast.makeText(
                        this,
                        "Quý khách không thể đặt nhiều hơn 1 ngày. Vui lòng chỉ chọn 1 ngày hẹn.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

//            R.id.mButtonDate -> {
//                var mCalendar = Calendar.getInstance()
//                val year = mCalendar.get(Calendar.YEAR)
//                val month = mCalendar.get(Calendar.MONTH + 1)
//                val day = mCalendar.get(Calendar.DAY_OF_MONTH)
//                val mDatePicker = DatePickerDialog(
//                    this,
//                    DatePickerDialog.OnDateSetListener { datePicker, selectedYear, selectedMonth, selectedDay ->
//                        Log.e("Date Selected", "Month: $selectedMonth Day: $selectedDay  Year: $selectedYear")
//                        var newDay = selectedDay
//                        val newMonth = selectedMonth + 1
//                        var newYear = selectedYear
//                        mTextViewDate.text = "$newDay-$newMonth-$newYear"
//                    }, year, month, day
//                )
//                mDatePicker.setTitle("Select date")
//                mDatePicker.show()
//            }
//
//            R.id.mButtonSubmit -> {
//                mProgressDialog?.show()
//                if (mTextViewDate.text.toString().isEmpty()) {
//                    mProgressDialog?.dismiss()
//                    Toast.makeText(this, "Vui lòng chọn ngày đặt lịch", Toast.LENGTH_SHORT).show()
//                    return
//                }
//                val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
//                val date = Date()
//                val mDateString = dateFormat.format(date)
//                val newDate = dateFormat.parse(mDateString)
//                val currentDate = newDate.time
//                print(currentDate)
//
//                val selectedDateString = mTextViewDate.text.toString()
//
//                val selectedNewDate = dateFormat.parse(selectedDateString)
//                val selectedDate = selectedNewDate.time
//                print(selectedDate)
//
//
//                if (selectedDate > currentDate) {
//                    mProgressDialog?.dismiss()
//                    Toast.makeText(this, "Đặt lịch hẹn thành công.", Toast.LENGTH_SHORT).show()
//                } else {
//                    mProgressDialog?.dismiss()
//                    mImageViewError.visibility = View.VISIBLE
//                }
//            }
        }
    }

}
