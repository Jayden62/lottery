package lottery.com.screens.book

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_day.view.*

class DayItem(var context: Context, var day: String, var callback: Callback) : BaseItem<Any>(context), View.OnClickListener {
    override fun onInitLayout(): Int = R.layout.item_day

    interface Callback {
        fun onTapDayItem(day: String)
    }

    override fun onBindView(view: View?) {
        view?.mTextViewDay?.text = day
        view?.mTextViewDay?.setOnClickListener(this)
        view?.mGroup?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTextViewDay, R.id.mGroup -> {
                callback.onTapDayItem(day)
            }

        }
    }
}