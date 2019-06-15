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
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Handler
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import lottery.com.model.Service
import lottery.com.room.respository.ServiceRepos
import lottery.com.screens.book.BookActivity
import lottery.com.utils.DialogUtils
import java.util.ArrayList

class ServiceActivity : AppCompatActivity(), View.OnClickListener, ServiceItem.Callback, SelectedItem.Callback {

    private var mAdapter: BaseAdapter<Any> = BaseAdapter()

    private var mAdapterSelected: BaseAdapter<Any> = BaseAdapter()

    private var data: MutableList<Service>? = mutableListOf()

    private var serviceRepos: ServiceRepos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_service)
        onInit()
    }

    private fun onInit() {
        val mProgressDialog = DialogUtils.showLoadingDialog(this, this.getString(R.string.loading_data))
        serviceRepos = ServiceRepos(this)
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
                createServicesDialog()

            }

            R.id.mImageViewBack -> {
                finish()
            }
        }
    }

    override fun onServiceItemSelected(value: Service?) {
        serviceRepos?.onAddOrUpdateService(value)
    }

    private fun createServicesDialog() {

        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.dialog_selected_service, null)
        dialog.setView(view)
        val mButtonConsultant = view.findViewById(R.id.mButtonConsultant) as Button
        val mButtonBook = view.findViewById(R.id.mButtonBook) as Button
        val mImageViewClose = view?.findViewById(R.id.mImageViewClose) as ImageView
        val alertDialog = dialog.create()
        val mRecyclerViewSelectedService = view.findViewById(R.id.mRecyclerViewSelectedService) as RecyclerView
        mAdapterSelected = BaseAdapter()
        mRecyclerViewSelectedService.layoutManager = LinearLayoutManager(this)
        mRecyclerViewSelectedService.adapter = mAdapterSelected
        /* Room database */
        serviceRepos?.getAllServices()?.observe(this, Observer { list ->
            data = list
            if (list.isNullOrEmpty()) {
                mAdapterSelected.addItem(SelectedIemEmpty(this))

            } else {
                for ((index, value) in list.withIndex()) {
                    mAdapterSelected.addItem(SelectedItem(this, value, this))
                }
            }
        })
        mButtonConsultant.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(this).create()
            mAlertDialog.setTitle(R.string.waiting_time)
            mAlertDialog.show()

//            object : CountDownTimer(60000, 1000) {
//                override fun onTick(millisUntilFinished: Long) {
//                    mAlertDialog.setMessage("00:" + millisUntilFinished / 1000)
//                }
//
//                override fun onFinish() {
//                    mAlertDialog.dismiss()
//                }
//            }.start()
        }
        mButtonBook.setOnClickListener {

            if (data.isNullOrEmpty()) {
                DialogUtils.showMessageDialog("Bạn phải chọn ít nhất 1 dịch vụ !", this)
            } else {
                val intent = Intent(this, BookActivity::class.java)
                intent.putParcelableArrayListExtra(Constants.Data.DATA, data as ArrayList<out Parcelable>)
                startActivity(intent)
            }
        }

        mImageViewClose.setOnClickListener {
            mAdapter.removeAll()
            mAdapter.notifyDataSetChanged()
            mAdapterSelected.notifyDataSetChanged()
            alertDialog?.dismiss()
            onInit()
        }
        alertDialog?.dismiss()

        alertDialog?.setCancelable(false)
        alertDialog?.show()
    }

    override fun onRemoveItem(item: Service?) {
        serviceRepos?.delete(item?.id!!)
        val pos = item?.let { mAdapterSelected.getPosition(it) }
        pos.let { it?.let { it -> mAdapterSelected.removeItemAt(it) } }

        if (mAdapterSelected.itemCount == 0) {
            mAdapterSelected.addItem(SelectedIemEmpty(this))
        }
    }
}
