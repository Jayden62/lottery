package lottery.com.screens.fragments.service

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_service.view.*

class ServiceItem(var context: Context, var name: String, var description: String, var callback: Callback) :
    BaseItem<Any>(context),
    View.OnClickListener {

    interface Callback {
        fun onTapItem()
    }

    override fun onInitLayout(): Int = R.layout.item_service

    override fun onBindView(view: View?) {
        view?.mConstrainLayout?.setOnClickListener(this)

        view?.mTextViewName?.text = name
        view?.mTextViewDescription?.text = description
    }

    override fun onClick(v: View?) {
        callback.onTapItem()
    }

}