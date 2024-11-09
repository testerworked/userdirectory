package com.sample.userdirectory

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    /**
     * Программа - Каталог Пользователей
     */

    private val userListSample = mutableListOf<User>()
    private lateinit var adapterUsers: ArrayAdapter<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        title = "Каталог пользователей"

        val nameEditT = findViewById<EditText>(R.id.nameET)
        val ageEditT = findViewById<EditText>(R.id.ageET)
        val saveB = findViewById<Button>(R.id.bSave)
        val lvUsers = findViewById<ListView>(R.id.listViewUsers)

        adapterUsers = ArrayAdapter(this, android.R.layout.simple_list_item_1, userListSample)
        lvUsers.adapter = adapterUsers

        saveB.setOnClickListener {
            val name = nameEditT.text.toString()
            val age = ageEditT.text.toString().toIntOrNull()

            if (name.isNotEmpty() && age != null) {
                val user = User(name, age)
                userListSample.add(user)
                adapterUsers.notifyDataSetChanged()
                nameEditT.text.clear()
                ageEditT.text.clear()
            } else {
                Toast.makeText(this, "Введите имя и возраст", Toast.LENGTH_SHORT).show()
            }
        }

        lvUsers.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val note = adapterUsers.getItem(position)
            adapterUsers.remove(note)
            adapterUsers.notifyDataSetChanged()
            Toast.makeText(this, "Пользователь удален", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                Toast.makeText(this, "Выход из программы", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}