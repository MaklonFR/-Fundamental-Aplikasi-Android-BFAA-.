package com.maklon.fr.githubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maklon.fr.githubuserapp.databinding.ItemRowUsersBinding
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: Any) {
        this.onItemClickCallback = onItemClickCallback as OnItemClickCallback
    }

    private var list = ArrayList<UserItem>()
    private var listFilter = ArrayList<UserItem>()

    fun setList(users: ArrayList<UserItem>) {
        list.clear()
        listFilter.clear()
        list.addAll(users)
        listFilter.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
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
    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (p0 == null || p0.length < 0) {
                    filterResults.count = listFilter.size
                    filterResults.values = filterResults
                } else {
                    val searchChr = p0.toString().lowercase(Locale.getDefault())
                    val itemUserModal = ArrayList<UserItem>()
                    for (item in itemUserModal) {
                        if (item.login.contains(searchChr)) {
                            itemUserModal.add(item)
                        }
                    }
                    filterResults.count = itemUserModal.size
                    filterResults.values = itemUserModal
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                list = filterResults!!.values as ArrayList<UserItem>
                notifyDataSetChanged()
            }


        }
    }
}



