package lottery.com.screens.service

import android.content.Context
import android.view.View
import lottery.com.R
import lottery.com.base.list.BaseItem
import kotlinx.android.synthetic.main.item_selected_service.view.*

class SelectedItem(var context: Context, var name: String?, var count: Int) : BaseItem<Any>(context) {


    override fun onInitLayout(): Int = R.layout.item_selected_service

    override fun onBindView(view: View?) {
        view?.mTextViewSelectedService?.text = name
        view?.mTextViewNumber?.text = count.toString()
    }
}