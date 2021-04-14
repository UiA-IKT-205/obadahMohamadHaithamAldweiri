package com.example.toDoApp.items


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.toDoApp.R
import com.example.toDoApp.databinding.ItemLayoutBinding
import com.example.toDoApp.items.data.item
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_details_activity.view.*
import kotlinx.android.synthetic.main.item_layout.view.*

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


        holder.apply {
            binding.delete.setOnClickListener{
                val item1 = items[position]
                items.remove(item1)

                deleteListsFromDataBase(item.job)
                notifyDataSetChanged()

            }

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }




    public fun updateCollection(newItems:MutableList<item>){
        items = newItems
        notifyDataSetChanged()

    }



    private fun deleteListsFromDataBase(doc: String) {
        val db = Firebase.firestore
        val docRef = db.collection("lists")
        docRef.document(doc).delete()


    }


}