package lottery.com.screens.book

import android.content.Context
import android.os.Build
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_frame.view.*
import lottery.com.model.MainTimeFrame

class TimeFrameActiveItem(
    var context: Context,
    var data: MainTimeFrame?,
    private var isPicked: Boolean,
    var callback: Callback
) :
    BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_frame

    interface Callback {
        fun onTapActive(data: MainTimeFrame?)
    }

    override fun onBindView(view: View?) {
        view?.mButton?.text = data?.detail
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            view?.mButton?.setOnClickListener {
                isPicked = if (isPicked) {
                    view.mButton?.setBackgroundColor(context.getColor(R.color.colorContact))
                    false
                } else {
                    view.mButton?.setBackgroundColor(context.getColor(R.color.colorGray))
                    true
                }
                callback.onTapActive(data)
            }
        }
    }
}