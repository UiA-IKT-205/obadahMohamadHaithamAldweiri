package com.example.toDoApp.items


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toDoApp.databinding.ItemLayoutBinding
import com.example.toDoApp.items.data.item

class ItemCollectionAdapter(private var items:MutableList<item>,
                            private val onItemClicked:
                                (item) -> Unit) : RecyclerView.Adapter<ItemCollectionAdapter.ViewHolder>()
{
    class ViewHolder(val binding:ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: item, onItemClicked:(item) -> Unit) {

            binding.job.text = item.job

            binding.card.setOnClickListener {
                onItemClicked(item)
            }
        }
    }







    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item,onItemClicked)




    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }




    public fun updateCollection(newItems:MutableList<item>){
        items = newItems
        notifyDataSetChanged()
    }





}