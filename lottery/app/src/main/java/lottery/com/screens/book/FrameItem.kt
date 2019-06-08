package lottery.com.screens.book

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_frame.view.*

class FrameItem(var context: Context, var frame: String) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_frame

    override fun onBindView(view: View?) {
        view?.mTextViewFrame?.text = frame
    }
}