package lottery.com.screens.booking

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.CompoundButton
import android.widget.TextView
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_date.view.*


class DateItem(var context: Context, var date: String, var isBold: Int) :
    BaseItem<Any>(context), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private var mTextViewDate: TextView? = null

    override fun onInitLayout(): Int = R.layout.item_date

    override fun onBindView(view: View?) {
        view?.mTextViewDate?.text = date
        view?.mCheckBox?.setOnCheckedChangeListener(this)
        view?.mConstrainLayout?.setOnClickListener(this)

        mTextViewDate = view?.mTextViewDate

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (isBold) {
                1 -> {
                    view?.mTextViewDate?.setTextColor(context.getColor(R.color.redColor))
                    view?.mCheckBox?.isChecked = true
                }
                0 -> {
                    view?.mTextViewDate?.setTextColor(context.getColor(R.color.grayColor))
                    view?.mCheckBox?.isChecked = false
                }
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isChecked) {
                mTextViewDate?.setTextColor(context.getColor(R.color.redColor))
            } else {
                mTextViewDate?.setTextColor(context.getColor(R.color.grayColor))
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.mTextViewDate, R.id.mConstrainLayout -> {

            }
        }
    }
}