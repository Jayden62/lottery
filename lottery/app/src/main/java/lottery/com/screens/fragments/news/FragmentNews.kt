package lottery.com.screens.fragments.news


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_service.*

import lottery.com.R
import lottery.com.base.list.BaseAdapter

class FragmentNews : Fragment() {
    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit()
    }

    private fun onInit() {
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRecyclerView?.adapter = mAdapter
        mAdapter.addItem(NewsItem(activity!!))
        mAdapter.addItem(NewsItem(activity!!))
        mAdapter.addItem(NewsItem(activity!!))
    }
}
