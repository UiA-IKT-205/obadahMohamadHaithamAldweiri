package com.example.toDoApp

import android.content.Intent
import android.os.Bundle
import android.util.SparseBooleanArray
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toDoApp.databinding.ActivityMainBinding
import com.example.toDoApp.items.ItemCollectionAdapter
import com.example.toDoApp.items.ItemDepositoryManager
import com.example.toDoApp.items.data.item
import com.example.toDoApp.items.data.item.*
import kotlinx.android.synthetic.main.activity_main.*
import com.example.toDoApp.R.id.progressBar1
import com.example.toDoApp.items.secondScreenActivity


class ItemHolder{

    companion object{

        var PickedItem: item? = null
    }


}

class MainActivity : AppCompatActivity() {
    private val TAG:String = " toDoApp:MainActivity"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemListing.layoutManager = LinearLayoutManager(this)
        binding.itemListing.adapter = ItemCollectionAdapter(mutableListOf<item>(), this::onItemClicked)


        ItemDepositoryManager.instance.onItems = {
            (binding.itemListing.adapter as ItemCollectionAdapter).updateCollection(it as MutableList<item>)
        }

        ItemDepositoryManager.instance.load(getString(R.string.app_name), this)


        binding.saveBt.setOnClickListener {

            val job = binding.job1.text.toString()
            binding.job1.setText("")
            additem(job)

        }


    }
    private fun additem(job: String) {

        val item = item(job)
        ItemDepositoryManager.instance.addItem(item)

    }


    private fun onItemClicked(item: item): Unit {
        ItemHolder.PickedItem = item
        val intent = Intent(this, secondScreenActivity::class.java)
        startActivity(intent)
    }
}