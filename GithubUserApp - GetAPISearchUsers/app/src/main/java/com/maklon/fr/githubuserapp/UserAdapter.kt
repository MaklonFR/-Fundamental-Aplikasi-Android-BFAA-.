package com.maklon.fr.githubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maklon.fr.githubuserapp.databinding.ItemRowUsersBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private  val list = ArrayList <userItem>()

    fun setList (users: ArrayList<userItem>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    inner class UserViewHolder (val binding: ItemRowUsersBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (user:userItem){
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}