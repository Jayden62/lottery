package lottery.com.screens.service

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_sub_service.*
import lottery.com.R
import lottery.com.base.list.BaseAdapter
import lottery.com.database.DBHelper
import lottery.com.utils.Constants
import lottery.com.model.TypeService
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.widget.Button
import lottery.com.local.LocalHelper
import lottery.com.model.Service
import lottery.com.screens.booking.BookingActivity
import lottery.com.utils.DialogUtils

class ServiceActivity : AppCompatActivity(), View.OnClickListener, ServiceItem.Callback, SelectedItem.Callback {

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    private var mAdapterSelected: BaseAdapter<Any> = BaseAdapter()

    private var mSelectedData: MutableList<Service>? = mutableListOf()

    private var data: MutableList<Service>? = mutableListOf()

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_service)
        onInit()
    }

    private fun onInit() {
        val mProgressDialog = DialogUtils.showLoadingDialog(this, this.getString(R.string.loading_data))
        mAdapter = BaseAdapter()
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRecyclerView?.adapter = mAdapter

        mFloatingButton.setOnClickListener(this)
        mImageViewBack.setOnClickListener(this)

        val value = intent.getParcelableExtra(Constants.Data.DATA) as TypeService
        data = DBHelper().getServices()

        if (data?.size == 0) {
            mAdapter.addItem(ServiceEmptyItem(this))
        } else {
            for (item in data!!) {
                if (value.id == item.typeId) {
                    mAdapter.addItem(ServiceItem(this, item, this))
                }
            }
        }
        Handler().postDelayed({ mProgressDialog.dismiss() }, 1500)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mFloatingButton -> {
//                val data = LocalHelper(this).getItems ?: return
                if (mSelectedData != null) {
                    createServicesDialog(mSelectedData)
                    mSelectedData = mutableListOf()
                }
            }

            R.id.mImageViewBack -> {
                finish()
            }
        }
    }

    override fun onServiceItemSelected(value: Service?) {
        mSelectedData?.add(value!!)
    }

    private fun createServicesDialog(data: MutableList<Service>?) {

        var counter = 0
        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.dialog_selected_service, null)
        dialog.setView(view)

        val mButtonBook = view.findViewById(R.id.mButtonBook) as Button
        val mButtonClose = view.findViewById(R.id.mButtonClose) as Button
        val mRecyclerViewSelectedService = view.findViewById(R.id.mRecyclerViewSelectedService) as RecyclerView
        mAdapterSelected = BaseAdapter()
        mRecyclerViewSelectedService.layoutManager = LinearLayoutManager(this)
        mRecyclerViewSelectedService.adapter = mAdapterSelected

        if (data?.size == 0 || data == null) {
            mAdapterSelected.addItem(SelectedIemEmpty(this))
        } else {
            for ((index, value) in data.withIndex()) {
                counter++
                mAdapterSelected.addItem(SelectedItem(this, value.name, counter, this, index))
            }
        }

//        for ((index, value) in local!!.withIndex()) {
//            counter++
//            mAdapterSelected.addItem(SelectedItem(this, value.name, counter, this, index))
//        }

        mButtonBook.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }


        mButtonClose.setOnClickListener {
            //            LocalHelper(this).saveItems(data)
            mAdapter.removeAll()
            mAdapter.notifyDataSetChanged()
            mAdapterSelected.notifyDataSetChanged()
            alertDialog?.dismiss()
            onInit()
        }
        alertDialog = dialog.create()
        alertDialog?.dismiss()

        alertDialog?.setCancelable(false)
        alertDialog?.show()
    }

    override fun onRemoveItem(pos: Int) {
        mAdapterSelected.removeItemAt(pos)
    }
}
