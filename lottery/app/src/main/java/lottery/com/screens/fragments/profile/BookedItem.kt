package lottery.com.screens.fragments.profile

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import lottery.com.model.Booked
import kotlinx.android.synthetic.main.item_booked.view.*

class BookedItem(context: Context, var item: Booked) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_booked

    override fun onBindView(view: View?) {
        view?.mTextViewDate?.text = item.schDate
        view?.mTextViewTime?.text = item.detailsTimeFrame
        view?.mTextViewService?.text = item.serviceName
    }
}