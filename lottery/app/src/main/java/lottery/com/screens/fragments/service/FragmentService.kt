package lottery.com.screens.fragments.service


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
import lottery.com.screens.fragments.subservice.SubServiceActivity

class FragmentService : Fragment(), ServiceItem.Callback {

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
        mAdapter.addItem(
            ServiceItem(
                context!!,
                "Tẩy trắng răng",
                "Cô dâu xinh đẹp trong ngày vui của Hùng Dũng là Triệu Mộc Trinh, 22 tuổi, quê ở huyện Hàm Yên (tỉnh Tuyên Quang)",
                this
            )
        )
    }

    override fun onTapItem() {
        startActivity(Intent(context, SubServiceActivity::class.java))
    }
}
