package com.example.toDoApp.items


import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toDoApp.*
import com.example.toDoApp.ItemHolder
import com.example.toDoApp.databinding.ActivityMainBinding
import com.example.toDoApp.databinding.ItemDetailsActivityBinding
import com.example.toDoApp.items.data.*

import kotlinx.android.synthetic.main.item_layout.*
import kotlinx.android.synthetic.main.item_details_activity.*
import kotlinx.android.synthetic.main.item_details_activity.delete


class secondScreenActivity: AppCompatActivity() {
    private val TAG:String = " toDoApp:secondScreenActivity"
    private lateinit var binding: ItemDetailsActivityBinding
    private lateinit var item: item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedItem = ItemHolder.PickedItem


        val joblist = arrayListOf<String>()
        val adapter = ArrayAdapter<String>(
            this, R.layout.simple_list_item_multiple_choice, joblist
        )



        add.setOnClickListener {

            joblist.add(editText.text.toString())

            listView.adapter =  adapter

            val item= job(editText.text.toString())
            binding.progressBar.progress = joblist.size

            adapter.notifyDataSetChanged()



            editText.text.clear()
        }

        clear.setOnClickListener {

            joblist.clear()
            adapter.notifyDataSetChanged()
            binding.progressBar.progress = joblist.size

        }

        listView.setOnItemClickListener { adapterView,
                                          view, i, l ->
            android.widget.Toast.makeText(
                this,
                "You Selected the item --> " + joblist.get(i),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        delete.setOnClickListener {
            val sublists = binding.job.text.toString()
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter.remove(joblist.get(item))


                }
                item--
            }

            binding.progressBar.progress = joblist.size
            position.clear()
            adapter.notifyDataSetChanged()


        }

        if(receivedItem != null){
            item = receivedItem
            Log.i("Details view", receivedItem.toString())
        } else{

            setResult(RESULT_CANCELED, Intent().apply {
            })

            finish()
        }

        binding.job.text = item.job





    }



}