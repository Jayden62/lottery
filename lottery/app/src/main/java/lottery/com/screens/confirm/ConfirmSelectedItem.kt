package lottery.com.screens.confirm

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import lottery.com.model.Service
import kotlinx.android.synthetic.main.item_selected_confirm.view.*

class ConfirmSelectedItem(context: Context, var service: Service) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_selected_confirm

    override fun onBindView(view: View?) {
        view?.mTextView?.text = service.name
    }
}