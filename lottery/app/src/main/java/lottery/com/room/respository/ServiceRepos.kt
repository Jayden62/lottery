package lottery.com.room.respository

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import lottery.com.model.Service
import lottery.com.room.dao.ServiceDao
import lottery.com.room.database.Database
import lottery.com.utils.Constants

class ServiceRepos() {
    companion object {
        val TAG = this.javaClass.simpleName
    }

    private var database: Database? = null
    private var dao: ServiceDao? = null
    private var mProgressDialog: ProgressDialog? = null

    constructor(context: Context) : this() {
        database = Room.databaseBuilder(context, Database::class.java, Constants.Room.db_name).build()
        dao = database?.serviceDao()
        mProgressDialog = ProgressDialog(context)
    }

    @SuppressLint("StaticFieldLeak")
    fun onAddOrUpdateService(service: Service?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        object : AsyncTask<Void, Void, String>() {

            override fun doInBackground(vararg voids: Void): String? {
                service?.let { dao?.onAddOrUpdateService(it) }
                return ""
            }

            override fun onPostExecute(aVoid: String) {
                super.onPostExecute(aVoid)
                result.value = true
            }
        }.execute()
        return result
    }

    @SuppressLint("StaticFieldLeak")
    fun getAllServices(): LiveData<MutableList<Service>>? {
        val liveData = MutableLiveData<MutableList<Service>>()
        object : AsyncTask<Void, Void, MutableList<Service>>() {

            override fun onPreExecute() {
                super.onPreExecute()
                mProgressDialog?.setMessage("Loading data...")
                mProgressDialog?.setCancelable(false)
                mProgressDialog?.show()
            }

            override fun doInBackground(vararg voids: Void): MutableList<Service>? {
                val result: MutableList<Service> = mutableListOf()
                dao?.getAllServices()?.let {
                    result.addAll(it)
                }
                return result
            }

            override fun onPostExecute(result: MutableList<Service>) {
                super.onPostExecute(result)
                liveData.value = result
                if (mProgressDialog?.isShowing!!) {
                    Handler().postDelayed({
                        mProgressDialog?.dismiss()
                    }, 500)
                }
            }
        }.execute()
        return liveData
    }

    @SuppressLint("StaticFieldLeak")
    fun delete(id: Int) {
        object : AsyncTask<Void, Void, Boolean>() {
            override fun doInBackground(vararg voids: Void): Boolean? {
                dao?.delete(id)
                return true
            }

        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteAll() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                dao?.deleteAll()
                return null
            }
        }.execute()
    }
}