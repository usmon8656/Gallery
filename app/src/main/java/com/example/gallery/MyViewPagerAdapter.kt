package com.example.gallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.databinding.ItemViewPagerBinding
import com.example.gallery.models.MyImage

class MyViewPagerAdapter (val list: ArrayList<MyImage> = ArrayList()): RecyclerView.Adapter<MyViewPagerAdapter.Vh>() {

        inner class Vh(val rvItem:ItemViewPagerBinding): RecyclerView.ViewHolder(rvItem.root){
            fun onBind(contact: MyImage){
                rvItem.tvName.text = contact.name
                rvItem.imageItem.setVideoURI(Uri.parse(contact.imageURI))
                rvItem.imageItem.start()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
            return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: Vh, position: Int) {
            holder.onBind(list[position])
        }

}