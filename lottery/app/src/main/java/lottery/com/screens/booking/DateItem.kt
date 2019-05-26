package lottery.com.screens.booking

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_date.view.*


class DateItem(var context: Context, var date: String, var isBold: Int, var callback: Callback) :
    BaseItem<Any>(context), View.OnClickListener {

    interface Callback {
        fun onTapDateItem(date: String)
    }

    private var mTextViewDate: TextView? = null

    private var isClicked: Boolean = false

    override fun onInitLayout(): Int = R.layout.item_date

    override fun onBindView(view: View?) {

        view?.mTextViewDate?.text = date
        view?.mConstrainLayout?.setOnClickListener(this)

        mTextViewDate = view?.mTextViewDate
        isClicked = !isClicked

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (isBold) {
                1 -> {
                    mTextViewDate?.background = context.getDrawable(R.drawable.bg_rouded_selected_date_item)
                }
                0 -> {
                    mTextViewDate?.background = context.getDrawable(R.drawable.bg_rouded_unselected_date_item)
                }
            }
        }

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mTextViewDate, R.id.mConstrainLayout -> {
                callback.onTapDateItem(date)
            }
        }
    }
}