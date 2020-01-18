package com.myapp.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.data.RvItem
import com.myapp.homework.*
import com.myapp.network.model.RetroProducts
import com.myapp.network.RetrofitClientInstance
import com.myapp.service.GetDataService
import com.myapp.ui.OnScrollListener
import com.myapp.ui.RvAdapter
import kotlinx.android.synthetic.main.activity_index_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndexPageActivity : AppCompatActivity() {

    // 메인에 보여질 아이템을 담는 리스트
    var showItemList: ArrayList<RvItem> = ArrayList()
    // 메인에 보여주기 전에 아이템을 미리 담아두는 리스트
    var rvItemList: ArrayList<RvItem>  = ArrayList()

    lateinit var adapter: RvAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var pb_loading:ProgressBar

    // 한 페이지에 보여줄 아이템 수
    val loadItemThreshold = 20
    // 한 페이지에서 받아오는 실제 아이템 데이터 수
    val pageThreshold = 50

    var page = 1
    var viewRegion = 1
    val spanCount = 2

    var loading:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index_page)

        pb_loading = findViewById<ProgressBar>(R.id.pb_loading)

        this.supportActionBar?.hide()

        layoutManager = GridLayoutManager(this,spanCount)
        rv_index.layoutManager = layoutManager

        updateProducts(1)
        updateItemList(0,20)
    }

    /*
        페이지에 해당하는 데이터를 네트워크를 사용해서 수신,
        받은 데이터를 리스트에 삽입
     */
    fun updateProducts(page: Int = 1){
        val service = RetrofitClientInstance.getRetrofitInstance()
            .create(GetDataService::class.java)
        service.getProducts(page).enqueue(object: Callback<RetroProducts> {
            override fun onFailure(call: Call<RetroProducts>, t: Throwable) {
            }

            override fun onResponse(call: Call<RetroProducts>, response: Response<RetroProducts>) {
                val products = response.body()?.body
                for(product in products!!){
                    rvItemList.add(
                        RvItem(
                            product.thumbnail_520,
                            product.title,
                            product.seller,
                            product.id
                        )
                    )
                }
            }
        })
    }

    /*
        Scroll 진행으로 인해 추가적인 아이템을 뷰에
        보여주기 위해 리스트 변수에 아이템을 삽입
     */
    fun updateItemList(start:Int, end:Int) {
        pb_loading.visibility = View.VISIBLE
        loading = true

        Handler().postDelayed({
            if(::adapter.isInitialized){
                adapter.notifyDataSetChanged()
            }else{
                adapter = RvAdapter(this)
                rv_index.adapter = adapter
                rv_index.addOnScrollListener(
                    OnScrollListener(
                        this
                    )
                )
            }

            for(i in start until end){
                showItemList.add(rvItemList[i])
            }

            loading = false
            pb_loading.visibility = View.INVISIBLE
        },5000)
    }

}
