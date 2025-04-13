package com.abpvt.todolist

import android.content.Context
import android.util.Log
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {
    private val filename = "listinfo.dat"

    fun writeData(itemList: ArrayList<String>, context: Context) {
        var oas: ObjectOutputStream? = null
        try {
            Log.d("FileHelper", "Writing data: $itemList")
            val fos: FileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            oas = ObjectOutputStream(fos)
            oas.writeObject(itemList)
            Log.i("FileHelper", "Data written successfully")
        } catch (e: IOException) {
            Log.e("FileHelper", "Error writing data: ${e.message}")
            e.printStackTrace() // Print the full stack trace for debugging
        } finally {
            oas?.close()
        }
    }

    fun readData(context: Context): ArrayList<String> {
        var itemList = ArrayList<String>() // Initialize an empty list by default
        var ois: ObjectInputStream? = null
        try {
            val fis: FileInputStream = context.openFileInput(filename)
            ois = ObjectInputStream(fis)
            itemList = ois.readObject() as ArrayList<String>
            Log.i("FileHelper", "Data read successfully: $itemList")
        } catch (e: FileNotFoundException) {
            Log.w("FileHelper", "File not found: ${e.message}")
            // It is correct to return an empty list when the file is not found
        } catch (e: Exception) {
            Log.e("FileHelper", "Error reading data: ${e.message}")
            e.printStackTrace()
        } finally {
            ois?.close()
        }
        return itemList
    }
}