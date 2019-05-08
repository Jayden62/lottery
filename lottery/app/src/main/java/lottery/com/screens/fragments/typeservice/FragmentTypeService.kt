package lottery.com.screens.fragments.typeservice


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.android.synthetic.main.fragment_service.view.*

import lottery.com.R
import lottery.com.base.list.BaseAdapter
import lottery.com.database.DBHelper
import lottery.com.screens.fragments.service.ServiceActivity
import lottery.com.screens.home.HomeActivity

class FragmentTypeService : Fragment(), TypeServiceItem.Callback {

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInit()
    }


    private fun onInit() {
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRecyclerView?.adapter = mAdapter
        val data = DBHelper().getTypeService() ?: return
        for (it in data) {
            mAdapter.addItem(TypeServiceItem(activity!!, it, this))
        }
    }


    override fun onTapItem() {
        startActivity(Intent(context, ServiceActivity::class.java))
    }
}
