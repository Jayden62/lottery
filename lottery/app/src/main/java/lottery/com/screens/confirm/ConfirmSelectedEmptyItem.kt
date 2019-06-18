package lottery.com.screens.confirm

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem

class ConfirmSelectedEmptyItem(context: Context) : BaseItem<Any>(context) {
    override fun onInitLayout(): Int = R.layout.item_confirm_selected_empty

    override fun onBindView(view: View?) {
    }

}