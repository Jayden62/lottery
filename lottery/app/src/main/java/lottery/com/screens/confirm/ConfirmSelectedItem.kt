package lottery.com.screens.confirm

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem

class ConfirmSelectedItem(context: Context) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_selected_confirm

    override fun onBindView(view: View?) {
    }
}