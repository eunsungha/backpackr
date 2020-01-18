package com.myapp.ui.base

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_detail_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

import android.transition.*
import android.widget.FrameLayout
import com.myapp.homework.*
import com.myapp.network.model.RetroDetailProduct
import com.myapp.network.RetrofitClientInstance
import com.myapp.service.GetDataService
import com.myapp.ui.SliderAdapter


class DetailPageActivity : AppCompatActivity(){

    lateinit var thumbnailList:ArrayList<String>
    lateinit var adapter:PagerAdapter

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, R.anim.slide_down)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_page)
        this.supportActionBar?.hide()

        bt_close.setOnClickListener(btCloseOnClickListner())
        supportPostponeEnterTransition()

        val id  = intent.getIntExtra("id",0)
        updateDetailProduct(id)

    }

    private fun btCloseOnClickListner() = View.OnClickListener{
        onBackPressed()
    }

    /*
        네트워크에서 제품 상세정보를 받아서 뷰를 구성
     */
    private fun updateDetailProduct(id: Int){
        val service = RetrofitClientInstance.getRetrofitInstance()
            .create(GetDataService::class.java)
        service.getDetailProduct(id).enqueue(object: Callback<RetroDetailProduct> {
            override fun onFailure(call: Call<RetroDetailProduct>, t: Throwable) {
            }

            override fun onResponse(call: Call<RetroDetailProduct>, response: Response<RetroDetailProduct>) {
                val responseBody = response.body()?.body
                val detail = responseBody!![0]

                thumbnailList = ArrayList(detail.thumbnail_list_320.split('#'))
                adapter = SliderAdapter(this@DetailPageActivity, thumbnailList)

                vp_slider.apply {
                    adapter = adapter
                    addOnPageChangeListener(
                        PageListener(
                            this@DetailPageActivity
                        )
                    )
                    transitionName="imageTransition"
                }

                pb_slider.apply{
                    progressDrawable.setColorFilter(
                        Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
                    max = thumbnailList.size
                    progress = 1
                }

                tv_detail_seller.text = detail.seller
                tv_detail_title.text = detail.title

                tv_discount_rate.text = detail.discount_rate
                tv_discount_cost.text = detail.discount_cost

                tv_cost.text = detail.cost
                tv_cost.paintFlags = tv_discount_cost.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                tv_description.text = detail.description + getString(R.string.addNewLine)

                supportStartPostponedEnterTransition()
            }
        })
    }


    /*
        이미지를 스와이핑하면 프로그레스바를 한단계씩 증가시킬수 있게
        Listener 사용
     */
    class PageListener(val mActivity: DetailPageActivity) : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            val pbar:ProgressBar = mActivity.findViewById(R.id.pb_slider)
            pbar.progress = position + 1
        }
    }
}