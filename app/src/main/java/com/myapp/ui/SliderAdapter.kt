package com.myapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myapp.homework.R

class SliderAdapter(var context: Context, var thumbnailList: List<String>) : PagerAdapter() {

    lateinit var inflater: LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as LinearLayout

    override fun getCount(): Int  = thumbnailList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image: ImageView
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view:View = inflater.inflate(R.layout.slider_image_item, container, false)
        image = view.findViewById(R.id.iv_slider_item)

        Glide.with(view)
            .load(thumbnailList[position])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(image)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}