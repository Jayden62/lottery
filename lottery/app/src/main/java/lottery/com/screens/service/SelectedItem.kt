package lottery.com.screens.service

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_selected_service.view.*
import lottery.com.model.Service

class SelectedItem(var context: Context, var item: Service?, var callback: Callback) :
    BaseItem<Any>(context, item) {

    interface Callback {
        fun onRemoveItem(item: Service?)
    }

    override fun onInitLayout(): Int = R.layout.item_selected_service

    override fun onBindView(view: View?) {
        view?.mTextViewSelectedService?.text = item?.name
        view?.mImageViewRemove?.setOnClickListener {
            callback.onRemoveItem(item)
        }
    }
}