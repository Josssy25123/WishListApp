package com.example.wishlistapp


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private val wishlistItems = ArrayList<WishlistItem>()
    private lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WishlistAdapter(wishlistItems)
        recyclerView.adapter = adapter

        // Set up input fields
        val itemNameInput = findViewById<EditText>(R.id.itemName)
        val itemPriceInput = findViewById<EditText>(R.id.itemPrice)
        val itemUrlInput = findViewById<EditText>(R.id.itemURL)
        val addButton = findViewById<Button>(R.id.addButton)
        val emptyTextView = findViewById<TextView>(R.id.emptyTextView)

        checkEmptyState(emptyTextView)

        // Add item to wishlist on button click
        addButton.setOnClickListener {
            val itemName = itemNameInput.text.toString()
            val itemPrice = itemPriceInput.text.toString()
            val itemUrl = itemUrlInput.text.toString()

            if (isInputValid(itemName, itemPrice, itemUrl)) {
                addItemToWishlist(itemName, itemPrice, itemUrl)
                clearInputs(itemNameInput, itemPriceInput, itemUrlInput)
                checkEmptyState(emptyTextView)
            } else{
                // Show an error message or a Toast indicating invalid input
            }

        }

        // Show the empty message if the list becomes empty
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmptyState(emptyTextView)
            }
        })
    }

    private fun isInputValid(name: String, price: String, url: String): Boolean {
        return name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()
    }

    private fun addItemToWishlist(name: String, price: String, url: String) {
        val newItem = WishlistItem(name, price, url)
        wishlistItems.add(newItem)
        adapter.notifyItemInserted(wishlistItems.size - 1)
    }

    private fun clearInputs(vararg inputs: EditText) {
        inputs.forEach { it.text.clear() }
    }

    private fun checkEmptyState(emptyTextView: TextView) {
        emptyTextView.visibility = if (wishlistItems.isEmpty()) View.VISIBLE else View.GONE
    }
}