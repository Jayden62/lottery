package lottery.com.screens.booking

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_day.view.*

class DayItem(var context: Context,var day : String) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_day

    override fun onBindView(view: View?) {
        view?.mTextViewDay?.text = day
    }
}