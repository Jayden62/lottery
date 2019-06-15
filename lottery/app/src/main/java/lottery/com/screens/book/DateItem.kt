package lottery.com.screens.book

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.TextView
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_date.view.*


class DateItem(var context: Context, var date: String, var isBold: Boolean, var callback: Callback) :
    BaseItem<Any>(context), View.OnClickListener {

    interface Callback {
        fun onTapDateItem(date: String)
    }

    private var mTextViewDate: TextView? = null

    private var mConstrainLayout: ConstraintLayout? = null

    private var isPicked: Boolean = false

    override fun onInitLayout(): Int = R.layout.item_date

    override fun onBindView(view: View?) {

        view?.mTextViewDate?.text = date
        isPicked = true
        mTextViewDate = view?.mTextViewDate
        mConstrainLayout = view?.mConstrainLayout
        mTextViewDate?.setOnClickListener(this)
        mConstrainLayout?.setOnClickListener(this)

        when (isBold) {
            true -> {
                mTextViewDate?.background = context.getDrawable(R.drawable.bg_rouded_selected_date_item)
            }
            false -> {
                mTextViewDate?.background = context.getDrawable(R.drawable.bg_rouded_unselected_date_item)
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