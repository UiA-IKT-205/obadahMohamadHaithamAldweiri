package com.example.toDoApp.items

import android.content.Context
import com.android.volley.RequestQueue
import com.example.toDoApp.items.data.item


class ItemDepositoryManager {

    private lateinit var itemCollection: MutableList<item>


    var onItems: ((List<item>) -> Unit)? = null
    var onItemUpdate: ((item: item) -> Unit)? = null







fun load(url: String, context: Context) {
    itemCollection = mutableListOf(
        item("item1"),
        item("item2"),
        item("item3")
    )

    onItems?.invoke(itemCollection)
}

    fun updateItem(item: item) {
        onItemUpdate?.invoke(item)
    }

    fun addItem(item: item) {
        itemCollection.add(item)
        onItems?.invoke(itemCollection)
    }

    companion object {
        val instance = ItemDepositoryManager()
    }
}
