package lottery.com.screens.fragments.service


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_service.*

import lottery.com.R
import lottery.com.base.list.BaseAdapter

class FragmentService : Fragment() {
    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRecyclerView?.adapter = mAdapter

        mAdapter?.addItem(ServiceItem(context!!))
        mAdapter?.addItem(ServiceItem(context!!))

    }
}
