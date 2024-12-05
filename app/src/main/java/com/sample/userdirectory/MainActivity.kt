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
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
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

        adapterUsers = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        lvUsers.adapter = adapterUsers

        userViewModel.userList.observe(this, Observer { users ->
            adapterUsers.clear()
            adapterUsers.addAll(users)
            adapterUsers.notifyDataSetChanged()
        })

        saveB.setOnClickListener {
            val name = nameEditT.text.toString()
            val age = ageEditT.text.toString().toIntOrNull()

            if (name.isNotEmpty() && age != null) {
                val user = User(name, age)
                userViewModel.userList.value?.add(user)
                userViewModel.userList.value = userViewModel.userList.value
                nameEditT.text.clear()
                ageEditT.text.clear()
            } else {
                Toast.makeText(this, "Введите имя и возраст", Toast.LENGTH_SHORT).show()
            }
        }

        lvUsers.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val userToRemove = userViewModel.userList.value?.get(position)
            userViewModel.userList.value?.remove(userToRemove)
            userViewModel.userList.value = userViewModel.userList.value
            Toast.makeText(this, "Пользователь удален", Toast.LENGTH_SHORT).show()
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