package lottery.com.screens.booking

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.TextView
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_date.view.*


class DateItem(var context: Context, var date: String, var isBold: Int) :
    BaseItem<Any>(context), View.OnClickListener {

    private var mTextViewDate: TextView? = null

    override fun onInitLayout(): Int = R.layout.item_date

    override fun onBindView(view: View?) {

        view?.mTextViewDate?.text = date
        view?.mConstrainLayout?.setOnClickListener(this)

        mTextViewDate = view?.mTextViewDate

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
                mTextViewDate?.background = context.getDrawable(R.drawable.bg_rouded_selected_date_item)
            }
        }
    }
}