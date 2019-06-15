package lottery.com.screens.book

import android.content.Context
import android.os.Build
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_frame.view.*

class TimeFrameActiveItem(var context: Context, var frame: String, private var isPicked: Boolean) :
    BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_frame

    override fun onBindView(view: View?) {
        view?.mButton?.text = frame
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            view?.mButton?.setOnClickListener {
                isPicked = if (isPicked) {
                    view.mButton?.setBackgroundColor(context.getColor(R.color.colorContact))
                    false
                } else {
                    view.mButton?.setBackgroundColor(context.getColor(R.color.colorGray))
                    true
                }
            }
        }
    }
}