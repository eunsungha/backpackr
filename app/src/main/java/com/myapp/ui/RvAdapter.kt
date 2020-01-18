package com.myapp.ui

import android.app.ActivityOptions
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myapp.data.RvItem
import com.myapp.homework.R
import com.myapp.ui.base.DetailPageActivity
import com.myapp.ui.base.IndexPageActivity
import kotlinx.android.synthetic.main.rv_item.view.*
import java.lang.Exception


class RvAdapter(private val mActivity: IndexPageActivity) : RecyclerView.Adapter<RvAdapter.RvViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        return RvViewHolder(
            LayoutInflater.from(mActivity).inflate(
                R.layout.rv_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mActivity.showItemList.size
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {

        val item = mActivity.showItemList[position]
        val listener = View.OnClickListener {

            val intent = Intent(mActivity, DetailPageActivity::class.java)
            intent.putExtra("id",item.id)

            val thumbnail: ImageView = mActivity.findViewById(R.id.iv_thumbnail)
            val mPair = Pair<View,String>(thumbnail,"imageTransition")

            val options:ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                mActivity, mPair)

            startActivity(mActivity, intent, options.toBundle())

        }
        holder.apply{
            bind(listener,item)
        }
    }

    class RvViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: RvItem){

            Glide.with(view)
                .load(item.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view.iv_thumbnail)

            view.iv_thumbnail.clipToOutline = true
            view.tv_title.text =  item.title
            view.tv_seller.text = item.seller
            view.setOnClickListener(listener)
        }
    }
}