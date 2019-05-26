package lottery.com.screens.service

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem

class SelectedIemEmpty(var context: Context) : BaseItem<Any>(context) {

    override fun onInitLayout(): Int = R.layout.item_no_data
    override fun onBindView(view: View?) {

    }
}