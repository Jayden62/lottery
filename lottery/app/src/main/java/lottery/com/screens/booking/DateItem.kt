package lottery.com.screens.booking

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_date.view.*

class DateItem(var context: Context, var date: String, var isBold: Boolean) : BaseItem<Any>(context),
    View.OnClickListener {

    override fun onInitLayout(): Int = R.layout.item_date

    override fun onBindView(view: View?) {
        view?.mTextViewDate?.text = date
        view?.mTextViewDate?.setOnClickListener(this)

        if (isBold) {
            view?.mConstrainLayout?.background = context.getDrawable(R.drawable.bg_rounded_date_item)
        }
    }

    override fun onClick(view: View?) {

    }
}