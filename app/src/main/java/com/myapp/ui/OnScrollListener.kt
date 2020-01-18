package com.myapp.ui

import androidx.recyclerview.widget.RecyclerView
import com.myapp.ui.base.IndexPageActivity

class OnScrollListener(private val mActivity : IndexPageActivity) : RecyclerView.OnScrollListener(){

    val visibleThreshold = 5
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = mActivity.layoutManager.childCount
        totalItemCount = mActivity.layoutManager.itemCount
        firstVisibleItem = mActivity.layoutManager.findFirstVisibleItemPosition()

        /*
            바닥에 도달해서 더이상 스크롤을 할 수 없기 때문에
            데이터를 추가로 수신하고 보여주는 리스트를 업데이트
         */
        if(!mActivity.loading && (totalItemCount - visibleItemCount) <=
            (firstVisibleItem + visibleThreshold)){

            mActivity.viewRegion += 1
            val initialSize = (mActivity.viewRegion-1) * mActivity.loadItemThreshold
            val updatedSize = initialSize + mActivity.loadItemThreshold

            /*
                다음 페이지에 보여질 아이템 수가 부족할 때,
                추가로 다음 페이지의 아이템을 수신
             */
            if(updatedSize > mActivity.page * mActivity.pageThreshold){
                mActivity.page += 1
                mActivity.updateProducts(mActivity.page)
            }

            mActivity.updateItemList(initialSize,updatedSize)
            mActivity.adapter.notifyDataSetChanged()

        }
    }
}