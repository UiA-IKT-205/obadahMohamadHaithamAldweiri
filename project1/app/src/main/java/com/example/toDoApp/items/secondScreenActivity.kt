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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.item_layout.*
import kotlinx.android.synthetic.main.item_details_activity.*
import kotlinx.android.synthetic.main.item_details_activity.delete


class secondScreenActivity: AppCompatActivity() {
    private val TAG:String = " toDoApp:secondScreenActivity"
    private lateinit var binding: ItemDetailsActivityBinding
    private lateinit var item: item
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val receivedItem = ItemHolder.PickedItem
        val bundle = intent.extras
        val title = bundle?.getString("title")



        val joblist = arrayListOf<String>()
        val adapter = ArrayAdapter<String>(
            this, R.layout.simple_list_item_multiple_choice, joblist
        )


        listView.adapter =  adapter
        adapter.notifyDataSetChanged()
        binding.progressBar.progress = joblist.size


        add.setOnClickListener {

            joblist.add(editText.text.toString())

            listView.adapter =  adapter

            val item= job(editText.text.toString())
            binding.progressBar.progress = joblist.size

            adapter.notifyDataSetChanged()
            addtodatabase2(item, binding.job.text.toString())


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
                "You Selected the following job --> " + joblist.get(i),
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
                    deleteJobsFromDataBase2(joblist.get(item), sublists)
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

        val sublists = binding.job.text.toString()
        val lista = sublists

        getListsFromDataBase2(sublists, joblist)

    }


    private fun deleteJobsFromDataBase2(joblist: String, sublist: String) {
        val db = Firebase.firestore
        val documentreferance = db.collection(sublist).document(joblist).delete()

                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

}


    private fun getListsFromDataBase2(sublist: String, joblist: ArrayList<String>) {
        val docRef = db.collection(sublist)
        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var title = document.get("job")
                    joblist.add(title.toString())
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        binding.progressBar.progress = joblist.size

    }

    private fun addtodatabase2(item: job, title: String){

        val list1 = hashMapOf("job" to item.Details, "ischecked" to false)
        val ref: CollectionReference = db.collection(title)
        ref.document(item.Details).set(list1)
            .addOnSuccessListener { documentReferance -> Log.d(TAG, "document added with id:{$documentReferance.id}")
            }
            .addOnFailureListener{e-> Log.w(TAG, "error could not add document", e )
            }
    }


}