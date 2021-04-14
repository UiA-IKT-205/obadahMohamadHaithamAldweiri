package com.example.toDoApp

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toDoApp.databinding.ActivityMainBinding
import com.example.toDoApp.items.ItemCollectionAdapter
import com.example.toDoApp.items.ItemDepositoryManager
import com.example.toDoApp.items.data.item


import com.example.toDoApp.items.secondScreenActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.ktx.firestore
import kotlinx.android.synthetic.main.item_details_activity.*


class ItemHolder{

    companion object{

        var PickedItem: item? = null
    }
}

class MainActivity : AppCompatActivity() {
    private val TAG:String = " toDoApp:MainActivity"
    private lateinit var binding: ActivityMainBinding

    private lateinit var auth:FirebaseAuth
    val db = Firebase.firestore

    private fun sendtodatabase(list: String) {
        val title = list
        val lista = hashMapOf("list_title" to title)

        val ref: CollectionReference = db.collection("lists")
        ref.document(title).set(lista)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        signInAnonymously()




        binding.itemListing.layoutManager = LinearLayoutManager(this)
        binding.itemListing.adapter = ItemCollectionAdapter(mutableListOf<item>(), this::onItemClicked)

        val holder = ItemDepositoryManager.instance
        getListsFromDataBase(holder)


        ItemDepositoryManager.instance.onItems = {
            (binding.itemListing.adapter as ItemCollectionAdapter).updateCollection(it as MutableList<item>)
        }

        ItemDepositoryManager.instance.load(getString(R.string.app_name), this)




        binding.saveBt.setOnClickListener {
            val job = binding.job1.text.toString()

            sendtodatabase(job)

            binding.job1.setText("")

            additem(job)

            val title = binding.job1.text
            val intent = Intent(this, secondScreenActivity::class.java)
            intent.putExtra("title", job)
        }


    }

    private fun signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "login fungerte ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e(TAG, "loging fungerte ikke ", it)
        }

    }


    private fun getListsFromDataBase(holder:ItemDepositoryManager) {
        val docRef = db.collection("lists")
        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var title = document.get("list_title")
                    var list = item(title.toString())

                    holder.addItem(list)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
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