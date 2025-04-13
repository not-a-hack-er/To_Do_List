package com.abpvt.todolist

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Properties with proper initialization (or lateinit)
    private lateinit var itemEditText: EditText // Renamed for clarity
    private lateinit var addButton: Button // Renamed for clarity
    private lateinit var itemsListView: ListView // Renamed for clarity
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private val itemList: ArrayList<String> = ArrayList() // Direct initialization
    private val fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // This MUST be called first

        Log.d("MainActivity", "onCreate() called")

        // Initialize views
        itemEditText = findViewById(R.id.editText) // Assuming these IDs exist in activity_main.xml
        addButton = findViewById(R.id.button)
        itemsListView = findViewById(R.id.list)

        // Load data from file
        loadData()

        // Set up adapter
        arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            itemList
        )
        itemsListView.adapter = arrayAdapter

        // Set up button click listener
        addButton.setOnClickListener {
            addItem()
        }

        // Set up item click listener
        itemsListView.setOnItemClickListener { _, _, position, _ ->
            showDeleteConfirmationDialog(position)
        }
        // Window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MainActivity", "onCreate() finished")
    }

    // Helper functions for clarity
    private fun addItem() {
        Log.d("MainActivity", "addItem() called")
        val newItemText = itemEditText.text.toString().trim() // Trim to remove leading/trailing spaces
        if (newItemText.isEmpty()) {
            Log.w("MainActivity", "Attempted to add an empty item")
            Toast.makeText(this, "Please enter an item", Toast.LENGTH_SHORT).show()
            return // Don't add empty items
        }

        itemList.add(newItemText)
        Log.i("MainActivity", "Item added: $newItemText")
        saveData()
        itemEditText.text.clear() // clear() is more efficient than setText("")
        arrayAdapter.notifyDataSetChanged()
        Log.d("MainActivity", "addItem() finished")
    }

    private fun showDeleteConfirmationDialog(position: Int) {
        Log.d("MainActivity", "showDeleteConfirmationDialog() called for position $position")
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Delete")
        alert.setMessage("Do you want to delete this message from the list?")
        alert.setCancelable(false)
        alert.setNegativeButton("NO") { dialog, _ ->
            Log.d("MainActivity", "Delete canceled")
            dialog.cancel()
        }
        alert.setPositiveButton("YES") { _, _ ->
            Log.d("MainActivity", "Deleting item at position $position")
            deleteItem(position)
        }
        alert.create().show()
        Log.d("MainActivity", "showDeleteConfirmationDialog() finished")
    }

    private fun deleteItem(position: Int) {
        val removedItem = itemList.removeAt(position)
        Log.i("MainActivity", "Item deleted: $removedItem")
        saveData()
        arrayAdapter.notifyDataSetChanged()
    }

    private fun loadData() {
        Log.d("MainActivity", "loadData() called")
        val data = fileHelper.readData(this)
        itemList.addAll(data)
        Log.i("MainActivity", "Data loaded. Item list: $itemList")
    }

    private fun saveData() {
        Log.d("MainActivity", "saveData() called")
        fileHelper.writeData(itemList, this)
    }
}