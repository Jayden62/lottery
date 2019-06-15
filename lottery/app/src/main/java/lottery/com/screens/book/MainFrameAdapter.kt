package lottery.com.screens.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import lottery.com.R
import lottery.com.model.MainTimeFrame

class MainFrameAdapter(context: Context, mTypes: MutableList<MainTimeFrame>) :
    ArrayAdapter<MainTimeFrame>(context, 0, mTypes) {
    private var data: MutableList<MainTimeFrame> = mTypes

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)
    }

    private fun customView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = convertView
        view = layoutInflater.inflate(R.layout.item_main_frame, parent, false)
        val mTextViewFrame = view?.findViewById(R.id.mTextViewFrame) as TextView
        mTextViewFrame.text = data[position].detail
        return view
    }
}