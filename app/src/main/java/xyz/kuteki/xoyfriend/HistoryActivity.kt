package xyz.kuteki.xoyfriend

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val db = DatabaseHelper(this)
        val functions = db.getAllFunctions()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            functions
        )

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
    }
}