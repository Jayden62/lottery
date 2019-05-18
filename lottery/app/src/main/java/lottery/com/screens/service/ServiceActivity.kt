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
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.widget.Button
import lottery.com.model.Service
import lottery.com.screens.booking.BookingActivity

class ServiceActivity : AppCompatActivity(), View.OnClickListener, ServiceItem.Callback {

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    private var mAdapterSelected: BaseAdapter<Any> = BaseAdapter()

    private var mSelectedData: MutableList<Service>? = mutableListOf()

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

        mFloatingButton.setOnClickListener(this)


        val value = intent.getParcelableExtra(Constants.Data.DATA) as TypeService
        val data = DBHelper().getServices()

        if (data?.size == 0) {
            mAdapter.addItem(ServiceEmptyItem(this))
        } else {
            for (item in data!!) {
                if (value.id == item.typeId) {
                    mAdapter.addItem(ServiceItem(this, item, this))
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mFloatingButton -> {
//                val intent = Intent(this, BookingActivity::class.java)
//                startActivity(intent)
                createServicesDialog(mSelectedData)
            }
        }
    }

    override fun onCheckItem(value: Service?) {
        mSelectedData?.add(value!!)
    }

    private fun createServicesDialog(data: MutableList<Service>?) {
        if (data == null) {
            return
        }
        var counter = 0
        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.dialog_selected_service, null)
        dialog.setView(view)

        val mButtonBook = view.findViewById(R.id.mButtonBook) as Button
        val mRecyclerViewSelectedService = view.findViewById(R.id.mRecyclerViewSelectedService) as RecyclerView
        mAdapterSelected = BaseAdapter()
        mRecyclerViewSelectedService?.layoutManager = LinearLayoutManager(this)
        mRecyclerViewSelectedService?.adapter = mAdapterSelected

        for (it in data!!) {
            counter++
            mAdapterSelected.addItem(SelectedItem(this, it.name, counter))
        }

        mButtonBook.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        val alertDialog = dialog.create()
        alertDialog.dismiss()

        dialog.setCancelable(true)
        alertDialog.show()
    }
}
