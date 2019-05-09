package lottery.com.screens.service

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_sub_service.*
import lottery.com.R
import lottery.com.base.list.BaseAdapter
import lottery.com.database.DBHelper
import lottery.com.helper.Constants
import lottery.com.model.TypeService

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
        val value = intent.getParcelableExtra(Constants.Data.DATA) as TypeService
        val data = DBHelper().getServices()

        if (data?.size == 0) {
            mAdapter.addItem(ServiceEmptyItem(this))
        } else {
            for (item in data!!) {
                if (value.id == item.typeId) {
                    mAdapter.addItem(ServiceItem(this, item))
                }
            }
        }
    }

    override fun onClick(v: View?) {
        finish()
    }
}
