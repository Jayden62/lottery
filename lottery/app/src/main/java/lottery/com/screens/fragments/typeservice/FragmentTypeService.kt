package lottery.com.screens.fragments.typeservice


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_service.*

import lottery.com.R
import lottery.com.base.list.BaseAdapter
import lottery.com.database.DBHelper
import lottery.com.utils.Constants
import lottery.com.model.TypeService
import lottery.com.screens.service.ServiceActivity
import lottery.com.utils.DialogUtils

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

        val data = DBHelper().getTypeServices() ?: return
        if (data.size != 0) {
            for (it in data) {
                mAdapter.addItem(TypeServiceItem(activity!!, it, this))
            }
        }

    }

    override fun onTapItem(data: TypeService?) {
        val intent = Intent(activity, ServiceActivity::class.java)
        intent.putExtra(Constants.Data.DATA, data)
        startActivity(intent)
    }
}
