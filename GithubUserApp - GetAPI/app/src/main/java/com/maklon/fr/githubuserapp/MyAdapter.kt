package com.maklon.fr.githubuserapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MyAdapter (val context: Context, val userList: List<UsersResponse>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
    var iduser : TextView
    var userName: TextView
    var myImage: ImageView
        init {
            iduser= itemView.findViewById(R.id.tv_item_user)
            userName=itemView.findViewById(R.id.tv_item_name)
            myImage = itemView.findViewById(R.id.img_item_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_row_users,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.iduser.text =userList[position].id.toString()
        holder.userName.text= userList[position].login
        Glide.with(context).load(userList.get(position).avatar)
            .circleCrop()
            .into(holder.myImage)

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}