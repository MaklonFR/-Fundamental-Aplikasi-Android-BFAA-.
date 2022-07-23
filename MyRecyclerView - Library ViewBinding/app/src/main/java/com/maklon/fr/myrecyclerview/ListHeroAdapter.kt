package com.maklon.fr.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maklon.fr.myrecyclerview.databinding.ItemRowHeroBinding

class ListHeroAdapter(private val listHero:ArrayList<Hero>) : RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    //membuat fungsi onclickItem RecyclerView
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

   override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
       val binding = ItemRowHeroBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
       //val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo) = listHero[position]

        Glide.with(holder.binding.cardView.context)
            .load(photo) // URL Gambar
            .circleCrop() // Mengubah image menjadi lingkaran
            .into(holder.binding.imgItemPhoto) // imag

        //holder.imgPhoto.setImageResource(photo)
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDescription.text = description

        //set OnclickListener RecyclerView
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHero[holder.adapterPosition]) }
        /*  holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Kamu melilih " +listHero[holder.adapterPosition].name,Toast.LENGTH_SHORT).show()
        }*/
    }


    override fun getItemCount():  Int = listHero.size

       class ListViewHolder(var binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root){
            //var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
            //var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
            //var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Hero)
    }

}