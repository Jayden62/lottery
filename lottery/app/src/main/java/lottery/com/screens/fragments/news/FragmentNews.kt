package lottery.com.screens.fragments.news


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_service.*

import lottery.com.R
import lottery.com.base.list.BaseAdapter
import lottery.com.database.DBHelper
import lottery.com.helper.Constants
import lottery.com.model.News
import lottery.com.screens.newsdetail.NewsDetailActivity

class FragmentNews : Fragment(), NewsItem.Callback {


    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit()
    }

    private fun onInit() {

        val data = DBHelper().getNews()
        if (data?.size != 0) {
            for (item in data!!) {
                mAdapter.addItem(NewsItem(activity!!, item, this))
            }
        }
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRecyclerView?.adapter = mAdapter

    }

    override fun onTapItem(data: News?) {
        val intent = Intent(activity, NewsDetailActivity::class.java)
        intent.putExtra(Constants.Data.DATA, data)
        startActivity(intent)
    }
}
