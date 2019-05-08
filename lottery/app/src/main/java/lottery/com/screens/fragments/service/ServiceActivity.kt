package lottery.com.screens.fragments.service

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_sub_service.*
import lottery.com.R
import lottery.com.base.list.BaseAdapter

class ServiceActivity : AppCompatActivity(), View.OnClickListener {

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_service)
        mImageViewBack.setOnClickListener(this)
        onInit()
    }

    private fun onInit() {
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRecyclerView?.adapter = mAdapter

        mAdapter.addItem(ServiceItem(this))
    }

    override fun onClick(v: View?) {
        finish()
    }
}
